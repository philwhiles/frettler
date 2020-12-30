/*
 * Copyright (C) 2020 Philip Whiles
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package me.flotsam.frettler.engine;

import static me.flotsam.frettler.engine.ScaleInterval.M2;
import static me.flotsam.frettler.engine.ScaleInterval.M3;
import static me.flotsam.frettler.engine.ScaleInterval.M6;
import static me.flotsam.frettler.engine.ScaleInterval.M7;
import static me.flotsam.frettler.engine.ScaleInterval.M9;
import static me.flotsam.frettler.engine.ScaleInterval.m9;
import static me.flotsam.frettler.engine.ScaleInterval.m10;
import static me.flotsam.frettler.engine.ScaleInterval.M10;
import static me.flotsam.frettler.engine.ScaleInterval.m11;
import static me.flotsam.frettler.engine.ScaleInterval.M11;
import static me.flotsam.frettler.engine.ScaleInterval.P8;
import static me.flotsam.frettler.engine.ScaleInterval.P1;
import static me.flotsam.frettler.engine.ScaleInterval.P4;
import static me.flotsam.frettler.engine.ScaleInterval.P5;
import static me.flotsam.frettler.engine.ScaleInterval.d5;
import static me.flotsam.frettler.engine.ScaleInterval.m2;
import static me.flotsam.frettler.engine.ScaleInterval.m3;
import static me.flotsam.frettler.engine.ScaleInterval.m6;
import static me.flotsam.frettler.engine.ScaleInterval.m7;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum IntervalPattern {

  //@formatter:off
  // PATTERNS FOR CHORD GENERATION
  CHORD_MIN11(
      true,
      PatternType.CHORD,
      "min11",
      P1, m3, P5, m7, M9, M11
  ),
  CHORD_DOM11(
      true,
      PatternType.CHORD,
      "dom11",
      P1, M3, P5, m7, M9, M11
  ),
  CHORD_DOM9(
      true,
      PatternType.CHORD,
      "dom9",
      P1, M3, P5, m7, M9
  ),
  CHORD_7SHARP9(
      true,
      PatternType.CHORD,
      "7#9",
      P1, M3, P5, m7, m10
  ),
  CHORD_7FLAT9(
      true,
      PatternType.CHORD,
      "7b9",
      P1, M3, P5, m7, m9
  ),
  CHORD_MAJ69(
      true,
      PatternType.CHORD,
      "6/9",
      P1, M3, P5, M6, M9
  ),
  CHORD_MAJ6MIN7(
      true,
      PatternType.CHORD,
      "6/min7",
      P1, M3, P5, M6, m7
  ),
  CHORD_MAJ9(
      true,
      PatternType.CHORD,
      "9",
      P1, M3, P5, M7, M9
  ),
  CHORD_MIN9(
      true,
      PatternType.CHORD,
      "min9",
      P1, m3, P5, m7, M9
  ),
  CHORD_DIM7(
      true,
      PatternType.CHORD,
      "dim7",
      P1, m3, d5, M6
  ),
  CHORD_7FLAT5(
      true,
      PatternType.CHORD,
      "7b5",
      P1, M3, d5, m7
  ),
  CHORD_MIN7(
      true,
      PatternType.CHORD,
      "min7",
      P1, m3, P5, m7
  ),
  CHORD_AUG7(
      true,
      PatternType.CHORD,
      "aug7",
      P1, M3, m6, m7
  ),
  CHORD_MAJ7(
      true,
      PatternType.CHORD,
      "7",
      P1, M3, P5, M7
  ),
  CHORD_DOM7(
      true,
      PatternType.CHORD,
      "dom7",
      P1, M3, P5, m7
  ),
  CHORD_7(
      true,
      CHORD_DOM7,
      PatternType.CHORD,
      "7"
  ),
  CHORD_MIN6(
      true,
      PatternType.CHORD,
      "min6",
      P1, m3, P5, M6
  ),
  CHORD_MAJ6(
      true,
      PatternType.CHORD,
      "6",
      P1, M3, P5, M6
  ),
  CHORD_MINMAJ7(
      true,
      PatternType.CHORD,
      "min/7",
      P1, m3, P5, M7
  ),
  CHORD_MIN7FLAT5(
      true,
      PatternType.CHORD,
      "m7b5",
      P1, m3, d5, m7
  ),
  CHORD_7PLUS(
      true,
      PatternType.CHORD,
      "7+",
      P1, M3, d5, M7
  ),
  CHORD_MIN7SUS4(
      true,
      PatternType.CHORD,
      "7sus4",
      P1, P4, P5, m7
  ),
  CHORD_ADD9(
      true,
      PatternType.CHORD,
      "add9",
      P1, M3, P5, M9
  ),
  CHORD_ADD11(
      true,
      PatternType.CHORD,
      "add11",
      P1, M3, P5, M11
  ),
  CHORD_AUG(
      true,
      PatternType.CHORD,
      "aug",
      P1, M3, m6
  ),
  CHORD_DIM(
      true,
      PatternType.CHORD,
      "dim",
      P1, m3, d5
  ),
  CHORD_MIN(
      true,
      PatternType.CHORD,
      "min",
      P1, m3, P5
  ),
  CHORD_SUS2(
      true,
      PatternType.CHORD,
      "sus2",
      P1, M2, P5
  ),
  CHORD_SUS4(
      true,
      PatternType.CHORD,
      "sus4",
      P1, P4, P5
  ),
  CHORD_MAJ(
      true,
      PatternType.CHORD,
      "maj",
      P1, M3, P5
  ),
  CHORD_5(
      true,
      PatternType.CHORD,
      "5",
      P1, P5
  ),

  // SCALES AND MODES
  SCALE_CHROMATIC(
      false,
      PatternType.SCALE,
      "Chromatic",
      P1, m2, M2, m3, M3, P4, d5, P5, m6, M6, m7, M7, P8, m9, M9, m10, M10, m11, M11 
  ),
  SCALE_MAJOR(
      true,
      PatternType.SCALE,
      "Major",
      P1, M2, M3, P4, P5, M6, M7
  ),
  SCALE_MELODIC_MINOR(
      true,
      PatternType.SCALE,
      "Melodic Minor",
      P1, M2, m3, P4, P5, M6, M7
  ),
  SCALE_MAJOR_PENTATONIC(
      true,
      SCALE_MAJOR,
      PatternType.SCALE,
      "Major Pentatonic",
      P1, M2, M3, P5, M6
  ),
  SCALE_MINOR(
      true,
      PatternType.SCALE,
      "Minor",
      P1, M2, m3, P4, P5, m6, m7
  ),
  MODE_AEOLIAN(
      true,
      SCALE_MINOR,
      PatternType.MODE,
      "Aeolian"
  ),
  SCALE_HARMONIC_MINOR(
      true,
      SCALE_MINOR,
      PatternType.SCALE,
      "Harmonic Minor"
  ),
  SCALE_MINOR_PENTATONIC(
      true,
      SCALE_MINOR,
      PatternType.SCALE,
      "Minor Pentatonic",
      P1, m3, P4, P5, m7
  ), 
  SCALE_BLUES_MAJOR(
      false,
      PatternType.SCALE,
      "Major Blues" ,
      P1, M2, m3, P5, M6
  ),
  SCALE_BLUES_MINOR(
      false,
      PatternType.SCALE,
      "Minor Blues",
      P1, m3, P4, d5, P5, m7
  ),
  MODE_DORIAN(
      true,
      PatternType.MODE,
      "Dorian",
      P1, M2, m3, P4, P5, M6, m7
  ), 
  MODE_MIXOLYDIAN(
      true,
      PatternType.MODE,
      "Mixolydian",
      P1, M2, M3, P4, P5, M6, m7
  ), 
  MODE_LYDIAN(
      true,
      PatternType.MODE,
      "Lydian",
      P1, M2, M3, d5, P5, M6, M7
  ), 
  MODE_IONIAN(
      true,
      SCALE_MAJOR,
      PatternType.MODE,
      "Ionian"
  ), 
  MODE_LOCRIAN(
      true,
      PatternType.MODE,
      "Locrian",
      P1, m2, m3, P4, d5, m6, m7
  ),
  
  ;
  //@formatter:on



  @Getter
  private String label;
  @Getter
  private List<ScaleInterval> intervals;
  @Getter
  private PatternType patternType;
  @Getter
  private IntervalPattern parentPattern;
  @Getter
  private IntervalPattern aliasPattern;
  @Getter
  private IntervalPatternMetadata metadata;
  
  private boolean scaleChordGenerationSupported;

  private IntervalPattern(boolean scaleChordGenerationSupported,
      IntervalPattern aliasPattern, PatternType patternType, String label) {
    this.scaleChordGenerationSupported = scaleChordGenerationSupported;
    this.aliasPattern = aliasPattern;
    this.patternType = patternType;
    this.label = label;
    this.intervals = aliasPattern.intervals;
    this.metadata = IntervalPatternAnalyser.analyse(this.intervals);
  }

  private IntervalPattern(boolean scaleChordGenerationSupported, IntervalPattern aliasPattern,
      String label) {
    this.scaleChordGenerationSupported = scaleChordGenerationSupported;
    this.aliasPattern = aliasPattern;
    this.patternType = aliasPattern.patternType;
    this.label = label;
    this.intervals = aliasPattern.intervals;
    this.metadata = IntervalPatternAnalyser.analyse(this.intervals);
  }

  private IntervalPattern(boolean scaleChordGenerationSupported,
      IntervalPattern parentPattern, PatternType patternType, String label,
      ScaleInterval... intervals) {
    this(scaleChordGenerationSupported, patternType, label, intervals);
    this.parentPattern = parentPattern;
  }

  private IntervalPattern(boolean scaleChordGenerationSupported,
      PatternType patternType, String label, ScaleInterval... intervals) {
    this.scaleChordGenerationSupported = scaleChordGenerationSupported;
    this.patternType = patternType;
    this.label = label;
    this.intervals = Arrays.asList(intervals);
    this.metadata = IntervalPatternAnalyser.analyse(this.intervals);
  }

  public String getTitle() {
    return this.name().toLowerCase() + (this.aliasPattern != null ? " an alias of " + this.aliasPattern.getTitle().toLowerCase() : " (" + this.label + ") " + this.intervals);
  }

  public boolean isScaleChordGenerationSupported() {
    return scaleChordGenerationSupported;
  }

  public enum PatternType {
    CHORD, SCALE, MODE
  }
}
