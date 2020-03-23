package com.thesis.onlinemall.product.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "T_PRODUCT_STOCK")
@ToString
public class TProductStock {
  @Id
  private String id;
  @Column(name="PRODUCTID")
  private  String productid;
  @Column(name="TOTAL")
  private int total;
  @Column(name="UNIT")
  private String unit;
  @Column(name="ACTUAL")
  private int actual;
  @Column(name="WARNING")
  private int warning;
}
