package argon;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Lexer {
  private PeekReader reader;
  private Token nextToken;

  public Lexer(InputStream input) {
    this.reader = new PeekReader(new InputStreamReader(input));
    this.nextToken = null;
  }

  public boolean eof() throws IOException {
    return nextToken == null && reader.eof();
  }

  public Token peek() throws IOException {
    if (nextToken == null) {
      nextToken = readToken();
    }
    return nextToken;
  }

  public Token advance() throws IOException {
    if (nextToken == null) {
      return readToken();
    }

    Token result = nextToken;
    nextToken = null;
    return result;
  }

  private Token readToken() throws IOException {
    if (reader.eof()) throw new IllegalStateException("no more tokens");
    while (Character.isWhitespace(reader.peek())) {
      reader.read();
    }
    char c = reader.peek();
    if (c == '(') {
      reader.read();
      return new Token(Token.Type.LPAREN, "(");
    } else if (c == ')') {
      reader.read();
      return new Token(Token.Type.RPAREN, ")");
    } else if (Character.isDigit(c) || (c == '-' && Character.isDigit(reader.peekAt(1)))) {
      return readNumber();
    } else if (c == '#') {
      return readBoolean();
    } else if (c == '"') {
      return readString();
    } else {
      return readSymbol();
    }
  }

  private Token readNumber() throws IOException {
    StringBuilder sb = new StringBuilder();
    if (reader.peek() == '-') {
      reader.read();
      sb.append('-');
    }
    while (!reader.eof() && Character.isDigit(reader.peek())) {
      sb.append(reader.read());
    }
    return new Token(Token.Type.INTEGER, sb.toString());
  }

  private Token readBoolean() throws IOException {
    reader.read();
    char c = reader.read();
    if (c != 't' && c != 'f') {
      throw new IllegalArgumentException("invalid boolean literal: #" + c);
    }
    return new Token(Token.Type.BOOLEAN, "#" + c);
  }

  private Token readString() throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(reader.read());
    while (!reader.eof() && reader.peek() != '"') {
      if (reader.peek() == '\\' && reader.peekAt(1) == '"') {
        sb.append(reader.read());
        sb.append(reader.read());
      } else {
        sb.append(reader.read());
      }
    }
    sb.append(reader.read());
    return new Token(Token.Type.STRING, sb.toString());
  }

  private Token readSymbol() throws IOException {
    StringBuilder sb = new StringBuilder();
    char c = reader.peek();
    while (!reader.eof() && !Character.isWhitespace(c) && c != '(' && c != ')') {
      sb.append(reader.read());
      c = reader.peek();
    }
    return new Token(Token.Type.SYMBOL, sb.toString());
  }
}
