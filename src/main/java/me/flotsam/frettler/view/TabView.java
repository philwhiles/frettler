/*
 * Copyright (C) 2020 Philip Whiles
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package me.flotsam.frettler.view;

import static java.lang.System.out;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.engine.ScaleNoteSequence;
import me.flotsam.frettler.instrument.Fret;
import me.flotsam.frettler.instrument.FrettedInstrument;


/**
 * Renders chords or scales in a horizontal fretboard representation, where notes appear in the
 * middle of strings, and the 'fretboard' has inlay markers mirroring standard guitar fret inlays.
 * 
 * @author philwhiles
 *
 */
public class TabView implements View {

  private FrettedInstrument instrument;
  private Options defaultOptions = new Options(false, true, true, false);

  public TabView(FrettedInstrument instrument) {
    this.instrument = instrument;
  }

  public void showScale(Scale scale, ScaleNoteSequence scaleNoteSequence) {
    showScale(scale, scaleNoteSequence, defaultOptions);
  }

  public void showScale(Scale scale, ScaleNoteSequence scaleNoteSequence, Options options) {
    out.println();
    out.print("    ");
    out.println(StringUtils
        .center(scale.getTitle() + " ~ (" + instrument.getInstrumentType().getLabel() + " ["
            + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(","))
            + "]", 84));
    out.println();
    initColourMap(scale);
    prepareSequence(scale, scaleNoteSequence);
    display(scale.getScaleNotes(), options);
  }

  private void prepareSequence(Scale scale, ScaleNoteSequence scaleNoteSequence) {
    ScaleNote scaleNote = scale.getHead();
    for (int i = 0; i < instrument.getFretsByString().size(); i++) {
      Fret lastStringFret = null;
      List<Fret> tonesInString = instrument.getFretsByString().get(i);
      for (Fret fret : tonesInString) {
        if (fret.getFretNum() == 0) {
          continue;
        }
        if (scale.containsNote(fret.getNote().getPitch())) {
          scaleNote = scale.findScaleNote(fret.getNote()).get();
        } else {
          continue;
        }
        



        
        
        
//          out.println("Fret " + fret.getNote());
      }

    }

  }

  public void display(List<ScaleNote> scaleNotes, Options options) {
//    out.println(createFretboardSide(true, options));

//    int octave = 0;
//    boolean octaveToggle = false;
//    for (int i = instrument.getFretsByString().size() - 1; i >= 0; i--) {
//      List<Fret> tonesInString = instrument.getFretsByString().get(i);
//      StringBuilder stringBuilder = new StringBuilder();
//      for (Fret fret : tonesInString) {
//        Optional<ScaleNote> note = Optional.empty();
//        if (!(instrument.isBanjo() && (fret.getFretNum() > 0 && fret.getFretNum() < 6)
//            && fret.getStringNum() == 0)) {
//          note = scaleNotes.stream()
//              .filter(sn -> sn.getNote().getPitch() == fret.getNote().getPitch()).findFirst();
//        }
//        if (octave != fret.getOctave()) {
//          octave = fret.getOctave();
//          octaveToggle = !octaveToggle;
//        }
//        String stringChar = "┈";
//
//        if (note.isPresent()) {
//          String fretStr = null;
//          if (options.isIntervals()) {
//            fretStr =
//                note.get().getInterval().isPresent() ? note.get().getInterval().get().getLabel()
//                    : note.get().getNote().getLabel();
//          } else {
//            fretStr = note.get().getNote().getLabel();
//          }
//          if (options.isColour()) {
//            Colour col = options.isOctaves() ? ColourMap.get((Integer) fret.getOctave())
//                : ColourMap.get(note.get().getNote().getPitch());
//            if (fret.getFretNum() == 0) {
//              stringBuilder.append(col).append(String.format("%3s", fretStr)).append(Colour.RESET)
//                  .append("┃┃");
//            } else {
//              fretStr = String.format("%s╴%s%s%s╶%s┃", stringChar, col, fretStr, Colour.RESET,
//                  StringUtils.repeat(stringChar, 3 - fretStr.length()));
//              stringBuilder.append(fretStr);
//            }
//          } else {
//            if (fret.getFretNum() == 0) {
//              stringBuilder.append(String.format("%3s", fretStr)).append("┃┃");
//            } else {
//              fretStr =
//                  fretStr.length() == 2 ? fretStr : String.format("%s%s", fretStr, stringChar);
//              stringBuilder.append(StringUtils.repeat(stringChar, 2)).append(fretStr)
//                  .append(StringUtils.repeat(stringChar, 2)).append("┃");
//            }
//          }
//
//        } else {
//          if (fret.getFretNum() == 0) {
//            stringBuilder.append("   ").append("┃┃");
//          } else {
//            if (instrument.isBanjo() && (fret.getFretNum() > 0 && fret.getFretNum() < 6)
//                && fret.getStringNum() == 0) {
//              stringBuilder.append("      ┃");
//            } else {
//              stringBuilder.append(StringUtils.repeat(stringChar, 6)).append("┃");
//            }
//          }
//        }
//      }
//      out.println(stringBuilder.toString());
//    }
//
//    out.println(createFretboardSide(false, options));
//    out.println();
//    out.println();
  }

  private List<List<StringScaleNote>> createStringScaleNotes(List<ScaleNote> scaleNotes) {
    return null;
  }

//  private String createFretboardSide(boolean upper, Options options) {
//    String lowerTop = "╹┗";
//    String lowerBottom = "┛";
//    String upperTop = "╻┏";
//    String upperBottom = "┓";
//    String side = "━";
//    String inlay = ".";
//
//    StringBuilder sb = new StringBuilder("   ").append(upper ? upperTop : lowerTop);
//    for (int fret = 1; fret <= instrument.getFrets(); fret++) {
//      sb.append(side.repeat(2));
//      sb.append(options.isInlays() && inlays.contains(fret) ? inlay : side);
//      sb.append(side.repeat(fret == instrument.getFrets() ? 3 : 4));
//    }
//    sb.append(upper ? upperBottom : lowerBottom);
//
//    return sb.toString();
//  }

  @Data
  @AllArgsConstructor
  public class Options {
    private boolean intervals;
    private boolean inlays;
    private boolean colour;
    private boolean octaves;
  }

  @Data
  @AllArgsConstructor
  public class StringScaleNote {
    private ScaleNote scaleNote;
    private int stringPosition;
  }
}

