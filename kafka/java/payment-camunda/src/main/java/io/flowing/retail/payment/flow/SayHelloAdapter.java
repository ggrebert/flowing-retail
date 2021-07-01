package io.flowing.retail.payment.flow;

import io.flowing.retail.payment.messages.Message;
import io.flowing.retail.payment.messages.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SayHelloAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    long refId = (long) context.getVariable("numberOfCommands");
    String correlationId = (String) context.getVariable("correlationId");
    String traceId = context.getProcessBusinessKey();

    messageSender.send( //
        new Message<SayHelloReceivedPayload>( //
            "SayHelloReceivedEvent", //
            traceId, //
            new SayHelloReceivedPayload()) //
    		.setCorrelationid(correlationId));
  }

}
