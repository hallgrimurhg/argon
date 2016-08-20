package argon;

public class EvalDefine implements Evaluator {
  @Override
  public Object eval(Object args, Environment env) {
    if (Pair.first(args) instanceof Pair) {
      return defineFunction(args, env);
    } else {
      return defineVariable(args, env);
    }
  }

  private Object defineFunction(Object args, Environment env) {
    Object header = Pair.first(args);
    Object name = Pair.first(header);
    Object params = Pair.rest(header);
    Object body = Pair.second(args);
    Function fn = new Closure(name, env, params, body);
    env.put(Argon.symbol(name), fn);
    return fn;
  }

  private Object defineVariable(Object args, Environment env) {
    Object name = Pair.first(args);
    Object value = Argon.eval(Pair.second(args), env);
    env.put(Argon.symbol(name), value);
    return value;
  }
}
