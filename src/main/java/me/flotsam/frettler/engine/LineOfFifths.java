package me.flotsam.frettler.engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LineOfFifths {
  static LinkedHashMap<Pitch, LineEntry> majorLine = new LinkedHashMap<>();
  static LinkedHashMap<Pitch, LineEntry> minorLine = new LinkedHashMap<>();
  static {
    ScaleNote c = Scale.CHROMATIC_SCALE.findScaleNote(Note.forPitch(Pitch.C)).get();
    for (int i = -7; i <= 7; i++) {
      int fifthIntervals = ScaleInterval.P5.getSemiTones();

      ScaleNote majorNote = Scale.getScaleNote(c, i * fifthIntervals);
      ScaleNote minorNote = Scale.getScaleNote(c, i * fifthIntervals + fifthIntervals * 3);

      List<Note> accidentals = new ArrayList<>();
      boolean isFlat = false;
      if (i > 0) {
        for (int n = -1; n < i - 1; n++) {
          Optional<Note> note =
              Note.getSharp(Scale.getScaleNote(c, n * fifthIntervals + 1).getNote().getPitch());
          if (note.isPresent()) {
            accidentals.add(note.get());
          }
        }
      } else if (i < 0) {
        for (int n = 3; n > i + 3; n--) {
          Optional<Note> note =
              Note.getFlat(Scale.getScaleNote(c, n * fifthIntervals + 1).getNote().getPitch());
          if (note.isPresent()) {
            accidentals.add(note.get());
            isFlat = true;
          }
        }
      }

      majorLine.put(majorNote.getNote().getPitch(),
          new LineEntry(majorNote.getNote(), minorNote.getNote(), accidentals, isFlat));
      minorLine.put(minorNote.getNote().getPitch(),
          new LineEntry(majorNote.getNote(), minorNote.getNote(), accidentals, isFlat));
    }
  }

  public static LineEntry getMajorEntry(Note note) {
    return majorLine.get(note.getPitch());
  }
  
  public static LineEntry getMinorEntry(Note note) {
    return minorLine.get(note.getPitch());
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
