package argon;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class PeekReader {
  private static final int MAX_PEEK = 10;

  private PushbackReader reader;

  public PeekReader(Reader in) {
    this.reader = new PushbackReader(in, MAX_PEEK);
  }

  public char read() throws IOException {
    return (char)reader.read();
  }

  public char peek() throws IOException {
    char c = (char)reader.read();
    reader.unread(c);
    return c;
  }

  public char peekAt(int n) throws IOException {
    if (n >= MAX_PEEK) {
      throw new IllegalArgumentException("exceeds max peek capacity");
    }
    char[] buf = new char[n + 1];
    reader.read(buf, 0, n + 1);
    char c = buf[n];
    reader.unread(buf, 0, n + 1);
    return c;
  }

  public boolean eof() throws IOException {
    return (byte)peek() == -1;
  }
}
