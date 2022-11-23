package com.killernr.pharmaceuticalcatalog.dataaccess;

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

  public static boolean isExistUser(User user){
    List<User> userList = jsonToList();
    for (User userFromList : userList) {
      if(userFromList.getName().equals(user.getName()) && userFromList.getPassword().equals(user.getPassword())){
        return true;
      }
    }
    return false;
  }

  private static List<User> jsonToList(){
    Path path = Path.of("resources","users.json");
    Gson gson = new Gson();
    String jsonToArray = null;
    try {
      if(readString(path).length() == 0){
        writeString(path, adminAccountToJson());
      }
      jsonToArray = readString(path);
    }
    catch (IOException e){
      System.out.println("Error is: " + e.getMessage());
    }
    User[] userArray = gson.fromJson(jsonToArray, User[].class);
    List<User> userList = new ArrayList<>(Arrays.asList(userArray));
    return userList;
  }

  public static String readString(Path path) throws IOException {
    return Files.readString(path);
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

  private static void writeString(Path path, String data) throws IOException {
    Files.writeString(path, data, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
  }
}
