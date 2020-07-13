package me.flotsam.frettler.view.console;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.instrument.Guitar;
import me.flotsam.frettler.instrument.Tone;

public class ChordView {

  private Options defaultOptions = new Options(false);
  private static final List<Integer> inlays = Arrays.asList(1, 3, 5, 7, 9, 12);
  private Guitar guitar;

  public ChordView(Guitar guitar) {
    this.guitar = guitar;
  }


  public void showChord(Chord chord) {
    showChordOccurence(chord, defaultOptions);
  }

  public void showChord(Chord chord, Options options) {
    List<Tone> tones = new ArrayList<>();
    out.println(StringUtils.center(chord.getTitle(), 32));
    out.println();

    List<List<Tone>> strings = guitar.getStringTones();
    List<Note> chordNotes =
        chord.getChordNotes().stream().map(sn -> sn.getNote()).collect(Collectors.toList());

    Tone tonic = null;
    List<List<Tone>> stringCandidates = new ArrayList<>();
    for (Note chordNote : chordNotes) {
      int stringNum = 0;
      for (List<Tone> stringTones : strings) {
        List<Tone> stringsCandidates = null;
        if (stringNum >= stringCandidates.size()) {
          stringsCandidates = new ArrayList<>();
          stringCandidates.add(stringsCandidates);
        } else {
          stringsCandidates = stringCandidates.get(stringNum);
        }
        for (Tone fretTone : stringTones) {
          if (tonic != null && fretTone.getStringNum() < tonic.getStringNum()) {
            break;
          }
          if (fretTone.getFret() > 3) {
            break;
          }
          if (chordNote == fretTone.getNote()) {
            stringsCandidates.add(fretTone);
            if (tonic == null) {
              tonic = fretTone;
            }
            break;
          }
        }
        stringNum++;
      }
    }
    for (List<Tone> toneList : stringCandidates) {
      toneList.stream().sorted(Comparator.comparing(Tone::getFret));
    }

    int stringNum = 0;
    for (List<Tone> stringsCandidates : stringCandidates) {
      if (stringsCandidates.size() > 0) {
        tones.add(stringsCandidates.get(0));
      } else {
        tones.add(new Tone(-1, null, -1, stringNum, null, 0));
      }
      stringNum++;
    }
    showTones(tones, options);
  }

  public void showChordOccurence(Chord chord) {
    showChordOccurence(chord, defaultOptions);
  }

  public void showChordOccurence(Chord chord, Options options) {
    List<Tone> tones = new ArrayList<>();
    out.println(StringUtils.center(chord.getTitle(), 32));
    out.println();

    List<List<Tone>> fretboardTones = guitar.getFretTones();
    List<Note> chordNotes =
        chord.getChordNotes().stream().map(sn -> sn.getNote()).collect(Collectors.toList());

    for (List<Tone> fretTones : fretboardTones) {
      for (Tone fretTone : fretTones) {
        if (chordNotes.contains(fretTone.getNote())) {
          tones.add(fretTone);
        }
      }
    }
    showTones(tones, defaultOptions);
  }


  public void showTones(List<Tone> tones, Options options) {
    ColourMap colourMap = new ColourMap();
    List<List<Tone>> fretboardTones = guitar.getFretTones();
    List<Integer> deadStrings = new ArrayList<>();
    int lowestFret = tones.stream().max(Comparator.comparing(Tone::getFret)).get().getFret();
    for (Tone tone : tones) {
      if (tone.getNote() == null) {
        deadStrings.add(tone.getStringNum());
      }
    }

    int fretNum = 0;
    for (List<Tone> fretTones : fretboardTones) {
      String inlay = inlays.contains(fretNum) ? String.format(" %-2s ", fretNum) : "    ";
      out.print(inlay);
      String sep = (fretNum == 0) ? "" : "┃";
      int stringNum = 0;
      for (Tone fretTone : fretTones) {
        String ldr = (stringNum == 0 && fretNum != 0) ? "┃" : (fretNum == 0 ? " " : "");
        if (tones.contains(fretTone)) {
          if (options.isColour()) {
            Colour col = colourMap.get(fretTone.getNote());
            out.print(String.format("%s %s%-2s%s%s", ldr, col, fretTone.getNote().getLabel(),
                Colour.RESET, sep));

          } else {
            out.print(String.format("%s %-2s%s", ldr, fretTone.getNote().getLabel(), sep));
          }
        } else {
          String mark = " ";
          if (fretNum == 0 && deadStrings.contains(stringNum)) {
            mark = "X";
          }
          out.print(String.format("%s %s %s", ldr, mark, sep));
        }
        stringNum++;
      }
      out.println(inlay);
      if (fretNum == lowestFret +2) {
        break;
      }
      if (fretNum == 0) {
        out.println("    ┏━━━┳━━━┳━━━┳━━━┳━━━┳━━━┓");
      } else if (fretNum < 12) {
        out.println("    ┣━━━╋━━━╋━━━╋━━━╋━━━╋━━━┫");
      }
      fretNum++;
    }
    out.println("    ┗━━━┻━━━┻━━━┻━━━┻━━━┻━━━┛");
    out.println();
    out.println();
  }

  @Data
  @AllArgsConstructor
  public class Options {
    private boolean colour;
  }

}
