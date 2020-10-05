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
import lombok.Getter;
import me.flotsam.frettler.engine.IntervalPattern;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Handles the chord analysis of a set of provided notes
 * 
 * @author philwhiles
 *
 */
@Command(name = "patterns", description = "Lists the scales/modes/chords that Frettler understands")
public class PatternsCommand implements Runnable {

  @Option(names = {"-t", "--type"}, description = "Optionally only list patterns of this type")
  @Getter
  protected IntervalPattern.PatternType patternType;

  @Override
  public void run() {
    for (IntervalPattern pattern : IntervalPattern.values()) {
      if (patternType == null || pattern.getPatternType() == patternType && pattern != IntervalPattern.SCALE_CHROMATIC) {
        out.println(pattern.getTitle());
      }
    }
  }
}
