package com.mbzshajib.mining.processor.uncertain.model;
 /**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/30/2015
 * @time: 7:17 PM
 * ****************************************************************
 */    

public class UInputData {
  private String id ;
  private double prefixValue;
  private double itemPValue;

  public UInputData(String id, double itemPValue) {
   this.id = id;
   this.itemPValue = itemPValue;
  }

  public String getId() {
   return id;
  }

  public void setId(String id) {
   this.id = id;
  }

  public double getPrefixValue() {
   return prefixValue;
  }

  public void setPrefixValue(double prefixValue) {
   this.prefixValue = prefixValue;
  }

  public double getItemPValue() {
   return itemPValue;
  }

  public void setItemPValue(double itemPValue) {
   this.itemPValue = itemPValue;
  }
 }
