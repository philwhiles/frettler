package me.flotsam.frettler.instrument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import lombok.Getter;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.engine.Tone;

public class Guitar {
  
  public static final int FRETS = 12;
  
  @Getter
  List<Tone> tones = new ArrayList<>();
  @Getter
  List<Note> stringNotes;
  @Getter
  List<List<Tone>> stringTones = new ArrayList<>();

  public Guitar(Note[] stringNotes) {
    this.stringNotes = Arrays.asList(stringNotes);

    for (int stringNum = 0; stringNum < stringNotes.length; stringNum++) {
      Optional<ScaleNote> optScaleNote =
          Scale.CHROMATIC_SCALE.findScaleNote(stringNotes[stringNum]);
      ScaleNote scaleNote = optScaleNote.get();

      for (int fret = 0; fret <= FRETS; fret++) {

        int octave = 0; // until known from prev tone

        // first look for relative tone on current string
        Tone prevTone = findRelativeToneUp(stringNum, scaleNote.getNote());
        if (prevTone != null) {
          octave = prevTone.getOctave() + 1;
        } else {
          // otherwise on the prev string
          prevTone = findRelativeToneUp(stringNum - 1, scaleNote.getNote());
          if (prevTone != null) {
            octave = prevTone.getFret() > fret ? prevTone.getOctave() : prevTone.getOctave() + 1;
          }
        }
        Tone tone = new Tone(stringNum * FRETS + fret, scaleNote.getNote(), octave,
            stringNum, stringNotes[stringNum], fret);
        tones.add(tone);
        scaleNote = scaleNote.getNextScaleNote();
      }
      final int x = stringNum;
      stringTones
          .add(tones.stream().filter(t -> t.getStringNum() == x).collect(Collectors.toList()));
    }
  }

  public void printFretboard(Chord chord) {
    System.out.print("    ");
    System.out.println(StringUtils.center(chord.getTitle(), 61));
    System.out.print("\n");
    printFretboard(chord.getChordNotes().stream().map(sn -> sn.getNote()).collect(Collectors.toList()));
  }

  public void printFretboard(Scale scale) {
    System.out.print("    ");
    System.out.println(StringUtils.center(scale.getTitle(), 61));
    System.out.print("\n");
    printFretboard(scale.getScaleNotes().stream().map(sn -> sn.getNote()).collect(Collectors.toList()));
  }

  public void printFretboard(List<Note> notes) {
    System.out.println("   |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 | 11 | 12 |");
    System.out.println("   -------------------------------------------------------------");
    for (int i = stringTones.size() - 1; i >= 0; i--) {
      List<Tone> tonesInString = stringTones.get(i);
      StringBuilder stringBuilder = new StringBuilder();
      for (Tone tone : tonesInString) {
        String toneLabel = tone.getNote().getLabel();
        if (tone.getFret() == 0) {
          if (notes.contains(tone.getNote())) {
            stringBuilder.append(" ").append(toneLabel).append("||");
          } else {
            stringBuilder.append("  ").append("||");
          }
        } else {
          if (notes.contains(tone.getNote())) {
            String toneStr = tone.getNote().toString().length() == 2 ? toneLabel
                : String.format("-%s", toneLabel);
            stringBuilder.append("--").append(toneStr);
          } else {
            stringBuilder.append("----");
          }
          stringBuilder.append("|");

        }
      }
      System.out.println(stringBuilder.toString());
    }
    System.out.println("   -------------------------------------------------------------");
  }

  private Tone findRelativeToneUpFromIndex(int stringNum, Note note, int index) {
    Tone tone = null;
    for (int idx = index; idx-- > 0;) {
      Tone aTone = tones.get(idx);
      if (aTone.getNote() == note && aTone.getStringNum() == stringNum) {
        tone = tones.get(idx);
        break;
      }
    }
    return tone;
  }

  private Tone findRelativeToneUp(int stringNum, Note note) {
    return findRelativeToneUpFromIndex(stringNum, note, tones.size());
  }
}
