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

package me.flotsam.frettler.instrument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleInterval;
import me.flotsam.frettler.engine.ScaleNote;

public abstract class FrettedInstrument {

  static final int DEFAULT_FRETS = 12;
  @Getter
  boolean lefty;
  
  @Getter
  List<Fret> allFrets = new ArrayList<>();
  @Getter
  List<Note> stringNotes;

  // remember element 0 in each inner List is the open string note
  List<List<Fret>> fretsByString = new ArrayList<>();

  // remember element 0 in the inner list is the open string notes
  List<List<Fret>> fretsByFret = new ArrayList<>();

  @Getter
  private int frets;

  @Getter
  private InstrumentType instrumentType;

  public List<List<Fret>> getFretsByFret(boolean lefty) {
    List<List<Fret>> fretList = new ArrayList<>();
    for (List<Fret> frets : fretsByFret) {
      List<Fret> newFretList = new ArrayList<>(frets);
      if (lefty) {
        Collections.reverse(newFretList);
      }
      fretList.add(newFretList);
    }
    return fretList;
  }

  public List<List<Fret>> getFretsByString(boolean lefty) {
    List<List<Fret>> fretList = new ArrayList<>();
    for (List<Fret> frets : fretsByString) {
      fretList.add(new ArrayList<>(frets));
    }
    if (lefty) {
      Collections.reverse(fretList);
    }
    return fretList;
  }

  public FrettedInstrument(InstrumentType instrumentType, int frets, Note[] strings, Tuning tuning) {
    this(instrumentType, frets, strings, tuning, false);
  }
  public FrettedInstrument(InstrumentType instrumentType, int frets, Note[] strings, Tuning tuning, boolean lefty) {
    this.instrumentType = instrumentType;
    this.frets = frets;

    if (tuning != null) {
      if (tuning.getInstrumentType() != instrumentType) {
        System.err.println("Tuning incompatible with instrument type");
        System.exit(-1);
      } else {
        strings = tuning.getNotes();
      }
    }
    this.stringNotes = Arrays.asList(strings);
    this.lefty = lefty;

    Scale chromaticScale = Scale.CHROMATIC_SCALE;
    Scale referenceScale = new Scale(strings[0], IntervalPattern.SCALE_CHROMATIC);
    int accumulativeDistance = 0;
    ScaleNote prevStringNote = referenceScale.getHead();

    for (int stringNum = 0; stringNum < stringNotes.size(); stringNum++) {
      ScaleNote stringNote = chromaticScale.findScaleNote(stringNotes.get(stringNum)).get();
      ScaleNote fretNote = stringNote;

      accumulativeDistance += calcRelativeDistance(prevStringNote, stringNote);
      for (int fretNum = 0; fretNum <= frets; fretNum++) {

        Fret fret = null;

        int fretDistance = accumulativeDistance + fretNum;
        int currentOctave = fretDistance / ScaleInterval.P8.getSemiTones();

        if (stringNum == 0 && (fretNum > 0 && fretNum < 6) && isBanjo()) {
          fret = new Fret(stringNum * frets + fretNum, null, currentOctave, stringNum,
              stringNotes.get(stringNum), fretNum);
        } else {
          fret = new Fret(stringNum * frets + fretNum, fretNote.getNote(), currentOctave, stringNum,
              stringNotes.get(stringNum), fretNum);

          fretNote = fretNote.getNextScaleNote();
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

      prevStringNote = stringNote;
    }
  }

  public String getLabel() {
    return instrumentType.getLabel();
  };

  private int calcRelativeDistance(ScaleNote fromNote, ScaleNote toNote) {
    // need to calculate distance from the from note in a new scale starting at that note
    // to avoid the wrapping issue if the to note occurs before the from note
    Scale referenceScale = new Scale(fromNote.getNote(), IntervalPattern.SCALE_CHROMATIC);
    ScaleNote from = referenceScale.findScaleNote(fromNote.getNote()).get();
    ScaleNote to = referenceScale.findScaleNote(toNote.getNote()).get();
    return to.getInterval().get().getSemiTones() - from.getInterval().get().getSemiTones();
  }

  public boolean isBanjo() {
    return false;
  }

  public int getStringCount() {
    return fretsByString.size();
  }

  public static FrettedInstrument getBiggerInstrument(FrettedInstrument instrument) {
    FrettedInstrument biggerInstrument = null;
    switch (instrument.getInstrumentType()) {
      case GUITAR:
        biggerInstrument = new Guitar(instrument.getStringNotes().toArray(new Note[] {}), null, 23);
        break;
      case BASSGUITAR:
        biggerInstrument = new BassGuitar(instrument.getStringNotes().toArray(new Note[] {}), null, 23);
        break;
      case UKELELE:
        biggerInstrument = new Ukelele(instrument.getStringNotes().toArray(new Note[] {}), null, 23);
        break;
      case MANDOLIN:
        biggerInstrument = new Mandolin(instrument.getStringNotes().toArray(new Note[] {}), null, 23);
        break;
      case BANJO:
        biggerInstrument = new Banjo(instrument.getStringNotes().toArray(new Note[] {}), null, 23);
        break;
      case CUSTOM:
        biggerInstrument =
            new CustomInstrument(instrument.getStringNotes().toArray(new Note[] {}), null, 23, instrument.lefty);
        break;
    }
    return biggerInstrument;
  }

  public enum InstrumentType {
    //@formatter:off
    CUSTOM("Custom Instrument"),
    GUITAR("Guitar"),
    BANJO("Banjo"),
    BASSGUITAR("Bass Guitar"),
    MANDOLIN("Mandolin"),
    UKELELE("Ukelele");
    //@formatter:on

    @Getter
    private String label;

    private InstrumentType(String label) {
      this.label = label;
    }
  }

  public enum InstrumentDefinition {
    GUITAR_EADGBE(InstrumentType.GUITAR, "EADGBE"), BASSGUITAR_EADG(InstrumentType.BASSGUITAR,
        "EADG"), MANDOLIN_GDAE(InstrumentType.MANDOLIN,
            "GDAE"), UKELELE_GCEA(InstrumentType.UKELELE, "GCEA");

    @Getter
    private InstrumentType instrumentType;
    @Getter
    private List<Note> tuning;

    private InstrumentDefinition(InstrumentType instrumentType, String tuning) {
      this.instrumentType = instrumentType;
      this.tuning = Arrays.asList(tuning.split("")).stream().map(s -> Note.valueOf(s))
          .collect(Collectors.toList());
    }

    public static Optional<InstrumentDefinition> findInstrument(InstrumentType instrumentType,
        List<Note> tuning) {
      return Arrays.asList(values()).stream()
          .filter(id -> id.getInstrumentType() == instrumentType && id.getTuning().equals(tuning))
          .findFirst();
    }
  }
}
