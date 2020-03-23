package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import lombok.Data;

@Data
public class CatalogUpdatedEvent {
  private CatalogEntity catalogEntity;

  public CatalogUpdatedEvent(CatalogEntity catalogEntity) {
    this.catalogEntity = catalogEntity;
  }
}
