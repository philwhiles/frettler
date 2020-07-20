package me.flotsam.frettler.command;

import me.flotsam.frettler.instrument.Guitar;
import picocli.CommandLine.Command;

/**
 * Handles the initial GUITAR command/param
 * @author philwhiles
 *
 */
@Command(name = "GUITAR", description = "Generates guitar scales and chords")
public class GuitarCommand extends FrettedInstrumentCommand implements Runnable {

  
  @Override
  public void run() {
   exec(new Guitar(strings));
  }
}
