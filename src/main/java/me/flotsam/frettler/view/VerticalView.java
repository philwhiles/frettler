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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.ChordBankInstance;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleInterval;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.instrument.Fret;
import me.flotsam.frettler.instrument.FrettedInstrument;

/**
 * Shows a chord fingering diagram, a chord arpeggio or a scale, using a vertical representation,
 * similar to standard guitar chord diagrams.
 * 
 * @author philwhiles
 *
 */
public class VerticalView {

  private Options defaultOptions = new Options(false, true, false);
  private static final List<Integer> inlays = Arrays.asList(1, 3, 5, 7, 9, 12, 15, 17, 19, 21, 23);
  private FrettedInstrument instrument;

  public VerticalView(FrettedInstrument instrument) {
    this.instrument = instrument;
  }

  public void showChord(ChordBankInstance chordBankInstance, Options options) {
    List<ChordFret> chordFrets = new ArrayList<>();
    Chord chord = chordBankInstance.getChord();

    out.println();
    out.println(StringUtils.center(chord.getTitle(), 30));
    out.println(StringUtils.center("(" + instrument.getInstrumentType().getLabel() + " ["
        + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(","))
        + "])", 30));
    out.println();

    List<List<Fret>> fretboardFrets = instrument.getFretsByFret();

    for (List<Fret> fretFrets : fretboardFrets) {
      int stringNum = 0;
      for (Fret fret : fretFrets) {
        if (fret.getNote() == null) {
          continue; // must be fret 1-5 of the 5th string on banjo
        }
        Optional<ScaleNote> chordScaleNoteForFret = chord.getChordNotes().stream()
            .filter(cn -> fret.getNote().getPitch().equals(cn.getNote().getPitch())).findAny();
        if (chordScaleNoteForFret.isPresent()) {
          String stringDef = chordBankInstance.getChordDefinition().getStrings().get(stringNum);
          if (!stringDef.equalsIgnoreCase("x")) {
            int stringDefVal = Integer.valueOf(stringDef);
            if (fret.getFretNum() == stringDefVal) {
              Fret altFret = new Fret(fret.getIndex(), chordScaleNoteForFret.get().getNote(),
                  fret.getOctave(), fret.getStringNum(), fret.getStringNote(), fret.getFretNum());
              chordFrets
                  .add(new ChordFret(altFret, chordScaleNoteForFret.get().getInterval().get()));
            }
          } else {
            chordFrets
                .add(new ChordFret(new Fret(-1, null, -1, stringNum, null, 0), ScaleInterval.P1));
          }
        }
        stringNum++;
      }
    }
    display(chordFrets, options);
  }

  public void showArpeggio(Chord chord) {
    showArpeggio(chord, defaultOptions);
  }

  public void showArpeggio(Chord chord, Options options) {
    List<ChordFret> chordFrets = new ArrayList<>();

    out.println();
    out.println(StringUtils.center(chord.getTitle(), 30));
    out.println(StringUtils.center("(" + instrument.getInstrumentType().getLabel() + " ["
        + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(","))
        + "])", 30));
    out.println();

    List<List<Fret>> fretboardFrets = instrument.getFretsByFret();

    for (List<Fret> fretFrets : fretboardFrets) {
      for (Fret fret : fretFrets) {
        if (fret.getNote() == null) {
          continue; // must be fret 1-5 of the 5th string on banjo
        }
        Optional<ScaleNote> chordScaleNoteForFret = chord.getChordNotes().stream()
            .filter(cn -> fret.getNote().getPitch().equals(cn.getNote().getPitch())).findAny();
        if (chordScaleNoteForFret.isPresent()) {
          Fret altFret = new Fret(fret.getIndex(), chordScaleNoteForFret.get().getNote(),
              fret.getOctave(), fret.getStringNum(), fret.getStringNote(), fret.getFretNum());
          chordFrets.add(new ChordFret(altFret, chordScaleNoteForFret.get().getInterval().get()));
        }
      }
    }
    display(chordFrets, options);
  }

  public void showScale(Scale scale) {
    showScale(scale, defaultOptions);
  }

  public void showScale(Scale scale, Options options) {
    List<ChordFret> chordFrets = new ArrayList<>();

    out.println();
    out.println(StringUtils.center(scale.getTitle(), 30));
    out.println(StringUtils.center("(" + instrument.getInstrumentType().getLabel() + " ["
        + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(","))
        + "])", 30));
    out.println();

    List<List<Fret>> fretboardFrets = instrument.getFretsByFret();

    for (List<Fret> fretFrets : fretboardFrets) {
      for (Fret fret : fretFrets) {
        if (fret.getNote() == null) {
          continue; // must be fret 1-5 of the 5th string on banjo
        }
        Optional<ScaleNote> scaleNoteForFret = scale.getScaleNotes().stream()
            .filter(sn -> fret.getNote().getPitch().equals(sn.getNote().getPitch())).findAny();
        if (scaleNoteForFret.isPresent()) {
          Fret altFret = new Fret(fret.getIndex(), scaleNoteForFret.get().getNote(),
              fret.getOctave(), fret.getStringNum(), fret.getStringNote(), fret.getFretNum());
          chordFrets.add(new ChordFret(altFret, scaleNoteForFret.get().getInterval().get()));
        }
      }
    }
    display(chordFrets, options);
  }

  private void display(List<ChordFret> chordFrets, Options options) {
    List<List<Fret>> fretboardFrets = instrument.getFretsByFret();
    // deadString concept left over from display of chord fingerings which may come back
    List<Integer> deadStrings = new ArrayList<>();
    for (ChordFret chordFret : chordFrets) {
      if (chordFret.getFret().getNote() == null) {
        deadStrings.add(chordFret.getFret().getStringNum());
      }
    }

    int lowestFret = chordFrets.stream()
        .max(Comparator.comparingInt(ct -> Integer.valueOf(ct.getFret().getFretNum()))).get()
        .getFret().getFretNum();

    int fretNum = 0;
    for (List<Fret> frets : fretboardFrets) {
      String inlay = inlays.contains(fretNum) ? String.format(" %-2s", fretNum) : "   ";
      out.print(inlay);
      int stringNum = 0;
      for (Fret fret : frets) {
        boolean lastString = stringNum == instrument.getStringCount() - 1;
        String ldr = fretNum == 0 ? " " : "┋";

        if (deadStrings.contains(stringNum)) {
          if (fretNum == 0) {
            out.print(String.format("%s%s%s", ldr, "X ", ldr));
          } else {
            out.print(String.format(" %s%s", ldr, "  "));
          }
        } else {
          Optional<ChordFret> chordFret =
              chordFrets.stream().filter(ct -> fret.getStringNum() == ct.getFret().getStringNum()
                  && fret.getFretNum() == ct.getFret().getFretNum()).findAny();

          if (chordFret.isPresent()) {
            String fretStr = null;
            if (options.isIntervals()) {
              fretStr = chordFret.get().getInterval().getLabel();
            } else {
              Note chordFretNote = chordFret.get().getFret().getNote();
              fretStr = chordFretNote.getLabel();
            }
            if (options.isColour()) {
              Colour col = options.isOctaves() ? ColourMap.get((Integer) fret.getOctave())
                  : ColourMap.get(fret.getNote().getPitch());
              out.print(String.format("%s%s%s", col, StringUtils.center(fretStr, lastString ? 3 : 4, ' '),
                  Colour.RESET));
            } else {
              out.print(String.format("%s", StringUtils.center(fretStr,  lastString ? 3 : 4, ' ')));
            }
          } else {
            out.print(String.format(" %s%s", ldr, StringUtils.repeat(' ', lastString ? 1 : 2)));
          }
        }
        stringNum++;
      }
      out.println(String.format("%s", inlay));
      if (fretNum >= 5 && fretNum >= lowestFret + 1) {
        break;
      }
      if (fretNum == 0) {
        out.println(createFretLine("┏", "┳", "┓"));
      } else if (fretNum < instrument.getFrets()) {
        out.println(createFretLine("┣", "╋", "┫"));
      }
      fretNum++;
    }
    out.println(createFretLine("┗", "┻", "┛"));
    out.println();
    out.println();
  }

  private String createFretLine(String begin, String mid, String end) {
    int strings = instrument.getStringCount();
    StringBuilder sb = new StringBuilder("    ");
    sb.append(begin);
    for (int n = 0; n < strings - 1; n++) {
      sb.append("━━━");
      if (n < strings - 2) {
        sb.append(mid);
      } else {
        sb.append(end);
      }
    }
    return sb.toString();
  }

  @Data
  @AllArgsConstructor
  public class Options {
    private final boolean intervals;
    private final boolean colour;
    private final boolean octaves;
  }


  @Data
  @RequiredArgsConstructor
  class ChordFret {

    @NonNull
    private final Fret fret;

    @NonNull
    private final ScaleInterval interval;

    private int weighting;
  }

}
