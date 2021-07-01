package io.flowing.retail.order.flow;

public class SayHelloCommandPayload {

  private long numberOfCommands;

  public long getNumberOfCommands() {
    return numberOfCommands;
  }

  public SayHelloCommandPayload setNumberOfCommands(long numberOfCommands) {
    this.numberOfCommands = numberOfCommands;
    return this;
  }
}
