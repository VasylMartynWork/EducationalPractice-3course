package com.killernr.pharmaceuticalcatalog.businesslogic;

public class AuthorizationValidation {
  public static boolean validate(String userLogin, String userPassword){
    // Це валідація, але ще спитати, чи так добре буде
    if(userLogin.matches("[A-Za-z0-9]{5,}") && userPassword.matches("[A-Za-z0-9]{5,}")){
      System.out.println("yeah!");
      return true;
    }
    return false;
  }

}
