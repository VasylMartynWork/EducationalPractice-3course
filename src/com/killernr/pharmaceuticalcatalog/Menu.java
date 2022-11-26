package com.killernr.pharmaceuticalcatalog;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.killernr.pharmaceuticalcatalog.dataaccess.CrudWithJson;
import com.killernr.pharmaceuticalcatalog.dataaccess.JsonAccess;
import com.killernr.pharmaceuticalcatalog.dataaccess.Medicine;
import com.killernr.pharmaceuticalcatalog.dataaccess.User;
import java.util.List;
import java.util.Scanner;

public class Menu {

  public static void authorization() {
    System.out.println("Введіть логін: ");
    Scanner userLoginInput = new Scanner(System.in);
    String userLogin = userLoginInput.nextLine();
    System.out.println("Введіть пароль: ");
    Scanner userPasswordInput = new Scanner(System.in);
    String userPassword = userPasswordInput.nextLine();
    User user = new User(userLogin, userPassword);
    if (JsonAccess.isExistUser(user) == 0) {
      adminMenu();
    } else if (JsonAccess.isExistUser(user) == 1) {
      registeredUserMenu(user.getName());
    }
  }

  public static void registration() {
    System.out.println("Введіть логін: ");
    Scanner userLoginInput = new Scanner(System.in);
    String userLogin = userLoginInput.next();
    System.out.println("Введіть пароль: ");
    Scanner userPasswordInput = new Scanner(System.in);
    String userPassword = userPasswordInput.next();
    String hashedUserPassword = BCrypt.withDefaults().hashToString(12, userPassword.toCharArray());
    User newUser = new User(userLogin, hashedUserPassword);
    JsonAccess.addUser(newUser);
    registeredUserMenu(newUser.getName());
  }

  public static void viewCatalog() {
    List<Medicine> medicineList = JsonAccess.medicineListFromJson();
    for (int i = 0; i < medicineList.size(); i++) {
      System.out.println(
          i + ". Назва: " + medicineList.get(i).getName() + " Ціна: " + medicineList.get(i)
              .getCost());
    }
  }

  public static void baseMenu() {
    System.out.println("Ласкаво просимо до фармацевтичного каталогу!");
    while (true) {
      System.out.println("1. Авторизація \n2. Реєстрація \n3. Вивід каталогу");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            authorization();
            break;
          case 2:
            registration();
            System.exit(0);
            break;
          case 3:
            viewCatalog();
            break;
          default:
            break;
        }
        System.out.println("Бажаєте продовжити?");
        System.out.println("1. Так");
        System.out.println("2. Ні");
        Scanner userInputChoice = new Scanner(System.in);
        int userChoiceContinue = userInputChoice.nextInt();
        if (userChoiceContinue == 2) {
          break;
        }
      } catch (Exception e) {
      }
    }
  }

  private static void registeredUserMenu(String userName) {
    System.out.println("Ласкаво просимо до фармацевтичного каталогу, " + userName);
    while (true) {
      System.out.println("1. Вивід каталогу");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            viewCatalog();
            break;
          default:
            break;
        }
        System.out.println("Бажаєте продовжити?");
        System.out.println("1. Так");
        System.out.println("2. Ні");
        Scanner userInputChoice = new Scanner(System.in);
        int userChoiceContinue = userInputChoice.nextInt();
        if (userChoiceContinue == 2) {
          System.exit(0);
        }
      } catch (Exception e) {
      }
    }
  }

  private static void adminMenu() {
    while (true) {
      System.out.println("Оберіть потрібний пункт меню: ");
      System.out.println(
          "1. Додати нові дані \n2. Змінити дані \n3. Видалити дані. \n4. Вивід каталогу");
      try {
        Scanner userInput = new Scanner(System.in);
        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            addDataMenu();
            break;
          case 2:
            updateDataMenu();
            break;
          case 3:
            deleteDataMenu();
            break;
          case 4:
            viewCatalog();
            break;
          default:
            break;
        }
        System.out.println("Бажаєте продовжити?");
        System.out.println("1. Так");
        System.out.println("2. Ні");
        Scanner userInputChoice = new Scanner(System.in);
        int userChoiceContinue = userInputChoice.nextInt();
        if (userChoiceContinue == 2) {
          System.exit(0);
        }
      } catch (Exception e) {
      }
    }
  }

  private static void addDataMenu() {
    while (true) {
      System.out.print("Введіть назву: ");
      try {
        Scanner userInputName = new Scanner(System.in);
        String name = userInputName.nextLine();
        System.out.print("Введіть ціну: ");
        Scanner userInputCost = new Scanner(System.in);
        int cost = userInputCost.nextInt();
        CrudWithJson.addNewData(name, cost);
        break;
      } catch (Exception e) {
      }
    }
  }

  private static void deleteDataMenu() {
    while (true) {
      viewCatalog();
      System.out.print("Оберіть номер товару: ");
      Scanner userInput = new Scanner(System.in);
      try {
        int userChoice = userInput.nextInt();
        CrudWithJson.deleteData(userChoice);
        break;
      } catch (Exception e) {
      }
    }
  }

  private static void updateDataMenu() {
    while (true) {
      viewCatalog();
      try {
        System.out.print("Оберіть номер товару: ");
        Scanner userInput = new Scanner(System.in);
        int userChoice = userInput.nextInt();
        System.out.print("Введіть нову назву: ");
        Scanner userInputName = new Scanner(System.in);
        String medicineName = userInputName.nextLine();
        System.out.print("Введіть нову ціну: ");
        Scanner userInputCost = new Scanner(System.in);
        int cost = userInputCost.nextInt();
        CrudWithJson.updateData(userChoice, medicineName, cost);
        break;
      } catch (Exception e) {
      }
    }
  }

}
