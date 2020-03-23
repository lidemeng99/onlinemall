package com.thesis.onlinemall.sales.infrastructure.entity;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "T_SHIPPING")
public class TShipping {

  @Id
  private String id;
  private String openid;
  private String receiver;
  private String phone;
  private String province;
  private String city;
  private String address;
  private String zip;
  private Date createtime;

}
