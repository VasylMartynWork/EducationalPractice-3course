package com.killernr.pharmaceuticalcatalog.dataaccess;

/**
 * Represents the user.
 * @author VasylMartynWork
 */
public class User {
  private String name;
  private String password;

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  /**
   * Return string that is the username.
   * @return String that is username.
   */
  public String getName() {
    return name;
  }

  /**
   * Return string that is the user password.
   * @return String that is the user password.
   */
  public String getPassword() {
    return password;
  }
}
