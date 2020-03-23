package com.thesis.onlinemall.product.domain.entity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ProductPrice {
  private  String productId;
  private BigDecimal price;
  private int taxRate;
}
