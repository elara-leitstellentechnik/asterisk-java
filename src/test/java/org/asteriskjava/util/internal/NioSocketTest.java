package org.asteriskjava.util.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author sere
 * @since 2017-10-25
 */
public class NioSocketTest {
	public static void main(String[] args) throws IOException {
		NioSocket socket = new NioSocket(1000, 1000, 1000);
		socket.connect(new InetSocketAddress("entwicklervm01", 8000));

		InputStream in = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		Scanner scanner = new Scanner(reader);
		scanner.useDelimiter(Pattern.compile("\r\n"));
		for (; ; ) {
			reader.readLine();
		}
	}

	public static void main1(String[] args) throws IOException {
		NioSocket socket = new NioSocket(10000, 10000, 10000);
		socket.connect(new InetSocketAddress("192.168.178.39", 8000));

		byte[] bytes = new Date().toString().getBytes(StandardCharsets.US_ASCII);
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length * 10000);
		while (buffer.hasRemaining()) {
			buffer.put(bytes);
		}
		buffer.flip();

		for (; ; ) {
			socket.getOutputStream().write(buffer.array());
		}
	}

	public static void main2(String[] args) throws IOException {
		// Create client SocketChannel
		SocketChannel client = SocketChannel.open();

		System.out.println("Channel: " + Integer.toBinaryString(client.validOps()));

		// nonblocking I/O
		client.configureBlocking(false);

		// Create selector
		Selector selector = Selector.open();

		// Record to selector (OP_CONNECT type)
		SelectionKey clientKey = client.register(selector, client.validOps());

		// Connection to host port 8000
		client.connect(new InetSocketAddress("192.168.178.39", 8000));

		byte[] bytes = new Date().toString().getBytes(StandardCharsets.US_ASCII);
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length * 10000);
		while (buffer.hasRemaining()) {
			buffer.put(bytes);
		}

		// Waiting for the connection
		while (selector.select(1500) > 0) {
			// Get keys
			Set<SelectionKey> keys = selector.selectedKeys();

			// For each key...
			for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext(); ) {
				SelectionKey key = i.next();

				// Remove the current key
				i.remove();

				// Get the socket channel held by the key
				SocketChannel channel = (SocketChannel) key.channel();

				System.out.println("Channel: "
					+ (key.isReadable() ? "r" : "-")
					+ (key.isWritable() ? "w" : "-")
					+ (key.isConnectable() ? "c" : "-")
				);

				// Attempt a connection
				if (key.isConnectable()) {

					// Connection OK
					System.out.println("Server Found");

					// Close pendent connections
					if (channel.isConnectionPending()) {
						channel.finishConnect();
					}
				} else {
					// Write continuously on the buffer
//                    for (int j = 1000; j-- > 0; ) {
					if (!buffer.hasRemaining()) {
						buffer.flip();
						System.out.println("Flip!");
					}
					int write = channel.write(buffer);
					System.out.println(write);
//                    }

				}
			}
		}
	}
}
