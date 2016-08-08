package argon;

import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Argon {
  public static void main(String[] args) throws IOException {
    repl();
  }

  public static void repl() throws IOException {
    Environment top = new Environment();

    top.put(new Symbol("null"), null);
    top.put(new Symbol("+"), BuiltinFunction.PLUS);
    top.put(new Symbol("-"), BuiltinFunction.MINUS);
    top.put(new Symbol("<"), BuiltinFunction.LT);
    top.put(new Symbol("="), BuiltinFunction.EQUALS);
    top.put(new Symbol("car"), BuiltinFunction.CAR);
    top.put(new Symbol("cdr"), BuiltinFunction.CDR);
    top.put(new Symbol("cons"), BuiltinFunction.CONS);
    top.put(new Symbol("list"), BuiltinFunction.LIST);
    top.put(new Symbol("quote"), new EvalQuote());
    top.put(new Symbol("define"), new EvalDefine());
    top.put(new Symbol("lambda"), new EvalLambda());
    top.put(new Symbol("if"), new EvalIf());
    top.put(new Symbol("cond"), new EvalCond());

    Console console = System.console();
    PrintWriter writer = console.writer();

    console.printf("Welcome to Argon!\n");
    while (true) {
      String line = console.readLine("> ");
      if (line == null) break;
      if (line.equals("")) continue;

      Lexer lexer = new Lexer(new ByteArrayInputStream(line.getBytes()));

      try {
        Object p = Parser.parse(lexer);
        print(eval(p, top), writer);
      } catch (Exception ex) {
        System.out.println("Evaluation error: " + ex.getMessage());
      }
    }
  }

  public static Object eval(Object x, Environment env) {
    if (x instanceof Symbol) {
      return env.get(symbol(x));
    } else if (!(x instanceof Pair)) {
      return x;
    }

    Object f = eval(Pair.first(x), env);
    if (!(f instanceof Evaluator)) {
      throw new IllegalArgumentException("not a procedure: " + Pair.first(x));
    }

    Evaluator ev = (Evaluator)f;
    return ev.eval(Pair.rest(x), env);
  }

  public static void print(Object x, PrintWriter writer) {
    if (x == null) {
      writer.println("'()");
    } else if (x instanceof Boolean) {
      if (bool(x)) {
        writer.println("#t");
      } else {
        writer.println("#f");
      }
    } else {
      writer.println(x);
    }
  }

  public static Symbol symbol(Object x) {
    if (x instanceof Symbol) return (Symbol)x;
    throw new IllegalArgumentException("not a symbol: " + x);
  }

  public static boolean bool(Object x) {
    if (x == null) return false;
    if (x instanceof Pair && Pair.isEmpty(x)) return false;
    if (x instanceof Boolean) return x == Boolean.TRUE;
    // Everything else is considered true.
    return true;
  }

  public static long integer(Object x) {
    if (x instanceof Long) return (long)x;
    throw new IllegalArgumentException("not an integer: " + x);
  }
}
