package me.flotsam.frettler.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import me.flotsam.frettler.engine.IntervalPattern.PatternType;

@Data
public class Chord {
  private boolean flatSecond;
  private boolean second;
  private boolean augmentedSecond;
  private boolean flatThird;
  private boolean third;
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

  private ScaleNote chordRootNote;
  private IntervalPattern chordPattern;
  private List<ScaleNote> chordNotes = new ArrayList<>();;


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
   * @param chordRootNote the tonic for the chord
   * @param chordPattern the scale pattern ie MAJOR, HARMONIC_MINOR
   */
  public Chord(Note chordRootNote, IntervalPattern chordPattern) {
    if (chordPattern.getPatternType() != PatternType.CHORD) {
      System.err.println("Interval pattern '" + chordPattern.getLabel() + "' is not a chord pattern");
      System.exit(-1);
    }
    Scale chromaticScaleFromChordRoot = new Scale(chordRootNote, IntervalPattern.CHROMATIC_SCALE);
    this.chordRootNote = chromaticScaleFromChordRoot.getHead();
    this.chordPattern = chordPattern;
    for (ScaleInterval interval : chordPattern.getIntervals()) {
      chordNotes.add(chromaticScaleFromChordRoot.findScaleNote(interval).get());
    }
    analyse();
  }


  /**
   * Creates a chord from a given note in a scale. Used to create a standard triad or quadriad chord from the tonic/root - in the
   * scale that the chordRootNote was taken. ie the chordRootNote may be 'D' sitting within say the scale of A Minor,
   * and will sit with that scales notes either side of it.
   * The chordType indicates the thirds to pick out of the root notes scale.
   * 
   * It gets the chromatic scale starting at the root note, then finds each of the derived chord notes relative to the tonic in the 
   * chromatic scale in order to work out the interval of each chord note.
   * 
   * @param chordRootNote the scale note tonic 
   * @param chordType standard or extended - this indicates the thirds to use
   */
  public Chord(ScaleNote chordRootNote, ChordType chordType) {
    this.chordRootNote = chordRootNote;

    Scale chromaticScaleFromChordRoot = new Scale(chordRootNote.getNote(), IntervalPattern.CHROMATIC_SCALE);

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
    analyse();
  }



  public String getLabel() {
    StringBuilder builder = new StringBuilder();
    String tonicName = chordRootNote.getNote().getLabel();
    builder.append(tonicName);
    int size = chordNotes.size();


    if (size == 3) {
      if (isThird() && isFifth())
        return tonicName;
      if (isThird() && isAugmentedFifth())
        return tonicName + "aug";
      if (isFlatThird() && isFifth())
        return tonicName + "m";
      if (isFlatThird() && isFlatFifth())
        return tonicName + "dim";
    } else if (size == 4) {
      if (isThird() && isFifth() && isSeventh())
        return tonicName + "M7";
      if (isFlatThird() && isFlatFifth() && isSixth())
        return tonicName + "dim7";
      if (isFlatThird() && isFlatFifth()
          && isFlatSeventh())
        return tonicName + "m7b5";
      if (isFlatThird() && isFifth() && isSeventh())
        return tonicName + "m maj7";
      if (isThird() && isFifth() && isFlatSeventh())
        return tonicName + "7";
      if (isThird() && isAugmentedFifth()
          && isFlatSeventh())
        return tonicName + "aug7";
      if (isThird() && isAugmentedFifth() && isSeventh())
        return tonicName + "7+";
    }

    if (isMajorRange())
      builder.append("M");
    else if (isMinorRange())
      builder.append("M");
    else if (isMinorRange())
      builder.append("m");
    else if (isSuspended()) {
      if (isSecond())
        builder.append("sus2");
      else if (isFourth())
        builder.append("sus4");
    }

    if (isFlatFifth() && !isFifth())
      builder.append("b5");
    else if (isFlatFifth() && isFifth())
      builder.append("#4");

    if (isAugmentedFifth() && !isFifth())
      builder.append("+");
    else if (isFifth() && isAugmentedFifth())
      builder.append("add b6");

    if (isFlatSeventh() && !isSeventh())
      builder.append("7");
    else if (isSeventh() && !isFlatSeventh())
      builder.append("M7");

    if (isFourth())
      builder.append("add4");
    if (isNinth() || isSecond())
      builder.append("add9");
    if (isSixth())
      builder.append("add6");

    return builder.toString();
  }


  public String getTitle() {
    return getLabel() + " (" + chordRootNote.getNote().getLabel() + " " + chordPattern.getLabel() + ")" + " ["
        + chordNotes.stream().map(n -> n.getNote().getLabel()).collect(Collectors.joining(","))
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

  public String toString() {
    return getLabel() + " ["
        + chordNotes.stream()
            .map(n -> n.getNote().getLabel() + " (" + n.getInterval().get().getLabel() + ")")
            .collect(Collectors.joining(", "))
        + "]";
  }

  private void analyse() {
    flatSecond = containsIntervals(ScaleInterval.PERFECT_UNISON);
    second =  containsIntervals(ScaleInterval.MAJOR_SECOND);
    flatThird =  containsIntervals(ScaleInterval.MINOR_THIRD);
    third =  containsIntervals(ScaleInterval.MAJOR_THIRD);
    fourth = containsIntervals(ScaleInterval.PERFECT_FOURTH);
    flatFifth = containsIntervals(ScaleInterval.DIMINISHED_FIFTH);
    fifth = containsIntervals(ScaleInterval.PERFECT_FIFTH);
    augmentedFifth = containsIntervals(ScaleInterval.MINOR_SIXTH);
    sixth = containsIntervals(ScaleInterval.MAJOR_SIXTH);
    flatSeventh = containsIntervals(ScaleInterval.MINOR_SEVENTH);
    seventh = containsIntervals(ScaleInterval.MAJOR_SEVENTH);
    ninth = containsIntervals(ScaleInterval.NINTH);
    eleventh = containsIntervals(ScaleInterval.ELEVENTH);

    majorRange = (third || fifth) && !(flatThird || flatFifth);
    minorRange = flatThird && !third;
    suspended = (!third && !flatThird && (fourth || second));
  }


}
