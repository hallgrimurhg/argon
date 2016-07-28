package argon;

import java.util.HashMap;

public class Environment {
  private final HashMap<Symbol, Object> env = new HashMap<>();
  private final Environment parent;

  public Environment() {
    this.parent = null;
  }

  public Environment(Environment parent) {
    this.parent = parent;
  }

  public Object get(Symbol name) {
    if (env.containsKey(name)) {
      return env.get(name);
    } else if (parent != null) {
      return parent.get(name);
    } else {
      throw new IllegalArgumentException("no such variable: " + name);
    }
  }

  public void put(Symbol name, Object value) {
    env.put(name, value);
  }
}
