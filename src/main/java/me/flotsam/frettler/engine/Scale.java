package me.flotsam.frettler.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import me.flotsam.frettler.engine.Chord.ChordType;
import me.flotsam.frettler.engine.IntervalPattern.PatternType;

enum Position {
  HEAD, MIDDLE, TAIL;
}


public class Scale {

  public static final Scale CHROMATIC_SCALE = new Scale(Arrays.asList(Note.values()));

  private ScaleNote head = null;
  private ScaleNote tail = null;
  private IntervalPattern scalePattern;
  private Note rootNote;


  public Scale(Note rootNote, IntervalPattern scalePattern) {
    if (scalePattern.getPatternType() != PatternType.SCALE) {
      System.err.println("Interval pattern '" + scalePattern.getLabel() + "' is not a scale pattern");
      System.exit(-1);
    }
    this.scalePattern = scalePattern;
    this.rootNote = rootNote;

    Optional<ScaleNote> westernScaleRoot = CHROMATIC_SCALE.findScaleNote(rootNote);
    for (ScaleInterval interval : scalePattern.getIntervals()) {
      ScaleNote scaleNote = Scale.getScaleNote(westernScaleRoot.get(), interval);
      addScaleNote(scaleNote.getNote(), Optional.of(interval));
    }
  }

  private Scale(List<Note> notes) {
    this.scalePattern = IntervalPattern.CHROMATIC_SCALE;
    this.rootNote = notes.get(0);
    for (Note note : notes) {
      addScaleNote(note, Optional.empty());
    }
  }


  private void addScaleNote(Note note, Optional<ScaleInterval> interval) {
    ScaleNote newNoteNode = new ScaleNote(note, interval, this);

    if (head == null) {
      head = newNoteNode;
      head.setPosition(Position.HEAD);
    } else {
      tail.setNextScaleNote(newNoteNode);
      tail.setPosition(Position.MIDDLE);
    }

    tail = newNoteNode;
    tail.setPosition(Position.TAIL);
    tail.setNextScaleNote(head);
  }

  public boolean containsNote(Note note) {
    ScaleNote currentNoteNode = head;

    if (head == null) {
      return false;
    } else {
      do {
        if (currentNoteNode.getNote() == note) {
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
        if (currentNoteNode.getNote() == note) {
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
        if (currentNoteNode.getInterval().get() == scaleInterval) {
          return Optional.of(currentNoteNode);
        }
        currentNoteNode = currentNoteNode.getNextScaleNote();
      } while (currentNoteNode != head);
      return Optional.empty();
    }
  }

  public ScaleNote getHead() {
    return head;
  }


  public ScaleNote getTail() {
    return tail;
  }


  public IntervalPattern getScalePattern() {
    return scalePattern;
  }

  public Note getRootNote() {
    return rootNote;
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
    if (scalePattern == IntervalPattern.CHROMATIC_SCALE) {
      title = scalePattern.getLabel() + " Scale";
    } else {
      title = rootNote.getLabel() + " " + scalePattern.getLabel() + " Scale";
    }
    return title;
  }

  public String toString() {
    ScaleNote currentNoteNode = head;
    StringBuilder builder = new StringBuilder();
    builder.append(getTitle()).append(" : ");
    if (head != null) {
      do {
        builder.append(currentNoteNode.getNote().getLabel()).append(":")
            .append(currentNoteNode.getInterval().get().getLabel()).append(" ");
        currentNoteNode = currentNoteNode.getNextScaleNote();
      } while (currentNoteNode != head);
    }
    builder.append("\n");
    return builder.toString();
  }

  public static ScaleNote getScaleNote(ScaleNote rootScaleNote, ScaleInterval interval) {
    ScaleNote scaleNote = rootScaleNote;
    for (int i = 0; i < interval.getValue(); i++) {
      scaleNote = scaleNote.getNextScaleNote();
    }
    return scaleNote;
  }

  public static ScaleNote getThirdNote(ScaleNote rootScaleNote, int third) {
    ScaleNote scaleNote = rootScaleNote;
    for (int i = 0; i < third; i++) {
      scaleNote = scaleNote.getNextScaleNote();
      if (scaleNote.getPosition() == Position.TAIL) {
        scaleNote = scaleNote.getNextScaleNote();
      }
    }
    return scaleNote;
  }

  public List<Chord> createScaleChords() {
    List<Chord> chords = new ArrayList<>();
    for (ScaleNote scaleNote : getScaleNotes()) {
      chords.add(new Chord(scaleNote, ChordType.STANDARD));
    }
    return chords;
  }
}


