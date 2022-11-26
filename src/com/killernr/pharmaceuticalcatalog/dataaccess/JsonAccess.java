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

public class JsonAccess {

  public static void addUser(User user){
    Path path = Path.of("resources","users.json");
    Gson gson = new Gson();
    List<User> userList = jsonToList();
    userList.add(user);
    String usersStr = gson.toJson(userList);
    try {
      writeString(path, usersStr);
    }
    catch (IOException e){
      System.out.println("Error" + e.getMessage());
    }
  }

  public static List<Medicine> medicineListFromJson(){
    Path pathToMedicineJson = Path.of("resources","medicine.json");
    Gson gson = new Gson();
    String jsonToArray = null;
    try {
      if(readString(pathToMedicineJson).length() == 0){
        writeString(pathToMedicineJson, testMedicineToJson());
      }
      jsonToArray = readString(pathToMedicineJson);
    }
    catch (IOException e){
      System.out.println("Error is: " + e.getMessage());
    }
    Medicine[] medicineArray = gson.fromJson(jsonToArray, Medicine[].class);
    List<Medicine> medicineList = new ArrayList<>(Arrays.asList(medicineArray));
    return medicineList;
  }

  private static String testMedicineToJson(){
    Medicine medicine = new Medicine("Анальгін",125);
    Gson gson = new Gson();
    List<Medicine> medicineList = new ArrayList<>();
    medicineList.add(medicine);
    return gson.toJson(medicineList);
  }

  public static int isExistUser(User user){
    List<User> userList = jsonToList();
    for (User userFromList : userList) {
      if(userFromList.getName().equals(user.getName()) && BCrypt.verifyer().verify(user.getPassword().toCharArray(), userFromList.getPassword()).verified){
        if(userFromList.getName().equals("Admin")){
          return 0;
        }
        return 1;
      }
    }
    return 2;
  }


  private static List<User> jsonToList(){
    Path pathToUserJson = Path.of("resources","users.json");
    Gson gson = new Gson();
    String jsonToArray = null;
    try {
      if(readString(pathToUserJson).length() == 0){
        writeString(pathToUserJson, adminAccountToJson());
      }
      jsonToArray = readString(pathToUserJson);
    }
    catch (IOException e){
      System.out.println("Error is: " + e.getMessage());
    }
    User[] userArray = gson.fromJson(jsonToArray, User[].class);
    List<User> userList = new ArrayList<>(Arrays.asList(userArray));
    return userList;
  }

//  public void access() {
//    User user = new User("KillerNR", "1234");
//    User user2 = new User("Boyoh", "4322");
//    List<User> users = new ArrayList<>();
//    users.add(user);
//    users.add(user2);
//    Gson gson = new Gson();
//    var a = gson.toJson(users);
//    List<User> users2 = new ArrayList<>();
//    User[] users3 = gson.fromJson(a,User[].class);
//    users2  = Arrays.asList(users3);
//    System.out.println(a);
//  }

  private static String adminAccountToJson(){
    User admin = new User("Admin","1234321");
    Gson gson = new Gson();
    List<User> users = new ArrayList<>();
    users.add(admin);
    return gson.toJson(users);
  }

  public static String readString(Path path) throws IOException {
    return Files.readString(path);
  }

  public static void writeString(Path path, String data) throws IOException {
    Files.writeString(path, data, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
  }
}
