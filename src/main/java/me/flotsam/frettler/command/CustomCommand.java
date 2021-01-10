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

import me.flotsam.frettler.instrument.CustomInstrument;
import picocli.CommandLine.Command;

/**
 * Handles the initial GUITAR command/param
 * @author philwhiles
 *
 */
@Command(name = "custom", description = "Generates scales and chords for your custom instrument")
public class CustomCommand extends FrettedInstrumentCommand implements Runnable {

  @Override
  public void run() {
   exec(new CustomInstrument(strings, frets));
  }
  
}