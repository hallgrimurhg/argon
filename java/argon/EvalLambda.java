package argon;

public class EvalLambda implements Evaluator {
  @Override
  public Object eval(Object args, Environment env) {
    Object params = Pair.first(args);
    Object body = Pair.second(args);
    return new Closure(null, env, params, body);
  }
}
