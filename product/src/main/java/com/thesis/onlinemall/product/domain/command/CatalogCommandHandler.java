package com.thesis.onlinemall.product.domain.command;

import com.thesis.onlinemall.product.domain.aggregate.CatalogAggregate;
import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import com.thesis.onlinemall.product.domain.entity.EntityFactory;
import com.thesis.onlinemall.product.domain.service.CatalogService;
import com.thesis.onlinemall.product.infrastructure.entity.TCatalog;
import com.thesis.onlinemall.product.infrastructure.repository.CatalogRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogCommandHandler {

  private CatalogRepository catalogRepository;
  private Repository<CatalogAggregate> repository;
  private CatalogService catalogService;

  @Autowired
  public CatalogCommandHandler(CatalogRepository catalogRepository,
      Repository<CatalogAggregate> repository, CatalogService catalogService) {
    this.catalogRepository = catalogRepository;
    this.repository = repository;
    this.catalogService = catalogService;
  }

  @CommandHandler
  public void handle(CreateCatalogCommand createCatalogCommand) throws Exception {
    CatalogEntity catalog = EntityFactory
        .create(createCatalogCommand.getCatalogId(), createCatalogCommand.getCatalogName(),
            createCatalogCommand.getDescription(), createCatalogCommand.getOrderNum(),
            createCatalogCommand.getParentId(), createCatalogCommand.getModifier());

    TCatalog tCatalog = new TCatalog();
    BeanUtils.copyProperties(tCatalog, catalog);
    catalogRepository.save(tCatalog);

    repository.newInstance(() -> new CatalogAggregate(catalog));
  }

  @CommandHandler
  public void handle(UpdateCatalogCommand updateCatalogCommand) throws Exception {
    Aggregate<CatalogAggregate> aggregate = repository.load(updateCatalogCommand.getCatalogId());
    CatalogEntity catalog = catalogService.findByID(updateCatalogCommand.getCatalogId());
    if (catalog == null) {
      throw new IllegalArgumentException(
          "can not found<" + updateCatalogCommand.getCatalogId() + ">");
    }
    catalog.setModifier(updateCatalogCommand.getModifier());
    aggregate.execute(aggregateRoot -> aggregateRoot
        .update(catalog, updateCatalogCommand.getCommandType()));
  }

  @CommandHandler
  public void handle(AddChildCatalogCommand addChildCatalogCommand) {
    Aggregate<CatalogAggregate> aggregate = repository.load(addChildCatalogCommand.getParentId());
    aggregate.execute(aggregateRoot -> aggregateRoot
        .addChild(addChildCatalogCommand.getCatalogId(), addChildCatalogCommand.getCatalogName(),
            addChildCatalogCommand.getDescription(),
            addChildCatalogCommand.getOrderNum(), addChildCatalogCommand.getParentId(),
            addChildCatalogCommand.getModifier()));

  }
}
