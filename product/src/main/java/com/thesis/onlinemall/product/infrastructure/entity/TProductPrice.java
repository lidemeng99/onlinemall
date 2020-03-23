package com.thesis.onlinemall.product.infrastructure.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "T_PRODUCT_PRICE")
public class TProductPrice {
  @Id
  private String id;
  @Column(name = "PRODUCTID")
  private BigDecimal price;
  @Column(name = "TAXRATE")
  private int taxRate;


}
