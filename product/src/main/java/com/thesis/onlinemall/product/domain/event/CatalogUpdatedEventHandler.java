package com.thesis.onlinemall.product.domain.event;

import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import com.thesis.onlinemall.product.infrastructure.entity.TCatalog;
import com.thesis.onlinemall.product.infrastructure.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CatalogUpdatedEventHandler {

  private CatalogRepository catalogRepository;

  @Autowired
  public CatalogUpdatedEventHandler(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  @EventHandler
  public void on(CatalogUpdatedEvent event) {
    CatalogEntity catalogEntity = event.getCatalogEntity();
    System.err.println(catalogEntity.toString());
    catalogRepository.submitCatalog(catalogEntity.getCatalogId(),
        catalogEntity.getModifier(),
        catalogEntity.getLastModifiedTime(),
        catalogEntity.getStatus().ordinal());
    catalogRepository.submitChildCatalog(catalogEntity.getCatalogId(),
        catalogEntity.getModifier(),
        catalogEntity.getLastModifiedTime(),
        catalogEntity.getStatus().ordinal());
    System.out.println(catalogEntity.getCatalogId() + " related record(child catalogs) had been updated");
  }

  @EventHandler
  public void on(ChildCatalogAddedEvent event) throws Exception{
    CatalogEntity catalogEntity = event.getCatalogEntity();
    TCatalog tCatalog = new TCatalog();
    BeanUtils.copyProperties(tCatalog,catalogEntity);
    log.info("saving object=="+tCatalog.toString());
    catalogRepository.save(tCatalog);
  }
}
