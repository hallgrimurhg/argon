package argon;

public class Symbol {
  private String value;

  public Symbol(String value) {
    this.value = value;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof String) {
      return value.equals(other);
    }

    if (!(other instanceof Symbol)) {
      return false;
    }

    return value.equals(((Symbol)other).value);
  }

  @Override
  public String toString() {
    return value;
  }
}
