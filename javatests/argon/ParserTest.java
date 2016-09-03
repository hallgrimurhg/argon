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
public class ParserTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testEmptyList() throws IOException {
    String line = "()";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Object o1 = Parser.parse(lexer);
    Assert.assertNull(o1);
  }

  @Test
  public void testNoTokens() throws IOException {
    String line = "";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    thrown.expect(IllegalStateException.class);
    Object o1 = Parser.parse(lexer);
  }

  @Test
  public void testSymbolAtom() throws IOException {
    String line = "abc";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Object o = Parser.parse(lexer);
    Assert.assertEquals(new Symbol("abc"), o);
  }

  @Test
  public void testIntegerAtom() throws IOException {
    String line = "1234";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Object o = Parser.parse(lexer);
    Assert.assertEquals(1234L, o);
  }

  @Test
  public void testStringAtom() throws IOException {
    String line = "\"abc\"";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Object o = Parser.parse(lexer);
    Assert.assertEquals("\"abc\"", o);
  }

  @Test
  public void testTrueAtom() throws IOException {
    String line = "#t";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Object o = Parser.parse(lexer);
    Assert.assertEquals(true, o);
  }

  @Test
  public void testFalseAtom() throws IOException {
    String line = "#f";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Object o = Parser.parse(lexer);
    Assert.assertEquals(false, o);
  }

  @Test
  public void testList() throws IOException {
    String line = "(+ 12 (#t \"abc\"))";
    Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

    Object outer = Parser.parse(lexer);
    Assert.assertEquals(3, Pair.length(outer));
    Assert.assertEquals(new Symbol("+"), Pair.first(outer));
    Assert.assertEquals(12L, Pair.second(outer));

    Object inner = Pair.third(outer);
    Assert.assertEquals(2, Pair.length(inner));
    Assert.assertEquals(true, Pair.first(inner));
    Assert.assertEquals("\"abc\"", Pair.second(inner));
  }
}
