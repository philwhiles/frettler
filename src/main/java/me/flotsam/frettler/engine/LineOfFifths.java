package me.flotsam.frettler.engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LineOfFifths {
  static LinkedHashMap<Note, LineEntry> majorLine = new LinkedHashMap<>();
  static LinkedHashMap<Note, LineEntry> minorLine = new LinkedHashMap<>();
  static {
    ScaleNote c = Scale.CHROMATIC_SCALE.findScaleNote(Note.forPitch(Pitch.C)).get();
    for (int i = -7; i <= 7; i++) {
      int fifthIntervals = ScaleInterval.P5.getSemiTones();

      ScaleNote majorNote = Scale.getScaleNote(c, i * fifthIntervals);
      ScaleNote minorNote = Scale.getScaleNote(c, i * fifthIntervals + fifthIntervals * 3);

//      System.out.println();
      
      List<Note> accidentals = new ArrayList<>();
      boolean isFlat = false;
      if (i >= 0) {
        for (int n = -1; n < i - 1; n++) {
          Optional<Note> note =
              Note.getSharp(Scale.getScaleNote(c, n * fifthIntervals + 1).getNote().getPitch());
          if (note.isPresent()) {
            accidentals.add(note.get());
          }
        }
//      System.out.println(majorNote.getNote());
//      System.out.println(minorNote.getNote());
        majorLine.put(majorNote.getNote(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), accidentals, isFlat));
        minorLine.put(minorNote.getNote(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), accidentals, isFlat));
      } else if (i < 0) {
        for (int n = 3; n > i + 3; n--) {
          Optional<Note> note =
              Note.getFlat(Scale.getScaleNote(c, n * fifthIntervals + 1).getNote().getPitch());
          if (note.isPresent()) {
            accidentals.add(note.get());
            isFlat = true;
          }
        }
//      System.out.println(majorNote.getNote().getFlat());
//      System.out.println(minorNote.getNote().getFlat());

        majorLine.put(majorNote.getNote().getFlat(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), accidentals, isFlat));
        minorLine.put(minorNote.getNote().getFlat(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), accidentals, isFlat));
      }
//      System.out.println(accidentals);

    }
  }

  public static LineEntry getMajorEntry(Note note) {
    LineEntry entry = majorLine.get(note);
    if (entry == null) {
      entry = majorLine.get(note.getAlternate());
    }
    return entry;
  }

  public static LineEntry getMinorEntry(Note note) {
    LineEntry entry = minorLine.get(note);
    if (entry == null) {
      entry = minorLine.get(note.getAlternate());
    }
    return entry;
  }

  @AllArgsConstructor
  static class LineEntry {
    @Getter
    private Note major;
    @Getter
    private Note minor;
    @Getter
    private List<Note> accidentals;
    @Getter
    private boolean flat;
  }
}
