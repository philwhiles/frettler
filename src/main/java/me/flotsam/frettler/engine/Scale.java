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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import me.flotsam.frettler.engine.Chord.ChordType;
import me.flotsam.frettler.engine.IntervalPattern.PatternType;
import me.flotsam.frettler.engine.LineOfFifths.LineEntry;
import me.flotsam.frettler.view.Colour;
import me.flotsam.frettler.view.ColourMap;

enum Position {
  HEAD, MIDDLE, TAIL;
}


/**
 * Is a type of cyclic linked list. Represents a scale, and created from a root note and a scale
 * pattern containing intervals.
 * 
 * @author philwhiles
 *
 */
public class Scale {

  public static final Scale CHROMATIC_SCALE = new Scale(Arrays.asList(Pitch.values()));

  @Getter
  private ScaleNote head = null;
  @Getter
  private ScaleNote tail = null;
  @Getter
  private IntervalPattern scalePattern;
  @Getter
  private Note rootNote;
  @Getter
  private List<Note> accidentals;

  public Scale(Note rootNote, IntervalPattern scalePattern) {
    if (scalePattern.getPatternType() == PatternType.CHORD) {
      System.err.println(
          "Interval pattern '" + scalePattern.getLabel() + "' is not a scale/mode pattern");
      System.exit(-1);
    }
    this.scalePattern = scalePattern;
    this.rootNote = rootNote;
    LineEntry lineEntry = null;
    if (scalePattern.getMetadata().isMinorRange()) {
      lineEntry = LineOfFifths.getMinorEntry(rootNote);
    } else {
      lineEntry = LineOfFifths.getMajorEntry(rootNote);
    }
    this.accidentals = lineEntry.getAccidentals();

    Optional<ScaleNote> rootScaleNote = CHROMATIC_SCALE.findScaleNote(rootNote);
    for (ScaleInterval interval : scalePattern.getIntervals()) {
      ScaleNote scaleNote = Scale.getScaleNote(rootScaleNote.get(), interval);
      Note note = scaleNote.getNote();
      final Note theNote = note;
      if (scalePattern != IntervalPattern.SCALE_CHROMATIC) {
        Optional<Note> accidental =
            this.accidentals.stream().filter(n -> n.getPitch() == theNote.getPitch()).findFirst();
        note = accidental.orElse(note);
      }
      addScaleNote(note, Optional.of(interval));
    }
  }


  public Scale(List<Pitch> pitches) {
    this.scalePattern = IntervalPattern.SCALE_CHROMATIC;
    this.rootNote = Note.forPitch(pitches.get(0));
    ScaleInterval[] intervals = ScaleInterval.values();
    int interval = 0;
    for (Pitch pitch : pitches) {
      addScaleNote(Note.forPitch(pitch), Optional.of(intervals[interval++]));
    }
  }



  private void addScaleNote(Note note, Optional<ScaleInterval> interval) {
    ScaleNote newNoteNode = new ScaleNote(note, interval, this);

    if (head == null) {
      head = newNoteNode;
      head.setPosition(Position.HEAD);
    } else {
      tail.setNextScaleNote(newNoteNode);
      newNoteNode.setPrevScaleNote(tail);
      tail.setPosition(Position.MIDDLE);
    }

    tail = newNoteNode;
    tail.setPosition(Position.TAIL);
    tail.setNextScaleNote(head);
    head.setPrevScaleNote(tail);
  }



  public boolean containsNote(Pitch pitch) {
    ScaleNote currentNoteNode = head;

    if (head == null) {
      return false;
    } else {
      do {
        if (currentNoteNode.getNote().getPitch() == pitch) {
          return true;
        }
        currentNoteNode = currentNoteNode.getNextScaleNote();
      } while (currentNoteNode != head);
      return false;
    }
  }

  public Optional<ScaleNote> findScaleNote(Note note) {
    ScaleNote currentNoteNode = head;

    if (head == null) {
      return Optional.empty();
    } else {
      do {
        if (currentNoteNode.getNote().getPitch() == note.getPitch()) {
          return Optional.of(currentNoteNode);
        }
        currentNoteNode = currentNoteNode.getNextScaleNote();
      } while (currentNoteNode != head);
      return Optional.empty();
    }
  }

  public Optional<ScaleNote> findScaleNote(ScaleInterval scaleInterval) {
    ScaleNote currentNoteNode = head;

    if (head == null) {
      return Optional.empty();
    } else {
      do {
        if (currentNoteNode.getInterval().get().getSemiTones() == scaleInterval.getSemiTones()) {
          return Optional.of(currentNoteNode);
        }
        currentNoteNode = currentNoteNode.getNextScaleNote();
      } while (currentNoteNode != head);
      return Optional.empty();
    }
  }

  public List<ScaleNote> getScaleNotes() {
    List<ScaleNote> scaleNotes = new ArrayList<>();
    ScaleNote scaleNote = head;

    if (scaleNote != null) {
      do {
        scaleNotes.add(scaleNote);
        scaleNote = scaleNote.getNextScaleNote();
      } while (scaleNote != head && scaleNote.getNote() != head.getNote());
    }
    return scaleNotes;
  }

  public String getTitle() {
    String title = null;
    if (scalePattern == IntervalPattern.SCALE_CHROMATIC) {
      title = scalePattern.getLabel() + " Scale";
    } else {
      title = rootNote.getLabel() + " " + scalePattern.getLabel();
    }
    return title;
  }

  public static ScaleNote getScaleNote(ScaleNote rootScaleNote, ScaleInterval interval) {
    return getScaleNote(rootScaleNote, interval.getSemiTones());
  }

  public static ScaleNote getScaleNote(ScaleNote rootScaleNote, int semiTones) {
    ScaleNote scaleNote = rootScaleNote;
    for (int i = 0; i < Math.abs(semiTones); i++) {
      scaleNote = semiTones <= 0 ? scaleNote.getPrevScaleNote() : scaleNote.getNextScaleNote();
    }
    return scaleNote;
  }

  public List<Chord> createScaleChords() {
    List<Chord> scaleChords = new ArrayList<>();
    List<ScaleNote> scaleNotesToUse = null;
    List<ScaleNote> scaleNotes = null;

    if (!scalePattern.isScaleChordGenerationSupported()) {
      System.err.println("Chord generation from this scale is currently not supported");
    } else {
      if (scalePattern.getParentPattern() == null) {
        scaleNotesToUse = getScaleNotes();
      } else {
        scaleNotes = getScaleNotes();
        scaleNotesToUse = new Scale(rootNote, scalePattern.getParentPattern()).getScaleNotes();
      }
      for (ScaleNote scaleNote : scaleNotesToUse) {
        if (scaleNotes == null || scaleNotes.stream().anyMatch(sn -> sn.equalsTonally(scaleNote))) {
          scaleChords.add(new Chord(scaleNote, ChordType.TRIAD, this.accidentals, null));
        }
      }
    }

    return scaleChords;
  }

  public String toString() {
    return describe(true);
  }


  public String describe(boolean mono) {
    StringBuilder sb = new StringBuilder();
    sb.append("\n          ");
    Optional<ScaleNote> chromaticScaleNote = CHROMATIC_SCALE.findScaleNote(rootNote);
    ScaleNote scaleNote = head;
    List<Note> notes = new ArrayList<>();
    List<ScaleInterval> intervals = new ArrayList<>();
    List<Note> chromaticScaleNotes = new ArrayList<>();
    List<Integer> steps = new ArrayList<>();

    ScaleNote lastNote = null;
    int rootCount = 0;
    while (rootCount < 2) {
      rootCount += chromaticScaleNote.get().getNote().getPitch() == head.getNote().getPitch() ? 1 : 0;

      Note note = chromaticScaleNote.get().getNote();

      final Note theNote = note;
      Optional<Note> accidental =
          this.accidentals.stream().filter(n -> n.getPitch() == theNote.getPitch()).findFirst();
      note = accidental.orElse(note);
 
      chromaticScaleNotes.add(note);
      if (note.getPitch() == scaleNote.getNote().getPitch()) {
        notes.add(note);
        intervals.add(scaleNote.getInterval().get());
        scaleNote = scaleNote.getNextScaleNote();
        if (lastNote != null) {
          steps.add(chromaticScaleNote.get().getInterval().get().getSemiTones() - lastNote.getInterval().get().getSemiTones()); 
        } else {
          steps.add(null);
        }
        lastNote = chromaticScaleNote.get();
      } else {
        notes.add(null);
        intervals.add(null);
        steps.add(null);
      }
      chromaticScaleNote = Optional.of(chromaticScaleNote.get().getNextScaleNote());
    }

    for (Note note : chromaticScaleNotes) {
      sb.append(String.format("%s%-2s    %s",
          notes.contains(note) ? (mono ? "" : ColourMap.get(note.getPitch())) : "", note.getLabel(),
          (mono ? "" : Colour.RESET)));
    }
    sb.append("\n          ");
    for (int n = 0; n < intervals.size(); n++) {
      ScaleInterval interval = intervals.get(n);
      if (interval == null) {
        sb.append(String.format("      "));
      } else
        sb.append(String.format("%s%-2s    %s",
            notes.contains(notes.get(n)) ? (mono ? "" : ColourMap.get(notes.get(n).getPitch()))
                : "",
            interval, (mono ? "" : Colour.RESET)));
    }
    sb.append("\n          ");
    for (int n = 0; n < steps.size(); n++) {
      Integer step = steps.get(n);
      if (step == null) {
        sb.append(String.format("      "));
      } else
        sb.append(String.format("%-6s", step == 2 ? "Tone" : "Semi"));
    }
    sb.append("\n\n");
    return sb.toString();
  }
}


