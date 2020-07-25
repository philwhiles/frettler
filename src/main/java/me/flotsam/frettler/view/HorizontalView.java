package me.flotsam.frettler.view;

import static java.lang.System.out;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.instrument.Fret;
import me.flotsam.frettler.instrument.FrettedInstrument;

public class HorizontalView {

  private static final List<Integer> inlays = Arrays.asList(1, 3, 5, 7, 9, 12, 15, 17, 19, 21, 23);
  private FrettedInstrument instrument;
  private Options defaultOptions = new Options(false, true, true);

  public HorizontalView(FrettedInstrument instrument) {
    this.instrument = instrument;
  }

  public void showFretboard() {
    showScale(Scale.CHROMATIC_SCALE, defaultOptions);
  }

  public void showFretboard(Options options) {
    showScale(Scale.CHROMATIC_SCALE, options);
  }

  public void showChord(Chord chord) {
    showChord(chord, defaultOptions);
  }

  public void showChord(Chord chord, Options options) {
    out.println();
    out.print("    ");
    out.println(StringUtils.center(chord.getTitle() + " ~ (" + instrument.getLabel() + " [" + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(",")) + "]" , 84));
    out.println();
    display(chord.getChordNotes(), options);
  }

  public void showScale(Scale scale) {
    showScale(scale, defaultOptions);
  }

  public void showScale(Scale scale, Options options) {
    out.println();
    out.print("    ");
    out.println(StringUtils.center(scale.getTitle() + " ~ (" + instrument.getLabel() + " [" + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(",")) + "]" , 84));
    out.println();
    display(scale.getScaleNotes(), options);
  }

  public void display(List<ScaleNote> scaleNotes, Options options) {
    ColourMap cm = new ColourMap();
    out.println(createFretboardSide(true, options));

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
            if (instrument.isBanjo() && (tone.getFretNum() > 0 && tone.getFretNum() < 6) && tone.getStringNum() == 0) {
              stringBuilder.append("      ┃");
            } else {
              stringBuilder.append("┈┈┈┈┈┈┃");
            }
          }
        }
      }
      out.println(stringBuilder.toString());
    }
    
    out.println(createFretboardSide(false, options));
    out.println();
    out.println();
  }
  
  private String createFretboardSide(boolean upper, Options options) {
   String lowerTop = "┗";
   String lowerBottom = "┛";
   String upperTop = "┏";
   String upperBottom = "┓";
   String side = "━";
   String inlay = ".";
    
   StringBuilder sb = new StringBuilder("   ").append(upper ? upperTop : lowerTop);
   for (int fret = 1; fret <= instrument.getFrets(); fret++) {
    sb.append(side.repeat(2));
    sb.append(options.isInlays() && inlays.contains(fret) ? inlay : side);
    sb.append(side.repeat(fret == instrument.getFrets() ? 3 : 4));
   }
   sb.append(upper ? upperBottom : lowerBottom);
   
   return sb.toString();
  }

  @Data
  @AllArgsConstructor
  public class Options {
    private boolean intervals;
    private boolean inlays;
    private boolean colour;
  }
}

