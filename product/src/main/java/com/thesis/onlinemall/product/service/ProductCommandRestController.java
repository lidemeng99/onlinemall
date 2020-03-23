package com.thesis.onlinemall.product.service;

import com.thesis.onlinemall.product.domain.command.CreateProductCommand;
import com.thesis.onlinemall.product.domain.command.ProductLifecycleCommand;
import com.thesis.onlinemall.product.domain.command.StockDeductCommand;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ProductCommandRestController {


  private CommandGateway commandGateway;

  @Autowired
  public ProductCommandRestController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping("/product")
  public String create(@RequestBody CreateProductCommand createProductCommand) {
    createProductCommand.setProductId(UUID.randomUUID().toString());
    commandGateway.send(createProductCommand);
    return createProductCommand.getProductId();
  }

  @PutMapping("/product/{id}/status")
  public void updateState(@PathVariable(name = "id") String productid,
      @RequestBody ProductLifecycleCommand productLifecycleCommand) {
    productLifecycleCommand.setProductId(productid);
    if (productLifecycleCommand.getProduct() != null) {
      productLifecycleCommand.getProduct().setProductId(productid);
    }

    commandGateway.sendAndWait(productLifecycleCommand);
  }

  @PostMapping("/product/{id}/stock/deduction")
  public void deductStock(@PathVariable String id,
      @RequestBody StockDeductCommand stockDeductCommand) {
    stockDeductCommand.setProductId(id);
    commandGateway.sendAndWait(stockDeductCommand);
  }

}
