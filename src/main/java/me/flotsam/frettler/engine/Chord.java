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

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import me.flotsam.frettler.engine.IntervalPattern.PatternType;
import me.flotsam.frettler.view.Colour;
import me.flotsam.frettler.view.ColourMap;

public class Chord {

  @Getter
  private ScaleNote chordRootNote;
  @Getter
  private IntervalPattern chordPattern;
  @Getter
  private List<ScaleNote> chordNotes = new ArrayList<>();;
  @Getter
  private ChordMetadata metaData;
  @Getter
  private List<Note> accidentals;

  public enum ChordType {
    STANDARD(new int[] {0, 2, 4}), EXTENDED(new int[] {0, 2, 4, 6});
    @Getter
    private int[] thirds;

    ChordType(int[] thirds) {
      this.thirds = thirds;
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
    Scale chromaticScaleFromChordRoot = new Scale(chordRootNote, IntervalPattern.SCALE_CHROMATIC);
    this.chordRootNote = chromaticScaleFromChordRoot.getHead();
    this.chordPattern = chordPattern;
    this.accidentals = LineOfFifths.getMajorEntry(chordRootNote).getAccidentals();
    for (ScaleInterval interval : chordPattern.getIntervals()) {
      ScaleNote scaleNote = chromaticScaleFromChordRoot.findScaleNote(interval).get();
      Note note = scaleNote.getNote();
      if (note.getAccidental() == Note.Accidental.SHARP && !accidentals.contains(note)) {
        note = note.getAlternate();
      }
      addScaleNote(chromaticScaleFromChordRoot, note, interval);
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
        new Scale(chordRootNote.getNote(), IntervalPattern.SCALE_CHROMATIC);

    for (int third : chordType.getThirds()) {
      ScaleNote chordNote = Scale.getScaleNote(chordRootNote, third);
      Optional<ScaleNote> noteInRootScale =
          chromaticScaleFromChordRoot.findScaleNote(chordNote.getNote());
      
      Note note = noteInRootScale.get().getNote();
      if (note.getAccidental() == Note.Accidental.SHARP && !accidentals.contains(note)) {
        note = note.getAlternate();
      }
      addScaleNote(chromaticScaleFromChordRoot, note, noteInRootScale.get().getInterval().get());
    }
    this.chordPattern = IntervalPattern.SCALE_CHROMATIC;
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

  private void addScaleNote(Scale scale, Note note, ScaleInterval interval) {
    ScaleNote newNote = new ScaleNote(note, Optional.of(interval), scale);
    chordNotes.add(newNote);
  }
  
  /**
   * Will search all possible chords having the first note as the tonic, and containing
   * all the other notes exclusively
   * @param notes the notes to use in the chord search
   * @return optionally! the matching chord
   */
  public static Optional<Chord> findChord(Note... notes) {
    Optional<Chord> result = Optional.empty();

    Set<Note> chordSet = new HashSet<>(Arrays.asList(notes));
    if (notes.length != chordSet.size()) {
      out.println("Ooops - spotted a dupicate note there!");
      System.exit(-1);
    }
    for (Note note : notes) {
      if (result.isPresent()) {
        break;
      }
      for (IntervalPattern pattern : IntervalPattern.values()) {
        if (pattern.getPatternType() != PatternType.CHORD) {
          continue;
        }
        Chord candidate = new Chord(note, pattern);
        if (candidate.containsOnlyNotes(notes)) {
          result = Optional.of(candidate);
          break;
        }
      }
    }
    return result;
  }

  /**
   * Will search all possible chords with all possible tonics (even if not in the notes provided)
   * having all the provided notes in, but not exclusively
   * @param notes the notes to use in the chord search
   * @return the matching chords
   */
  public static List<Chord> findAllChords(Note... notes) {
    Set<Note> chordSet = new HashSet<>(Arrays.asList(notes));
    if (notes.length != chordSet.size()) {
      out.println("Ooops - spotted a dupicate note there!");
      System.exit(-1);
    }
    List<Chord> chords = new ArrayList<>();
    for (Pitch pitch : Pitch.values()) {
      for (IntervalPattern pattern : IntervalPattern.values()) {
        if (pattern.getPatternType() != PatternType.CHORD) {
          continue;
        }
        Chord candidate = new Chord(Note.forPitch(pitch), pattern);
        if (candidate.containsNotes(notes)) {
          chords.add(candidate);
        }
      }
    }
    return chords;
  }

  /**
   * Will search all possible chords having the first note as the tonic, and containing
   * all the other notes, but not exclusively
   * @param notes the notes to use in the chord search
   * @return the matching chords
   */
  public static List<Chord> findChords(Note... notes) {
    Set<Note> chordSet = new HashSet<>(Arrays.asList(notes));
    if (notes.length != chordSet.size()) {
      out.println("Ooops - spotted a dupicate note there!");
      System.exit(-1);
    }
    List<Chord> chords = new ArrayList<>();
    for (IntervalPattern pattern : IntervalPattern.values()) {
      if (pattern.getPatternType() != PatternType.CHORD) {
        continue;
      }
      Chord candidate = new Chord(notes[0], pattern);
      if (candidate.containsNotes(notes)) {
        chords.add(candidate);
      }
    }
    return chords;
  }

  public String getLabel() {
    return chordRootNote.getNote().getLabel() + metaData.label;
  }

  public String getTitle() {
    return getLabel() + "   (" + chordRootNote.getNote().name().toLowerCase() + " "
        + chordPattern.name().toLowerCase() + ")" + "   ["
        + chordNotes.stream().map(n -> n.getNote().getLabel() + "(" + n.getInterval().get() + ")")
            .collect(Collectors.joining(", "))
        + "]";
  }

  public String toString() {
    return describe(false);
  }

  public String describe(boolean mono) {
    StringBuilder sb = new StringBuilder();
    Scale chromaticScaleFromChordRoot =
        new Scale(chordRootNote.getNote(), IntervalPattern.SCALE_CHROMATIC);
    sb.append("\n          ");
    ScaleNote scaleNote = chromaticScaleFromChordRoot.findScaleNote(chordRootNote.getNote()).get();

    List<Note> notes = new ArrayList<>();
    List<ScaleInterval> intervals = new ArrayList<>();
    List<Note> chordsNotes = new ArrayList<>();

    do {
      Note note = scaleNote.getNote();
      notes.add(note);
      Optional<ScaleNote> chordNote =
          chordNotes.stream().filter(sn -> sn.getNote() == note).findAny();
      intervals.add(scaleNote.getInterval().get());
      if (chordNote.isPresent()) {
        chordsNotes.add(chordNote.get().getNote());
      }
      scaleNote = scaleNote.getNextScaleNote();
    } while (scaleNote.getNote() != chordRootNote.getNote());


    for (Note note : notes) {
      sb.append(String.format("%s%-2s    %s",
          chordsNotes.contains(note) ? (mono ? "" : ColourMap.get(note)) : "", note.getLabel(),
          (mono ? "" : Colour.RESET)));
    }
    sb.append("\n          ");
    for (int n = 0; n < intervals.size(); n++) {
      ScaleInterval interval = intervals.get(n);
      sb.append(String.format("%s%-2s    %s",
          chordsNotes.contains(notes.get(n)) ? (mono ? "" : ColourMap.get(notes.get(n))) : "",
          interval, (mono ? "" : Colour.RESET)));
    }
    sb.append("\n\n");
    return sb.toString();
  }

  public boolean containsOnlyNotes(Note... notes) {
    int cnt = 0;
    for (Note note : notes) {
      for (ScaleNote scaleNote : chordNotes) {
        if (scaleNote.getNote() == note) {
          cnt++;
          break;
        }
      }
    }
    return cnt == chordNotes.size() && cnt == notes.length;
  }

  public boolean containsNotes(Note... notes) {
    int cnt = 0;
    for (Note note : notes) {
      for (ScaleNote scaleNote : chordNotes) {
        if (scaleNote.getNote() == note) {
          cnt++;
          break;
        }
      }
    }
    return cnt == notes.length;
  }

  public boolean containsOnlyIntervals(ScaleInterval... intervals) {
    int cnt = 0;
    for (ScaleInterval interval : intervals) {
      for (ScaleNote note : chordNotes) {
        if (note.getInterval().get() == interval) {
          cnt++;
          break;
        }
      }
    }
    return cnt == chordNotes.size() && cnt == intervals.length;
  }


  private ChordMetadata analyse() {
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
    for (IntervalPattern pattern : IntervalPattern.values()) {
      if (pattern.getPatternType() != PatternType.CHORD) {
        continue;
      }
      if (containsOnlyIntervals(pattern.getIntervals().toArray(new ScaleInterval[] {}))) {
        meta.label = pattern.getLabel();
        meta.chordPattern = pattern;
        break;
      }
    }

    return meta;
  }
}
