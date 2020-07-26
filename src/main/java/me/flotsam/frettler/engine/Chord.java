package me.flotsam.frettler.engine;

import static me.flotsam.frettler.engine.ScaleInterval.M11;
import static me.flotsam.frettler.engine.ScaleInterval.M2;
import static me.flotsam.frettler.engine.ScaleInterval.M3;
import static me.flotsam.frettler.engine.ScaleInterval.M6;
import static me.flotsam.frettler.engine.ScaleInterval.M7;
import static me.flotsam.frettler.engine.ScaleInterval.M9;
import static me.flotsam.frettler.engine.ScaleInterval.P1;
import static me.flotsam.frettler.engine.ScaleInterval.P4;
import static me.flotsam.frettler.engine.ScaleInterval.P5;
import static me.flotsam.frettler.engine.ScaleInterval.d5;
import static me.flotsam.frettler.engine.ScaleInterval.m10;
import static me.flotsam.frettler.engine.ScaleInterval.m3;
import static me.flotsam.frettler.engine.ScaleInterval.m6;
import static me.flotsam.frettler.engine.ScaleInterval.m7;
import static me.flotsam.frettler.engine.ScaleInterval.m9;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import me.flotsam.frettler.engine.IntervalPattern.PatternType;

public class Chord {
  //@formatter:off
  private static List<ChordPattern> patternBank = Arrays.asList(new ChordPattern[] {
      new ChordPattern("min11", P1, m3, P5, m7, M9, M11),
      new ChordPattern("dom11", P1, M3, P5, m7, M9, M11),
      new ChordPattern("9b5", P1, M3, P5, m7, M9),
      new ChordPattern("M7add9", P1, M3, P5, M7, M9),
      new ChordPattern("7#9", P1, M3, P5, m7, m10),
      new ChordPattern("7b9", P1, M3, P5, m7, m9),
      new ChordPattern("dom9", P1, M3, P5, m7, M9),
      new ChordPattern("maj6/9", P1, M3, P5, M6, M9),
      new ChordPattern("maj9", P1, M3, P5, M7, M9),
      new ChordPattern("min9", P1, m3, P5, m7, M9),
      new ChordPattern("dim7", P1, m3, d5, M6),
      new ChordPattern("7#5", P1, M3, m6, m7),
      new ChordPattern("7b5", P1, M3, d5, m7),
      new ChordPattern("min7", P1, m3, P5, m7),
      new ChordPattern("aug7", P1, M3, d5, m7),
      new ChordPattern("maj7", P1, M3, P5, M7),
      new ChordPattern("dom7", P1, M3, P5, m7),
      new ChordPattern("min6", P1, m3, P5, M6),
      new ChordPattern("maj6", P1, M3, P5, M6),
      new ChordPattern("m maj7", P1, m3, P5, M7),
      new ChordPattern("m7b5", P1, m3, d5, m7),
      new ChordPattern("7+", P1, M3, d5, M7),
      new ChordPattern("7sus4", P1, P4, P5, m7),
      new ChordPattern("add9", P1, M3, P5, M9),
      new ChordPattern("add11", P1, M3, P5, M11),
      new ChordPattern("aug", P1, M3, m6),
      new ChordPattern("dim", P1, m3, d5),
      new ChordPattern("m", P1, m3, P5),
      new ChordPattern("sus2", P1, M2, P5),
      new ChordPattern("sus4", P1, P4, P5),
      new ChordPattern("", P1, M3, P5)});
  //@formatter:on

  @Getter
  private ScaleNote chordRootNote;
  @Getter
  private IntervalPattern chordPattern;
  @Getter
  private List<ScaleNote> chordNotes = new ArrayList<>();;
  @Getter
  private ChordMetadata metaData;

  public enum ChordType {
    STANDARD(new int[] {0, 2, 4}), EXTENDED(new int[] {0, 2, 4, 6});
    private int[] thirds;

    ChordType(int[] thirds) {
      this.thirds = thirds;
    }

    public int[] getThirds() {
      return thirds;
    }
  }

  /**
   * Used to create a Chord from a root note when we know what the chords scale pattern should be
   * 
   * @param chordRootNote the tonic for the chord
   * @param chordPattern the scale pattern ie MAJOR, HARMONIC_MINOR
   */
  public Chord(Note chordRootNote, IntervalPattern chordPattern) {
    if (chordPattern.getPatternType() != PatternType.CHORD) {
      System.err
          .println("Interval pattern '" + chordPattern.getLabel() + "' is not a chord pattern");
      System.exit(-1);
    }
    Scale chromaticScaleFromChordRoot = new Scale(chordRootNote, IntervalPattern.CHROMATIC_SCALE);
    this.chordRootNote = chromaticScaleFromChordRoot.getHead();
    this.chordPattern = chordPattern;
    for (ScaleInterval interval : chordPattern.getIntervals()) {
      chordNotes.add(chromaticScaleFromChordRoot.findScaleNote(interval).get());
    }
    metaData = analyse();
  }


  /**
   * Creates a chord from a given note in a scale. Used to create a standard triad or quadriad chord
   * from the tonic/root - in the scale that the chordRootNote was taken. ie the chordRootNote may
   * be 'D' sitting within say the scale of A Minor, and will sit with that scales notes either side
   * of it. The chordType indicates the thirds to pick out of the root notes scale.
   * 
   * It gets the chromatic scale starting at the root note, then finds each of the derived chord
   * notes relative to the tonic in the chromatic scale in order to work out the interval of each
   * chord note.
   * 
   * @param chordRootNote the scale note tonic
   * @param chordType standard or extended - this indicates the thirds to use
   */
  public Chord(ScaleNote chordRootNote, ChordType chordType) {
    this.chordRootNote = chordRootNote;

    Scale chromaticScaleFromChordRoot =
        new Scale(chordRootNote.getNote(), IntervalPattern.CHROMATIC_SCALE);

    for (int third : chordType.getThirds()) {
      ScaleNote chordNote = Scale.getScaleNote(chordRootNote, third);
      Optional<ScaleNote> noteInRootScale =
          chromaticScaleFromChordRoot.findScaleNote(chordNote.getNote());
      chordNotes.add(noteInRootScale.get());
    }
    this.chordPattern = IntervalPattern.CHROMATIC_SCALE;
    for (IntervalPattern pattern : IntervalPattern.values()) {
      int matches = 0;
      for (ScaleInterval chordsScaleInterval : pattern.getIntervals()) {
        for (ScaleNote chordNote : chordNotes) {
          if (chordNote.getInterval().get() == chordsScaleInterval) {
            matches++;
          }
        }
      }
      if (matches == pattern.getIntervals().size()) {
        this.chordPattern = pattern;
        break;
      }
    }
    metaData = analyse();
  }

  public String getLabel() {
    return chordRootNote.getNote().getLabel() + metaData.label;
  }

  public String getTitle() {
    return getLabel() + " (" + chordRootNote.getNote().getLabel() + " " + chordPattern.getLabel()
        + ")" + " ["
        + chordNotes.stream().map(n -> n.getNote().getLabel()).collect(Collectors.joining(","))
        + "]";
  }

  public String toString() {
    return getLabel() + " ["
        + chordNotes.stream()
            .map(n -> n.getNote().getLabel() + " (" + n.getInterval().get().getLabel() + ")")
            .collect(Collectors.joining(", "))
        + "]";
  }

  public boolean containsIntervals(ScaleInterval... intervals) {
    int cnt = 0;
    for (ScaleInterval interval : intervals) {
      for (ScaleNote note : chordNotes) {
        if (note.getInterval().get() == interval) {
          cnt++;
          break;
        }
      }
    }
    return cnt == intervals.length;
  }


  public ChordMetadata analyse() {
    ChordMetadata meta = new ChordMetadata();
    for (ScaleNote note : chordNotes) {
      Optional<ScaleInterval> interval = note.getInterval();
      if (interval.isPresent()) {
        switch (interval.get()) {
          case m2:
            meta.minorSecond = true;
            break;
          case M2:
            meta.majorSecond = true;
            break;
          case m3:
            meta.minorThird = true;
            break;
          case M3:
            meta.majorThird = true;
            break;
          case P4:
            meta.perfectFourth = true;
            break;
          case d5:
            meta.diminishedFifth = true;
            break;
          case P5:
            meta.perfectFifth = true;
            break;
          case m6:
            meta.minorSixth = true;
            break;
          case M6:
            meta.majorSixth = true;
            break;
          case m7:
            meta.minorSeventh = true;
            break;
          case M7:
            meta.majorSeventh = true;
            break;
          case M9:
            meta.majorNinth = true;
            break;
          case M11:
            meta.majorEleventh = true;
            break;
          default:
            break;
        }
      }
    }
    meta.majorRange =
        (meta.majorThird || meta.perfectFifth) && !(meta.minorThird || meta.diminishedFifth);
    meta.minorRange = meta.minorThird && !meta.majorThird;
    meta.suspended =
        (!meta.majorThird && !meta.minorThird && (meta.perfectFourth || meta.majorSecond));

    meta.label = "UNCLASSIFIED";
    for (ChordPattern pattern : patternBank) {
      if (containsIntervals(pattern.getIntervals().toArray(new ScaleInterval[] {}))) {
        meta.label = pattern.getLabel();
        break;
      }
    }

    return meta;
  }
}
