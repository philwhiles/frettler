package me.flotsam.frettler.engine;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public class ChordPattern {
  @Getter private String label;
  @Getter private List<ScaleInterval> intervals;

  public ChordPattern(String label, ScaleInterval ...intervals) {
    this.label = label;
    this.intervals = Arrays.asList(intervals);
  }
}
