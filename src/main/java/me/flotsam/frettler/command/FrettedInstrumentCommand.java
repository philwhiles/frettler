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

package me.flotsam.frettler.command;

import static java.lang.System.out;
import java.util.List;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.instrument.FrettedInstrument;
import me.flotsam.frettler.view.VerticalView;
import me.flotsam.frettler.view.HorizontalView;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Base class that handles the initial instrument command param
 * 
 * @author philwhiles
 *
 */
@Command
public abstract class FrettedInstrumentCommand extends FrettlerCommand {

  @Option(names = {"-c", "--chords"}, description = "chord mode (view dependant)")
  boolean chordMode = false;

  @Option(names = {"-s", "--strings"}, split = ",", paramLabel = "note",
      description = "comma separated list of string tunings ie E,A,D,G,B,E")
  Note[] strings = new Note[] {};

  @Option(names = {"-f", "--frets"}, paramLabel = "num",
      description = "overrides the default 12 frets displayed")
  Integer frets = 12;

  public void exec(FrettedInstrument instrument) {

    Scale scale = null;
    Chord chord = null;
    if (this.intervalPattern.getPatternType() == IntervalPattern.PatternType.CHORD) {
      chord = new Chord(this.root, this.intervalPattern);
    } else {
      scale = new Scale(this.root, this.intervalPattern);
    }

    if (this.view.isHorizontal()) {
      HorizontalView instrumentView = new HorizontalView(instrument);
      HorizontalView.Options instrumentViewOptions =
          instrumentView.new Options(intervals, true, !mono);

      if (scale != null) {
        instrumentView.showScale(scale, instrumentViewOptions);
        if (chordMode) {
          List<Chord> chords = scale.createScaleChords();
          for (Chord aChord : chords) {
            out.println(aChord.getTitle());
            out.println();
          }
        }
      } else {
        chord = new Chord(this.root, this.intervalPattern);
        instrumentView.showChord(chord, instrumentViewOptions);
      }
    } else {
      VerticalView verticalView = new VerticalView(instrument);
      VerticalView.Options verticalViewOptions = verticalView.new Options(intervals, !mono);

      if (scale != null) {
        verticalView.showScale(scale, verticalViewOptions);
        if (chordMode) {
          List<Chord> chords = scale.createScaleChords();
          for (Chord aChord : chords) {
            out.println(aChord.getTitle());
            out.println();
          }
        }
      } else {
        verticalView.showArpeggio(chord, verticalViewOptions);
      }
    }
  }
}
