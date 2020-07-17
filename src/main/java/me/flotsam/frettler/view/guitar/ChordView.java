package me.flotsam.frettler.view.guitar;

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
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.ScaleInterval;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.instrument.guitar.Guitar;
import me.flotsam.frettler.instrument.guitar.Fret;

public class ChordView {

  private Options defaultOptions = new Options(false, true);
  private static final List<Integer> inlays = Arrays.asList(1, 3, 5, 7, 9, 12);
  private Guitar guitar;

  public ChordView(Guitar guitar) {
    this.guitar = guitar;
  }


  public void showChord(Chord chord) {
    showChordOccurence(chord, defaultOptions);
  }

  public void showChord(Chord chord, Options options) {
    List<ChordTone> tones = new ArrayList<>();

    out.println();
    out.println(StringUtils.center(chord.getTitle(), 32));
    out.println();

    List<List<Fret>> strings = guitar.getStringFrets();

    Fret tonic = null;
    List<List<ChordTone>> stringCandidates = new ArrayList<>();
    // work thru the notes in the chord
    for (ScaleNote chordScaleNote : chord.getChordNotes()) {
      int stringNum = 0;
      // go through all strings looking for the chord note candidate in each
      for (List<Fret> stringTones : strings) {
        List<ChordTone> stringsCandidates = null;
        // first chord note thru we won;t have created the strings candidates list
        if (stringNum >= stringCandidates.size()) {
          stringsCandidates = new ArrayList<>();
          stringCandidates.add(stringsCandidates);
        } else {
          stringsCandidates = stringCandidates.get(stringNum);
        }
        // go down the string looking for chord note candidates
        for (Fret fretTone : stringTones) {
          // once the first note in the chord has been done, we don't want subsequent chord note
          // candidates on strings lower than the tonic string
          if (tonic != null && fretTone.getStringNum() < tonic.getStringNum()) {
            break;
          }
          // only creating fingerings a human can play in first 4 frets!
          if (fretTone.getFret() > 4) {
            break;
          }
          // found a candidate 
          if (chordScaleNote.getNote() == fretTone.getNote()) {
            stringsCandidates.add(new ChordTone(fretTone, chordScaleNote.getInterval().get()));
            // tonic by default the first note in chord
            if (tonic == null) {
              tonic = fretTone;
            }
            break;
          }
        }
        stringNum++;
      }
    }
    
    // sort the candidates by fret num - they were recorded in chord order ie C first, then G...
    for (List<ChordTone> toneList : stringCandidates) {
      toneList.sort(Comparator.comparing(ct -> ct.getTone().getFret()));
    }

    // for each string
    for (int stringNum = 0; stringNum < stringCandidates.size(); stringNum++) {
      List<ChordTone> stringsCandidates = stringCandidates.get(stringNum);
      // find the chosen candidate from the last string at the end of the tone list
      ChordTone prevTone = null;
      if (tones.size() > 0) {
        prevTone = tones.get(tones.size() - 1);
      }
      ChordTone tone = null;
      // now go thru this strings candidates
      for (ChordTone thisChordTone : stringsCandidates) {
        if (prevTone != null) {
          // was the prev string chosen tone identical to this one? don't want repeats
          // if same note and occtave move on to next string candidate
          if (!prevTone.getTone().equalsTone(thisChordTone.getTone())) {
            tone = thisChordTone;
            break;
          }
        } else {
          // was no prev string tone chosen either because we are on string 0
          // or none suitable (too low down on fretboard)  on prev string
          tone = thisChordTone;
          break;
        }
      }
      // after process of elimination did we find a suitable tone?
      if (tone != null) {
        tones.add(tone);
      } else {
        tones.add(new ChordTone(new Fret(-1, null, -1, stringNum, null, 0), null));
      }
    }
    showTones(tones, options);
  }

  public void showChordOccurence(Chord chord) {
    showChordOccurence(chord, defaultOptions);
  }

  public void showChordOccurence(Chord chord, Options options) {
    List<ChordTone> tones = new ArrayList<>();

    out.println();
    out.println(StringUtils.center(chord.getTitle(), 32));
    out.println();

    List<List<Fret>> fretboardTones = guitar.getFretFrets();

    for (List<Fret> fretTones : fretboardTones) {
      for (Fret fretTone : fretTones) {
        Optional<ScaleNote> chordScaleNoteForTone = chord.getChordNotes().stream().filter(cn -> fretTone.getNote().equals(cn.getNote()))
            .findAny();
        if (chordScaleNoteForTone.isPresent()) {
          tones.add(new ChordTone(fretTone, chordScaleNoteForTone.get().getInterval().get()));
        }
      }
    }
    showTones(tones, defaultOptions);
  }


  private void showTones(List<ChordTone> chordTones, Options options) {
    ColourMap colourMap = new ColourMap();
    List<List<Fret>> fretboardTones = guitar.getFretFrets();
    List<Integer> deadStrings = new ArrayList<>();
    int lowestFret = chordTones.stream().max(Comparator.comparingInt(ct -> 
    Integer.valueOf(ct.getTone().getFret()))).get().getTone().getFret();
        
    for (ChordTone chordTone : chordTones) {
      if (chordTone.getTone().getNote() == null) {
        deadStrings.add(chordTone.getTone().getStringNum());
      }
    }

    int fretNum = 0;
    for (List<Fret> fretTones : fretboardTones) {
      String inlay = inlays.contains(fretNum) ? String.format(" %-2s ", fretNum) : "    ";
      out.print(inlay);
      String sep = (fretNum == 0) ? "" : "┃";
      int stringNum = 0;
      for (Fret fretTone : fretTones) {
        String ldr = (stringNum == 0 && fretNum != 0) ? "┃" : (fretNum == 0 ? " " : "");

        Optional<ChordTone> chordTone = chordTones.stream().filter(ct -> fretTone == ct.getTone())
            .findAny();
        
        if (chordTone.isPresent()) {
          String fretStr = null;
          if (options.isIntervals()) {
            fretStr = chordTone.get().getInterval().getLabel();
          } else {
            fretStr = chordTone.get().getTone().getNote().getLabel();
          }
          if (options.isColour()) {
            Colour col = colourMap.get(fretTone.getNote());
            out.print(String.format("%s %s%-2s%s%s", ldr, col, fretStr,
                Colour.RESET, sep));

          } else {
            out.print(String.format("%s %-2s%s", ldr, fretStr, sep));
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
      if (fretNum >= 5 && fretNum >= lowestFret + 1 ) {
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
    private final boolean intervals;
    private final boolean colour;
  }
  
  
  @Data
  @AllArgsConstructor
  class ChordTone {
    private final Fret tone;
    private final ScaleInterval interval;
  }

}
