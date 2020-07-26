package me.flotsam.frettler.engine;

import lombok.Getter;

public class ChordMetadata {

  @Getter
  String label;
  @Getter
  boolean minorSecond;
  @Getter
  boolean majorSecond;
  @Getter
  boolean augmentedSecond;
  @Getter
  boolean minorThird;
  @Getter
  boolean majorThird;
  @Getter
  boolean perfectFourth;
  @Getter
  boolean diminishedFifth;
  @Getter
  boolean perfectFifth;
  @Getter
  boolean minorSixth;
  @Getter
  boolean majorSixth;
  @Getter
  boolean minorSeventh;
  @Getter
  boolean majorSeventh;
  @Getter
  boolean majorNinth;
  @Getter
  boolean majorEleventh;
  @Getter
  boolean majorRange;
  @Getter
  boolean minorRange;
  @Getter
  boolean suspended;
  
  
  public ChordMetadata() {
    // TODO Auto-generated constructor stub
  }
  
  public enum Quality {
    MAJOR, MINOR, AUGMENTED, DIMINISHED, DOMINANT
  }

}
