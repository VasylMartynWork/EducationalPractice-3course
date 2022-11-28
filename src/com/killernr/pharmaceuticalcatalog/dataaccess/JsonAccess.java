package com.killernr.pharmaceuticalcatalog.dataaccess;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class that provides user file handling capabilities that can read data from a file with
 * data about medicine and translate it into a List.
 *
 * @author VasylMartynWork
 */
public class JsonAccess {

  /**
   * Add data about user to JSON.
   *
   * @param user The object from which the data is taken.
   * @throws IOException If file don't exist.
   */
  public static void addUser(User user) throws IOException {
    Path path = Path.of("resources", "users.json");
    Gson gson = new Gson();
    List<User> userList = jsonToList();
    userList.add(user);
    String usersStr = gson.toJson(userList);
    try {
      writeString(path, usersStr);
    } catch (IOException e) {
      System.out.println("Error is: " + e.getMessage());
    }
  }

  /**
   * Perform a searching needed medicine.
   * @param medicineName Name of medicine that needed.
   */
  public static void searchMedicine(String medicineName){
    List<Medicine> medicineList = medicineListFromJson();
    for (Medicine medicine : medicineList) {
      if(medicine.getName().equals(medicineName)){
        System.out.println("Назва: " + medicine.getName() + " Ціна: " + medicine.getCost());
        break;
      }
    }
  }

  /**
   * Perform a filtering medicine catalog and searching a medicine which cost is lower than specified by the user.
   * @param medicineCost Medicine cost that is maximum cost for filtering.
   */
  public static void filterMedicine(int medicineCost){
    List<Medicine> medicineList = medicineListFromJson();
    medicineList = medicineList.stream().filter(x -> x.getCost() <= medicineCost).toList();
    for (Medicine medicine : medicineList) {
      System.out.println("Назва: " + medicine.getName() + " Ціна: " + medicine.getCost());
    }
  }

  /**
   * Returns a list, converts a JSON file into a string, reads data from this string into an array
   * of objects, and converts this array into a list.
   *
   * @return List with objects of Medicine class.
   */
  public static List<Medicine> medicineListFromJson() {
    Path pathToMedicineJson = Path.of("resources", "medicine.json");
    Gson gson = new Gson();
    String jsonToArray = null;
    try {
      if (readString(pathToMedicineJson).length() == 0) {
        writeString(pathToMedicineJson, testMedicineToJson());
      }
      jsonToArray = readString(pathToMedicineJson);
    } catch (IOException e) {
      System.out.println("Error is: " + e.getMessage());
    }
    Medicine[] medicineArray = gson.fromJson(jsonToArray, Medicine[].class);
    return new ArrayList<>(Arrays.asList(medicineArray));
  }

  private static String testMedicineToJson() {
    Medicine medicine = new Medicine("Анальгін", 125);
    Gson gson = new Gson();
    List<Medicine> medicineList = new ArrayList<>();
    medicineList.add(medicine);
    return gson.toJson(medicineList);
  }

  /**
   * Returns a number. Checks whether data about a specific user exists in the users file.
   *
   * @param user User which data is checked.
   * @return Number, if user is admin, return 0, if registered user return 1, if not exists in file,
   * return 2.
   */
  public static int existingUserCheck(User user) {
    List<User> userList = jsonToList();
    for (User userFromList : userList) {
      if (userFromList.getName().equals(user.getName()) && BCrypt.verifyer()
          .verify(user.getPassword().toCharArray(), userFromList.getPassword()).verified) {
        if (userFromList.getName().equals("Admin")) {
          return 0;
        }
        return 1;
      }
    }
    return 2;
  }

  /**
   * Return true if user exists in file with users and false, if he doesn't exist.
   * @param userName Name of the user that need to be checked.
   * @return True or false
   */
  public static boolean isExistUserRegistration(String userName) {
    List<User> userList = jsonToList();
    for (User user : userList) {
      if(user.getName().equals(userName)){
        return true;
      }
    }
    return false;
  }

  private static List<User> jsonToList() {
    Path pathToUserJson = Path.of("resources", "users.json");
    Gson gson = new Gson();
    String jsonToArray = null;
    try {
      if (readString(pathToUserJson).length() == 0) {
        writeString(pathToUserJson, adminAccountToJson());
      }
      jsonToArray = readString(pathToUserJson);
    } catch (IOException e) {
      System.out.println("Error is: " + e.getMessage());
    }
    User[] userArray = gson.fromJson(jsonToArray, User[].class);
    return new ArrayList<>(Arrays.asList(userArray));
  }

  private static String adminAccountToJson() {
    User admin = new User("Admin", "1234321");
    Gson gson = new Gson();
    List<User> users = new ArrayList<>();
    users.add(admin);
    return gson.toJson(users);
  }

  /**
   * Read string from file.
   *
   * @param path Path to file which from string is red.
   * @return String with data from file.
   * @throws IOException If file don't exist.
   */
  public static String readString(Path path) throws IOException {
    return Files.readString(path);
  }

  /**
   * Write new data to file.
   *
   * @param path Path to file in which we write data.
   * @param data Data that is written to a file.
   * @throws IOException If file don't exist.
   */
  public static void writeString(Path path, String data) throws IOException {
    Files.writeString(path, data, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE,
        StandardOpenOption.CREATE);
  }
}
