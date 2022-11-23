package com.killernr.pharmaceuticalcatalog;

import com.killernr.pharmaceuticalcatalog.dataaccess.JsonAccess;
import com.killernr.pharmaceuticalcatalog.dataaccess.User;
import java.util.Scanner;

public class Menu {

  public static void authorization(){
    System.out.println("Введіть логін: ");
    Scanner userLoginInput = new Scanner(System.in);
    String userLogin = userLoginInput.next();
    System.out.println("Введіть пароль: ");
    Scanner userPasswordInput = new Scanner(System.in);
    String userPassword = userPasswordInput.next();
    User user = new User(userLogin, userPassword);
    if(JsonAccess.isExistUser(user)){
      System.out.println("True");
    }
    else {
      System.out.println("False");
    }
  }

  public static void registration(){
    System.out.println("Введіть логін: ");
    Scanner userLoginInput = new Scanner(System.in);
    String userLogin = userLoginInput.next();
    System.out.println("Введіть пароль: ");
    Scanner userPasswordInput = new Scanner(System.in);
    String userPassword = userPasswordInput.next();
    User newUser = new User(userLogin, userPassword);
    JsonAccess.addUser(newUser);
  }

  public static void viewCatalog(){
    System.out.println("Soon!");
  }
  public static void baseMenu(){
    System.out.println("Ласкаво просимо до фармацевтичного каталогу!");
    System.out.println("1. Авторизація \n2. Реєстрація \n3. Вивід каталогу");
    System.out.println("Оберіть потрібний пункт меню: ");
    while(true){
      Scanner userInput = new Scanner(System.in);
      try{
        int userChoice = userInput.nextInt();
        switch (userChoice){
          case 1:
            authorization();
            break;
          case 2:
            registration();
            break;
          case 3:
            viewCatalog();
            break;
          default:
            break;
        }
        break;
      }
      catch (Exception e){
      }
    }
  }

}
