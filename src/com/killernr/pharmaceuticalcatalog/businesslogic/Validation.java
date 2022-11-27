package com.killernr.pharmaceuticalcatalog.businesslogic;

/**
 * This class have only one method, it is a validating of login and password which user wrote.
 * @author VasylMartynWork
 */
public class Validation {

  /**
   * Returns boolean, true if login and password matches to regex and false if they don't match.
   * @param userLogin The login that user wrote.
   * @param userPassword the password that user wrote.
   * @return true or false.
   */
  public static boolean validate(String userLogin, String userPassword){
    return userLogin.matches("[A-Za-z0-9]{5,}") && userPassword.matches("[A-Za-z0-9]{5,}");
  }

}
