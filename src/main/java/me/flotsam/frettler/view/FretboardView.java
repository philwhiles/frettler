package me.flotsam.frettler.view;

import static java.lang.System.out;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.instrument.Fret;
import me.flotsam.frettler.instrument.FrettedInstrument;

public class FretboardView {

  private FrettedInstrument instrument;
  private Options defaultOptions = new Options(false, true, true);

  public FretboardView(FrettedInstrument instrument) {
    this.instrument = instrument;
  }

  public void showFretboard() {
    showFretboard(Scale.CHROMATIC_SCALE, defaultOptions);
  }

  public void showFretboard(Options options) {
    showFretboard(Scale.CHROMATIC_SCALE, options);
  }

  public void showFretboard(Chord chord) {
    showFretboard(chord, defaultOptions);
  }

  public void showFretboard(Chord chord, Options options) {
    out.println();
    out.print("    ");
    out.println(StringUtils.center(instrument.getLabel() + " ~ " + chord.getTitle(), 84));
    out.println();
    showFretboard(chord.getChordNotes(), options);
  }

  public void showFretboard(Scale scale) {
    showFretboard(scale, defaultOptions);
  }

  public void showFretboard(Scale scale, Options options) {
    out.println();
    out.print("    ");
    out.println(StringUtils.center(instrument.getLabel() + " ~ " + scale.getTitle(), 84));
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


    for (int i = instrument.getFretsByString().size() - 1; i >= 0; i--) {
      List<Fret> tonesInString = instrument.getFretsByString().get(i);
      StringBuilder stringBuilder = new StringBuilder();
      for (Fret tone : tonesInString) {
        Optional<ScaleNote> note =
            scaleNotes.stream().filter(sn -> sn.getNote() == tone.getNote()).findFirst();

        if (note.isPresent()) {
          String fretStr = null;
          if (options.isIntervals()) {
            fretStr = note.get().getInterval().isPresent() ? note.get().getInterval().get().getLabel() : note.get().getNote().getLabel();
          } else {
              fretStr = note.get().getNote().getLabel();
          }
          if (options.isColour()) {
            Colour col = cm.get(fretStr);
            if (tone.getFretNum() == 0) {
              fretStr = fretStr.length() == 2 ? fretStr : String.format("%2s", fretStr);
              stringBuilder.append("").append(col).append(fretStr).append(Colour.RESET).append("┃┃");
            } else {
              if (fretStr.length() == 2) {
                fretStr = String.format("┈╴%s%s%s╶┈┃", col, fretStr, Colour.RESET);
              } else {
                fretStr = String.format("┈╴%s%s%s╶┈┈┃", col, fretStr, Colour.RESET);
              }
              stringBuilder.append(fretStr);
            }
          } else {
            if (tone.getFretNum() == 0) {
              fretStr = fretStr.length() == 2 ? fretStr : String.format("%2s", fretStr);
              stringBuilder.append("").append(fretStr).append("┃┃");
            } else {
              fretStr = fretStr.length() == 2 ? fretStr : String.format("%s┈", fretStr);
              stringBuilder.append("┈┈").append(fretStr).append("┈┈┃");
            }
          }

        } else {
          if (tone.getFretNum() == 0) {
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

