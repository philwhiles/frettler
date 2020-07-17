package me.flotsam.frettler.instrument.guitar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import lombok.Getter;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleNote;

public class Guitar {
  
  public static final int FRETS = 12;

  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.E, Note.A, Note.D, Note.G, Note.B, Note.E};

  @Getter
  List<Fret> allFrets = new ArrayList<>();
  @Getter
  List<Note> stringNotes;
  
  // remember element 0 in each inner List is the open string note
  @Getter
  List<List<Fret>> stringFrets = new ArrayList<>();
  
  // remember element 0 in the inner list is the open string notes
  @Getter
  List<List<Fret>> fretFrets = new ArrayList<>();


  public Guitar() {
    this(DEFAULT_STRINGS);
  }

  public Guitar(Note[] strings) {
    if (strings.length > 0) {
      this.stringNotes = Arrays.asList(strings);
    } else {
      this.stringNotes = Arrays.asList(DEFAULT_STRINGS);
    }

    for (int stringNum = 0; stringNum < stringNotes.size(); stringNum++) {
      Optional<ScaleNote> optScaleNote =
          Scale.CHROMATIC_SCALE.findScaleNote(stringNotes.get(stringNum));
      ScaleNote scaleNote = optScaleNote.get();

      for (int fretNum = 0; fretNum <= FRETS; fretNum++) {

        int octave = 0; // until known from prev fret

        // first look for relative fret note on current string
        Fret prevFret = findRelativeFretUp(stringNum, scaleNote.getNote());
        if (prevFret != null) {
          octave = prevFret.getOctave() + 1;
        } else {
          // otherwise on the prev string
          prevFret = findRelativeFretUp(stringNum - 1, scaleNote.getNote());
          if (prevFret != null) {
            octave = prevFret.getFret() > fretNum ? prevFret.getOctave() : prevFret.getOctave() + 1;
          }
        }
        Fret fret = new Fret(stringNum * FRETS + fretNum, scaleNote.getNote(), octave,
            stringNum, stringNotes.get(stringNum), fretNum);
        allFrets.add(fret);
        
        List<Fret> currentFretsFrets;
        if (fretFrets.size() == fretNum) {
          currentFretsFrets = new ArrayList<>();
          fretFrets.add(currentFretsFrets);
        }
        currentFretsFrets = fretFrets.get(fretNum);
        currentFretsFrets.add(fret);
        
        scaleNote = scaleNote.getNextScaleNote();
      }
      final int x = stringNum;
      stringFrets
          .add(allFrets.stream().filter(t -> t.getStringNum() == x).collect(Collectors.toList()));
    }
  }



  private Fret findRelativeFretUpFromIndex(int stringNum, Note note, int index) {
    Fret fret = null;
    for (int idx = index; idx-- > 0;) {
      Fret aFret = allFrets.get(idx);
      if (aFret.getNote() == note && aFret.getStringNum() == stringNum) {
        fret = allFrets.get(idx);
        break;
      }
    }
    return fret;
  }

  private Fret findRelativeFretUp(int stringNum, Note note) {
    return findRelativeFretUpFromIndex(stringNum, note, allFrets.size());
  }
}
