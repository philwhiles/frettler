package me.flotsam.frettler.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Chord {

  public enum ChordType {
    STANDARD(new int [] {0,2,4}),
    EXTENDED(new int [] {0,2,4,6});
    private int [] thirds;
    ChordType (int [] thirds) {
        this.thirds = thirds;
    }
    public int[] getThirds() {
      return thirds;
    }
  }
  
  private ScaleNote chordRootNote;
  
  private ScalePattern chordPattern;
  private List<ScaleNote> chordNotes = new ArrayList<>();;

  public Chord(Note chordRootNote, ScalePattern chordPattern) {
    Scale chromaticScaleFromChordRoot = new Scale(ScalePattern.CHROMATIC, chordRootNote);
    this.chordRootNote = chromaticScaleFromChordRoot.getHead();
    this.chordPattern = chordPattern;
    for (ScaleInterval interval : chordPattern.getIntervals()) {
      chordNotes.add(chromaticScaleFromChordRoot.findScaleNote(interval).get());    
    }
  }
  
  
  /**
   * Creates a scale chord from a point in that scale 
   * @param chordRootNote the scale note tonic
   * @param chordType standard or extended - this indicates the thirds to use
   */
  public Chord(ScaleNote chordRootNote, ChordType chordType) {
    this.chordRootNote = chordRootNote;

    Scale chromaticScaleFromChordRoot = new Scale(ScalePattern.CHROMATIC, chordRootNote.getNote());

    for (int third : chordType.getThirds()) {
      ScaleNote chordNote = Scale.getThirdNote(chordRootNote, third);
      Optional<ScaleNote> noteInRootScale =
          chromaticScaleFromChordRoot.findScaleNote(chordNote.getNote());
      chordNotes.add(noteInRootScale.get());
    }
    for (ScalePattern chordPattern : ScalePattern.values()) {
      int matches = 0;
      for (ScaleInterval chordsScaleInterval : chordPattern.getIntervals()) {
        for (ScaleNote chordNote : chordNotes) {
          if (chordNote.getInterval().get() == chordsScaleInterval) {
            matches++;
          }
        }
      }
      if (matches == chordPattern.getIntervals().size()) {
        this.chordPattern = chordPattern;
        break;
      }
    }
  }

//  public String getLabel() {
//    StringBuilder builder = new StringBuilder();
//    builder.append(chordRootNote.getNote().getLabel());
//    if (chordType == ChordType.SEVENTH) {
//      if (chordPattern == ChordPattern.MAJOR) {
//        builder.append("maj7");
//      } else {
//        builder.append(chordPattern.getLabel());
//        builder.append(chordType.getLabel());
//      }
//    } else {
//      builder.append(chordPattern.getLabel());
//    }
//    return builder.toString();
//  }


  public String getLabel() {
    StringBuilder builder = new StringBuilder();
    String tonicName = chordRootNote.getNote().getLabel();
    builder.append(tonicName);
    int size = chordNotes.size();
    ChordAnalyzer chordAnalyzer = new ChordAnalyzer(this);


    if (size == 3) {
      if (chordAnalyzer.isThird() && chordAnalyzer.isFifth())
        return tonicName;
      if (chordAnalyzer.isThird() && chordAnalyzer.isAugmentedFifth())
        return tonicName + "aug";
      if (chordAnalyzer.isFlatThird() && chordAnalyzer.isFifth())
        return tonicName + "m";
      if (chordAnalyzer.isFlatThird() && chordAnalyzer.isFlatFifth())
        return tonicName + "dim";
    } else if (size == 4) {
      if (chordAnalyzer.isThird() && chordAnalyzer.isFifth() && chordAnalyzer.isSeventh())
        return tonicName + "M7";
      if (chordAnalyzer.isFlatThird() && chordAnalyzer.isFlatFifth() && chordAnalyzer.isSixth())
        return tonicName + "dim7";
      if (chordAnalyzer.isFlatThird() && chordAnalyzer.isFlatFifth() && chordAnalyzer.isFlatSeventh())
        return tonicName + "m7b5";
      if (chordAnalyzer.isFlatThird() && chordAnalyzer.isFifth() && chordAnalyzer.isSeventh())
        return tonicName + "m maj7";
      if (chordAnalyzer.isThird() && chordAnalyzer.isFifth() && chordAnalyzer.isFlatSeventh())
        return tonicName + "7";
      if (chordAnalyzer.isThird() && chordAnalyzer.isAugmentedFifth() && chordAnalyzer.isFlatSeventh())
        return tonicName + "aug7";
      if (chordAnalyzer.isThird() && chordAnalyzer.isAugmentedFifth() && chordAnalyzer.isSeventh())
        return tonicName + "7+";
    }

    if (chordAnalyzer.isMajorRange())
      builder.append("M");
    else if (chordAnalyzer.isMinorRange())
      builder.append("M");
    else if (chordAnalyzer.isMinorRange())
      builder.append("m");
    else if (chordAnalyzer.isSuspended()) {
      if (chordAnalyzer.isSecond())
        builder.append("sus2");
      else if (chordAnalyzer.isFourth())
        builder.append("sus4");
    }

    if (chordAnalyzer.isFlatFifth() && !chordAnalyzer.isFifth())
      builder.append("b5");
    else if (chordAnalyzer.isFlatFifth() && chordAnalyzer.isFifth())
      builder.append("#4");

    if (chordAnalyzer.isAugmentedFifth() && !chordAnalyzer.isFifth())
      builder.append("+");
    else if (chordAnalyzer.isFifth() && chordAnalyzer.isAugmentedFifth())
      builder.append("add b6");

    if (chordAnalyzer.isFlatSeventh() && !chordAnalyzer.isSeventh())
      builder.append("7");
    else if (chordAnalyzer.isSeventh() && !chordAnalyzer.isFlatSeventh())
      builder.append("M7");

    if (chordAnalyzer.isFourth())
      builder.append("add4");
    if (chordAnalyzer.isNinth() || chordAnalyzer.isSecond())
      builder.append("add9");
    if (chordAnalyzer.isSixth())
      builder.append("add6");

    return builder.toString();
  }

  /**
   * Return true if the chord contains a note that is a major third from the tonic, false otherwise.
   * Note that chord lacking a third are neither major nor minor.
   */
  public boolean isMajor() {
    return containsIntervals(ScaleInterval.MAJOR_THIRD);
  }

  /**
   * Return true if the chord contains a note that is a minor third from the tonic, false otherwise.
   * Note that chords lacking a third are neither major nor minor.
   */
  public boolean isMinor() {
    return containsIntervals(ScaleInterval.MINOR_THIRD);
  }

  public String getTitle() {
    return getLabel() + " (" + chordRootNote.getNote() + " " + chordPattern.getLabel() + ")" + " ["
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
        + chordNotes.stream().map(n -> n.getNote().getLabel()).collect(Collectors.joining(","))
        + "]";
  }

  public static List<Chord> createScaleChords(Scale scale) {
    List<Chord> chords = new ArrayList<>();
    for (ScaleNote scaleNote : scale.getScaleNotes()) {
      chords.add(new Chord(scaleNote, ChordType.STANDARD));
    }
    return chords;
  }
}
