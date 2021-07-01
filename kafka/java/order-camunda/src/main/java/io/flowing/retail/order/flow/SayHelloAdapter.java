package io.flowing.retail.order.flow;

import io.flowing.retail.order.messages.Message;
import io.flowing.retail.order.messages.MessageSender;
import io.flowing.retail.order.persistence.OrderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    
    messageSender.send( //
        new Message<SayHelloCommandPayload>( //
            "SayHelloCommand", //
            traceId, //
            new SayHelloCommandPayload() //
              .setNumberOfCommands(ordersNumber)));
  }

}
