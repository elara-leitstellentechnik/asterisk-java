package org.asteriskjava.util.internal;

import org.asteriskjava.util.Log;
import org.asteriskjava.util.LogFactory;

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

	private final int connectTimeout;
	private final SocketChannel channel;
	private final OutputStream outputStream;
	private final InputStream inputStream;

	public NioSocket(final int connectTimeout, final int writeTimeout, final int readTimeout) throws IOException {
		this.connectTimeout = connectTimeout;

		channel = SocketChannel.open();
		channel.configureBlocking(false);

		inputStream = new InputStream() {
			private final SelectionKey key = channel.register(Selector.open(), SelectionKey.OP_READ);

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
					key.selector().select(readTimeout);
					millis = System.currentTimeMillis() - millis;
					LOGGER.debug("< " + key.isReadable() + " " + millis);
					if (!key.isReadable()) {
						throw new SocketTimeoutException("recv buffer was empty for " + readTimeout + "ms");
					}
					ByteBuffer buffer = ByteBuffer.wrap(b);
					buffer.position(off);
					buffer.limit(off + len);
					int read = channel.read(buffer);
					LOGGER.debug("< " + read);
					return read;
				} catch (IOException e) {
					throw e;
				}
			}
		};

		outputStream = new OutputStream() {
			private final SelectionKey key = channel.register(Selector.open(), SelectionKey.OP_WRITE);

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
						int write = channel.write(buffer);
						LOGGER.debug("> " + write);
						if (write == 0) {
							// nothing written? => wait for channel!
							long millis = System.currentTimeMillis();
							key.selector().select(writeTimeout);
							millis = System.currentTimeMillis() - millis;
							boolean writable = key.isWritable();
							LOGGER.debug("> " + writable + " " + millis);
							if (!writable) {
								throw new SocketTimeoutException("send buffer was full for " + writeTimeout + "ms");
							}
						}
					}
				} catch (IOException e) {
					LOGGER.debug(e);
					closeSilently();
					throw e;
				}
			}
		};
	}

	public void connect(SocketAddress endpoint) throws IOException {
		try {
			channel.connect(endpoint);

			Selector selector = Selector.open();
			SelectionKey key = channel.register(selector, SelectionKey.OP_CONNECT);
			selector.select(connectTimeout);
			if (!key.isConnectable()) {
				throw new SocketTimeoutException();
			}
			channel.finishConnect();
			selector.close();
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
		LOGGER.debug("!");
		channel.close();
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
}
