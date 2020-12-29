package me.flotsam.fretller.engine;

import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.ChordBank;
import me.flotsam.frettler.engine.ChordBank.ChordDefinition;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.instrument.FrettedInstrument;
import me.flotsam.frettler.instrument.FrettedInstrument.InstrumentDefinition;
import me.flotsam.frettler.instrument.Guitar;

public class ChordBankTest {

  @Test
  public void ensureUniqueChords() {
    Map<InstrumentDefinition, Map<String, List<List<String>>>> instrumentChords = new HashMap<>();

    Map<InstrumentDefinition, List<ChordDefinition>> definitions = ChordBank.getDefinitions();
    for (InstrumentDefinition instrument : definitions.keySet()) {
      List<ChordDefinition> instrumentsDefs = definitions.get(instrument);
      for (ChordDefinition chordDef : instrumentsDefs) {
        Map<String, List<List<String>>> chordChords = instrumentChords.get(instrument);
        if (chordChords == null) {
          chordChords = new HashMap<>();
          instrumentChords.put(instrument, chordChords);
        }
        String key = chordDef.getChordRoot() + ":" + chordDef.getAddedNote() + ":"
            + chordDef.getChordPattern().getLabel();
        List<List<String>> stringList = chordChords.get(key);
        if (stringList == null) {
          stringList = new ArrayList<>();
          chordChords.put(key, stringList);
        }
        if (stringList.contains(chordDef.getStrings())) {
          fail("Duplicated chord definition for " + chordDef.getChordRoot() + " "
              + chordDef.getChordPattern() + " " + chordDef.getStrings());
        }
        stringList.add(chordDef.getStrings());
      }
    }
  }

  @Test
  public void validateChordFrets() {
    Map<InstrumentDefinition, List<ChordDefinition>> definitions = ChordBank.getDefinitions();
    for (InstrumentDefinition instrument : definitions.keySet()) {
      List<ChordDefinition> instrumentsDefs = definitions.get(instrument);
      for (ChordDefinition chordDef : instrumentsDefs) {
        InstrumentDefinition instrumentDef = instrument;
        FrettedInstrument thisInstrument = null;
        switch (instrumentDef.getInstrumentType()) {
          case GUITAR:
            thisInstrument = new Guitar(instrumentDef.getTuning().toArray(new Note[] {}), 30);
            break;
          default:
            break;
        }

        Chord chord = new Chord(chordDef.getChordRoot(), chordDef.getChordPattern(), chordDef.getAddedNote());
        int stringNum = -1;
        for (String str : chordDef.getStrings()) {
          stringNum++;
          if (str.toUpperCase().equals("X")) {
            continue;
          }
          int fretNum = Integer.parseInt(str);
          Note fretNote = thisInstrument.getFretsByString().get(stringNum).get(fretNum).getNote();
          if (!chord.containsNotes(fretNote)) {
            fail("\nChord contains " + chord.getChordNotes() + "\nChordDef "
                + chordDef.getChordRoot() + " " + chordDef.getChordPattern().getLabel()
                + chordDef.getStrings() + " invalid fret num " + fretNum + " on string num "
                + (instrument.getTuning().size() - stringNum) + " (" + fretNote.getLabel() + ")");
          }
        }
      }
    }
  }
}
