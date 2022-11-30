package com.vmwork.pharmaceuticalcatalog.dataaccess;

/**
 * Represents the user.
 * @author VasylMartynWork
 */
public class User {
  private String login;
  private String password;

  public User(String login, String password) {
    this.login = login;
    this.password = password;
  }

  /**
   * Return string that is the user login.
   * @return String that is user login.
   */
  public String getLogin() {
    return login;
  }

  /**
   * Return string that is the user password.
   * @return String that is the user password.
   */
  public String getPassword() {
    return password;
  }
}
