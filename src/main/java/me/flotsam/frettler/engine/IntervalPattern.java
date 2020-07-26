package me.flotsam.frettler.engine;

import static me.flotsam.frettler.engine.ScaleInterval.d5;
import static me.flotsam.frettler.engine.ScaleInterval.M2;
import static me.flotsam.frettler.engine.ScaleInterval.M7;
import static me.flotsam.frettler.engine.ScaleInterval.M6;
import static me.flotsam.frettler.engine.ScaleInterval.M3;
import static me.flotsam.frettler.engine.ScaleInterval.m2;
import static me.flotsam.frettler.engine.ScaleInterval.m7;
import static me.flotsam.frettler.engine.ScaleInterval.m6;
import static me.flotsam.frettler.engine.ScaleInterval.m3;
import static me.flotsam.frettler.engine.ScaleInterval.P5;
import static me.flotsam.frettler.engine.ScaleInterval.P4;
import static me.flotsam.frettler.engine.ScaleInterval.P1;
import java.util.Arrays;
import java.util.List;

public enum IntervalPattern {

  // SCALES
  CHROMATIC_SCALE(
      false,
      PatternType.SCALE,
      "Chromatic",
      P1, 
      m2,
      M2,
      m3,
      M3, 
      P4, 
      d5,
      P5, 
      m6,
      M6, 
      m7,
      M7 
  ),
  MAJOR_SCALE(
      true,
      PatternType.SCALE,
      "Major",
      P1, 
      M2,
      M3, 
      P4, 
      P5, 
      M6, 
      M7
  ),
  MELODIC_MINOR_SCALE(
      true,
      PatternType.SCALE,
      "Melodic Minor",
      P1,
      m3,
      P4,
      P5,
      M6,
      M7
  ),
  HARMONIC_MINOR_SCALE(
      true,
      PatternType.SCALE,
      "Harmonic Minor",
      P1,
      M2,
      m3,
      P4,
      P5,
      m6,
      M7
  ),
  MAJOR_PENTATONIC_SCALE(
      true,
      MAJOR_SCALE,
      PatternType.SCALE,
      "Major Pentatonic",
      P1,
      M2,
      M3, 
      P5,
      M6
  ),
  AEOLIAN_MODE(
      true,
      PatternType.MODE,
      "Aeolian",
      P1,
      M2,
      m3,
      P4,
      P5,
      m6,
      m7
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
      P1,
      m3, 
      P4,
      P5,
      m7
  ), 
  BLUES_SCALE(
      false,
      PatternType.SCALE,
      "Blues",
      P1,
      m3, 
      P4,
      d5,
      P5,
      m7
  ),
  DORIAN_MODE(
      true,
      PatternType.MODE,
      "Dorian",
      P1,
      M2,
      m3, 
      P4,
      P5,
      M6,
      m7
  ), 
  MIXOLYDIAN_MODE(
      true,
      PatternType.MODE,
      "Mixolydian",
      P1,
      M2,
      M3, 
      P4,
      P5,
      M6,
      m7
  ), 
  LYDIAN_MODE(
      true,
      PatternType.MODE,
      "Lydian",
      P1,
      M2,
      M3, 
      d5,
      P5,
      M6,
      M7
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
      P1,
      m2,
      m3,
      P4,
      d5,
      m6,
      m7
  ),
  
  
  // TRIADS FOR CHORD GENERATION
  MAJOR_TRIAD(
      true,
      PatternType.CHORD,
      "Major Triad",
      P1, 
      M3, 
      P5 
  ),
  MINOR_TRIAD(
      true,
      PatternType.CHORD,
      "Minor Triad",
      P1, 
      m3, 
      P5 
  ),
  DIMINISHED_TRIAD(
      true,
      PatternType.CHORD,
      "Diminished Triad",
      P1, 
      m3, 
      d5 
  ),

  // QUADRIADS FOR CHORD GENERATION  
  MAJOR_QUADRIAD(
      true,
      PatternType.CHORD,
      "Major Quadriad (7th)",
      P1, 
      M3, 
      P5, 
      M7
  ),
  MINOR_QUADRIAD(
      true,
      PatternType.CHORD,
      "Minor Quadriad  (7th)",
      P1, 
      m3, 
      P5,
      m7
  ),
  DIMINISHED_QUADRIAD(
      true,
      PatternType.CHORD,
      "Diminished Quadriad (7th)",
      P1, 
      m3, 
      d5, 
      m7
  ),
  MINOR_MAJOR_QUADRIAD(
      true,
      PatternType.CHORD,
      "MinorMajor Quadriad (7th)",
      P1, 
      m3, 
      P5,
      M7 
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
