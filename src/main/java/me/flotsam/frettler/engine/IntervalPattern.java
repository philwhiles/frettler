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
import static me.flotsam.frettler.engine.ScaleInterval.PERFECT_UNISON;
import java.util.Arrays;
import java.util.List;

public enum IntervalPattern {

  // SCALES
  CHROMATIC_SCALE(
      false,
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
      true,
      PatternType.SCALE,
      "Major",
      PERFECT_UNISON, 
      MAJOR_SECOND,
      MAJOR_THIRD, 
      PERFECT_FOURTH, 
      PERFECT_FIFTH, 
      MAJOR_SIXTH, 
      MAJOR_SEVENTH
  ),
  MELODIC_MINOR_SCALE(
      true,
      PatternType.SCALE,
      "Melodic Minor",
      PERFECT_UNISON,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MAJOR_SIXTH,
      MAJOR_SEVENTH
  ),
  HARMONIC_MINOR_SCALE(
      true,
      PatternType.SCALE,
      "Harmonic Minor",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MINOR_SIXTH,
      MAJOR_SEVENTH
  ),
  MAJOR_PENTATONIC_SCALE(
      true,
      MAJOR_SCALE,
      PatternType.SCALE,
      "Major Pentatonic",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MAJOR_THIRD, 
      PERFECT_FIFTH,
      MAJOR_SIXTH
  ),
  AEOLIAN_MODE(
      true,
      PatternType.MODE,
      "Aeolian",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MINOR_SIXTH,
      MINOR_SEVENTH
  ),
  MINOR_SCALE(
      true,
      AEOLIAN_MODE,
      PatternType.SCALE,
      "Minor Scale"
  ),
  MINOR_PENTATONIC_SCALE(
      true,
      MINOR_SCALE,
      PatternType.SCALE,
      "Minor Pentatonic",
      PERFECT_UNISON,
      MINOR_THIRD, 
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MINOR_SEVENTH
  ), 
  BLUES_SCALE(
      false,
      PatternType.SCALE,
      "Blues",
      PERFECT_UNISON,
      MINOR_THIRD, 
      PERFECT_FOURTH,
      DIMINISHED_FIFTH,
      PERFECT_FIFTH,
      MINOR_SEVENTH
  ),
  DORIAN_MODE(
      true,
      PatternType.MODE,
      "Dorian",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MINOR_THIRD, 
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MAJOR_SIXTH,
      MINOR_SEVENTH
  ), 
  MIXOLYDIAN_MODE(
      true,
      PatternType.MODE,
      "Mixolydian",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MAJOR_THIRD, 
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MAJOR_SIXTH,
      MINOR_SEVENTH
  ), 
  LYDIAN_MODE(
      true,
      PatternType.MODE,
      "Lydian",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MAJOR_THIRD, 
      DIMINISHED_FIFTH,
      PERFECT_FIFTH,
      MAJOR_SIXTH,
      MAJOR_SEVENTH
  ), 
  IONIAN_MODE(
      true,
      MAJOR_SCALE,
      PatternType.MODE,
      "Ionian"
  ), 
  LOCRIAN_MODE(
      true,
      PatternType.MODE,
      "Aeolian",
      PERFECT_UNISON,
      MINOR_SECOND,
      MINOR_THIRD,
      PERFECT_FOURTH,
      DIMINISHED_FIFTH,
      MINOR_SIXTH,
      MINOR_SEVENTH
  ),
  
  
  // TRIADS FOR CHORD GENERATION
  MAJOR_TRIAD(
      true,
      PatternType.CHORD,
      "Major Triad",
      PERFECT_UNISON, 
      MAJOR_THIRD, 
      PERFECT_FIFTH 
  ),
  MINOR_TRIAD(
      true,
      PatternType.CHORD,
      "Minor Triad",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      PERFECT_FIFTH 
  ),
  DIMINISHED_TRIAD(
      true,
      PatternType.CHORD,
      "Diminished Triad",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      DIMINISHED_FIFTH 
  ),

  // QUADRIADS FOR CHORD GENERATION  
  MAJOR_QUADRIAD(
      true,
      PatternType.CHORD,
      "Major Quadriad (7th)",
      PERFECT_UNISON, 
      MAJOR_THIRD, 
      PERFECT_FIFTH, 
      MAJOR_SEVENTH
  ),
  MINOR_QUADRIAD(
      true,
      PatternType.CHORD,
      "Minor Quadriad  (7th)",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      PERFECT_FIFTH,
      MINOR_SEVENTH
  ),
  DIMINISHED_QUADRIAD(
      true,
      PatternType.CHORD,
      "Diminished Quadriad (7th)",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      DIMINISHED_FIFTH, 
      MINOR_SEVENTH
  ),
  MINOR_MAJOR_QUADRIAD(
      true,
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
  private IntervalPattern parentPattern;
  private boolean scaleChordGenerationSupported;

  private IntervalPattern(boolean scaleChordGenerationSupported, IntervalPattern aliasPattern, PatternType patternType,String label) {
    this.scaleChordGenerationSupported = scaleChordGenerationSupported;
    this.patternType = patternType;
    this.label = label;
    this.intervals = aliasPattern.intervals;
  }
  private IntervalPattern(boolean scaleChordGenerationSupported, IntervalPattern aliasPattern, String label) {
    this.scaleChordGenerationSupported = scaleChordGenerationSupported;
    this.patternType = aliasPattern.patternType;
    this.label = label;
    this.intervals = aliasPattern.intervals;
   
  }
  private IntervalPattern(boolean scaleChordGenerationSupported, IntervalPattern parentPattern, PatternType patternType, String label, ScaleInterval ... intervals) {
    this(scaleChordGenerationSupported, patternType, label, intervals);
    this.parentPattern = parentPattern;
  }
  private IntervalPattern(boolean scaleChordGenerationSupported, PatternType patternType, String label, ScaleInterval ... intervals) {
    this.scaleChordGenerationSupported = scaleChordGenerationSupported;
    this.patternType = patternType;
    this.label = label;
    this.intervals = Arrays.asList(intervals);
  }

  public PatternType getPatternType() {
    return patternType;
  }
  public IntervalPattern getParentPattern() {
    return parentPattern;
  }
  public List<ScaleInterval> getIntervals() {
    return intervals;
  }
  public String getLabel() {
    return label;
  }
  public boolean isScaleChordGenerationSupported() {
    return scaleChordGenerationSupported;
  }
  
  public enum PatternType {
    CHORD, SCALE, MODE
  }
}
