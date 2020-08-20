package me.flotsam.frettler.engine;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This started as something far more complex. Far too complex! I tried to extend the circular Scale class for a circle of fifths.
 * And it was ... complex. Sitting back, reading more about the whole COF theory and looking at what the engine could already do for me, and also
 * at what I needed from the COF, I came to the conclusion that a LINE of fifths was all that was needed. 
 * @author philwhiles
 */
public class LineOfFifths {

  static LinkedHashMap<ScaleNote, ScaleTuple> majorLine = new LinkedHashMap<>();
  static LinkedHashMap<ScaleNote, ScaleTuple> minorLine = new LinkedHashMap<>();
  {
    Scale chromatic = Scale.CHROMATIC_SCALE;
    ScaleNote c = chromatic.findScaleNote(Pitch.C).get();
    for (int i = -7; i <= 7; i++) {
      ScaleNote majorNote = Scale.getScaleNote(c, i * 7);
      ScaleNote minorNote = Scale.getScaleNote(c, i * 7 + 21);

      Scale majorScale = new Scale(majorNote.getPitch(), IntervalPattern.SCALE_MAJOR);
      Scale minorScale = new Scale(minorNote.getPitch(), IntervalPattern.SCALE_MINOR);


      majorLine.put(majorNote, new ScaleTuple(majorScale, minorScale));
      minorLine.put(minorNote, new ScaleTuple(majorScale, minorScale));
    }
  }

  public static void main(String[] args) {
    LineOfFifths lof = new LineOfFifths();
    lof.foo();
  }

  public void foo() {
    for (Map.Entry<ScaleNote, ScaleTuple> entry : majorLine.entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue().getMajor() + " and " + entry.getValue().getMinor());
    }
  }

}


@AllArgsConstructor
class ScaleTuple {
  @Getter
  private Scale major;
  @Getter
  private Scale minor;
}
