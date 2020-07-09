package me.flotsam.frettler.view;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.instrument.Guitar;
import me.flotsam.frettler.instrument.Tone;
import static java.lang.System.out;

public class GuitarConsole {

  private Guitar guitar;
  public GuitarConsole(Guitar guitar) {
    this.guitar = guitar;
  }

  public void showFretboard() {
    showFretboard(Scale.CHROMATIC_SCALE);
  }

  public void showFretboard(Chord chord) {
    out.print("    ");
    out.println(StringUtils.center(chord.getTitle(), 61));
    out.print("\n");
    showFretboard(chord.getChordNotes().stream().map(sn -> sn.getNote()).collect(Collectors.toList()));
  }

  public void showFretboard(Scale scale) {
    out.print("    ");
    out.println(StringUtils.center(scale.getTitle(), 61));
    out.print("\n");
    showFretboard(scale.getScaleNotes().stream().map(sn -> sn.getNote()).collect(Collectors.toList()));
  }

  public void showFretboard(List<Note> notes) {
    out.println("   |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 | 11 | 12 |");
    out.println("   -------------------------------------------------------------");
    for (int i = guitar.getStringTones().size() - 1; i >= 0; i--) {
      List<Tone> tonesInString = guitar.getStringTones().get(i);
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
      out.println(stringBuilder.toString());
    }
    out.println("   -------------------------------------------------------------");
  }
}
