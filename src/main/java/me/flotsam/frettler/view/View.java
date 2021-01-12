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

  public default List<List<SequenceFretNote>> prepareSequence(Scale scale,
      Sequence scaleNoteSequence, FrettedInstrument instrument, boolean reversed, boolean open, int jump, int group) {
    List<List<SequenceFretNote>> allStringFrets = new ArrayList<>();
    int[] sequence = scaleNoteSequence.getSequence();
    int seqIdx = jump;
    List<ScaleNote> notesToUse = new ArrayList<>();

    for (int i = 0; i < 200; i++) {
      notesToUse.add(scale.getNthScaleNote(sequence[seqIdx]));
      if (++seqIdx == sequence.length) {
        seqIdx = 0;
      }
    }
    seqIdx = 0;

    for (int i = 0; i < instrument.getFretsByString().size(); i++) {
      int firstFret = -1;
      int lastFret = -1;
      List<SequenceFretNote> stringFrets = new ArrayList<>();
      allStringFrets.add(stringFrets);

      List<Fret> tonesInString = instrument.getFretsByString().get(i);
      for (Fret fret : tonesInString) {
        if (!open && fret.getFretNum() == 0) {
          continue;
        }
        if (fret.getNote().getPitch() == notesToUse.get(seqIdx).getNote().getPitch()) {
          if (firstFret == -1) {
            firstFret = fret.getFretNum();
          } else {
            lastFret = fret.getFretNum();
            if (Math.abs(lastFret - firstFret) > 5 && Math.abs(lastFret + 12 - firstFret) > 5) {
              break;
            }
          }

          stringFrets.add(new SequenceFretNote(fret, notesToUse.get(seqIdx).getInterval().get()));
          seqIdx++;

          if (stringFrets.size() == group) {
            break;
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
