package io.flowing.retail.payment.messages;

public class RetrieveSayHelloCommandPayload {

  private long numberOfCommands;
  
  public long getNumberOfCommands() {
    return numberOfCommands;
  }
  public RetrieveSayHelloCommandPayload setNumberOfCommands(long numberOfCommands) {
    this.numberOfCommands = numberOfCommands;
    return this;
  }

}
