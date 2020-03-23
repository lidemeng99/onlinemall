package com.thesis.onlinemall.product.domain.command;

import com.thesis.onlinemall.product.domain.aggregate.ProductAggregate;
import com.thesis.onlinemall.product.domain.command.CommandConstants.PRODUCT_COMMAND;
import com.thesis.onlinemall.product.domain.entity.EntityFactory;
import com.thesis.onlinemall.product.domain.entity.ProductEntity;
import com.thesis.onlinemall.product.domain.entity.StatusConstants.STATUS_PRODUCT;
import com.thesis.onlinemall.product.infrastructure.entity.TProduct;
import com.thesis.onlinemall.product.infrastructure.repository.ProductRepository;
import java.util.Optional;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandHandler {

  private Repository<ProductAggregate> repository;
  private ProductRepository productRepository;

  @Autowired
  public ProductCommandHandler(
      Repository<ProductAggregate> repository,
      ProductRepository productRepository) {
    this.repository = repository;
    this.productRepository = productRepository;
  }

  @CommandHandler
  public void handle(CreateProductCommand createProductCommand) throws Exception {
    ProductEntity productEntity = EntityFactory.createProduct(createProductCommand.getProductId(),
        createProductCommand.getProductName(),
        createProductCommand.getShortDesc(),
        createProductCommand.getFullDesc(),
        createProductCommand.getThumbnail(),
        createProductCommand.getCatalogId(),
        createProductCommand.getModifier(),
        createProductCommand.getCatalogName());
    TProduct product = new TProduct();
    BeanUtils.copyProperties(productEntity, product);
    product.setStatus(productEntity.getStatus().ordinal());
    productRepository.save(product);

    repository.newInstance(() -> new ProductAggregate(productEntity));
  }

  @CommandHandler
  public void handle(ProductLifecycleCommand productLifecycleCommand) {
    Aggregate<ProductAggregate> aggregate = repository.load(productLifecycleCommand.getProductId());
    Optional<TProduct> productOptional = productRepository
        .findById(productLifecycleCommand.getProductId());
    if (!productOptional.isPresent()) {
      throw new IllegalArgumentException(
          "can not found<" + productLifecycleCommand.getProductId() + ">");
    }
    TProduct tProduct = productOptional.get();
    ProductEntity productEntity = new ProductEntity();
    BeanUtils.copyProperties(tProduct, productEntity);
    productEntity.setStatus(STATUS_PRODUCT.values()[tProduct.getStatus()]);
    productEntity.setModifier(productLifecycleCommand.getOperator());
    ProductEntity product = productLifecycleCommand.getProduct();
    if(product != null &&
        (productLifecycleCommand.getCommand() == PRODUCT_COMMAND.STOCK_APPLY
            || productLifecycleCommand.getCommand() == PRODUCT_COMMAND.STOCK_APPROVE)){
      productEntity.setStock(product.getStock());
      productEntity.setUnit(product.getUnit());
    }else if(product != null &&
        (productLifecycleCommand.getCommand() == PRODUCT_COMMAND.PRICE_APPLY
            || productLifecycleCommand.getCommand() == PRODUCT_COMMAND.PRICE_APPROVE)){
      productEntity.setPrice(product.getPrice());
    }

    aggregate.execute(aggregateRoot -> aggregateRoot
        .apply(productEntity,productLifecycleCommand.getCommand()));
  }
}
