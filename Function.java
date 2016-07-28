package argon;

public abstract class Function implements Evaluator {
  @Override
  public Object eval(Object args, Environment env) {
    Pair head = new Pair();
    Pair last = head;

    Object arg = args;
    while (arg != null) {
      last.head = Argon.eval(Pair.first(arg), env);
      arg = Pair.rest(arg);
      if (arg != null) {
        last = last.append();
      }
    }
    return apply(head);
  }

  public abstract Object apply(Object args);
}
