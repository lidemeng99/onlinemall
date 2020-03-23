package com.thesis.onlinemall.product.infrastructure.entity;

import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "T_PRODUCT")
public class TProduct {
  @Id
  @Column(name="PRODUCTID")
  private String productId;
  @Column(name="PRODUCTNAME")
  private String productName;
  @Column(name="SHORTDESC")
  private String shortDesc;
  @Column(name="FULLDESC")
  private String fullDesc;
  private String thumbnail;
  @Column(name="CATALOGID")
  private String catalogId;
  @Column(name="CREATETIME")
  private Date createTime;
  @Column(name="LASTMODIFIEDTIME")
  private Date lastModifiedTime;
  @Column(name="MODIFIER")
  private String modifier;
  @Column(name = "CATALOGNAME")
  private String catalogName;

  private int status;
  private int stock;
  private String unit;
  private BigDecimal price;

}
