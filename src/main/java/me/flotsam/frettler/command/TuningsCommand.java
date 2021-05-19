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
import java.util.Arrays;
import lombok.Getter;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.instrument.FrettedInstrument;
import me.flotsam.frettler.instrument.Tuning;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Handles the chord analysis of a set of provided notes
 * 
 * @author philwhiles
 *
 */
@Command(name = "tunings", description = "Lists the tunings that Frettler understands")
public class TuningsCommand implements Runnable {

  @Option(names = {"-i", "--instrument"}, description = "Optionally only list tunings for this instrument")
  @Getter
  protected FrettedInstrument.InstrumentType instrumentType;

  @Override
  public void run() {
    for (Tuning tuning : Tuning.values()) {
      if (instrumentType == null || tuning.getInstrumentType() == instrumentType) {
        out.println(tuning.name().toLowerCase() + " " + Arrays.toString(tuning.getNotes()));
      }
    }
  }
}
