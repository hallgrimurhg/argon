package argon;

import java.io.IOException;

public class Parser {
  public static Object parse(Lexer lexer) throws IOException {
    if (lexer.peek().getType() == Token.Type.LPAREN) {
      return parseList(lexer);
    } else {
      return parseAtom(lexer);
    }
  }

  private static Object parseList(Lexer lexer) throws IOException {
    lexer.advance();
    if (!lexer.eof() && lexer.peek().getType() == Token.Type.RPAREN) {
      // Empty list
      lexer.advance();
      return null;
    }
    Pair head = new Pair();
    Pair last = head;
    while (!lexer.eof()) {
      Token t = lexer.peek();
      if (t.getType() == Token.Type.LPAREN) {
        last.head = parseList(lexer);
      } else if (t.getType() != Token.Type.RPAREN) {
        last.head = parseAtom(lexer);
      }
      if (lexer.peek().getType() == Token.Type.RPAREN) break;
      last = last.append();
    }
    if (lexer.peek().getType() == Token.Type.RPAREN) {
      lexer.advance();
    }
    return head;
  }

  private static Object parseAtom(Lexer lexer) throws IOException {
    Token atom = lexer.advance();
    switch (atom.getType()) {
      case INTEGER:
        return Long.parseLong(atom.getValue());
      case STRING:
        return atom.getValue();
      case SYMBOL:
        return new Symbol(atom.getValue());
      case BOOLEAN:
        return atom.getValue().equals("#t");
    }
    return null;
  }
}
