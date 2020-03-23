package com.thesis.onlinemall.product.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
@Data
@NoArgsConstructor
@ToString
public class  CatalogEntity {
  private String catalogId;
  private String catalogName;
  private String description;
  private int orderNum;
  private String parentId;
  private Date createTime;
  private Date lastModifiedTime;
  private String modifier;
  private StatusConstants.STATUS_CATALOG status = StatusConstants.STATUS_CATALOG.DRAFT;


  public void linkTo(String parentId){
    this.parentId = parentId;
  }
  public void submit(){
    this.status = StatusConstants.STATUS_CATALOG.SUBMITTED;
    this.lastModifiedTime = new Date();
  }

  public void approve(){
    if(this.status != StatusConstants.STATUS_CATALOG.SUBMITTED || StringUtils.isEmpty(catalogId)){
      throw new IllegalStateException("just can approve with submitted status!");
    }
    this.status = StatusConstants.STATUS_CATALOG.APPROVED;
    this.lastModifiedTime = new Date();
  }

  public void cancel(){
//    if(this.status != StatusConstants.STATUS_CATALOG.APPROVED || StringUtils.isEmpty(catalogId)){
//      throw new IllegalStateException("just can cancel the approved catalog!");
//    }
    this.status = StatusConstants.STATUS_CATALOG.CANCELED;
    this.lastModifiedTime = new Date();
  }
}
