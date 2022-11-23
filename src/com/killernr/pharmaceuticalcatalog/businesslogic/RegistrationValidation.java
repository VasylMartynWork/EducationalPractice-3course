package com.killernr.pharmaceuticalcatalog.businesslogic;

import com.killernr.pharmaceuticalcatalog.dataaccess.JsonAccess;
import com.killernr.pharmaceuticalcatalog.dataaccess.User;

public class RegistrationValidation {
  public static void register(){
    User user = new User("KillerNR","123422");
    JsonAccess.addUser(user);
  }

}
