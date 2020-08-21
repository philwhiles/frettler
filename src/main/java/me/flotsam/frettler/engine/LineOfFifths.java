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
  static
  {
    ScaleNote c = Scale.CHROMATIC_SCALE.findScaleNote(Pitch.C).get();
    for (int i = -7; i <= 7; i++) {
      int fifthIntervals = ScaleInterval.P5.getSemiTones();

      ScaleNote majorNote = Scale.getScaleNote(c, i * fifthIntervals);
      ScaleNote minorNote = Scale.getScaleNote(c, i * fifthIntervals + 21);

      Scale majorScale = new Scale(majorNote.getPitch(), IntervalPattern.SCALE_MAJOR);
      Scale minorScale = new Scale(minorNote.getPitch(), IntervalPattern.SCALE_MINOR);


      List<Note> accidentals = new ArrayList<>();
      if (i > 0) {
        for (int n = -1; n < i - 1; n++) {
          ScaleNote sharpNote = Scale.getScaleNote(c, n * fifthIntervals + 1);
          System.out.println(sharpNote);
          Optional<Note> note =
              Note.getSharp(Scale.getScaleNote(c, n * fifthIntervals + 1).getPitch());
          if (note.isPresent()) {
            accidentals.add(note.get());
          }
        }
      } else if (i < 0) {
        for (int n = 3; n > i + 3; n--) {
          ScaleNote flatNote = Scale.getScaleNote(c, n * fifthIntervals + 1);
          System.out.println(flatNote);
          Optional<Note> note =
              Note.getFlat(Scale.getScaleNote(c, n * fifthIntervals + 1).getPitch());
          if (note.isPresent()) {
            accidentals.add(note.get());
          }
        }
      }

      majorLine.put(majorNote.getPitch(), new LineEntry(majorScale, minorScale, accidentals));
      minorLine.put(minorNote.getPitch(), new LineEntry(majorScale, minorScale, accidentals));
      System.out.println("----------------");
    }
  }
  
  public static LineEntry getMajorEntry(Pitch pitch) {
    return majorLine.get(pitch);
  }
  


  @AllArgsConstructor
  static class LineEntry {
    @Getter
    private Scale major;
    @Getter
    private Scale minor;
    @Getter
    private List<Note> accidentals;
  }
}
