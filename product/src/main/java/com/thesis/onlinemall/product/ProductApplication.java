package com.thesis.onlinemall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {


  public static void main(String[] args) {
    SpringApplication.run(ProductApplication.class, args);
//    // 首先要有个命000000000000令总线
//    CommandBus commandBus = new SimpleCommandBus.Builder().build() ;
//    // 建一个命令网关以便为发送命令方提供友好的api
//    CommandGateway commandGateway = new DefaultCommandGateway.Builder().commandBus(commandBus).build();
//    // 我们准备把事件存在文件里在"events/"文件夹下
//    //EventStore eventStore =
//    EventBus eventBus = new SimpleEventBus();
//    // 配置一下事件仓储把事件存起来
//    EventSourcingRepository repository = new EventSourcingRepository(ToDoItem.class, eventStore);
//    repository.setEventBus(eventBus);
//    // 要让Axon知道哪个类能处理命令总线里的命令，订阅一下
//    AggregateAnnotationCommandHandler.subscribe(ToDoItem.class, repository, commandBus);
//    // 然后我们向命令总线发送一些命令

//    commandGateway.send(new CreateToDoItemCommand(itemId, "8点要上班"));
// );
  }

}
