package me.flotsam.frettler.engine;

import lombok.Data;

@Data
public class ChordAnalyzer {

  private boolean flatSecond;
  private boolean second;
  private boolean augmentedSecond;
  private boolean flatThird;
  private boolean third;
//  private boolean tritone;
  private boolean fourth;
  private boolean flatFifth;
  private boolean fifth;
  private boolean augmentedFifth;
  private boolean sixth;
  private boolean flatSeventh;
  private boolean seventh;
  private boolean ninth;
  private boolean eleventh;
  private boolean majorRange;
  private boolean minorRange;
  private boolean suspended;

  public ChordAnalyzer(Chord chord) {

//    flatSecond = chord.containsIntervals(ScaleInterval.DIMINISHED_SECOND);
    flatSecond = chord.containsIntervals(ScaleInterval.PERFECT_UNISON);
    second = chord.containsIntervals(ScaleInterval.MAJOR_SECOND);
    flatThird = chord.containsIntervals(ScaleInterval.MINOR_THIRD);
    third = chord.containsIntervals(ScaleInterval.MAJOR_THIRD);
//    tritone = chord.contains(ScaleInterval.TRITONE);
    fourth = chord.containsIntervals(ScaleInterval.PERFECT_FOURTH);
    flatFifth = chord.containsIntervals(ScaleInterval.DIMINISHED_FIFTH);
    fifth = chord.containsIntervals(ScaleInterval.PERFECT_FIFTH);
//    augmentedFifth = chord.containsIntervals(ScaleInterval.AUGMENTED_FIFTH);
    augmentedFifth = chord.containsIntervals(ScaleInterval.MINOR_SIXTH);
    sixth = chord.containsIntervals(ScaleInterval.MAJOR_SIXTH);
    flatSeventh = chord.containsIntervals(ScaleInterval.MINOR_SEVENTH);
    seventh = chord.containsIntervals(ScaleInterval.MAJOR_SEVENTH);
    ninth = chord.containsIntervals(ScaleInterval.NINTH);
    eleventh = chord.containsIntervals(ScaleInterval.ELEVENTH);
    
    majorRange = (third || fifth) && !(flatThird || flatFifth);
    minorRange = flatThird && !third;
    suspended = (!third && !flatThird && (fourth || second));
  }

  // public boolean equals(Object o) {
  // if(o == null) return false;
  // if(!(o instanceof SequenceAnalyzer)) return false;
  // SequenceAnalyzer other = (SequenceAnalyzer)o;
  //
  // return
  // second == other.second &&
  // third == other.third &&
  // fourth == other.fourth &&
  // fifth == other.fifth &&
  // sixth == other.sixth &&
  // seventh == other.seventh &&
  // flatThird == other.flatThird &&
  // flatFifth == other.flatFifth &&
  // flatSeventh == other.flatSeventh &&
  // augmentedFifth == other.augmentedFifth &&
  // tritone == other.tritone;
  // }
  //

}
