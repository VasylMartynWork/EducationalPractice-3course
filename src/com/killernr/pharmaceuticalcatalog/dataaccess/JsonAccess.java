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
    List<User> users = new ArrayList<>();
    users.add(user);
    Gson gson = new Gson();
    Path path = Path.of("resources","users.json");
    String usersStr = gson.toJson(users);
    try {
      writeString(path, usersStr);
    }
    catch (IOException e){
      System.out.println("Error" + e.getMessage());
    }
  }

  public void access() {
    User user = new User("KillerNR", "1234");
    User user2 = new User("Boyoh", "4322");
    List<User> users = new ArrayList<>();
    users.add(user);
    users.add(user2);
    Gson gson = new Gson();
    var a = gson.toJson(users);
    List<User> users2 = new ArrayList<>();
    User[] users3 = gson.fromJson(a,User[].class);
    users2  = Arrays.asList(users3);
    System.out.println(a);
  }

  private static void writeString(Path path, String data) throws IOException {
    Files.writeString(path, data, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
  }
}
