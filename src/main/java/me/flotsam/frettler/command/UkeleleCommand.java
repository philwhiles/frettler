package me.flotsam.frettler.command;

import me.flotsam.frettler.instrument.stringed.Ukelele;
import picocli.CommandLine.Command;

/**
 * Handles the initial UKELELE command/param
 * @author philwhiles
 *
 */
@Command(name = "UKELELE", description = "Generates ukelele scales and chords")
public class UkeleleCommand extends StringedInstrumentCommand implements Runnable {

  
  @Override
  public void run() {
   exec(new Ukelele(strings));
  }
}
