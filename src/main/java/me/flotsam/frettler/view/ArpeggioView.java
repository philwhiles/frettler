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

import static java.lang.System.out;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Sequence;
import me.flotsam.frettler.instrument.FrettedInstrument;


/**
 * Renders chords or scales in a horizontal fretboard representation, where notes appear in the
 * middle of strings, and the 'fretboard' has inlay markers mirroring standard guitar fret inlays.
 * 
 * @author philwhiles
 *
 */
public class ArpeggioView implements View {

  private FrettedInstrument instrument;

  public ArpeggioView(FrettedInstrument instrument) {
    this.instrument = instrument;
  }

  public void showChord(Chord chord, Sequence scaleNoteSequence, Options options) {
    out.println();
    out.print("    ");
    out.println(StringUtils
        .center(chord.getTitle() + " ~ (" + instrument.getInstrumentType().getLabel() + " ["
            + instrument.getStringNotes().stream().map(Note::name).collect(Collectors.joining(","))
            + "]", 104));
    out.println(StringUtils.center(scaleNoteSequence.getLabel(), 104));
    out.println();
    initColourMap(chord);
    List<List<SequenceFretNote>> fretSequence = prepareSequence(scale, scaleNoteSequence, instrument, options.isReverse(), options.isOpen(), options.getPosition(), options.getGroup());
    display(fretSequence, options);
  }

  public void display(List<List<SequenceFretNote>> sequenceNotes, Options options) {
    int[] counts = new int[instrument.getStringCount()];
    int count = 0;
    for (int i = 0; i < sequenceNotes.size(); i++) {
      counts[i] = count;
      count += sequenceNotes.get(i).size();
    }

    for (int i = sequenceNotes.size() - 1; i >= 0; i--) {
      StringBuilder buff =
          new StringBuilder("    " + instrument.getStringNotes().get(i).getLabel() + "|");
      buff.append(StringUtils.repeat('-', 4 * counts[i]));
      buff.append("---");
      int ansiSize = 0;
      for (int n = 0; n < sequenceNotes.get(i).size(); n++) {
        String fretNumLbl = Integer.toString(sequenceNotes.get(i).get(n).getFret().getFretNum());

        Colour col = ColourMap.get(sequenceNotes.get(i).get(n).getFret().getNote().getPitch());
        if (options.isColour()) {
          buff.append(String.format("%s-%s%s%s-", fretNumLbl.length() == 1 ? "-" : "", col,
              fretNumLbl, Colour.RESET));
          ansiSize += col.toString().length() + Colour.RESET.toString().length();
        } else {
          buff.append(String.format("%s-%s-", fretNumLbl.length() == 1 ? "-" : "", fretNumLbl));
        }
      }
      out.println(buff.toString() + StringUtils.repeat('-', 100 - buff.length() + ansiSize));
    }
    out.println();


    out.print("          ");
    for (List<SequenceFretNote> stringNotes : sequenceNotes) {
      for (SequenceFretNote fretNote : stringNotes) {
        Colour col = ColourMap.get(fretNote.getFret().getNote().getPitch());
        if (options.isColour()) {
          out.print(String.format("%s%2s%s  ", col, fretNote.getFret().getNote().getLabel(),
              Colour.RESET));
        } else {
          out.print(String.format("%2s  ", fretNote.getFret().getNote().getLabel()));
        }
      }
    }
    out.println();
    out.print("          ");
    for (List<SequenceFretNote> stringNotes : sequenceNotes) {
      for (SequenceFretNote fretNote : stringNotes) {
        Colour col = ColourMap.get(fretNote.getFret().getNote().getPitch());
        if (options.isColour()) {
          out.print(String.format("%s%2s%s  ", col, fretNote.getScaleInterval().getLabel(),
              Colour.RESET));
        } else {
          out.print(String.format("%2si  ", fretNote.getScaleInterval().getLabel()));
        }
      }
    }

    out.println();
    out.println();
  }



  @Data
  @AllArgsConstructor
  public class Options {
    private boolean colour;
    private boolean reverse;
    private boolean open;
    private int position;
    private int group;
  }
}

