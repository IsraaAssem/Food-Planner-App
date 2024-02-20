package com.example.foodplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

  @SerializedName("idIngredient")
  @Expose
  int  idIngredient;
  String strIngredient;

  public int getIdIngredient() {
    return idIngredient;
  }

  public void setIdIngredient(int idIngredient) {
    this.idIngredient = idIngredient;
  }

  public String getStrIngredient() {
    return strIngredient;
  }

  public void setStrIngredient(String strIngredient) {
    this.strIngredient = strIngredient;
  }
}
