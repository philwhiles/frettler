package me.flotsam.frettler.command;

import me.flotsam.frettler.instrument.Banjo;
import picocli.CommandLine.Command;

/**
 * Handles the initial BANJO command/param
 * @author philwhiles
 *
 */
@Command(name = "banjo", description = "Generates banjo scales and chords")
public class BanjoCommand extends FrettedInstrumentCommand implements Runnable {

  
  @Override
  public void run() {
   exec(new Banjo(strings));
  }
}
