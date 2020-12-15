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

package me.flotsam.frettler;

import java.util.concurrent.Callable;
import me.flotsam.frettler.command.BanjoCommand;
import me.flotsam.frettler.command.BassGuitarCommand;
import me.flotsam.frettler.command.LookupCommand;
import me.flotsam.frettler.command.CompletionsCommand;
import me.flotsam.frettler.command.FifthsCommand;
import me.flotsam.frettler.command.FrettlerCommand;
import me.flotsam.frettler.command.GuitarCommand;
import me.flotsam.frettler.command.MandolinCommand;
import me.flotsam.frettler.command.MenuCommand;
import me.flotsam.frettler.command.PatternsCommand;
import me.flotsam.frettler.command.UkeleleCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(name = "frettler",
    description = "Generates scales/arpeggios, chords and arpeggios for fretted instruments",
    mixinStandardHelpOptions = true,
    subcommands = {MenuCommand.class, FifthsCommand.class, HelpCommand.class, PatternsCommand.class, CompletionsCommand.class, LookupCommand.class, GuitarCommand.class,
        BassGuitarCommand.class, BanjoCommand.class, MandolinCommand.class, UkeleleCommand.class})
public class Main implements Callable<Integer> {

  public static void main(String... args) throws Exception {
    new CommandLine(new Main()).setCaseInsensitiveEnumValuesAllowed(true)
        .setAbbreviatedOptionsAllowed(true).setAbbreviatedSubcommandsAllowed(true).execute(args);
  }

  @Override
  public Integer call() throws Exception {
    CommandLine.usage(new FrettlerCommand(), System.out);
    return 0;
  }
}

