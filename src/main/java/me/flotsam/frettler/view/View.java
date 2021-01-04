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

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleInterval;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.engine.Sequence;
import me.flotsam.frettler.instrument.Fret;
import me.flotsam.frettler.instrument.FrettedInstrument;

public interface View {


  public default void initColourMap(Scale scale) {
    scale.getScaleNotes().forEach(sn -> ColourMap.get(sn.getNote().getPitch()));
  }

  public default void initColourMap(Chord chord) {
    chord.getChordNotes().forEach(cn -> ColourMap.get(cn.getNote().getPitch()));
  }

  public default List<List<SequenceFretNote>> prepareSequence(Scale scale, Sequence scaleNoteSequence, FrettedInstrument instrument,
      boolean reversed, boolean open) {
    List<List<SequenceFretNote>> allStringFrets = new ArrayList<>();
    int[] sequence = scaleNoteSequence.getSequence();
    int seqIdx = 0;
    List<ScaleNote> notesToUse = new ArrayList<>();

    // if (options.isReverse()) {
    // int[] reversed = new int[sequence.length];
    // for (int n = 0; n < sequence.length; n++) {
    // reversed[n] = sequence[sequence.length -n];
    // }
    // sequence = reversed;
    // }
    for (int i = 0; i < 200; i++) {
      notesToUse.add(scale.getNthScaleNote(sequence[seqIdx]));
      if (++seqIdx == sequence.length) {
        seqIdx = 0;
      }
    }
    seqIdx = 0;

    Fret lastFret = null;
    for (int i = 0; i < instrument.getFretsByString().size(); i++) {
      List<SequenceFretNote> stringFrets = new ArrayList<>();
      allStringFrets.add(stringFrets);

      List<Fret> tonesInString = instrument.getFretsByString().get(i);
      for (Fret fret : tonesInString) {
        if (!open && fret.getFretNum() == 0) {
          continue;
        }
        if (fret.getNote().getPitch() == notesToUse.get(seqIdx).getNote().getPitch()) {
          // SequenceFretNote lastStringFret =
          // stringFrets.size() == 0 ? null : stringFrets.get(stringFrets.size() - 1);
          if (lastFret != null && Math.abs(fret.getFretNum() - lastFret.getFretNum()) > 5) {
            continue;
          } else {
            stringFrets.add(new SequenceFretNote(fret, notesToUse.get(seqIdx).getInterval().get()));
            lastFret = fret;
            seqIdx++;

            if (stringFrets.size() == scaleNoteSequence.getGrouping()) {
              break;
            }
          }
        }
      }
    }
    return allStringFrets;
  }
  
  @Data
  @AllArgsConstructor
  public class SequenceFretNote {
    private Fret fret;
    private ScaleInterval scaleInterval;
  }
}
