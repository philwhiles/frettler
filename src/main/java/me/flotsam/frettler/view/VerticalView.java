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
import java.util.Collections;
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
import me.flotsam.frettler.engine.Pitch;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleInterval;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.engine.Sequence;
import me.flotsam.frettler.instrument.Fret;
import me.flotsam.frettler.instrument.FrettedInstrument;

/**
 * Shows a chord fingering diagram, a chord arpeggio or a scale, using a vertical representation,
 * similar to standard guitar chord diagrams.
 * 
 * @author philwhiles
 *
 */
public class VerticalView implements View {

  private static final int TITLE_CENTER = 47;
  private static final List<Integer> inlays = Arrays.asList(1, 3, 5, 7, 9, 12, 15, 17, 19, 21, 23);
  private FrettedInstrument instrument;

  public VerticalView(FrettedInstrument instrument) {
    this.instrument = instrument;
  }

  public void showChord(ChordBankInstance chordBankInstance, Options options) {
    List<ChordFret> chordFrets = new ArrayList<>();
    Chord chord = chordBankInstance.getChord();
    initColourMap(chord);
    out.println();
    out.println(StringUtils.center(chord.getTitle(), TITLE_CENTER));
    printChordTitle(chord, options);
    out.println(StringUtils
        .center(instrument.getLabel() + (options.isLefty() ? " (Left) " : " (Right) ") + "["
            + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(","))
            + "]", TITLE_CENTER));
    out.println();

    List<List<Fret>> fretboardFrets = instrument.getFretsByFret(false);
    List<String> chordsStringDefs =
        new ArrayList<>(chordBankInstance.getChordDefinition().getStrings());
    List<ScaleNote> chordNotes = chord.getChordNotes();
    if (chord.getAddedNote() != null) {
      chordNotes.add(chord.getAddedScaleNote());
    }
    for (List<Fret> fretFrets : fretboardFrets) {
      int stringNum = 0;
      for (Fret fret : fretFrets) {
        if (fret.getNote() == null) {
          continue; // must be fret 1-5 of the 5th string on banjo
        }
        Optional<ScaleNote> chordScaleNoteForFret = chordNotes.stream()
            .filter(cn -> fret.getNote().getPitch().equals(cn.getNote().getPitch())).findAny();
        if (chordScaleNoteForFret.isPresent()) {
          String stringDef = chordsStringDefs.get(stringNum);

          if (!stringDef.equalsIgnoreCase("x")) {
            int stringDefVal = Integer.valueOf(stringDef);
            if (fret.getFretNum() == stringDefVal) {
              Fret altFret = new Fret(fret.getIndex(), chordScaleNoteForFret.get().getNote(),
                  fret.getOctave(), fret.getStringNum(), fret.getStringNote(), fret.getFretNum());
              chordFrets
                  .add(new ChordFret(altFret, chordScaleNoteForFret.get().getInterval().get()));
            }
          }
        }
        stringNum++;
      }
    }
    for (int sn = 0; sn < chordsStringDefs.size(); sn++) {
      if (chordsStringDefs.get(sn).equalsIgnoreCase("x")) {
        chordFrets.add(new ChordFret(new Fret(-1, null, -1, sn, null, 0), ScaleInterval.P1));
      }
    }
    chordFrets = chordFrets.stream()
        .sorted((a, b) -> a.getFret().getStringNum() - b.getFret().getStringNum())
        .collect(Collectors.toList());

    display(chord.getChordRoot().getPitch(), chordFrets, options, ViewMode.CHORD);
  }

  public void showArpeggio(Chord chord, Options options) {
    List<ChordFret> chordFrets = new ArrayList<>();

    initColourMap(chord);
    out.println();
    out.println(StringUtils.center(chord.getTitle(), TITLE_CENTER));
    printChordTitle(chord, options);
    out.println(StringUtils
        .center(instrument.getLabel() + (options.isLefty() ? " (Left) " : " (Right) ") + "["
            + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(","))
            + "]", TITLE_CENTER));
    out.println();

    List<List<Fret>> fretboardFrets = instrument.getFretsByFret(options.isLefty());

    List<ScaleNote> chordNotes = chord.getChordNotes();
    if (chord.getAddedNote() != null) {
      chordNotes.add(chord.getAddedScaleNote());
    }

    for (List<Fret> fretFrets : fretboardFrets) {
      for (Fret fret : fretFrets) {
        if (fret.getNote() == null) {
          continue; // must be fret 1-5 of the 5th string on banjo
        }
        Optional<ScaleNote> chordScaleNoteForFret = chordNotes.stream()
            .filter(cn -> fret.getNote().getPitch().equals(cn.getNote().getPitch())).findAny();
        if (chordScaleNoteForFret.isPresent()) {
          Fret altFret = new Fret(fret.getIndex(), chordScaleNoteForFret.get().getNote(),
              fret.getOctave(), fret.getStringNum(), fret.getStringNote(), fret.getFretNum());
          chordFrets.add(new ChordFret(altFret, chordScaleNoteForFret.get().getInterval().get()));
        }
      }
    }
    chordFrets = chordFrets.stream()
        .sorted((a, b) -> a.getFret().getStringNum() - b.getFret().getStringNum())
        .collect(Collectors.toList());
    display(chord.getChordRoot().getPitch(), chordFrets, options, ViewMode.VERTICAL);
  }

  public void showScaleBox(Scale scale, Sequence sequence, Options options) {
    showScale(scale, sequence, options, ViewMode.BOX);
  }

  public void showScale(Scale scale, Sequence sequence, Options options) {
    showScale(scale, sequence, options, ViewMode.VERTICAL);
  }

  private void showScale(Scale scale, Sequence sequence, Options options, ViewMode viewMode) {
    List<ChordFret> chordFrets = new ArrayList<>();
    initColourMap(scale);

    out.println();
    out.println(StringUtils.center(scale.getTitle(), TITLE_CENTER));
    out.println(StringUtils
        .center(instrument.getLabel() + (options.isLefty() ? " (Left) " : " (Right) ") + "["
            + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(","))
            + "]", TITLE_CENTER));

    printScaleTitle(scale, options);

    List<List<SequenceFretNote>> fretSequence = null;
    if (sequence != Sequence.NONE) {
      instrument = FrettedInstrument.getBiggerInstrument(instrument);
      fretSequence = prepareSequence(scale, sequence, instrument, false, options.isOpen(),
          options.getPosition(), options.getGroup());
      out.println(StringUtils.center(sequence.getLabel(), TITLE_CENTER));
      out.println(StringUtils.center(
          "Position: " + options.getPosition() + " Group: " + options.getGroup(), TITLE_CENTER));
    }
    List<List<Fret>> fretboardFrets = instrument.getFretsByFret(options.isLefty());

    for (List<Fret> fretFrets : fretboardFrets) {
      for (Fret fret : fretFrets) {
        if (fret.getNote() == null) {
          continue; // must be fret 1-5 of the 5th string on banjo
        }
        Optional<ScaleNote> scaleNoteForFret = scale.getScaleNotes().stream()
            .filter(sn -> fret.getNote().getPitch().equals(sn.getNote().getPitch())).findAny();
        if (scaleNoteForFret.isPresent()) {
          if (fretSequence != null && !fretSequence.get(fret.getStringNum()).stream()
              .anyMatch(sfn -> sfn.getFret().equals(fret))) {
            continue;
          }

          Fret altFret = new Fret(fret.getIndex(), scaleNoteForFret.get().getNote(),
              fret.getOctave(), fret.getStringNum(), fret.getStringNote(), fret.getFretNum());
          chordFrets.add(new ChordFret(altFret, scaleNoteForFret.get().getInterval().get()));
        }
      }
    }
    chordFrets = chordFrets.stream()
        .sorted((a, b) -> a.getFret().getStringNum() - b.getFret().getStringNum())
        .collect(Collectors.toList());

    display(scale.getRootNote().getPitch(), chordFrets, options, viewMode);
  }

  public void showDisplay(List<ScaleNote> scaleNotes, Options options) {
    List<ChordFret> chordFrets = new ArrayList<>();
    List<List<Fret>> fretboardFrets = instrument.getFretsByFret(options.isLefty());

    for (List<Fret> fretFrets : fretboardFrets) {
      for (Fret fret : fretFrets) {
        if (fret.getNote() == null) {
          continue; // must be fret 1-5 of the 5th string on banjo
        }
        Optional<ScaleNote> scaleNoteForFret = scaleNotes.stream()
            .filter(sn -> fret.getNote().getPitch().equals(sn.getNote().getPitch())).findAny();
        if (scaleNoteForFret.isPresent()) {

          Fret altFret = new Fret(fret.getIndex(), scaleNoteForFret.get().getNote(),
              fret.getOctave(), fret.getStringNum(), fret.getStringNote(), fret.getFretNum());
          chordFrets.add(new ChordFret(altFret, scaleNoteForFret.get().getInterval().get()));
        }
      }
    }
    chordFrets = chordFrets.stream()
        .sorted((a, b) -> a.getFret().getStringNum() - b.getFret().getStringNum())
        .collect(Collectors.toList());
    display(Pitch.C, chordFrets, options, ViewMode.VERTICAL);
  }

  private void display(Pitch root, List<ChordFret> chordFrets, Options options, ViewMode viewMode) {
    List<List<Fret>> fretboardFrets = instrument.getFretsByFret(options.isLefty());
    List<Integer> deadStrings = calculateDeadStrings(chordFrets, options);
    Note noteSummary[] = new Note[instrument.getStringCount()];
    ScaleInterval intervalSummary[] = new ScaleInterval[instrument.getStringCount()];
    Integer octaveSummary[] = new Integer[instrument.getStringCount()];

    int lowestFret = calculateLowestFret(chordFrets);
    int highestFret = calculateHighestFret(chordFrets);

    // if chord view calculate the barring
    if (viewMode == ViewMode.CHORD) {
      if (options.isLefty()) {
        calculateBarringLeft(chordFrets, highestFret);
      } else {
        calculateBarringRight(chordFrets, highestFret);
      }
    }

    int fretNum = 0;
    int fretsPrinted = 0;
    for (List<Fret> frets : fretboardFrets) {
      if (fretNum > 0 && fretNum < highestFret - 1) {
        fretNum++;
        continue;
      }
      boolean barring = false;
      fretsPrinted++;
      out.print(inlays.contains(fretNum) ? String.format("         %2s ", fretNum) : "            ");
      int stringNum = 0;
      for (Fret fret : frets) {
        boolean lastString = stringNum == instrument.getStringCount() - 1;
        String ldr = fretNum == 0 ? " " : "┆";

        if (deadStrings.contains(stringNum)) {
          if (fretNum == 0) {
            out.print(String.format("%s%s%s", ldr, "x ", ldr));
          } else {
            out.print(String.format(" %s%s", ldr, lastString ? " " : "  "));
          }
        } else {
          Optional<ChordFret> chordFret =
              chordFrets.stream().filter(ct -> fret.getStringNum() == ct.getFret().getStringNum()
                  && fret.getFretNum() == ct.getFret().getFretNum()).findAny();

          if (chordFret.isPresent()) {
            ChordFret theChordFret = chordFret.get();
            String fretLabel = null;
            if (viewMode == ViewMode.CHORD) {
              fretLabel = fretNum == 0 ? "○" : "●";
              intervalSummary[stringNum] = theChordFret.getInterval();
              Note chordFretNote = theChordFret.getFret().getNote();
              noteSummary[stringNum] = chordFretNote;
              octaveSummary[stringNum] = fret.getOctave();
            } else {
              if (options.isIntervals()) {
                fretLabel = theChordFret.getInterval().getLabel();
              } else {
                Note chordFretNote = theChordFret.getFret().getNote();
                fretLabel = chordFretNote.getLabel();
              }
            }
            barring = theChordFret.isBarred();
            String fretStr =
                StringUtils.center(fretLabel, lastString ? 3 : 4, (barring ? '━' : ' '));
            if (options.isLefty()) {
              if (theChordFret.isStartsBarre()) {
                if (lastString) {
                  fretStr = fretStr.replaceFirst("━$", " ");
                } else {
                  fretStr = fretStr.replaceFirst("━━$", "  ");
                }
              } else if (stringNum == 0) {
                fretStr = fretStr.replaceFirst("^━", " ");
              }
            } else {
              if (theChordFret.isStartsBarre()) {
                fretStr = fretStr.replaceFirst("^━", " ");
              } else if (lastString) {
                fretStr = fretStr.replaceFirst("━$", " ");
              }
            }
            if (options.isColour()) {
              Colour col = options.isOctaves() ? ColourMap.get((Integer) fret.getOctave())
                  : ColourMap.get(fret.getNote().getPitch());
              Colour special =
                  (viewMode == ViewMode.BOX && fret.getNote().getPitch() == root) ? Colour.INVERSE
                      : Colour.NORMAL;

              StringBuilder colourFretStr = new StringBuilder();
              boolean after = false;
              for (int i = 0; i < fretStr.length(); i++) {
                String theChar = fretStr.substring(i, i + 1);
                if (!after) {
                  if (!theChar.equals(" ") && !theChar.equals("━")) {
                    colourFretStr.append(special);
                    colourFretStr.append(col);
                    after = true;
                  }
                } else {
                  if (theChar.equals(" ") || theChar.equals("━")) {
                    colourFretStr.append(Colour.RESET);
                  }
                }
                colourFretStr.append(theChar);
              }
              out.print(colourFretStr.toString());
            } else {
              out.print(String.format("%s", fretStr));
            }
            if (options.isLefty() && theChordFret.isStartsBarre()) {
              barring = false;
            }
          } else { // chordFret.isPresent()
            char c = (barring ? '━' : ' ');
            ldr = barring ? "┿" : ldr;
            out.print(String.format("%s%s%s", c, ldr, StringUtils.repeat(c, lastString ? 1 : 2)));
          }
        }
        stringNum++;
      }
      out.println(inlays.contains(fretNum) ? String.format(" %-2s", fretNum) : "");
      if (fretsPrinted >= 6 && fretNum >= lowestFret + 1) {
        break;
      }
      if (fretNum == 0) {
        out.println(createFretLine("┌", "┬", "┐"));
      } else if (fretNum < instrument.getFrets()) {
        out.println(createFretLine("├", "┼", "┤"));
      }
      fretNum++;
    }
    out.println(createFretLine("└", "┴", "┘"));

    // print the chord summary
    if (viewMode == ViewMode.CHORD) {
      printChordFooter(noteSummary, intervalSummary, octaveSummary, options);
    }

    out.println();
    out.println();
  }

  private void printScaleTitle(Scale scale, Options options) {
    String monoScaleDetails = getScaleDetails(scale, false);
    String colourScaleDetails = getScaleDetails(scale, true);
    int pad = (TITLE_CENTER - monoScaleDetails.length()) / 2;
    out.print(StringUtils.repeat(' ', pad));
    out.println(options.isColour() ? colourScaleDetails : monoScaleDetails);
    out.println();
  }

  private void printChordTitle(Chord chord, Options options) {
    String monoChordDetails = getChordDetails(chord, false);
    String colourChordDetails = getChordDetails(chord, true);
    int pad = (TITLE_CENTER - monoChordDetails.length()) / 2;
    out.print(StringUtils.repeat(' ', pad));
    out.println(options.isColour() ? colourChordDetails : monoChordDetails);
  }
  private void printChordFooter(Note[] noteSummary, ScaleInterval[] intervalSummary, Integer[] octaveSummary, Options options) {
    StringBuilder noteBuilder = new StringBuilder("            ");
    StringBuilder intervalBuilder = new StringBuilder("            ");
    for (int n = 0; n < instrument.getStringCount(); n++) {
      Note note = noteSummary[n];
      if (note != null) {
        if (options.isColour()) {
          Colour col = options.isOctaves() ? ColourMap.get((Integer) octaveSummary[n])
              : ColourMap.get(note.getPitch());
          noteBuilder.append(String.format("%s%s%s", col,
              StringUtils.center(note.getLabel(), 4, ' '), Colour.RESET));
          intervalBuilder.append(String.format("%s%s%s", col,
              StringUtils.center(intervalSummary[n].getLabel(), 4, ' '), Colour.RESET));
        } else {
          noteBuilder.append(String.format("%s", StringUtils.center(note.getLabel(), 4, ' ')));
          intervalBuilder.append(
              String.format("%s", StringUtils.center(intervalSummary[n].getLabel(), 4, ' ')));
        }
      } else {
        noteBuilder.append("    ");
        intervalBuilder.append("    ");
      }
    }
    out.println(noteBuilder.toString());
    out.println(intervalBuilder.toString());
    out.print("   ");
  }

  private int calculateLowestFret(List<ChordFret> chordFrets) {
    int lowestFret = chordFrets.stream()
        .max(Comparator.comparingInt(ct -> Integer.valueOf(ct.getFret().getFretNum()))).get()
        .getFret().getFretNum();
    return lowestFret;
  }

  private int calculateHighestFret(List<ChordFret> chordFrets) {
    int highestFret = 0;
    Optional<ChordFret> highestFretOpt =
        chordFrets.stream().filter(cf -> cf.getFret().getFretNum() > 0)
            .min(Comparator.comparingInt(ct -> Integer.valueOf(ct.getFret().getFretNum())));
    if (!highestFretOpt.isEmpty()) { // else a chord that is just a combo of open or muted strings
      highestFret = highestFretOpt.get().getFret().getFretNum();
    }
    return highestFret;
  }

  private List<Integer> calculateDeadStrings(List<ChordFret> chordFrets, Options options) {
    List<Integer> deadStrings = new ArrayList<>();
    for (ChordFret chordFret : chordFrets) {
      if (chordFret.getFret().getNote() == null) {
        int deadString =
            options.isLefty() ? instrument.getStringCount() - 1 - chordFret.getFret().getStringNum()
                : chordFret.getFret().getStringNum();
        deadStrings.add(deadString);
      }
    }
    return deadStrings;
  }

  private void calculateBarringRight(List<ChordFret> chordFrets, int highestFret) {
    // the chordfrets are in string order 0 being low E
    // work thru strings to the right but not the last one
    for (int cfn = 0; cfn < chordFrets.size() - 1; cfn++) {
      ChordFret chordFret = chordFrets.get(cfn);
      int fretNum = chordFret.getFret().getFretNum();

      // an 'x' or 'o' string
      if (chordFret.getFret().getNote() == null || fretNum == 0) {
        continue;
      }

      // assume barred
      boolean barred = true;
      boolean paired = false;
      if (fretNum == highestFret) {
        for (int cfx = cfn + 1; cfx < chordFrets.size(); cfx++) {
          ChordFret toTheRight = chordFrets.get(cfx);
          if (toTheRight.getFret().getFretNum() == 0 || toTheRight.getFret().getNote() == null) {
            barred = false;
            break;
          }
          if (toTheRight.getFret().getFretNum() == fretNum) {
            paired = true;
          }
        }
        if (barred && paired) {
          for (int cfx = cfn; cfx < chordFrets.size(); cfx++) {
            ChordFret cf = chordFrets.get(cfx);
            if (cf.getFret().getFretNum() == fretNum) {
              cf.setBarred(true);
              if (cfx == cfn) {
                cf.setStartsBarre(true);
              }
            }
          }
          break;
        }
      }
    }
  }

  private void calculateBarringLeft(List<ChordFret> chordFrets, int highestFret) {
    Collections.reverse(chordFrets);
    // the chordfrets are in string order 0 being low E
    // work thru strings to the right but not the last one
    for (int cfn = chordFrets.size() - 1; cfn > 0; cfn--) {
      ChordFret chordFret = chordFrets.get(cfn);
      int fretNum = chordFret.getFret().getFretNum();

      // an 'x' or 'o' string
      if (chordFret.getFret().getNote() == null || fretNum == 0) {
        continue;
      }

      // assume barred
      boolean barred = true;
      boolean paired = false;
      if (fretNum == highestFret) {
        for (int cfx = cfn - 1; cfx >= 0; cfx--) {
          ChordFret toTheRight = chordFrets.get(cfx);
          if (toTheRight.getFret().getFretNum() == 0 || toTheRight.getFret().getNote() == null) {
            barred = false;
            break;
          }
          if (toTheRight.getFret().getFretNum() == fretNum) {
            paired = true;
          }
        }
        if (barred && paired) {
          for (int cfx = cfn; cfx >= 0; cfx--) {
            ChordFret cf = chordFrets.get(cfx);
            if (cf.getFret().getFretNum() == fretNum) {
              cf.setBarred(true);
              if (cfx == cfn) {
                cf.setStartsBarre(true);
              }
            }
          }
          break;
        }
      }
    }
    Collections.reverse(chordFrets);
  }

  private String createFretLine(String begin, String mid, String end) {
    int strings = instrument.getStringCount();
    StringBuilder sb = new StringBuilder("             ");
    sb.append(begin);
    for (int n = 0; n < strings - 1; n++) {
      sb.append("───");
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
    private boolean intervals;
    private boolean colour;
    private boolean octaves;
    private boolean open;
    private int position;
    private int group;
    private boolean lefty;
  }


  @Data
  @RequiredArgsConstructor
  class ChordFret {
    @NonNull
    private Fret fret;
    @NonNull
    private ScaleInterval interval;
    private boolean barred;
    private boolean startsBarre;
  }

  private enum ViewMode {
    VERTICAL, CHORD, BOX;
  }

}
