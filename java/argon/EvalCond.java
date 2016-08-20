package argon;

public class EvalCond implements Evaluator {
  @Override
  public Object eval(Object args, Environment env) {
    Object p = args;
    while (p != null) {
      Object clause = Pair.first(p);

      Object cond = Argon.eval(Pair.first(clause), env);
      if (Argon.bool(cond)) {
        return Argon.eval(Pair.second(clause), env);
      }

      p = Pair.rest(p);
    }

    return null;
  }
}
