package com.thesis.onlinemall.product.infrastructure.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "T_CATALOG")
@ToString
public class TCatalog {
  @Id
  @Column(name="CATALOGID")
  private String catalogId;
  @Column(name="CATALOGNAME")
  private String catalogName;
  private String description;
  @Column(name="ORDERNUM")
  private int ordernum;
  @Column(name="PARENTID")
  private String parentId;
  @Column(name="CREATETIME")
  private Date createTime;
  @Column(name="LASTMODIFIEDTIME")
  private Date lastModifiedTime;
  @Column(name="MODIFIER")
  private String modifier;
  private int status;

}
