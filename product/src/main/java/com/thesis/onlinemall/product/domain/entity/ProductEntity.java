package com.thesis.onlinemall.product.domain.entity;

import com.thesis.onlinemall.product.domain.entity.StatusConstants.STATUS_PRODUCT;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class ProductEntity {
  private String productId;
  private String productName;
  private String shortDesc;
  private String fullDesc;
  private String thumbnail;
  private String catalogId;
  private String catalogName;
  private Date createTime;
  private Date lastModifiedTime;
  private String modifier;
  private StatusConstants.STATUS_PRODUCT status;
  private int stock;
  private String unit;
  private BigDecimal price;

  private ProductStock actualStock;

  public ProductEntity() {
  }

  public ProductEntity(String productId, String productName, String shortDesc,
      String fullDesc, String thumbnail, String catalogId, String catalogName,
      String modifier) {
    this.productId = productId;
    this.productName = productName;
    this.shortDesc = shortDesc;
    this.fullDesc = fullDesc;
    this.thumbnail = thumbnail;
    this.catalogId = catalogId;
    this.catalogName = catalogName;
    this.modifier = modifier;
  }

  /**
   * initially state
   */
  public void submit(){
    this.status=STATUS_PRODUCT.SUBMITTED;
    this.lastModifiedTime = new Date();
  }
  /**
   * Stock apply
   */
  public void applyStock(){
    if(this.status != STATUS_PRODUCT.SUBMITTED){
      throw new IllegalStateException("excepted:SUBMITTED,actual:"+this.status);
    }
    this.status=STATUS_PRODUCT.STOCKING;
    this.lastModifiedTime = new Date();
  }

  /**
   * Approve Stock applicant
   */
  public void stock(){
    if(this.status != STATUS_PRODUCT.STOCKING){
      throw new IllegalStateException("excepted:STOCKING,actual:"+this.status);
    }
    this.status=STATUS_PRODUCT.STOCKED;
    this.lastModifiedTime = new Date();
  }

  /**
   * apply price
   */
  public void applyPrice(){
    if(this.status != STATUS_PRODUCT.STOCKED){
      throw new IllegalStateException("excepted:STOCKED,actual:"+this.status);
    }
    this.status=STATUS_PRODUCT.PRICING;
    this.lastModifiedTime = new Date();
  }

  /**
   * apply price
   */
  public void price(){
    if(this.status != STATUS_PRODUCT.PRICING){
      throw new IllegalStateException("excepted:PRICING,actual:"+this.status);
    }
    this.status=STATUS_PRODUCT.PRICED;
    this.lastModifiedTime = new Date();
  }

  /**
   * apply launch
   */
  public void applyLaunch(){
    if(this.status != STATUS_PRODUCT.PRICED){
      throw new IllegalStateException("excepted:PRICED,actual:"+this.status);
    }
    this.status=STATUS_PRODUCT.LAUNCHING;
    this.lastModifiedTime = new Date();
  }

  /**
   * launch
   */
  public void launch(){
    if(this.status != STATUS_PRODUCT.LAUNCHING){
      throw new IllegalStateException("excepted:LAUNCHING,actual:"+this.status);
    }
    this.status=STATUS_PRODUCT.LAUNCHED;
    this.lastModifiedTime = new Date();
  }

  /**
   * apply cancel
   */
  public void applyCancel(){
    if(this.status != STATUS_PRODUCT.LAUNCHED){
      throw new IllegalStateException("excepted:LAUNCHED,actual:"+this.status);
    }
    this.status=STATUS_PRODUCT.CANCELLING;
    this.lastModifiedTime = new Date();
  }

  /**
   * launch
   */
  public void cancel(){
    if(this.status != STATUS_PRODUCT.CANCELLING){
      throw new IllegalStateException("excepted:CANCELLING,actual:"+this.status);
    }
    this.status=STATUS_PRODUCT.CANCELLED;
    this.lastModifiedTime = new Date();
  }

  public void delete(){
    this.setStatus(STATUS_PRODUCT.DELETED);
  }
}
