package argon;

public class Token {
  public static enum Type { LPAREN, RPAREN, STRING, INTEGER, BOOLEAN, SYMBOL }

  private Type type;
  private String value;

  public Token(Type type, String value) {
    this.type = type;
    this.value = value;
  }

  public Type getType() { return type; }
  public String getValue() { return value; }

  @Override
  public String toString() {
    return String.format("(%s %s)", type.name(), value);
  }
}
