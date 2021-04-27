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



  default int[] getOrderedStrings(FrettedInstrument instrument, boolean lefty) {
    int[] values = new int[instrument.getStringCount()];
    int i = 0;
    if (lefty) {
      for (int s = 0; s<instrument.getStringCount(); s++) {
        values[i++] = s;
      }
    } else {
      for (int s = instrument.getStringCount() - 1; s >= 0; s--) {
        values[i++] = s;
      }
    }
    return values;
  }

  default void initColourMap(Scale scale) {
    scale.getScaleNotes().forEach(sn -> ColourMap.get(sn.getNote().getPitch()));
  }

  default void initColourMap(Chord chord) {
    chord.getChordNotes().forEach(cn -> ColourMap.get(cn.getNote().getPitch()));
  }

  default List<List<SequenceFretNote>> prepareSequence(Scale scale, Sequence scaleNoteSequence,
      FrettedInstrument instrument, boolean reversed, boolean open, int jump, int group) {
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

    int firstFretOnPrevString = -1;
    int lastFretOnPrevString = -1;
    int firstFret = -1;
    int lastFret = -1;
    for (int i = 0; i < instrument.getFretsByString(false).size(); i++) {
      firstFret = -1;
      lastFret = -1;
      List<SequenceFretNote> stringFrets = new ArrayList<>();
      allStringFrets.add(stringFrets);

      List<Fret> tonesInString = instrument.getFretsByString(false).get(i);
      for (Fret fret : tonesInString) {
        if (!open && fret.getFretNum() == 0) {
          continue;
        }
        if (fret.getNote().getPitch() == notesToUse.get(seqIdx).getNote().getPitch()) {
          int candidateFret = fret.getFretNum();

          if (firstFret != -1 && Math.abs(candidateFret - firstFret) > 5) {
            // move on to the next string
            break;
          }

          if (lastFretOnPrevString != -1) {
            if (Math.abs(lastFretOnPrevString - candidateFret) > 5
                || Math.abs(firstFretOnPrevString - candidateFret) > 5) {
              // move on to a lower fret
              continue;
            }
          }

          firstFret = firstFret == -1 ? candidateFret : firstFret;
          lastFret = candidateFret;
          stringFrets.add(new SequenceFretNote(fret, notesToUse.get(seqIdx).getInterval().get()));

          seqIdx++;

          if (stringFrets.size() == group) {
            break;
          }
        }
      }
      lastFretOnPrevString = lastFret;
      firstFretOnPrevString = firstFret;
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
