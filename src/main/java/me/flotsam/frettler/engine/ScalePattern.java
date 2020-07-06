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

public enum ScalePattern {

  CHROMATIC(
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
  MAJOR(
      "Major",
      PERFECT_UNISON, 
      MAJOR_SECOND,
      MAJOR_THIRD, 
      PERFECT_FOURTH, 
      PERFECT_FIFTH, 
      MAJOR_SIXTH, 
      MAJOR_SEVENTH, 
      PERFECT_OCTAVE
  ),
  NATURAL_MINOR(
      "Natural Minor",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MINOR_SIXTH,
      MINOR_SEVENTH,
      PERFECT_OCTAVE
  ),
  MELODIC_MINOR(
      "Melodic Minor",
      PERFECT_UNISON,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MAJOR_SIXTH,
      MAJOR_SEVENTH,
      PERFECT_OCTAVE
  ),
  HARMONIC_MINOR(
      "Harmonic Minor",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MINOR_THIRD,
      PERFECT_FOURTH,
      PERFECT_FIFTH,
      MINOR_SIXTH,
      MAJOR_SEVENTH,
      PERFECT_OCTAVE
  ),
  PENTATONIC(
      "Pentatonic",
      PERFECT_UNISON,
      MAJOR_SECOND,
      MAJOR_THIRD, 
      PERFECT_FIFTH,
      MAJOR_SIXTH,
      PERFECT_OCTAVE
  ),
  
  // TRIADS FOR CHORD GENERATION
  MAJOR_TRIAD(
      "Major Triad",
      PERFECT_UNISON, 
      MAJOR_THIRD, 
      PERFECT_FIFTH 
  ),
  MINOR_TRIAD(
      "Minor Triad",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      PERFECT_FIFTH 
  ),
  DIMINSIHED_TRIAD(
      "Diminished Triad",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      DIMINISHED_FIFTH 
  ),

  // QUADRIADS FOR CHORD GENERATION  
  MAJOR_QUADRIAD(
      "Major Quadriad (7th)",
      PERFECT_UNISON, 
      MAJOR_THIRD, 
      PERFECT_FIFTH, 
      MAJOR_SEVENTH
  ),
  MINOR_QUADRIAD(
      "Minor Quadriad  (7th)",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      PERFECT_FIFTH,
      MINOR_SEVENTH
  ),
  DIMINSIHED_QUADRIAD(
      "Diminished Quadriad (7th)",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      DIMINISHED_FIFTH, 
      MINOR_SEVENTH
  ),
  MINOR_MAJOR_QUADRIAD(
      "MinorMajor Quadriad (7th)",
      PERFECT_UNISON, 
      MINOR_THIRD, 
      PERFECT_FIFTH,
      MAJOR_SEVENTH 
  ),
  
  ;
  
  // guitar scientist can show the intervals which will be easier than the below
  
//  AEOLIAN = new Scale("aeolian", Interval.parse("W h W W h W W"));
//  ALTERED = new Scale("altered", Interval.parse("h W h W W W W"));
//  ALTERED_bb7 = new Scale("altered bb7", Interval.parse("h W h W W h m3"));
//  ARABIAN = new Scale("arabian", Interval.parse("W W h h W W W"));
//  AUGMENTED = new Scale("augmented", Interval.parse("m3 h m3 h m3 h"));
//  AUGMENTED_IONIAN = new Scale("augmented ionian", Interval.parse("W W h m3 h W h"));
//  AUGMENTED_LYDIAN = new Scale("augmented lydian", Interval.parse("W W W W h W h"));
//  BALINESE = new Scale("balinese", Interval.parse("h W m3 W 3"));
//  BLUES = new Scale("blues", Interval.parse("m3 w h h m3 w"));
//  BYZANTINE = new Scale("byzantine", Interval.parse("h m3 h W h m3 h"));
//  CHINESE = new Scale("chinese", Interval.parse("3 W h 3 h"));
//  CHROMATIC = new Scale("chromatic", Interval.parse("h h h h h h h h h h h h"));
//  DIMINISHED_HALFTONE_WHOLETONE = new Scale("diminished halftone-wholetone",
//          Interval.parse("h W h W h W h W"));
//  DOUBLE_HARMONIC = new Scale("double harmonic", Interval.parse("h m3 h w h m3 h"));
//  DORIAN = new Scale("dorian", Interval.parse("W h W W W h W"));
//  EGYPTIAN = new Scale("egyptian", Interval.parse("W m3 W m3 W"));
//  ENIGMATIC = new Scale("enigmatic", Interval.parse("h m3 W W W h h"));

//  HINDU = new Scale("hindu", Interval.parse("W W h W h W W"));
//  HIRAJOSHI = new Scale("hirajoshi", Interval.parse("W h 3 h 3"));
//  HUNGARIAN_MAJOR = new Scale("hungarian major", Interval.parse("m3 h W h W h W"));
//  HUNGARIAN_MINOR = new Scale("hungarian minor", Interval.parse("W h m3 h h m3 h"));
//  ICHIKOSUCHO = new Scale("ichikosucho", Interval.parse("W W h h h W W h"));
//  IONIAN = new Scale("ionian", Interval.parse("w w h w w w h"));
//  IWATO = new Scale("iwato", Interval.parse("h 3 h 3 w"));
//  KUMOI = new Scale("kumoi", Interval.parse("W h 3 W m3"));
//  LEADING_WHOLETONE = new Scale("leading wholetone", Interval.parse("W W W W W h h"));
//  LOCRIAN = new Scale("locrian", Interval.parse("h W W h W W W"));
//  LYDIAN = new Scale("lydian", Interval.parse("W W W h W W h"));
//  MAJOR = new Scale("major", Interval.parse("w w h w w w h"));
//  MAJOR_BEBOP = new Scale("major bebop", Interval.parse("w w h w w h h h"));

//  MELODIC_MINOR = new Scale("melodic minor", Interval.parse("w h w w w w h"));
//  MINOR_PENTATONIC = new Scale("minor pentatonic", Interval.parse("m3 w w m3 w"));
//  MIXOLYDIAN = new Scale("mixolydian", Interval.parse("W W h W W h W"));
//  MOHAMMEDAN = new Scale("mohammedan", Interval.parse("W h W W h m3 h"));
//  MONGOLIAN = new Scale("mongolian", Interval.parse("W W m3 W m3"));
//  NATURAL_MINOR = new Scale("natural minor", Interval.parse("w h w w h w w"));
//  OVERTONE = new Scale("overtone", Interval.parse("W W W h W h W"));
//  PELOG = new Scale("pelog", Interval.parse("h W 3 h 3"));
//  PERSIAN = new Scale("persian", Interval.parse("h m3 h h W m3 h"));
//  PHRYGIAN = new Scale("phrygian", Interval.parse("h W W W h W W"));
//  PROMETHEUS = new Scale("prometheus", Interval.parse("W W W m3 h W"));
//  PURVI_THETA = new Scale("purvi theta", Interval.parse("h m3 W h h m3 h"));
//  SIX_TONE_SYMMETRICAL = new Scale("six tone symmetrical",
//          Interval.parse("h m3 h m3 h m3"));
//  SPANISH_8_TONE = new Scale("spanish 8 tone", Interval.parse("h W h h h W W W"));
//  TODI_THETA = new Scale("todi theta", Interval.parse("h W m3 h h m3 h"));
//  TRITONE = new Scale("tritone", Interval.parse("h m3 m3 h m3 h"));
//  WHOLETONE = new Scale("wholetone", Interval.parse("w w w w w w"));

  private String label;
  private List<ScaleInterval> intervals;

  private ScalePattern(String label, ScaleInterval ... intervals) {
    this.intervals = Arrays.asList(intervals);
    this.label = label;
  }

  public List<ScaleInterval> getIntervals() {
    return intervals;
  }
  public String getLabel() {
    return label;
  }
}
