package me.flotsam.fretller.engine;

import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.ChordBank;
import me.flotsam.frettler.engine.ChordBank.ChordDefinition;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.ScaleInterval;
import me.flotsam.frettler.instrument.FrettedInstrument;
import me.flotsam.frettler.instrument.FrettedInstrument.InstrumentDefinition;
import me.flotsam.frettler.instrument.Guitar;

public class IntervalPatternTest {

  @Test
  public void ensureUniquePatterns() {
    List<List<ScaleInterval>> intervals = new ArrayList<>();
    
    for (IntervalPattern ip :  IntervalPattern.values()) {
      if (ip.getParentPattern() != null || ip.getAliasPattern() != null) {
        continue;
      }
      if (intervals.contains(ip.getIntervals())) {
        fail("Duplication in interval pattern " + ip.name());
      } else {
        intervals.add(ip.getIntervals());
      }
    }
  }
}