package com.thesis.onlinemall.product.service;


import com.thesis.onlinemall.product.domain.command.AddChildCatalogCommand;
import com.thesis.onlinemall.product.domain.command.CreateCatalogCommand;
import com.thesis.onlinemall.product.domain.command.UpdateCatalogCommand;
import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import com.thesis.onlinemall.product.domain.entity.StatusConstants.STATUS_CATALOG;
import com.thesis.onlinemall.product.domain.service.CatalogService;
import com.thesis.onlinemall.product.infrastructure.entity.TCatalog;
import com.thesis.onlinemall.product.infrastructure.repository.CatalogRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CatalogRestController {

  private CommandGateway commandGateway;
  private CatalogService catalogService;
  private CatalogRepository catalogRepository;

  @Autowired
  public CatalogRestController(
      CommandGateway commandGateway,
      CatalogService catalogService,
      CatalogRepository catalogRepository) {
    this.commandGateway = commandGateway;
    this.catalogService = catalogService;
    this.catalogRepository = catalogRepository;
  }

  @PostMapping("/catalog")
  public void create(@RequestBody CreateCatalogCommand createCatalogCommand) {
    if (StringUtils.isEmpty(createCatalogCommand.getCatalogId())) {
      createCatalogCommand.setCatalogId(UUID.randomUUID().toString());
    }
    commandGateway.send(createCatalogCommand);
  }

  @PutMapping("/catalog/{id}/status")
  public void submit(@PathVariable(name = "id") String catalogid,
      @RequestParam(required = false, defaultValue = "0") int cqrs_cancel,
      @RequestBody UpdateCatalogCommand command) {
    if (cqrs_cancel == 0) {
      command.setCatalogId(catalogid);
      commandGateway.send(command);
    } else {
      catalogRepository.submitCatalog(catalogid, command.getModifier(), new Date(),
          STATUS_CATALOG.CANCELED.ordinal());
    }

  }

  @PostMapping("/catalog/{id}/child")
  public String addChild(@PathVariable(name = "id") String catalogid,
      @RequestBody AddChildCatalogCommand addChildCatalogCommand) {
    addChildCatalogCommand.setParentId(catalogid);
    addChildCatalogCommand.setCatalogId(UUID.randomUUID().toString());
    commandGateway.send(addChildCatalogCommand);
    return addChildCatalogCommand.getCatalogId();
  }

  @GetMapping("/catalogs")
  public @ResponseBody
  List<CatalogEntity> findall(@RequestParam(required = false) String pid,
      @RequestParam(required = false, defaultValue = "9999") int status) {
    if (StringUtils.isEmpty(pid)) {
      pid = StringUtils.EMPTY;
    }

    List<TCatalog> catalogs = status==9999 ? catalogRepository.findByPID(pid)
        : catalogRepository.findByPID(pid, status);
    List<CatalogEntity> entities = new ArrayList<>(catalogs.size());
    catalogs.forEach(catalog -> {
      CatalogEntity entity = new CatalogEntity();
      BeanUtils.copyProperties(catalog, entity);
      if (catalog.getStatus() == 1) {
        entity.setStatus(STATUS_CATALOG.SUBMITTED);
      } else if (catalog.getStatus() == 2) {
        entity.setStatus(STATUS_CATALOG.APPROVED);
      }
      entities.add(entity);
    });
    return entities;
  }

}
