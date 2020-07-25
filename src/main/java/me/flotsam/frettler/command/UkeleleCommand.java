package me.flotsam.frettler.command;

import me.flotsam.frettler.instrument.Ukelele;
import picocli.CommandLine.Command;

/**
 * Handles the initial UKELELE command/param
 * @author philwhiles
 *
 */
@Command(name = "ukelele", description = "Generates ukelele scales and chords")
public class UkeleleCommand extends FrettedInstrumentCommand implements Runnable {

  
  @Override
  public void run() {
   exec(new Ukelele(strings, frets));
  }
}
