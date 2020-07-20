package me.flotsam.frettler.view;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.ScaleInterval;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.instrument.Fret;
import me.flotsam.frettler.instrument.FrettedInstrument;

public class ChordView {

  private Options defaultOptions = new Options(false, true);
  private static final List<Integer> inlays = Arrays.asList(1, 3, 5, 7, 9, 12);
  private FrettedInstrument instrument;

  public ChordView(FrettedInstrument instrument) {
    this.instrument = instrument;
  }


  public void showChord(Chord chord) {
    showChordOccurence(chord, defaultOptions);
  }

  public void showChord(Chord chord, Options options) {
    List<ChordFret> tones = new ArrayList<>();

    out.println();
    out.println(StringUtils.center(chord.getTitle(), 32));
    out.println();

    List<List<Fret>> strings = instrument.getFretsByString();

    Fret tonic = null;
    List<List<ChordFret>> stringCandidates = new ArrayList<>();

    // work thru the notes in the chord and find candidates on each string
    for (ScaleNote chordScaleNote : chord.getChordNotes()) {
      int stringNum = 0;
      // go through all strings looking for the chord note candidate in each
      for (List<Fret> stringFrets : strings) {
        List<ChordFret> stringsCandidates = null;
        // first chord note thru we won;t have created the strings candidates list
        if (stringNum >= stringCandidates.size()) {
          stringsCandidates = new ArrayList<>();
          stringCandidates.add(stringsCandidates);
        } else {
          stringsCandidates = stringCandidates.get(stringNum);
        }
        // go down the string looking for chord note candidates
        for (Fret fret : stringFrets) {
          // once the first note in the chord has been done, we don't want subsequent chord note
          // candidates on strings lower than the tonic string
          if (tonic != null && fret.getStringNum() < tonic.getStringNum()) {
            break;
          }
          // only creating fingerings a human can play in first 4 frets!
          if (fret.getFretNum() > 4) {
            break;
          }
          // found a candidate 
          if (chordScaleNote.getNote() == fret.getNote()) {
            stringsCandidates.add(new ChordFret(fret, chordScaleNote.getInterval().get()));
            // tonic by default the first note in chord
            if (tonic == null) {
              tonic = fret;
            }
            break;
          }
        }
        stringNum++;
      }
    }
    
    
    // sort the candidates by fret num - they were recorded in chord order ie C first, then G...
    for (List<ChordFret> toneList : stringCandidates) {
      toneList.sort(Comparator.comparing(cf -> cf.getFret().getFretNum()));
    }

    // now we have the fret sorted string candidates
    
    
//    List<Integer> stringCandidateCounts = stringCandidates.stream().map(sl -> sl.size()).collect(Collectors.toList());
//    System.err.println("string candidate counts : " + stringCandidateCounts);
//
////    Map<Note, Long> noteCandidateCounts = stringCandidates.stream().flatMap(Collection::stream).collect(Collectors.groupingBy(e -> ((ChordFret)e).getFret().getNote(), Collectors.counting()));
//    Map<Note, List<ChordFret>> noteCandidates = new HashMap<>();
//    for (ChordFret chordFret : stringCandidates.stream().flatMap(List::stream).collect(Collectors.toList())) {
//      List<ChordFret> notesChordFrets = noteCandidates.get(chordFret.getFret().getNote());
//      if (notesChordFrets == null) {
//        notesChordFrets = new ArrayList<>();
//        noteCandidates.put(chordFret.getFret().getNote(), notesChordFrets);
//      }
//      notesChordFrets.add(chordFret);
//    }
//    System.err.println("noteCandidates: " + noteCandidates);
    

    // for each string
    for (int stringNum = 0; stringNum < stringCandidates.size(); stringNum++) {
//      System.err.println("Candidates: " + stringCandidates.get(stringNum));

      List<ChordFret> stringsCandidates = stringCandidates.get(stringNum);
      // find the chosen candidate from the last string at the end of the tone list
      ChordFret prevTone = null;
      if (tones.size() > 0) {
        prevTone = tones.get(tones.size() - 1);
      }
      ChordFret tone = null;
      // now go thru this strings candidates
      for (ChordFret thisChordTone : stringsCandidates) {
        if (prevTone != null) {
          // was the prev string chosen tone identical to this one? don't want repeats
          // if same note and occtave move on to next string candidate
          if (!prevTone.getFret().equalsTonally(thisChordTone.getFret())) {
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
        tones.add(new ChordFret(new Fret(-1, null, -1, stringNum, null, 0), ScaleInterval.PERFECT_UNISON));
      }
    }
    showTones(tones, options);
  }

  public void showChordOccurence(Chord chord) {
    showChordOccurence(chord, defaultOptions);
  }

  public void showChordOccurence(Chord chord, Options options) {
    List<ChordFret> tones = new ArrayList<>();

    out.println();
    out.println(StringUtils.center(chord.getTitle(), 32));
    out.println();

    List<List<Fret>> fretboardFrets = instrument.getFretsByFret();

    for (List<Fret> fretFrets : fretboardFrets) {
      for (Fret fret : fretFrets) {
        Optional<ScaleNote> chordScaleNoteForTone = chord.getChordNotes().stream().filter(cn -> fret.getNote().equals(cn.getNote()))
            .findAny();
        if (chordScaleNoteForTone.isPresent()) {
          tones.add(new ChordFret(fret, chordScaleNoteForTone.get().getInterval().get()));
        }
      }
    }
    showTones(tones, defaultOptions);
  }


  private void showTones(List<ChordFret> chordTones, Options options) {
    ColourMap colourMap = new ColourMap();
    List<List<Fret>> fretboardFrets = instrument.getFretsByFret();
    List<Integer> deadStrings = new ArrayList<>();
    int lowestFret = chordTones.stream().max(Comparator.comparingInt(ct -> 
    Integer.valueOf(ct.getFret().getFretNum()))).get().getFret().getFretNum();
        
    for (ChordFret chordFret : chordTones) {
      if (chordFret.getFret().getNote() == null) {
        deadStrings.add(chordFret.getFret().getStringNum());
      }
    }

    int fretNum = 0;
    for (List<Fret> fretTones : fretboardFrets) {
      String inlay = inlays.contains(fretNum) ? String.format(" %-2s ", fretNum) : "    ";
      out.print(inlay);
      String sep = (fretNum == 0) ? "" : "┃";
      int stringNum = 0;
      for (Fret fretTone : fretTones) {
        String ldr = (stringNum == 0 && fretNum != 0) ? "┃" : (fretNum == 0 ? " " : "");

        Optional<ChordFret> chordTone = chordTones.stream().filter(ct -> fretTone == ct.getFret())
            .findAny();
        
        if (chordTone.isPresent()) {
          String fretStr = null;
          if (options.isIntervals()) {
            fretStr = chordTone.get().getInterval().getLabel();
          } else {
            fretStr = chordTone.get().getFret().getNote().getLabel();
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
        out.println(createFretLine("┏", "┳", "┓"));
      } else if (fretNum < 12) {
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
   for (int n=0;n < strings; n++) {
     sb.append("━━━");
     if (n < strings - 1) {
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
