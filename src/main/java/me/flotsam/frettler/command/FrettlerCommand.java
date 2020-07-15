package me.flotsam.frettler.command;

import lombok.Data;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.IntervalPattern;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Base class for the primary commands
 * @author philwhiles
 *
 */
@Data
public class FrettlerCommand {

  @Parameters(index = "0", description="The guitar view to use")
  protected View view;
  
  @Parameters(index = "1", description="The type to display")
  protected Type type;

  @Parameters(index = "2", description="The root/tonic of the chord or scale")
  protected Note root;
  
  @Parameters(index = "3", description="The IntervalPattern to use to generate the chord or scale")
  protected IntervalPattern intervalPattern;
  
  @Option(names = {"-d", "--display"}, description="Display in monochrome or colour")
  protected Display display = Display.COLOUR;
  
  @Option(names = {"-l", "--labels"}, description="Show note ot interval labels")
  protected Labels labels = Labels.NOTES;

  public FrettlerCommand() {
    // TODO Auto-generated constructor stub
  }

  public enum View {
    HORIZONTAL, VERTICAL
  }
  public enum Type {
    SCALE, CHORD
  }
  public enum Display {
    COLOUR, MONO
  }
  public enum Labels {
    NOTES, INTERVALS
  }
}


