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
  List<Tone> tones = new ArrayList<>();
  @Getter
  List<Note> stringNotes;
  
  // remember element 0 in each inner List is the open string note
  @Getter
  List<List<Tone>> stringTones = new ArrayList<>();
  
  // remember element 0 in the inner list is the open string notes
  @Getter
  List<List<Tone>> fretTones = new ArrayList<>();


  public Guitar() {
    this(DEFAULT_STRINGS);
  }

  public Guitar(Note[] stringNotes) {
    this.stringNotes = Arrays.asList(stringNotes);

    for (int stringNum = 0; stringNum < stringNotes.length; stringNum++) {
      Optional<ScaleNote> optScaleNote =
          Scale.CHROMATIC_SCALE.findScaleNote(stringNotes[stringNum]);
      ScaleNote scaleNote = optScaleNote.get();

      for (int fretNum = 0; fretNum <= FRETS; fretNum++) {

        int octave = 0; // until known from prev tone

        // first look for relative tone on current string
        Tone prevTone = findRelativeToneUp(stringNum, scaleNote.getNote());
        if (prevTone != null) {
          octave = prevTone.getOctave() + 1;
        } else {
          // otherwise on the prev string
          prevTone = findRelativeToneUp(stringNum - 1, scaleNote.getNote());
          if (prevTone != null) {
            octave = prevTone.getFret() > fretNum ? prevTone.getOctave() : prevTone.getOctave() + 1;
          }
        }
        Tone tone = new Tone(stringNum * FRETS + fretNum, scaleNote.getNote(), octave,
            stringNum, stringNotes[stringNum], fretNum);
        tones.add(tone);
        
        List<Tone> currentFretsTones;
        if (fretTones.size() == fretNum) {
          currentFretsTones = new ArrayList<>();
          fretTones.add(currentFretsTones);
        }
        currentFretsTones = fretTones.get(fretNum);
        currentFretsTones.add(tone);
        
        scaleNote = scaleNote.getNextScaleNote();
      }
      final int x = stringNum;
      stringTones
          .add(tones.stream().filter(t -> t.getStringNum() == x).collect(Collectors.toList()));
    }
  }



  private Tone findRelativeToneUpFromIndex(int stringNum, Note note, int index) {
    Tone tone = null;
    for (int idx = index; idx-- > 0;) {
      Tone aTone = tones.get(idx);
      if (aTone.getNote() == note && aTone.getStringNum() == stringNum) {
        tone = tones.get(idx);
        break;
      }
    }
    return tone;
  }

  private Tone findRelativeToneUp(int stringNum, Note note) {
    return findRelativeToneUpFromIndex(stringNum, note, tones.size());
  }
}
