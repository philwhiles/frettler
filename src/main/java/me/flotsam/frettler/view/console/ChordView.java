package me.flotsam.frettler.view.console;

import static java.lang.System.out;
import java.util.Arrays;
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

  private Options defaultOptions = new Options(true);
  private static final List<Integer> inlays = Arrays.asList(1, 3, 5, 7, 9, 12);
  private Guitar guitar;

  public ChordView(Guitar guitar) {
    this.guitar = guitar;
  }

//  public void showOneChord(Chord chord) {
//    out.println(StringUtils.center(chord.getTitle(), 32));
//    out.println();
//    
//    
//    showChord(chord.getChordNotes());    
//  }

  public void showChord(Chord chord) {
    showChord(chord, defaultOptions);
  }

  public void showChord(Chord chord, Options options) {
    out.println(StringUtils.center(chord.getTitle(), 32));
    out.println();
    showChord(chord.getChordNotes(), options);    
  }
  
  public void showChord(List<ScaleNote> scaleNotes, Options options) {
    ColourMap colourMap = new ColourMap();
    List<List<Tone>> fretboardTones = guitar.getFretTones();
    List<Note> chordNotes =
        scaleNotes.stream().map(sn -> sn.getNote()).collect(Collectors.toList());

    int fretNum = 0;
    for (List<Tone> fretTones : fretboardTones) {
      String inlay = inlays.contains(fretNum) ? String.format(" %-2s ", fretNum) : "    ";
      out.print(inlay);
      String sep = (fretNum == 0) ? "" : "┃";
      List<Note> fretNotes =
          fretTones.stream().map(ft -> ft.getNote()).collect(Collectors.toList());
      int stringNum = 0;
      for (Note fretNote : fretNotes) {
        String ldr = (stringNum == 0 && fretNum != 0) ? "┃" : (fretNum == 0 ? " " : "");
        if (chordNotes.contains(fretNote)) {
          if (options.isColour()) {
            Colour col = colourMap.get(fretNote);
            out.print(String.format("%s %s%-2s%s%s", ldr, col, fretNote.getLabel(), Colour.RESET, sep));
            
          } else {
            out.print(String.format("%s %-2s%s", ldr, fretNote.getLabel(), sep));
          }

        } else {
          String mark = chordNotes.contains(guitar.getStringNotes().get(stringNum)) ? " " : " ";
          out.print(String.format("%s %s %s", ldr, mark, sep));

        }
        stringNum++;
      }
      out.println(inlay);
      if (fretNum == 0) {
        out.println("    ┏━━━┳━━━┳━━━┳━━━┳━━━┳━━━┓");
      } else if (fretNum == 12) {
        out.println("    ┗━━━┻━━━┻━━━┻━━━┻━━━┻━━━┛");
      } else {
        out.println("    ┣━━━╋━━━╋━━━╋━━━╋━━━╋━━━┫");
      }
      fretNum++;
    }
    out.println();
    out.println();
  }
  
  @Data
  @AllArgsConstructor
  public class Options {
    private boolean colour;
  }

}
