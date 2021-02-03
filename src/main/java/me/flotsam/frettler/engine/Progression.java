package me.flotsam.frettler.engine;

import lombok.Getter;

public enum Progression {
  
  P1("standard", 1,4,5),
  P2("police", 1,6,4,5),
  P3("toto", 6,4,1,5),
  P4("doowoop", 1,6,2,5),
  P5("dylan", 1,4,5,1),
  P6("kylie", 1,5,6,3,4,1,1,5),
  P7("george", 2,1,5),
  P8("stones", 1,5,6,4);
  
  @Getter private String description;
  @Getter private int[] sequence;
  
  private Progression(String description, int... sequence) {
    this.description = description;
    this.sequence = sequence;
  }

}
