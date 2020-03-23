package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import lombok.Data;

@Data
public class ChildCatalogAddedEvent {
  private CatalogEntity catalogEntity;

  public ChildCatalogAddedEvent(CatalogEntity catalogEntity) {
    this.catalogEntity = catalogEntity;
  }
}
