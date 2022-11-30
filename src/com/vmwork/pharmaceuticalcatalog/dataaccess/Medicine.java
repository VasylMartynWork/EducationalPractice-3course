package com.vmwork.pharmaceuticalcatalog.dataaccess;

/**
 * Represents medicine. Have two fields: name and cost.
 *
 * @author VasylMartynWork
 */
public class Medicine {

  private String name;
  private double cost;

  public Medicine(String name, double cost) {
    this.name = name;
    this.cost = cost;
  }

  /**
   * Return string that is the name of medicine.
   *
   * @return String that is name of medicine.
   */
  public String getName() {
    return name;
  }

  /**
   * Return number that is a cost of medicine.
   *
   * @return Number that is a cost of medicine.
   */
  public double getCost() {
    return cost;
  }
}
