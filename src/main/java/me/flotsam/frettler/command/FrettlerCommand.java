/*
    Copyright (C) 2020  Philip Whiles

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package me.flotsam.frettler.command;

import lombok.Getter;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Sequence;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Base class for the primary commands
 * 
 * @author philwhiles
 *
 */
public class FrettlerCommand {

  @Parameters(index = "0", defaultValue = "HORIZONTAL", description = "The view to use")
  View view;

  @Parameters(index = "1", defaultValue = "C", description = "The root/tonic of the chord or scale")
  Note root;

  @Parameters(index = "2", defaultValue = "SCALE_MAJOR",
      description = "The interval pattern to use")
  IntervalPattern intervalPattern;

//  @Parameters(index = "3", defaultValue = "NONE",
//      description = "The scale sequence to use")
  Sequence sequence = Sequence.NONE;

  @Option(names = {"-m", "--mono"}, description = "Display in 'monochrome'")
  @Getter
  boolean mono;

  @Option(names = {"-o", "--octaves"}, description = "Colourize octaves instead of notes/intervals")
  @Getter
  boolean octaves;

  @Option(names = {"-i", "--intervals"}, description = "Show interval labels instead of notes")
  @Getter
  boolean intervals;

  
  public enum View {
    HORIZONTAL, H(HORIZONTAL), VERTICAL, V(VERTICAL), DISPLAY, D(DISPLAY), FIND, F(FIND), CHORD, C(CHORD), BOX, B(BOX), TAB, T(TAB), PROGRESSION, P(PROGRESSION);
    @Getter
    private View type;

    View(View alias) {
      this.type = alias;
    }
    View() {
      type = this;
    }
  }
}


