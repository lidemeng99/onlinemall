package com.thesis.onlinemall.product.domain.command;

import com.thesis.onlinemall.product.domain.aggregate.CatalogAggregate;
import com.thesis.onlinemall.product.domain.aggregate.ProductAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommandConfiguration {

  @Bean
  EventSourcingRepository<CatalogAggregate> catalogAggregateEventSourcingRepository(
      EventStore eventStore) {
    return EventSourcingRepository.builder(CatalogAggregate.class).eventStore(eventStore).build();
  }

  @Bean
  EventSourcingRepository<ProductAggregate> productAggregateEventSourcingRepository(
      EventStore eventStore) {
    return EventSourcingRepository.builder(ProductAggregate.class).eventStore(eventStore).build();
  }
}
