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
import me.flotsam.frettler.engine.LineOfFifths.LineEntry;
import me.flotsam.frettler.view.Colour;
import me.flotsam.frettler.view.ColourMap;

public class Chord {

  @Getter
  private ScaleNote chordRootNote;
  @Getter
  private Note chordRoot;
  @Getter
  private List<ScaleNote> chordNotes = new ArrayList<>();;
  @Getter
  private ChordMetadata metaData;
  @Getter
  private List<Note> accidentals;
  @Getter
  private Note addedNote;
  @Getter
  private ScaleNote addedScaleNote;

  public enum ChordType {
    STANDARD(new int[] {0, 2, 4}), EXTENDED(new int[] {0, 2, 4, 6});
    @Getter
    private int[] thirds;

    ChordType(int[] thirds) {
      this.thirds = thirds;
    }
  }
  //DYAD 2
  //TRIAD 3
  // TETRAD 4
  // PENTAD 5
  // HEXAD 6

  /**
   * Used to create a Chord from a root note when we know what the chords scale pattern should be
   * 
   * @param chordRootNote the tonic for the chord
   * @param chordPattern the scale pattern ie MAJOR, HARMONIC_MINOR
   */
  public Chord(Note chordRootNote, IntervalPattern chordPattern, Note addedNote) {
    this.chordRoot = chordRootNote;
    if (chordPattern.getPatternType() != PatternType.CHORD) {
      System.err
          .println("Interval pattern '" + chordPattern.getLabel() + "' is not a chord pattern");
      System.exit(-1);
    }
    Scale chromaticScaleFromChordRoot = new Scale(chordRootNote, IntervalPattern.SCALE_CHROMATIC);
    this.chordRootNote = chromaticScaleFromChordRoot.getHead();
    this.addedNote = addedNote;
    if (addedNote != null) {
      Optional<ScaleNote> optAddedScaleNote = chromaticScaleFromChordRoot.findScaleNote(addedNote);
      addedScaleNote = optAddedScaleNote.get();
    }
    
    LineEntry lineEntry = null;
    if (chordPattern.getMetadata().isMinorRange()) {
      lineEntry = LineOfFifths.getMinorEntry(this.chordRootNote.getNote());
    } else {
      lineEntry = LineOfFifths.getMajorEntry(this.chordRootNote.getNote());
    } 
    this.accidentals = lineEntry.getAccidentals();
    
    for (ScaleInterval interval : chordPattern.getIntervals()) {
      ScaleNote scaleNote = chromaticScaleFromChordRoot.findScaleNote(interval).get();
      Note note = scaleNote.getNote();
      final Note theNote = note;
        Optional<Note> accidental =
            this.accidentals.stream().filter(n -> n.getPitch() == theNote.getPitch()).findFirst();
        note = accidental.orElse(note);
      addScaleNote(chromaticScaleFromChordRoot, note, interval);
    }

    this.metaData = analyse();
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
   * @param accidentals the list of sharps/flats in the chord
   */
  public Chord(ScaleNote chordRootNote, ChordType chordType, List<Note> accidentals, Note addedNote) {
    this.chordRootNote = chordRootNote;
    this.chordRoot = chordRootNote.getNote();
    this.accidentals = accidentals;
    this.addedNote = addedNote;

    Scale chromaticScaleFromChordRoot =
        new Scale(chordRootNote.getNote(), IntervalPattern.SCALE_CHROMATIC);

    if (addedNote != null) {
      Optional<ScaleNote> optAddedScaleNote = chromaticScaleFromChordRoot.findScaleNote(addedNote);
      addedScaleNote = optAddedScaleNote.get();
    }
    for (int third : chordType.getThirds()) {
      ScaleNote chordNote = Scale.getScaleNote(chordRootNote, third);
      Optional<ScaleNote> noteInRootScale =
          chromaticScaleFromChordRoot.findScaleNote(chordNote.getNote());

      Note note = noteInRootScale.get().getNote();
      addScaleNote(chromaticScaleFromChordRoot, note, noteInRootScale.get().getInterval().get());
    }
    metaData = analyse();
  }
  

  private void addScaleNote(Scale scale, Note note, ScaleInterval interval) {
    ScaleNote newNote = new ScaleNote(note, Optional.of(interval), scale);
    chordNotes.add(newNote);
  }

  /**
   * Will search all possible chords having the first note as the tonic, and containing all the
   * other notes exclusively
   * 
   * @param notes the notes to use in the chord search
   * @return optionally! the matching chord
   */
  public static Optional<Chord> findChord(Note... notes) {
    Optional<Chord> result = Optional.empty();

    for (Note note : notes) {
      if (result.isPresent()) {
        break;
      }
      for (IntervalPattern pattern : IntervalPattern.values()) {
        if (pattern.getPatternType() != PatternType.CHORD) {
          continue;
        }
        // TODO? cannot find the chords with added Notes
        Chord candidate = new Chord(note, pattern, null);
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
   * 
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
        // TODO? cannot find the chords with added Notes
        Chord candidate = new Chord(Note.forPitch(pitch), pattern, null);
        if (candidate.containsNotes(notes)) {
          chords.add(candidate);
        }
      }
    }
    return chords;
  }

  /**
   * Will search all possible chords having the first note as the tonic, and containing all the
   * other notes, but not exclusively
   * 
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
      // TODO? cannot find the chords with added Notes
      Chord candidate = new Chord(notes[0], pattern, null);
      if (candidate.containsNotes(notes)) {
        chords.add(candidate);
      }
    }
    return chords;
  }

  public String getLabel() {
    return chordRoot.getLabel() + metaData.label + ((addedNote != null) ? "/"+addedNote.getLabel() : "");
  }

  public String getTitle() {
    StringBuilder sb = new StringBuilder();
    sb.append(getLabel()).append("   (").append(chordRoot.name().toLowerCase()).append(" ").append(getMetaData().getChordPattern().name().toLowerCase());
    if (addedNote != null) {
      sb.append("/").append(addedNote.getLabel());
    }
    sb.append(")    [");
    
    for (ScaleNote cn:chordNotes) {
      Note note = cn.getNote();
      final Note theNote = note;
      Optional<Note> accidental =
          this.accidentals.stream().filter(n -> n.getPitch() == theNote.getPitch()).findFirst();
      note = accidental.orElse(note);
      sb.append(note.getLabel()).append("(").append(cn.getInterval().get().getLabel()).append("), ");
    }
    sb.replace(sb.length()-2, sb.length()-1, "]");
    return sb.toString();
  }

  public String toString() {
    return describe(false);
  }

  //TODO handle addedNote
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
      final Note theNote = note;

      Optional<Note> accidental =
          this.accidentals.stream().filter(n -> n.getPitch() == theNote.getPitch()).findFirst();
      note = accidental.orElse(note);
      notes.add(note);
      
      
      Optional<ScaleNote> chordNote =
          chordNotes.stream().filter(sn -> sn.getNote().getPitch() == theNote.getPitch()).findAny();
      intervals.add(scaleNote.getInterval().get());
      if (chordNote.isPresent()) {
        chordsNotes.add(note);
      }
      scaleNote = scaleNote.getNextScaleNote();
    } while (scaleNote.getNote().getPitch() != chordRootNote.getNote().getPitch());


    for (Note note : notes) {
      // find the current notes pitch in the chordNotes
      Optional<ScaleNote> chordMatch =
          chordNotes.stream().filter(cn -> cn.getNote().getPitch() == note.getPitch()).findFirst();
      if (chordMatch.isPresent()) {
        sb.append(String.format("%s%-2s    %s", (mono ? "" : ColourMap.get(note.getPitch())),
            note.getLabel(), (mono ? "" : Colour.RESET)));
      } else {
        sb.append(String.format("%-2s    ", note.getLabel()));
      }
    }
    sb.append("\n          ");
    for (int n = 0; n < intervals.size(); n++) {
      ScaleInterval interval = intervals.get(n);
      sb.append(String.format("%s%-2s    %s",
          chordsNotes.contains(notes.get(n)) ? (mono ? "" : ColourMap.get(notes.get(n).getPitch()))
              : "",
          interval, (mono ? "" : Colour.RESET)));
    }
    sb.append("\n\n");
    return sb.toString();
  }

  public boolean containsOnlyNotes(Note... notes) {
    int cnt = 0;
    for (Note note : notes) {
      for (ScaleNote scaleNote : chordNotes) {
        if (scaleNote.getNote().getPitch() == note.getPitch()) {
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
        if (scaleNote.getNote().getPitch() == note.getPitch()) {
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
    List<ScaleInterval> intervals = (List<ScaleInterval>) chordNotes.stream()
      .map(cn->cn.getInterval())
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.toList());

    IntervalPatternMetadata intervalPatternMetadata = IntervalPatternAnalyser.analyse(intervals);

    IntervalPattern chordPattern = null;
    String label = " ???";
    for (IntervalPattern pattern : IntervalPattern.values()) {
      if (pattern.getPatternType() != PatternType.CHORD) {
        continue;
      }
      if (containsOnlyIntervals(pattern.getIntervals().toArray(new ScaleInterval[] {}))) {
        label = pattern.getLabel();
        chordPattern = pattern;
        break;
      }
    }

    return new ChordMetadata(intervalPatternMetadata, label, chordPattern);
  }
}
