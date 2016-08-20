package argon;

public class Pair {
  public Object head;
  public Object tail;

  public Pair() {
    this.head = null;
    this.tail = null;
  }

  public Pair(Object head, Object tail) {
    this.head = head;
    this.tail = tail;
  }

  public static Pair cons(Object head, Object tail) {
    return new Pair(head, tail);
  }

  public Pair append() {
    Pair p = new Pair();
    tail = p;
    return p;
  }

  public static Object first(Object x) {
    return x instanceof Pair ? ((Pair)x).head : null; 
  }

  public static Object rest(Object x) {
    return x instanceof Pair ? ((Pair)x).tail : null;
  }

  public static Object second(Object x) {
    return first(rest(x));
  }

  public static Object third(Object x) {
    return first(rest(rest(x)));
  }

  public static boolean isEmpty(Object x) {
    if (x == null) return true;
    return first(x) == null && rest(x) == null;
  }

  @Override
  public String toString() {
    if (head == null && tail == null) {
      return "'()";
    }

    StringBuilder sb = new StringBuilder();
    sb.append("(");
    sb.append(head);

    Object p = tail;
    while (p != null) {
      if (!(p instanceof Pair)) {
        sb.append(" . ");
        sb.append(p);
        break;
      }
      sb.append(" ");
      sb.append(first(p));
      p = rest(p);
    }

    sb.append(")");
    return sb.toString();
  }
}
