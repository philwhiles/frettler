package me.flotsam.frettler.view.console;

import static java.lang.System.out;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.instrument.Guitar;
import me.flotsam.frettler.instrument.Tone;

public class GuitarView {

  private Guitar guitar;
  private Options defaultOptions = new Options(false, true, true);

  public GuitarView(Guitar guitar) {
    this.guitar = guitar;
  }

  public void showFretboard() {
    showFretboard(Scale.CHROMATIC_SCALE, defaultOptions);
  }

  public void showFretboard(Chord chord) {
    showFretboard(chord, defaultOptions);
  }

  public void showFretboard(Chord chord, Options options) {
    out.print("    ");
    out.println(StringUtils.center(chord.getTitle(), 84));
    out.println();
    showFretboard(chord.getChordNotes(), options);
  }

  public void showFretboard(Scale scale) {
    showFretboard(scale, defaultOptions);
  }

  public void showFretboard(Scale scale, Options options) {
    out.print("    ");
    out.println(StringUtils.center(scale.getTitle(), 84));
    out.println();
    showFretboard(scale.getScaleNotes(), options);
  }

  public void showFretboard(List<ScaleNote> scaleNotes, Options options) {
    ColourMap cm = new ColourMap();

    if (options.isInlays()) {
      out.println(
          "   ┏━━.━━━━━━━━━━━━━.━━━━━━━━━━━━━.━━━━━━━━━━━━━.━━━━━━━━━━━━━.━━━━━━━━━━━━━━━━━━━━.━━━┓");
    } else {
      out.println(
          "   ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
    }


    for (int i = guitar.getStringTones().size() - 1; i >= 0; i--) {
      List<Tone> tonesInString = guitar.getStringTones().get(i);
      StringBuilder stringBuilder = new StringBuilder();
      for (Tone tone : tonesInString) {
        Optional<ScaleNote> note =
            scaleNotes.stream().filter(sn -> sn.getNote() == tone.getNote()).findFirst();

        if (note.isPresent()) {
          String fretStr = options.isIntervals() ? note.get().getInterval().get().getLabel()
              : note.get().getNote().getLabel();

          if (options.isColour()) {
            Colour col = cm.get(fretStr);
            if (tone.getFret() == 0) {
              fretStr = fretStr.length() == 2 ? fretStr : String.format("%2s", fretStr);
              stringBuilder.append("").append(col).append(fretStr).append(Colour.RESET).append("┃┃");
            } else {
              if (fretStr.length() == 2) {
                fretStr = String.format("%s%s%s", col, fretStr, Colour.RESET);
              } else {
                fretStr = String.format("%s%s%s┈", col, fretStr, Colour.RESET);
              }
              stringBuilder.append("┈┈").append(fretStr).append("┈┈┃");
            }
          } else {
            if (tone.getFret() == 0) {
              fretStr = fretStr.length() == 2 ? fretStr : String.format("%2s", fretStr);
              stringBuilder.append("").append(fretStr).append("┃┃");
            } else {
              fretStr = fretStr.length() == 2 ? fretStr : String.format("%s┈", fretStr);
              stringBuilder.append("┈┈").append(fretStr).append("┈┈┃");
            }
          }

        } else {
          if (tone.getFret() == 0) {
            stringBuilder.append("  ").append("┃┃");
          } else {
            stringBuilder.append("┈┈┈┈┈┈┃");
          }
        }
      }
      out.println(stringBuilder.toString());
    }

    if (options.isInlays()) {
      out.println(
          "   └━━.━━━━━━━━━━━━━.━━━━━━━━━━━━━.━━━━━━━━━━━━━.━━━━━━━━━━━━━.━━━━━━━━━━━━━━━━━━━━.━━━┛");
    } else {
      out.println(
          "   └━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }
    out.println();
    out.println();
  }

  @Data
  @AllArgsConstructor
  public class Options {
    private boolean intervals;
    private boolean inlays;
    private boolean colour;
  }
}

