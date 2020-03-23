package com.thesis.onlinemall.product.domain.entity;

import com.thesis.onlinemall.product.domain.entity.StatusConstants.STATUS_PRODUCT;
import java.util.Date;

public class EntityFactory {

  public static CatalogEntity create(String catalogId,
      String catalogName,
      String description,
      int orderNum,
      String parentId,
      String modifier) {
    CatalogEntity entity = new CatalogEntity();
    entity.setCatalogId(catalogId);
    entity.setCatalogName(catalogName);
    entity.setDescription(description);
    entity.setOrderNum(orderNum);
    entity.setParentId(parentId);
    entity.setModifier(modifier);
    entity.setCreateTime(new Date());
    entity.setLastModifiedTime(new Date());
    entity.setStatus(StatusConstants.STATUS_CATALOG.DRAFT);
    return entity;
  }

  public static ProductEntity createProduct(String productId,
      String productName,
      String shortDesc,
      String fullDesc,
      String thumbnail,
      String catalogId,
      String modifier,
      String catalogName) {
    ProductEntity entity = new ProductEntity();
    entity.setProductId(productId);
    entity.setProductName(productName);
    entity.setShortDesc(shortDesc);
    entity.setFullDesc(fullDesc);
    entity.setThumbnail(thumbnail);
    entity.setCatalogId(catalogId);
    entity.setCatalogName(catalogName);
    entity.setModifier(modifier);
    entity.setCreateTime(new Date());
    entity.setLastModifiedTime(new Date());
    entity.setStatus(STATUS_PRODUCT.SUBMITTED);
    return entity;
  }
}
