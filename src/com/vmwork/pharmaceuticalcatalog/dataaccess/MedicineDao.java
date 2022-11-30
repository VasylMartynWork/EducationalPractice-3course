package com.vmwork.pharmaceuticalcatalog.dataaccess;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * The implementation of the DAO pattern for the Medicine class provides CRUD capabilities
 *
 * @author VasylMartynWork
 */
public class MedicineDao implements Dao<Medicine> {

  private List<Medicine> medicineList;

  public MedicineDao() {
    medicineList = JsonAccess.medicineListFromJson();
  }

  /**
   * Returns an object of the Medicine class at the specified index.
   *
   * @param id Index of object which we need.
   * @return Object of the Medicine class.
   */
  @Override
  public Optional<Medicine> get(int id) {
    return Optional.ofNullable(medicineList.get(id));
  }

  /**
   * Returns list with objects of Medicine class.
   *
   * @return List with objects of Medicine class.
   */
  @Override
  public List<Medicine> getAll() {
    return medicineList;
  }

  /**
   * Add data about medicine to JSON.
   *
   * @param medicine Object whose data will be added to JSON.
   * @throws IOException If file don't exist.
   */
  @Override
  public void save(Medicine medicine) throws IOException {
    Path pathToMedicineJson = Path.of("resources", "medicine.json");
    medicineList.add(medicine);
    Gson gson = new Gson();
    String jsonFromMedicine = gson.toJson(medicineList);
    try {
      JsonAccess.writeString(pathToMedicineJson, jsonFromMedicine);
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Updates JSON, changes the old data to the data entered by the user.
   *
   * @param medicine New data.
   * @param id       Index of element of JSON that will be changed.
   * @throws IOException If file don't exist.
   */
  @Override
  public void update(Medicine medicine, int id) throws IOException {
    Path pathToMedicineJson = Path.of("resources", "medicine.json");
    medicineList.set(id, medicine);
    Gson gson = new Gson();
    String jsonFromMedicine = gson.toJson(medicineList);
    try {
      JsonAccess.writeString(pathToMedicineJson, jsonFromMedicine);
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }


  /**
   * Deletes data about medicine from JSON.
   *
   * @param id Index of object that needs to be deleted.
   * @throws IOException If file don't exist.
   */
  @Override
  public void delete(int id) throws IOException {
    Path pathToMedicineJson = Path.of("resources", "medicine.json");
    medicineList.remove(id);
    Gson gson = new Gson();
    String jsonFromMedicine = gson.toJson(medicineList);
    try {
      JsonAccess.writeString(pathToMedicineJson, jsonFromMedicine);
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
