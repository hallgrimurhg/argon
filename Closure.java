package argon;

public class Closure extends Function {
  private Object name;
  private Environment parent;
  private Object params;
  private Object body;

  public Closure(Object name, Environment parent, Object params, Object body) {
    this.name = name;
    this.parent = parent;
    this.params = params;
    this.body = body;
  }

  @Override
  public Object apply(Object args) {
    Environment env = new Environment(parent);

    Object name = params;
    Object value = args;
    while (name != null) {
      env.put(Argon.symbol(Pair.first(name)), Pair.first(value));

      name = Pair.rest(name);
      value = Pair.rest(value);
    }

    return Argon.eval(body, env);
  }

  @Override
  public String toString() {
    if (name != null) {
      return "#<procedure:" + name + ">";
    } else {
      return "#<procedure>";
    }
  }
}
