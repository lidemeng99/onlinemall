package com.thesis.onlinemall.sales.domain.command;

import com.thesis.onlinemall.sales.domain.aggregate.OrderAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommandConfiguration {

  @Bean
  EventSourcingRepository<OrderAggregate> orderAggregateEventSourcingRepository(
      EventStore eventStore) {
    return EventSourcingRepository.builder(OrderAggregate.class).eventStore(eventStore).build();
  }

}
