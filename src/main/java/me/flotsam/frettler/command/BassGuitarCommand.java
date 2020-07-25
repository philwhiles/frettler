package me.flotsam.frettler.command;

import me.flotsam.frettler.instrument.BassGuitar;
import picocli.CommandLine.Command;

/**
 * Handles the initial BASSGUITAR command/param
 * @author philwhiles
 *
 */
@Command(name = "bassguitar", description = "Generates bass guitar scales (and chords?!)")
public class BassGuitarCommand extends FrettedInstrumentCommand implements Runnable {

  
  @Override
  public void run() {
   exec(new BassGuitar(strings, frets));
  }
}
