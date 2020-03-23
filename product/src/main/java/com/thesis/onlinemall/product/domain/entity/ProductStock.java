package com.thesis.onlinemall.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductStock {
  private String productId;
  private int total;
  private String unit;
  private int actual;
  private int warning;

  public ProductStock(String productId, int total, String unit) {
    this.productId = productId;
    this.total = total;
    this.unit = unit;
    this.warning = total / 10;
  }

}
