package argon;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LexerTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testTrimWhitespace() throws IOException {
    String line = "   (    10 )";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t1 = lexer.advance();
    Assert.assertEquals(Token.Type.LPAREN, t1.getType());
    Assert.assertEquals("(", t1.getValue());

    Token t2 = lexer.advance();
    Assert.assertEquals(Token.Type.INTEGER, t2.getType());
    Assert.assertEquals("10", t2.getValue());

    Token t3 = lexer.advance();
    Assert.assertEquals(Token.Type.RPAREN, t3.getType());
    Assert.assertEquals(")", t3.getValue());
  }

  @Test
  public void testOnlyWhitespace() throws IOException {
    String line = "   ";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    thrown.expect(IllegalStateException.class);
    Token t = lexer.advance();
  }

  @Test
  public void testStringToken() throws IOException {
    String line = "\"abc\"";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t = lexer.advance();
    Assert.assertEquals(Token.Type.STRING, t.getType());
    Assert.assertEquals("\"abc\"", t.getValue());
  }

  @Test
  public void testInteger() throws IOException {
    String line = "123";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t = lexer.advance();
    Assert.assertEquals(Token.Type.INTEGER, t.getType());
    Assert.assertEquals("123", t.getValue());
  }

  @Test
  public void testNegativeInteger() throws IOException {
    String line = "-23";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t = lexer.advance();
    Assert.assertEquals(Token.Type.INTEGER, t.getType());
    Assert.assertEquals("-23", t.getValue());
  }

  @Test
  public void testLeftParenthesis() throws IOException {
    String line = "(";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t = lexer.advance();
    Assert.assertEquals(Token.Type.LPAREN, t.getType());
    Assert.assertEquals("(", t.getValue());
  }

  @Test
  public void testRightParenthesis() throws IOException {
    String line = ")";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t = lexer.advance();
    Assert.assertEquals(Token.Type.RPAREN, t.getType());
    Assert.assertEquals(")", t.getValue());
  }

  @Test
  public void testSymbol() throws IOException {
    String line = "abc write-line pair? *test*";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t1 = lexer.advance();
    Assert.assertEquals(Token.Type.SYMBOL, t1.getType());
    Assert.assertEquals("abc", t1.getValue());

    Token t2 = lexer.advance();
    Assert.assertEquals(Token.Type.SYMBOL, t2.getType());
    Assert.assertEquals("write-line", t2.getValue());

    Token t3 = lexer.advance();
    Assert.assertEquals(Token.Type.SYMBOL, t3.getType());
    Assert.assertEquals("pair?", t3.getValue());

    Token t4 = lexer.advance();
    Assert.assertEquals(Token.Type.SYMBOL, t4.getType());
    Assert.assertEquals("*test*", t4.getValue());
  }

  @Test
  public void testTrueLiteral() throws IOException {
    String line = "#t";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t = lexer.advance();
    Assert.assertEquals(Token.Type.BOOLEAN, t.getType());
    Assert.assertEquals("#t", t.getValue());
  }

  @Test
  public void testFalseLiteral() throws IOException {
    String line = "#f";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Token t = lexer.advance();
    Assert.assertEquals(Token.Type.BOOLEAN, t.getType());
    Assert.assertEquals("#f", t.getValue());
  }

  @Test
  public void testInvalidBooleanLiteral() throws IOException {
    String line = "#x";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    thrown.expect(IllegalArgumentException.class);
    Token t = lexer.advance();
  }

  @Test
  public void testEof() throws IOException {
    String line = "(1 2";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Assert.assertFalse(lexer.eof());
    Assert.assertNotNull(lexer.advance());
    Assert.assertFalse(lexer.eof());
    Assert.assertNotNull(lexer.advance());
    Assert.assertFalse(lexer.eof());
    Assert.assertNotNull(lexer.advance());
    Assert.assertTrue(lexer.eof());
  }

  @Test
  public void testEmptyString() throws IOException {
    String line = "";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Assert.assertTrue(lexer.eof());
  }

  @Test
  public void testAdvanceAtEof() throws IOException {
    String line = "";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    thrown.expect(IllegalStateException.class);
    lexer.advance();
  }

  @Test
  public void testPeekAtEof() throws IOException {
    String line = "";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    thrown.expect(IllegalStateException.class);
    lexer.peek();
  }
}
