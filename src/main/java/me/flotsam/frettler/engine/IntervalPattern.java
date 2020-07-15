package me.flotsam.frettler.engine;

import static me.flotsam.frettler.engine.ScaleInterval.DIMINISHED_FIFTH;
import static me.flotsam.frettler.engine.ScaleInterval.MAJOR_SECOND;
import static me.flotsam.frettler.engine.ScaleInterval.MAJOR_SEVENTH;
import static me.flotsam.frettler.engine.ScaleInterval.MAJOR_SIXTH;
import static me.flotsam.frettler.engine.ScaleInterval.MAJOR_THIRD;
import static me.flotsam.frettler.engine.ScaleInterval.MINOR_SECOND;
import static me.flotsam.frettler.engine.ScaleInterval.MINOR_SEVENTH;
import static me.flotsam.frettler.engine.ScaleInterval.MINOR_SIXTH;
import static me.flotsam.frettler.engine.ScaleInterval.MINOR_THIRD;
import static me.flotsam.frettler.engine.ScaleInterval.PERFECT_FIFTH;
import static me.flotsam.frettler.engine.ScaleInterval.PERFECT_FOURTH;
import static me.flotsam.frettler.engine.ScaleInterval.PERFECT_OCTAVE;
import static me.flotsam.frettler.engine.ScaleInterval.PERFECT_UNISON;
import java.util.Arrays;
import java.util.List;

public enum IntervalPattern {

  CHROMATIC_SCALE(
      PatternType.SCALE,
      "Chromatic",
      PERFECT_UNISON, 
      MINOR_SECOND,
      MAJOR_SECOND,
      MINOR_THIRD,
      MAJOR_THIRD, 
      PERFECT_FOURTH, 
      DIMINISHED_FIFTH,
      PERFECT_FIFTH, 
      MINOR_SIXTH,
      MAJOR_SIXTH, 
      MINOR_SEVENTH,
      MAJOR_SEVENTH 
  ),
  MAJOR_SCALE(
      PatternType.SCALE,
      "Major",
      PERFECT_UNISON, 
      MAJOR_SECOND,
      MAJOR_THIRD, 
      PERFECT_FOURTH, 
      PERFECT_FIFTH, 
      MAJOR_SIXTH, 
      MAJOR_SEVENTH,
      PERFECT_UNISON
  ),
  NATURAL_MINOR_SCALE(
      PatternType.SCALE,
      "Natural Minor",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MINOR_SIXTH,
      MINOR_SEVENTH,
      PERFECT_UNISON
  ),
  MELODIC_MINOR_SCALE(
      PatternType.SCALE,
      "Melodic Minor",
      PERFECT_UNISON,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MAJOR_SIXTH,
      MAJOR_SEVENTH,
      PERFECT_UNISON
  ),
  HARMONIC_MINOR_SCALE(
      PatternType.SCALE,
      "Harmonic Minor",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MINOR_SIXTH,
      MAJOR_SEVENTH,
      PERFECT_UNISON
  ),
  PENTATONIC_SCALE(
      PatternType.SCALE,
      "Pentatonic",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MAJOR_THIRD, 
      PERFECT_FIFTH,
      MAJOR_SIXTH,
      PERFECT_UNISON
  ),
  
  // TRIADS FOR CHORD GENERATION
  MAJOR_TRIAD(
      PatternType.CHORD,
      "Major Triad",
      PERFECT_UNISON, 
      MAJOR_THIRD, 
      PERFECT_FIFTH 
  ),
  MINOR_TRIAD(
      PatternType.CHORD,
      "Minor Triad",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      PERFECT_FIFTH 
  ),
  DIMINISHED_TRIAD(
      PatternType.CHORD,
      "Diminished Triad",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      DIMINISHED_FIFTH 
  ),

  // QUADRIADS FOR CHORD GENERATION  
  MAJOR_QUADRIAD(
      PatternType.CHORD,
      "Major Quadriad (7th)",
      PERFECT_UNISON, 
      MAJOR_THIRD, 
      PERFECT_FIFTH, 
      MAJOR_SEVENTH
  ),
  MINOR_QUADRIAD(
      PatternType.CHORD,
      "Minor Quadriad  (7th)",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      PERFECT_FIFTH,
      MINOR_SEVENTH
  ),
  DIMINISHED_QUADRIAD(
      PatternType.CHORD,
      "Diminished Quadriad (7th)",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      DIMINISHED_FIFTH, 
      MINOR_SEVENTH
  ),
  MINOR_MAJOR_QUADRIAD(
      PatternType.CHORD,
      "MinorMajor Quadriad (7th)",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      PERFECT_FIFTH,
      MAJOR_SEVENTH 
  );
  


  private String label;
  private List<ScaleInterval> intervals;
  private PatternType patternType;

  private IntervalPattern(PatternType patternType, String label, ScaleInterval ... intervals) {
    this.patternType = patternType;
    this.label = label;
    this.intervals = Arrays.asList(intervals);
  }

  public PatternType getPatternType() {
    return patternType;
  }
  public List<ScaleInterval> getIntervals() {
    return intervals;
  }
  public String getLabel() {
    return label;
  }
  
  public enum PatternType {
    CHORD, SCALE
  }
}
