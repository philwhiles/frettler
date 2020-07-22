package me.flotsam.frettler.command;

import me.flotsam.frettler.instrument.Mandolin;
import picocli.CommandLine.Command;

/**
 * Handles the initial MANDOLIN command/param
 * @author philwhiles
 *
 */
@Command(name = "mandolin", description = "Generates mandolin scales and chords")
public class MandolinCommand extends FrettedInstrumentCommand implements Runnable {

  
  @Override
  public void run() {
   exec(new Mandolin(strings));
  }
}
