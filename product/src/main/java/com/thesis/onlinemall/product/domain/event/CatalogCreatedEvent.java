package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import com.thesis.onlinemall.product.domain.entity.StatusConstants;
import lombok.Data;

import java.util.Date;

@Data
public class CatalogCreatedEvent {
  private String catalogId;
  private Date receiveTime;
  private StatusConstants.STATUS_CATALOG status;
  private CatalogEntity catalog;

  @Override
  public String toString() {
    return "CatalogCreatedEvent{" +
        "catalogId='" + catalogId + '\'' +
        ", receiveTime='" + receiveTime + '\'' +
        ", status=" + status +
        ", catalog=" + catalog.toString() +
        '}';
  }
}
