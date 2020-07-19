package me.flotsam.frettler.command;

import me.flotsam.frettler.instrument.stringed.Banjo;
import picocli.CommandLine.Command;

/**
 * Handles the initial BANJO command/param
 * @author philwhiles
 *
 */
@Command(name = "BANJO", description = "Generates banjo scales and chords")
public class BanjoCommand extends StringedInstrumentCommand implements Runnable {

  
  @Override
  public void run() {
   exec(new Banjo(strings));
  }
}
