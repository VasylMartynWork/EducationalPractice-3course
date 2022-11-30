package com.vmwork.pharmaceuticalcatalog.dataaccess;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author VasylMartynWork
 * @param <T> the type of elements in this list.
 */
public interface Dao<T> {

  /**
   * Returns object which index is written.
   * @param id Index of object which we need.
   * @return Object.
   */
  Optional<T> get(int id);

  /**
   * Returns list with objects.
   * @return List with objects.
   */
  List<T> getAll();

  /**
   * Add new data to JSON file.
   * @param t Object that will be added to JSON.
   */
  void save(T t) throws IOException;

  /**
   * Updates JSON, replaces old data with new data. The index indicates exactly which data needs to be changed.
   * @param t Object with data that replace old object.
   * @param id Index of object that needs to be replaced.
   */
  void update(T t, int id) throws IOException;

  /**
   * Deletes data from JSON, the index indicates exactly which data needs to be deleted.
   * @param id Index of object that needs to be deleted.
   */
  void delete(int id) throws IOException;
}
