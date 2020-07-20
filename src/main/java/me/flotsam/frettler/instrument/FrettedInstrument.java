package me.flotsam.frettler.instrument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleNote;

public abstract class FrettedInstrument {

  @Getter
  List<Fret> allFrets = new ArrayList<>();
  @Getter
  List<Note> stringNotes;

  // remember element 0 in each inner List is the open string note
  @Getter
  List<List<Fret>> fretsByString = new ArrayList<>();

  // remember element 0 in the inner list is the open string notes
  @Getter
  List<List<Fret>> fretsByFret = new ArrayList<>();

  @Getter
  private int frets;

  @Getter
  private String label;


  public FrettedInstrument(String label, int frets, Note[] strings) {
    this.label = label;
    this.frets = frets;
    this.stringNotes = Arrays.asList(strings);

    for (int stringNum = 0; stringNum < stringNotes.size(); stringNum++) {
      Optional<ScaleNote> optScaleNote =
          Scale.CHROMATIC_SCALE.findScaleNote(stringNotes.get(stringNum));
      ScaleNote scaleNote = optScaleNote.get();

      for (int fretNum = 0; fretNum <= frets; fretNum++) {

        int octave = 0; // until known from prev fret
        Fret fret = null;

        if (stringNum == 0 && (fretNum > 0 && fretNum < 6) && isBanjo()) {
          fret = new Fret(stringNum * frets + fretNum, null, octave, stringNum,
              stringNotes.get(stringNum), fretNum);
        } else {
          // first look for relative fret note on current string
          Fret prevFret = findRelativeFretUp(stringNum, scaleNote.getNote());
          if (prevFret != null) {
            octave = prevFret.getOctave() + 1;
          } else {
            // otherwise on the prev string
            prevFret = findRelativeFretUp(stringNum - 1, scaleNote.getNote());
            if (prevFret != null) {
              octave =
                  prevFret.getFretNum() > fretNum ? prevFret.getOctave() : prevFret.getOctave() + 1;
            }
          }
          fret = new Fret(stringNum * frets + fretNum, scaleNote.getNote(), octave, stringNum,
              stringNotes.get(stringNum), fretNum);

          scaleNote = scaleNote.getNextScaleNote();
        }
        allFrets.add(fret);

        List<Fret> currentFretsFrets;
        if (fretsByFret.size() == fretNum) {
          currentFretsFrets = new ArrayList<>();
          fretsByFret.add(currentFretsFrets);
        }
        currentFretsFrets = fretsByFret.get(fretNum);
        currentFretsFrets.add(fret);
      }
      final int x = stringNum;
      fretsByString
          .add(allFrets.stream().filter(t -> t.getStringNum() == x).collect(Collectors.toList()));
    }
  }

  public boolean isBanjo() {
    return false;
  }

  public int getStringCount() {
    return fretsByString.size();
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
