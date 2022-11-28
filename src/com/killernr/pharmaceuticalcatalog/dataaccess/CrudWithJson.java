//package com.killernr.pharmaceuticalcatalog.dataaccess;
//
//import com.google.gson.Gson;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.List;
//
//public class CrudWithJson {
//  public static void addNewData(String name, int cost){
//    Path pathToMedicine = Path.of("resources", "medicine.json");
//    List<Medicine> medicineList = JsonAccess.medicineListFromJson();
//    Medicine medicine = new Medicine(name,cost);
//    medicineList.add(medicine);
//    Gson gson = new Gson();
//    String jsonFromMedicine = gson.toJson(medicineList);
//    try{
//      JsonAccess.writeString(pathToMedicine, jsonFromMedicine);
//    }
//    catch (IOException e){
//      System.out.println("Error: " + e.getMessage());
//    }
//  }
//
//  public static void deleteData(int userChoice){
//    Path pathToMedicine = Path.of("resources", "medicine.json");
//    List<Medicine> medicineList = JsonAccess.medicineListFromJson();
//    medicineList.remove(userChoice);
//    Gson gson = new Gson();
//    String jsonFromMedicine = gson.toJson(medicineList);
//    try{
//      JsonAccess.writeString(pathToMedicine, jsonFromMedicine);
//    }
//    catch (IOException e){
//      System.out.println("Error: " + e.getMessage());
//    }
//  }
//
//  public static void updateData(Medicine medicine, int userChoice){
//    Path pathToMedicine = Path.of("resources", "medicine.json");
//    List<Medicine> medicineList = JsonAccess.medicineListFromJson();
//    medicineList.set(userChoice, medicine);
//    Gson gson = new Gson();
//    String jsonFromMedicine = gson.toJson(medicineList);
//    try{
//      JsonAccess.writeString(pathToMedicine, jsonFromMedicine);
//    }
//    catch (IOException e){
//      System.out.println("Error: " + e.getMessage());
//    }
//
//  }
//
//}
