package io.flowing.retail.order.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.flowing.retail.order.messages.Message;
import io.flowing.retail.order.messages.MessageSender;
import io.flowing.retail.order.persistence.OrderRepository;

@Component
public class SayHelloAdapter implements JavaDelegate {
  
  @Autowired
  private MessageSender messageSender;  

  @Autowired
  private OrderRepository orderRepository;  

  @Override
  public void execute(DelegateExecution context) throws Exception {
    long ordersNumber = orderRepository.count();
    String traceId = context.getProcessBusinessKey(); 
    System.err.println("\n\nHello ! 😊 💋 \n\n");
    messageSender.send( //
        new Message<SayHelloCommandPayload>( //
            "SayHelloCommand", //
            traceId, //
            new SayHelloCommandPayload() //
              .setNumberOfCommands(ordersNumber)));
  }

}
