package org.asteriskjava.util.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author sere
 * @since 2018-01-29
 */
public class BufferedReaderCrLfOnly extends Reader {
	private final Reader in;
	private final char[] cbuf = new char[8192];
	private int off, len;

	public BufferedReaderCrLfOnly(Reader in) {
		this.in = in;
	}

	@Override
	public void close() throws IOException {
		synchronized (lock) {
			in.close();
		}
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (len <= 0) {
			return 0;
		}
		synchronized (lock) {
			// fill buffer?
			if (this.len == 0) {
				this.off = 0;
				this.len = in.read(this.cbuf);
			}

			// end of file?
			if (this.len < 0) {
				return -1;
			}

			assert this.len > 0;

			// copy from buffer
			int size = Math.min(len, this.len);
			System.arraycopy(this.cbuf, this.off, cbuf, off, size);
			this.len -= size;
			this.off += size;
			return size;
		}
	}

	/**
	 * Reads a line of text.  A line is considered to be terminated by a carriage return ('\r') followed immediately by a line feed ('\n').
	 *
	 * @return A String containing the contents of the line, not including a carriage return ('\r') followed immediately by a line feed ('\n'), or null if the end of the stream has been reached
	 * @exception IOException If an I/O error occurs
	 * @see BufferedReader#readLine()
	 */
	public String readLine() throws IOException {
		synchronized (lock) {
			// end of file?
			if (len < 0) {
				return null;
			}

			for (StringBuilder sb = new StringBuilder(); ; ) {
				// search in buffer for CR LF
				for (int size = 0; size < len - 1; size++) {
					if (cbuf[off + size] == '\r' && cbuf[off + size + 1] == '\n') {
						sb.append(cbuf, off, size);
						off += size + 2;
						len -= size + 2;
						return sb.toString();
					}
				}

				// copy from buffer all but last CR
				boolean crSwallowed = len > 0 && cbuf[off + len - 1] == '\r';
				sb.append(cbuf, off, len - (crSwallowed ? 1 : 0));
				off = crSwallowed ? 1 : 0;

				// fill buffer
				len = in.read(cbuf, off, cbuf.length - off);

				// end of file?
				if (len < 0) {
					if (crSwallowed) {
						sb.append('\r');
					}
					return sb.length() > 0 ? sb.toString() : null;
				}

				assert len > 0;

				// prepend CR?
				if (crSwallowed) {
					assert off == 1;
					cbuf[0] = '\r';
					off = 0;
					len++;
				}
			}
		}
	}

}
