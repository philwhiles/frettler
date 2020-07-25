package me.flotsam.frettler;

import java.util.concurrent.Callable;
import me.flotsam.frettler.command.BanjoCommand;
import me.flotsam.frettler.command.BassGuitarCommand;
import me.flotsam.frettler.command.CompletionsCommand;
import me.flotsam.frettler.command.FrettlerCommand;
import me.flotsam.frettler.command.GuitarCommand;
import me.flotsam.frettler.command.MandolinCommand;
import me.flotsam.frettler.command.UkeleleCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(name = "frettler",
    description = "Generates scalei/nodes, chords and arpeggios for fretted instruments",
    subcommands = {HelpCommand.class, CompletionsCommand.class, GuitarCommand.class,
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

