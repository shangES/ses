package icbc.cmis.util;

public class AuthorizationException extends Exception {

  public AuthorizationException() {
  }

  public AuthorizationException(String info) {
    super(info);
  }
}