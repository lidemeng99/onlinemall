package com.thesis.onlinemall.sales.infrastructure.entity;


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "T_ORDER")
@ToString
public class TOrder {
  @Id
  private String orderid;
  private String openid;
  private String shippingid;
  private BigDecimal payment;
  private BigDecimal postage;
  private BigDecimal totalamount;
  private Date paymenttime;
  private Date sendtime;
  private Date endtime;
  private Date closetime;
  private int status;
  private Date createtime;
  private Date lastmodifiedtime;
  private String modifier;
}
