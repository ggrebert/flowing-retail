package io.flowing.retail.payment.messages;

import java.io.IOException;

import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableBinding(Sink.class)
public class MessageListener {  
  
  @Autowired
  private ProcessEngine camunda;

  @Autowired
  private ObjectMapper objectMapper;

  @StreamListener(target = Sink.INPUT, 
      condition="(headers['type']?:'')=='RetrievePaymentCommand'")
  @Transactional
  public void retrievePaymentCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<RetrievePaymentCommandPayload> message = objectMapper.readValue(messageJson, new TypeReference<Message<RetrievePaymentCommandPayload>>(){});
    RetrievePaymentCommandPayload retrievePaymentCommand = message.getData();    
    
    System.out.println("Retrieve payment: " + retrievePaymentCommand.getAmount() + " for " + retrievePaymentCommand.getRefId());
    
    camunda.getRuntimeService().createMessageCorrelation(message.getType()) //
      .processInstanceBusinessKey(message.getTraceid())
      .setVariable("amount", retrievePaymentCommand.getAmount()) //
      .setVariable("remainingAmount", retrievePaymentCommand.getAmount()) //
      .setVariable("refId", retrievePaymentCommand.getRefId()) //
      .setVariable("correlationId", message.getCorrelationid()) //
      .correlateWithResult();    
  }

  @StreamListener(target = Sink.INPUT,
          condition="(headers['type']?:'')=='SayHelloCommand'")
  @Transactional
  public void retrieveSayHelloCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<RetrieveSayHelloCommandPayload> message = objectMapper.readValue(messageJson, new TypeReference<Message<RetrieveSayHelloCommandPayload>>(){});
    RetrieveSayHelloCommandPayload retrieveSayHelloCommand = message.getData();

    System.out.println("Say hello, number of commands :" + retrieveSayHelloCommand.getNumberOfCommands());

    camunda.getRuntimeService().createMessageCorrelation(message.getType()) //
            .processInstanceBusinessKey(message.getTraceid())
            .setVariable("numberOfCommands", retrieveSayHelloCommand.getNumberOfCommands()) //
            .setVariable("correlationId", message.getCorrelationid()) //
            .correlateWithResult();
  }
    
    
}
