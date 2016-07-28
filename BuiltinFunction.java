package argon;

public abstract class BuiltinFunction extends Function {
  public static final Function PLUS = new BuiltinFunction("+") {
    @Override
    public Object apply(Object args) {
      long result = 0;
      while (args != null) {
        result += (long)Pair.first(args);
        args = Pair.rest(args);
      }
      return result;
    }
  };

  public static final Function MINUS = new BuiltinFunction("-") {
    @Override
    public Object apply(Object args) {
      long value = (long)Pair.first(args);
      Object p = Pair.rest(args);
      while (p != null) {
        value -= (long)Pair.first(p);
        p = Pair.rest(p);
      }
      return value;
    }
  };

  public static final Function LT = new BuiltinFunction("<") {
    @Override
    public Object apply(Object args) {
      long a = (long)Pair.first(args);
      long b = (long)Pair.second(args);
      return a < b;
    }
  };

  public static final Function EQUALS = new BuiltinFunction("=") {
    @Override
    public Object apply(Object args) {
      long a = (long)Pair.first(args);
      long b = (long)Pair.second(args);
      return a == b;
    }
  };

  public static final Function CAR = new BuiltinFunction("car") {
    @Override
    public Object apply(Object args) {
      return Pair.first(Pair.first(args));
    }
  };

  public static final Function CDR = new BuiltinFunction("cdr") {
    @Override
    public Object apply(Object args) {
      return Pair.rest(Pair.first(args));
    }
  };

  public static final Function CONS = new BuiltinFunction("cons") {
    @Override
    public Object apply(Object args) {
      return Pair.cons(Pair.first(args), Pair.second(args));
    }
  };

  public static final Function LIST = new BuiltinFunction("list") {
    @Override
    public Object apply(Object args) {
      return args;
    }
  };

  private String name;

  public BuiltinFunction(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "#<procedure:" + name + ">";
  }
}
