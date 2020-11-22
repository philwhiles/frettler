package me.flotsam.frettler.engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LineOfFifths {
  @Getter
  static LinkedHashMap<Note, LineEntry> majorLine = new LinkedHashMap<>();
  @Getter
  static LinkedHashMap<Note, LineEntry> minorLine = new LinkedHashMap<>();
  @Getter
  static LinkedHashMap<Note, LineEntry> dimLine = new LinkedHashMap<>();

  static {
    ScaleNote c = Scale.CHROMATIC_SCALE.findScaleNote(Note.forPitch(Pitch.C)).get();
    for (int i = -7; i <= 7; i++) {
      int fifthIntervals = ScaleInterval.P5.getSemiTones();

      ScaleNote majorNote = Scale.getScaleNote(c, i * fifthIntervals);
      ScaleNote minorNote = Scale.getScaleNote(c, i * fifthIntervals + fifthIntervals * 3);
      ScaleNote dimNote = Scale.getScaleNote(c, i * fifthIntervals + fifthIntervals * 5);
      
      List<Note> accidentals = new ArrayList<>();
      if (i >= 0) {
        for (int n = -1; n < i - 1; n++) {
          Optional<Note> note =
              Note.getSharp(Scale.getScaleNote(c, n * fifthIntervals + 1).getNote().getPitch());
          if (note.isPresent()) {
            accidentals.add(note.get());
          }
        }
        majorLine.put(majorNote.getNote(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), dimNote.getNote(), accidentals));
        minorLine.put(minorNote.getNote(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), dimNote.getNote(), accidentals));
        dimLine.put(dimNote.getNote(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), dimNote.getNote(), accidentals));
      } else if (i < 0) {
        for (int n = 3; n > i + 3; n--) {
          Optional<Note> note =
              Note.getFlat(Scale.getScaleNote(c, n * fifthIntervals + 1).getNote().getPitch());
          if (note.isPresent()) {
            accidentals.add(note.get());
          }
        }

        majorLine.put(majorNote.getNote().getFlat(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), dimNote.getNote(), accidentals));
        minorLine.put(minorNote.getNote().getFlat(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), dimNote.getNote(), accidentals));
        dimLine.put(dimNote.getNote(),
            new LineEntry(majorNote.getNote(), minorNote.getNote(), dimNote.getNote(), accidentals));
      }
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

  public static LineEntry getDimEntry(Note note) {
    LineEntry entry = dimLine.get(note);
    if (entry == null) {
      entry = dimLine.get(note.getAlternate());
    }
    return entry;
  }

  @AllArgsConstructor
  public static class LineEntry {
    @Getter
    private Note major;
    @Getter
    private Note minor;
    @Getter
    private Note dim;
    @Getter
    private List<Note> accidentals;
  }
}
