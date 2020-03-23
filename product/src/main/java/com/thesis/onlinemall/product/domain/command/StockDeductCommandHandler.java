package com.thesis.onlinemall.product.domain.command;

import com.thesis.onlinemall.product.domain.aggregate.ProductAggregate;
import com.thesis.onlinemall.product.infrastructure.repository.StockRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockDeductCommandHandler {

  private Repository<ProductAggregate> repository;

  @Autowired
  public StockDeductCommandHandler(
      Repository<ProductAggregate> repository) {
    this.repository = repository;
  }

  @CommandHandler
  public void handle(StockDeductCommand stockDeductCommand) {
    Aggregate<ProductAggregate> aggregate = repository.load(stockDeductCommand.getProductId());
    aggregate.execute(aggregateRoot -> aggregateRoot
        .deduct(stockDeductCommand.getProductId(), stockDeductCommand.getDeduct()));
  }
}
