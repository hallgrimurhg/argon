package argon;

public class EvalIf implements Evaluator {
  @Override
  public Object eval(Object args, Environment env) {
    Object cond = Argon.eval(Pair.first(args), env);
    if (Argon.bool(cond)) {
      return Argon.eval(Pair.second(args), env);
    } else {
      return Argon.eval(Pair.third(args), env);
    }
  }
}
