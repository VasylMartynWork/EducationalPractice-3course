package com.killernr.pharmaceuticalcatalog.dataaccess;

public class Medicine {
  private String name;
  private int cost;

  public Medicine(String name, int cost) {
    this.name = name;
    this.cost = cost;
  }

  public String getName() {
    return name;
  }

  public int getCost() {
    return cost;
  }
}
