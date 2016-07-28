package argon;

public class EvalQuote implements Evaluator {
  @Override
  public Object eval(Object args, Environment env) {
    return Pair.first(args);
  }
}
