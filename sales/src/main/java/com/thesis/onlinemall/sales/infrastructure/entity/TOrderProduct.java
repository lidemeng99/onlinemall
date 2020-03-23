package com.thesis.onlinemall.sales.infrastructure.entity;


import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "T_ORDER_PRODUCT")
public class TOrderProduct {

  @Id
  private String id;
  private String orderid;
  private String productid;
  private String productname;
  private BigDecimal unitprice;
  private int quanlity;
  private BigDecimal totalprice;
  private String url;
}
