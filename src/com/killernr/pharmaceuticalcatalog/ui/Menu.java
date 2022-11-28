package com.killernr.pharmaceuticalcatalog.ui;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.killernr.pharmaceuticalcatalog.businesslogic.Validation;
import com.killernr.pharmaceuticalcatalog.dataaccess.JsonAccess;
import com.killernr.pharmaceuticalcatalog.dataaccess.Medicine;
import com.killernr.pharmaceuticalcatalog.dataaccess.MedicineDao;
import com.killernr.pharmaceuticalcatalog.dataaccess.User;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents utility class that implements menu.
 * @author VasylMartynWork
 */
public class Menu {
  static MedicineDao medicineDao = new MedicineDao();

  /**
   * Displays the authorization menu.
   */
  public static void authorization() {
    while (true) {
      System.out.println("Введіть логін: ");
      Scanner userLoginInput = new Scanner(System.in);
      try {
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
        break;
      }
      catch (Exception e){
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays the registration menu.
   */
  public static void registration() {
    while(true) {
      System.out.println("Введіть логін: ");
      Scanner userLoginInput = new Scanner(System.in);
      try {
        String userLogin = userLoginInput.nextLine();
        System.out.println("Введіть пароль: ");
        Scanner userPasswordInput = new Scanner(System.in);
        String userPassword = userPasswordInput.next();
        if(!JsonAccess.isExistUserRegistration(userLogin)) {
          if (Validation.validate(userLogin, userPassword)) {
            String hashedUserPassword = BCrypt.withDefaults()
                .hashToString(12, userPassword.toCharArray());
            User newUser = new User(userLogin, hashedUserPassword);
            JsonAccess.addUser(newUser);
            registeredUserMenu(newUser.getName());
            System.exit(0);
          } else {
            System.out.println(
                "Помилка, логін та пароль повинні бути не менше 5 символів, \nта складатись тільки з великих та маленьких латинських літер та цифр");
          }
        }
        else {
          System.out.println("Помилка, такий користувач вже існує");
        }
      }
      catch (Exception e){
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays a catalog of medicines.
   * @throws IOException If file don't exist.
   */
  public static void viewCatalog() throws IOException {
    List<Medicine> medicineList = medicineDao.getAll();
    if(medicineList.isEmpty()) {
      throw new IOException();
    }
    for (int i = 0; i < medicineList.size(); i++) {
      System.out.println(i + ". Назва: " + medicineList.get(i).getName() + " Ціна: " + medicineList.get(i).getCost());
    }
  }

  /**
   * Displays base menu.
   */
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
            try {
              viewCatalog();
            }
            catch (IOException e){
              System.out.println("Помилка, файл пустий");
            }
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
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays menu for registered user.
   * @param userName Username of registered user.
   */
  private static void registeredUserMenu(String userName) {
    System.out.println("Ласкаво просимо до фармацевтичного каталогу, " + userName);
    while (true) {
      System.out.println("1. Вивід каталогу");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        int userChoice = userInput.nextInt();
        if(userChoice == 1){
          viewCatalog();
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
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays menu for admin.
   */
  private static void adminMenu() {
    while (true) {
      System.out.println("Оберіть потрібний пункт меню: ");
      System.out.println(
          "1. Додати нові дані \n2. Змінити дані \n3. Видалити дані \n4. Вивід каталогу");
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
            try {
              viewCatalog();
            }
            catch (IOException e){
              System.out.println("Помилка, файл пустий");
            }
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
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays the data addition menu.
   */
  private static void addDataMenu() {
    while (true) {
      System.out.print("Введіть назву: ");
      try {
        Scanner userInputName = new Scanner(System.in);
        String name = userInputName.nextLine();
        System.out.print("Введіть ціну: ");
        Scanner userInputCost = new Scanner(System.in);
        int cost = userInputCost.nextInt();
        Medicine medicine = new Medicine(name, cost);
        medicineDao.save(medicine);
        break;
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays the data deletion menu.
   */
  private static void deleteDataMenu() {
    while (true) {
      try {
        viewCatalog();
      }
      catch (IOException e){
        System.out.println("Помилка, файл пустий");
        break;
      }
      System.out.print("Оберіть номер товару: ");
      Scanner userInput = new Scanner(System.in);
      try {
        int userChoice = userInput.nextInt();
        medicineDao.delete(userChoice);
        break;
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays the data update menu.
   */
  private static void updateDataMenu() {
    while (true) {
      try {
        viewCatalog();
      }
      catch (IOException e){
        System.out.println("Помилка, файл пустий");
        break;
      }
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
        Medicine medicine = new Medicine(medicineName, cost);
        medicineDao.update(medicine, userChoice);
        break;
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }
}
