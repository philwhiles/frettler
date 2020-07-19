package me.flotsam.frettler.command;

import me.flotsam.frettler.instrument.stringed.Mandolin;
import picocli.CommandLine.Command;

/**
 * Handles the initial MANDOLIN command/param
 * @author philwhiles
 *
 */
@Command(name = "MANDOLIN", description = "Generates mandolin scales and chords")
public class MandolinCommand extends StringedInstrumentCommand implements Runnable {

  
  @Override
  public void run() {
   exec(new Mandolin(strings));
  }
}
