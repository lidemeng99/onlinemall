package com.thesis.onlinemall.product.domain.service;

import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import com.thesis.onlinemall.product.domain.entity.StatusConstants.STATUS_CATALOG;
import com.thesis.onlinemall.product.infrastructure.entity.TCatalog;
import com.thesis.onlinemall.product.infrastructure.repository.CatalogRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

  private CatalogRepository catalogRepository;

  @Autowired
  public CatalogService(
      CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  public CatalogEntity findByID(String catalogID)
      throws InvocationTargetException, IllegalAccessException {
    Optional<TCatalog> catalogOptional = catalogRepository.findById(catalogID);
    CatalogEntity catalogEntity = new CatalogEntity();
    if (!catalogOptional.isPresent()) {
      return null;
    }
    TCatalog catalog = catalogOptional.get();
    BeanUtils.copyProperties(catalog,catalogEntity);
    if (catalog.getStatus() == 1) {
      catalogEntity.setStatus(STATUS_CATALOG.SUBMITTED);
    } else if (catalog.getStatus() == 2) {
      catalogEntity.setStatus(STATUS_CATALOG.APPROVED);
    }
    return catalogEntity;
  }
}
