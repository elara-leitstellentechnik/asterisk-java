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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;


/**
 * Default implementation of the SocketConnectionFacade interface using java.io.
 *
 * @author srt
 * @version $Id$
 */
public class SocketConnectionFacadeImpl implements SocketConnectionFacade
{
    public static final Pattern CRNL_PATTERN = Pattern.compile("\r\n");
    public static final Pattern NL_PATTERN = Pattern.compile("\n");
	private NioSocket nioSocket;
    private Socket socket;
	private BufferedReaderCrLfOnly scanner;
    private BufferedWriter writer;
    private Trace trace;

	/**
     * Creates a new instance for use with the Manager API that uses CRNL ("\r\n") as line delimiter.
     *
     * @param host        the foreign host to connect to.
     * @param port        the foreign port to connect to.
     * @param ssl         <code>true</code> to use SSL, <code>false</code> otherwise.
     * @param timeout     0 incidcates default
     * @param readTimeout see {@link Socket#setSoTimeout(int)}
     * @param encoding    the encoding used for transmission of strings
     * @throws IOException if the connection cannot be established.
     */
    public SocketConnectionFacadeImpl(String host, int port, boolean ssl, int timeout, int readTimeout, Charset encoding) throws IOException
    {
        this(host, port, ssl, timeout, readTimeout, encoding, CRNL_PATTERN);
    }

    /**
     * Creates a new instance for use with the Manager API that uses the given line delimiter.
     *
     * @param host        the foreign host to connect to.
     * @param port        the foreign port to connect to.
     * @param ssl         <code>true</code> to use SSL, <code>false</code> otherwise.
     * @param timeout     0 incidcates default
     * @param readTimeout see {@link Socket#setSoTimeout(int)}
     * @param encoding    the encoding used for transmission of strings
     * @param lineDelimiter a {@link Pattern} for matching the line delimiter for the socket
     * @throws IOException if the connection cannot be established.
     */
    public SocketConnectionFacadeImpl(String host, int port, boolean ssl, int timeout, int readTimeout, Charset encoding, Pattern lineDelimiter) throws IOException
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
     * Creates a new instance for use with FastAGI that uses NL ("\n") as line delimiter.
     *
     * @param socket the underlying socket.
     * @throws IOException if the connection cannot be initialized.
     */
    SocketConnectionFacadeImpl(Socket socket) throws IOException {
	    socket.setSoTimeout(MAX_SOCKET_READ_TIMEOUT_MILLIS);
	    initialize(socket, StandardCharsets.UTF_8, NL_PATTERN, socket.getInputStream(), socket.getOutputStream());
    }

	/** 70 mi = 70 * 60 * 1000 */
	private static final int MAX_SOCKET_READ_TIMEOUT_MILLIS = 4200000;


    private void initialize(Socket socket, Charset encoding, Pattern pattern, InputStream inputStream, OutputStream outputStream) throws IOException
    {
        this.socket = socket;

        this.scanner = new BufferedReaderCrLfOnly(new InputStreamReader(inputStream, encoding));
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream, encoding));
    }

    @Override
    public String readLine() throws IOException
    {
	    String line;
	    line = scanner.readLine();

        if (trace != null)
        {
            trace.received(line);
        }
        return line;
    }

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
        if (trace != null){
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
