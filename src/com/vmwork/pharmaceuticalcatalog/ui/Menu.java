package com.vmwork.pharmaceuticalcatalog.ui;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.vmwork.pharmaceuticalcatalog.businesslogic.Validation;
import com.vmwork.pharmaceuticalcatalog.dataaccess.JsonAccess;
import com.vmwork.pharmaceuticalcatalog.dataaccess.Medicine;
import com.vmwork.pharmaceuticalcatalog.dataaccess.MedicineDao;
import com.vmwork.pharmaceuticalcatalog.dataaccess.User;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

/**
 * Represents utility class that implements menu.
 *
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
        if (JsonAccess.existingUserCheck(user) == 0) {
          adminMenu();
        } else if (JsonAccess.existingUserCheck(user) == 1) {
          registeredUserMenu(user.getLogin());
        } else {
          System.out.println("Такого користувача не існує");
        }
        break;
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays the registration menu.
   */
  public static void registration() {
    while (true) {
      System.out.println("Введіть логін: ");
      try {
        Scanner userLoginInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));
        String userLogin = userLoginInput.nextLine();
        System.out.println("Введіть пароль: ");
        Scanner userPasswordInput = new Scanner(System.in);
        String userPassword = userPasswordInput.nextLine();
        if (!JsonAccess.isExistUserRegistration(userLogin)) {
          if (Validation.validate(userLogin, userPassword)) {
            String hashedUserPassword = BCrypt.withDefaults()
                .hashToString(12, userPassword.toCharArray());
            User user = new User(userLogin, hashedUserPassword);
            JsonAccess.addUser(user);
            registeredUserMenu(user.getLogin());
            System.exit(0);
          } else {
            System.out.println(
                "Помилка, логін та пароль повинні бути не менше 5 символів, \nта складатись тільки з великих та маленьких латинських літер та цифр");
          }
        } else {
          System.out.println("Помилка, такий користувач вже існує");
        }
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays a catalog of medicines.
   *
   * @throws IOException If file don't exist.
   */
  public static void viewCatalog() throws IOException {
    List<Medicine> medicineList = medicineDao.getAll();
    if (medicineList.isEmpty()) {
      System.out.println("Помилка, каталог пустий");
      return;
    }
    for (int i = 0; i < medicineList.size(); i++) {
      System.out.printf("%d. Назва: %s  Ціна: %,.2f %n", i, medicineList.get(i).getName(),
          medicineList.get(i).getCost());
    }
  }

  /**
   * Displays searching menu.
   */
  public static void searchMenu() {
    while (true) {
      System.out.println("Введіть назву: ");
      Scanner medicineNameInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      try {
        String medicineName = medicineNameInput.nextLine();
        JsonAccess.searchMedicine(medicineName);
        break;
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays filtering menu.
   */
  public static void filterMenu() {
    while (true) {
      System.out.println("Введіть максимальну ціну: ");
      Scanner medicineCostInput = new Scanner(System.in);
      try {
        int medicineCost = medicineCostInput.nextInt();
        JsonAccess.filterMedicine(medicineCost);
        break;
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  /**
   * Displays base menu.
   */
  public static void baseMenu() {
    System.out.println("Ласкаво просимо до фармацевтичного каталогу!");
    while (true) {
      System.out.println(
          "1. Авторизація \n2. Реєстрація \n3. Вивід каталогу \n4. Пошук в каталозі \n5. Фільтрувати \n6. Вихід");
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
            } catch (IOException e) {
              System.out.println("Помилка, файл пустий");
            }
            break;
          case 4:
            searchMenu();
            break;
          case 5:
            filterMenu();
            break;
          case 6:
            System.exit(0);
            break;
          default:
            System.out.println("Помилка, введіть число від 1 до 6");
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
   *
   * @param userName Username of registered user.
   */
  private static void registeredUserMenu(String userName) {
    System.out.println("Ласкаво просимо до фармацевтичного каталогу, " + userName);
    while (true) {
      System.out.println("1. Вивід каталогу \n2. Пошук в каталозі \n3. Фільтрувати \n4. Вихід");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            viewCatalog();
            break;
          case 2:
            searchMenu();
            break;
          case 3:
            filterMenu();
            break;
          case 4:
            System.exit(0);
            break;
          default:
            System.out.println("Помилка, введіть числа 1 або 4");
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
   * Displays menu for admin.
   */
  private static void adminMenu() {
    while (true) {
      System.out.println("Оберіть потрібний пункт меню: ");
      System.out.println(
          "1. Додати нові дані \n2. Змінити дані \n3. Видалити дані \n4. Вивід каталогу \n5. Пошук в каталозі \n6. Фільтрувати \n7. Вихід");
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
            } catch (IOException e) {
              System.out.println("Помилка, файл пустий");
            }
            break;
          case 5:
            searchMenu();
            break;
          case 6:
            filterMenu();
            break;
          case 7:
            System.exit(0);
            break;
          default:
            System.out.println("Помилка, введіть число від 1 до 7");
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
        Scanner userNameInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));

        String medicineName = userNameInput.nextLine();
        System.out.print("Введіть ціну: ");
        Scanner userCostInput = new Scanner(System.in);
        double medicineCost = userCostInput.nextDouble();
        Medicine medicine = new Medicine(medicineName, medicineCost);
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
      } catch (IOException e) {
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
      } catch (IOException e) {
        System.out.println("Помилка, файл пустий");
        break;
      }
      try {
        System.out.print("Оберіть номер товару: ");
        Scanner userInput = new Scanner(System.in);
        int userChoice = userInput.nextInt();
        System.out.print("Введіть нову назву: ");
        Scanner userNameInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));
        String medicineName = userNameInput.nextLine();
        System.out.print("Введіть нову ціну: ");
        Scanner userCostInput = new Scanner(System.in);
        double medicineCost = userCostInput.nextDouble();
        Medicine medicine = new Medicine(medicineName, medicineCost);
        medicineDao.update(medicine, userChoice);
        break;
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }
}
