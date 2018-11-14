/*
 *  Copyright 2004-2006 Stefan Reuter
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.asteriskjava.util.internal;

import org.asteriskjava.util.SocketConnectionFacade;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Default implementation of the SocketConnectionFacade interface using java.io.
 *
 * @author srt
 * @version $Id$
 */
public class SocketConnectionFacadeImpl implements SocketConnectionFacade
{
	/** 8 MB */
	private static final int BUFFER_LIMIT = 8 * 1024 * 1024;
    public static final Pattern CRNL_PATTERN = Pattern.compile("\r\n");
    public static final Pattern NL_PATTERN = Pattern.compile("\n");
	private NioSocket nioSocket;
    private Socket socket;
    private Charset encoding;
    private InputStream scanner;
    private BufferedWriter writer;
    private Trace trace;

	/**
	 * Creates a new instance for use with the Manager API that uses CRNL ("\r\n") as line delimiter.
     *
     * @param host the foreign host to connect to.
     * @param port the foreign port to connect to.
     * @param ssl <code>true</code> to use SSL, <code>false</code> otherwise.
     * @param timeout 0 incidcates default
     * @param readTimeout see {@link Socket#setSoTimeout(int)}
     * @throws IOException if the connection cannot be established.
     */
    public SocketConnectionFacadeImpl(String host, int port, boolean ssl, int timeout, int readTimeout) throws IOException
    {
        this(host, port, ssl, timeout, readTimeout, StandardCharsets.UTF_8, CRNL_PATTERN);
    }

    /**
     * Creates a new instance for use with the Manager API that uses the given
     * encoding and CRNL ("\r\n") as line delimiter.
     *
     * @param host the foreign host to connect to.
     * @param port the foreign port to connect to.
     * @param ssl <code>true</code> to use SSL, <code>false</code> otherwise.
     * @param timeout 0 incidcates default
     * @param readTimeout see {@link Socket#setSoTimeout(int)}
     * @param encoding the encoding used for transmission of strings (all
     *            connections should use the same encoding)
     * @throws IOException if the connection cannot be established.
     */
    public SocketConnectionFacadeImpl(String host, int port, boolean ssl, int timeout, int readTimeout, Charset encoding)
            throws IOException
    {
        this(host, port, ssl, timeout, readTimeout, encoding, CRNL_PATTERN);
    }

    /**
     * Creates a new instance for use with the Manager API that uses UTF-8 as
     * encoding and the given line delimiter.
     *
     * @param host the foreign host to connect to.
     * @param port the foreign port to connect to.
     * @param ssl <code>true</code> to use SSL, <code>false</code> otherwise.
     * @param timeout 0 incidcates default
     * @param readTimeout see {@link Socket#setSoTimeout(int)}
     * @param lineDelimiter a {@link Pattern} for matching the line delimiter
     *            for the socket
     * @throws IOException if the connection cannot be established.
     */
    public SocketConnectionFacadeImpl(String host, int port, boolean ssl, int timeout, int readTimeout,
            Pattern lineDelimiter) throws IOException
    {
        this(host, port, ssl, timeout, readTimeout, StandardCharsets.UTF_8, lineDelimiter);
    }

    /**
     * Creates a new instance for use with the Manager API that uses the given
     * encoding and line delimiter.
     *
     * @param host the foreign host to connect to.
     * @param port the foreign port to connect to.
     * @param ssl <code>true</code> to use SSL, <code>false</code> otherwise.
     * @param timeout 0 incidcates default
     * @param readTimeout see {@link Socket#setSoTimeout(int)}
     * @param encoding the encoding used for transmission of strings (all
     *            connections should use the same encoding)
     * @param lineDelimiter a {@link Pattern} for matching the line delimiter
     *            for the socket
     * @throws IOException if the connection cannot be established.
     */
    public SocketConnectionFacadeImpl(String host, int port, boolean ssl, int timeout, int readTimeout, Charset encoding,
            Pattern lineDelimiter) throws IOException
    {
	    Socket socket;

	    if (ssl)
	    {
		    throw new UnsupportedOperationException("SSL sockets are disabled for ELARA");
	    }
	    else {
            nioSocket = new NioSocket(timeout, timeout, readTimeout);
            nioSocket.connect(new InetSocketAddress(host, port));
            socket = nioSocket.getSocket();
        }

	    initialize(socket, encoding, lineDelimiter, nioSocket.getInputStream(), nioSocket.getOutputStream());
	    if (System.getProperty(Trace.TRACE_PROPERTY, "false").equalsIgnoreCase("true")) {
		    trace = new FileTrace(socket);
	    }
    }

    /**
     * Creates a new instance for use with FastAGI that uses NL ("\n") as line
     * delimiter.
     *
     * @param socket the underlying socket.
     * @throws IOException if the connection cannot be initialized.
     */
    SocketConnectionFacadeImpl(Socket socket) throws IOException
    {
        socket.setSoTimeout(MAX_SOCKET_READ_TIMEOUT_MILLIS);
        initialize(socket, StandardCharsets.UTF_8, NL_PATTERN, socket.getInputStream(), socket.getOutputStream());
    }

    /** 70 mi = 70 * 60 * 1000 */
    private static final int MAX_SOCKET_READ_TIMEOUT_MILLIS = 4200000;

    private void initialize(Socket socket, Charset encoding, Pattern pattern, InputStream inputStream, OutputStream outputStream) throws IOException
    {
        this.socket = socket;

	    this.encoding = encoding;
	    this.scanner = inputStream;
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream, encoding));
    }

    @Override
    public void activateGZIP() throws IOException
    {
	    if (scanner instanceof GZIPInputStream) {
		    throw new IllegalStateException("GZIP is active already");
	    }

	    // rescue unprocessed bytes from `buf` ?!
	    if (end - off > 0) {
		    byte[] remaining = Arrays.copyOfRange(this.buf, off, end);
		    off = end = 0;
		    scanner = new SequenceInputStream(new ByteArrayInputStream(remaining), scanner);
	    }

	    scanner = new GZIPInputStream(scanner);
    }

	private byte[] buf = new byte[512];
	private int off = 0;
	private int end = 0;

	@Override
    public String readLine() throws IOException
    {
	    String line;
	    int posCrLf = off;
	    loop:
	    while (true) {
		    assert end <= buf.length;

		    for (; posCrLf < end - 1; posCrLf++) {
			    if (buf[posCrLf] == '\r' && buf[posCrLf + 1] == '\n') {
				    line = new String(buf, off, posCrLf - off, encoding);
				    off = posCrLf + 2;
				    break loop;
			    }
		    }

		    // ensure free capacity
		    if (off == end) {
		    	// reset if empty
		    	off = end = posCrLf = 0;
		    } else if (end == buf.length) {
			    if (off == 0) {
				    // enlarge buffer
				    int newLength = buf.length * 2;
				    if (newLength > BUFFER_LIMIT) {
					    throw new IllegalStateException();
				    }
				    buf = Arrays.copyOfRange(buf, 0, newLength);
			    } else {
				    // move to the left
				    end -= off;  // new end after moving (aka length)
				    posCrLf -= off;
				    System.arraycopy(buf, off, buf, 0, end);
				    off = 0;
			    }
		    }
		    assert end < buf.length;

		    int read = scanner.read(buf, end, buf.length - end);
		    if (read < 0) {
			    return null;
		    }
		    end += read;
	    }

        if (trace != null)
        {
            trace.received(line);
        }
        return line;
    }

    @Override
    public void write(String s) throws IOException
    {
        writer.write(s);
        if (trace != null)
        {
            trace.sent(s);
        }
    }

    @Override
    public void flush() throws IOException
    {
        writer.flush();
    }

    @Override
    public void close() throws IOException
    {
	    if (nioSocket != null) {
		    nioSocket.close();
	    }
        socket.close();
	    scanner.close();
        // close the trace only if it was activated (the object is not null)
        if (trace != null)
        {
            trace.close();
        }
    }

    @Override
    public boolean isConnected()
    {
        return socket.isConnected();
    }

    @Override
    public InetAddress getLocalAddress()
    {
        return socket.getLocalAddress();
    }

    @Override
    public int getLocalPort()
    {
        return socket.getLocalPort();
    }

    @Override
    public InetAddress getRemoteAddress()
    {
        return socket.getInetAddress();
    }

    @Override
    public int getRemotePort()
    {
        return socket.getPort();
    }
}
