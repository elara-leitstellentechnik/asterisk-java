package org.asteriskjava.util.internal;

import org.asteriskjava.util.Log;
import org.asteriskjava.util.LogFactory;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NioSocket implements AutoCloseable {

	private static final Log LOGGER = LogFactory.getLog(NioSocket.class);
	private static final boolean DEBUG = LOGGER.isDebugEnabled();

	private final int connectTimeout;

	private final SocketChannel channel;
	private final Selector selectorRead;
	private final Selector selectorWrite;
	private final OutputStream outputStream;
	private final InputStream inputStream;

	public NioSocket(final int connectTimeout, final int writeTimeout, final int readTimeout) throws IOException {
		this.connectTimeout = connectTimeout;
		try {
			channel = SocketChannel.open();
			log("+");
			channel.configureBlocking(false);
			selectorRead = Selector.open();
			selectorWrite = Selector.open();
			inputStream = getInputStream(readTimeout);
			outputStream = getOutputStream(writeTimeout);
		} catch (Throwable e) {
			closeSilently();
			throw e;
		}
	}

	private InputStream getInputStream(final int readTimeout) throws IOException {
		return new InputStream() {
			private final SelectionKey key = channel.register(selectorRead, SelectionKey.OP_READ);

			@Override
			public int read() throws IOException {
				byte[] bytes = new byte[1];
				for (; ; ) {
					int len = read(bytes);
					switch (len) {
						case 1:
							return bytes[0] & 0xff;
						case 0:
							continue;
						case -1:
							return -1;
						default:
							throw new AssertionError("Invalid length: " + len);
					}
				}
			}

			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				try {
					long millis = System.currentTimeMillis();
					int read;
					try {
						key.selector().select(readTimeout);
						if (DEBUG) {
							millis = System.currentTimeMillis() - millis;
							log("< " + (!key.isValid() ? "invalid" : key.isReadable()) + " " + millis);
						}
						if (!key.isValid()) {
							if (selectorRead.isOpen()) {
								return -1;
							} else {
								throw new EOFException();
							}
						}
						read = channel.read(ByteBuffer.wrap(b, off, len));
					} catch (IllegalStateException e) {
						if (selectorRead.isOpen()) {
							return -1;
						} else {
							EOFException ee = new EOFException();
							ee.initCause(e);
							throw ee;
						}
					}
					if (DEBUG) {
						log("< " + read);
					}
					if (read == 0 && readTimeout > 0) {
						throw new SocketTimeoutException("recv buffer was empty for " + readTimeout + "ms");
					}
					return read;
				} catch (IOException e) {
					log(e);
					closeSilently();
					throw e;
				}
			}
		};
	}

	private OutputStream getOutputStream(final int writeTimeout) throws IOException {
		return new OutputStream() {
			private final SelectionKey key = channel.register(selectorWrite, SelectionKey.OP_WRITE);

			@Override
			public void write(int b) throws IOException {
				write(new byte[]{(byte) b});
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				try {
					ByteBuffer buffer = ByteBuffer.wrap(b);
					buffer.position(off);
					buffer.limit(off + len);
					while (buffer.hasRemaining()) {
						try {
							int write = channel.write(buffer);
							if (DEBUG) {
								log("> " + write);
							}
							if (write == 0) {
								// nothing written? => wait for channel!
								long millis = !DEBUG ? 0 : System.currentTimeMillis();
								key.selector().select(writeTimeout);
								if (DEBUG) {
									millis = System.currentTimeMillis() - millis;
									log("> " + (!key.isValid() ? "invalid" : key.isWritable()) + " " + millis);
								}
								if (!key.isValid()) {
									throw new EOFException();
								}
								if (!key.isWritable()) {
									throw new SocketTimeoutException("send buffer was full for " + writeTimeout + "ms");
								}
							}
						} catch (IllegalStateException e) {
							EOFException ee = new EOFException();
							ee.initCause(e);
							throw ee;
						}
					}
				} catch (IOException e) {
					log(e);
					closeSilently();
					throw e;
				}
			}
		};
	}

	public void connect(SocketAddress endpoint) throws IOException {
		try {
			channel.connect(endpoint);
			try (Selector selector = Selector.open()) {
				SelectionKey key = channel.register(selector, SelectionKey.OP_CONNECT);
				selector.select(connectTimeout);
				if (!key.isConnectable()) {
					throw new SocketTimeoutException();
				}
				channel.finishConnect();
			}
		} catch (Throwable e) {
			closeSilently();
			throw e;
		}
	}

	public InputStream getInputStream() throws IOException {
		return inputStream;
	}

	public OutputStream getOutputStream() throws IOException {
		return outputStream;
	}

	@Override
	public void close() throws IOException {
		log("-");
		if (channel != null) {
			channel.close();
		}
		if (selectorRead != null && selectorRead.isOpen()) {
			selectorRead.close();
		}
		if (selectorWrite != null && selectorWrite.isOpen()) {
			selectorWrite.close();
		}
	}

	private void closeSilently() {
		try {
			close();
		} catch (IOException ignored) {
		}
	}

	public Socket getSocket() {
		return channel.socket();
	}

	private void log(Object obj) {
		if (DEBUG) {
			LOGGER.debug(System.identityHashCode(channel.socket()) + " " + obj);
		}
	}

}
