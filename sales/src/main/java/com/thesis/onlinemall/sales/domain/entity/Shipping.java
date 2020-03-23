package com.thesis.onlinemall.sales.domain.entity;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Shipping {
  private String id;
  private String openid;
  private String receiver;
  private String phone;
  private String province;
  private String city;
  private String address;
  private String zip;
  private Date createtime;

  public Shipping(String id, String openid, String receiver, String phone, String province,
      String city, String address, String zip) {
    this.id = id;
    this.openid = openid;
    this.receiver = receiver;
    this.phone = phone;
    this.province = province;
    this.city = city;
    this.address = address;
    this.zip = zip;
    this.createtime = new Date();
  }

}
