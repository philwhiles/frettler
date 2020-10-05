package me.flotsam.frettler.engine;

import java.util.List;

public class IntervalPatternAnalyser {
  public static IntervalPatternMetadata analyse(List<ScaleInterval> intervals) {
    IntervalPatternMetadata meta = new IntervalPatternMetadata();
    for (ScaleInterval interval : intervals) {
      switch (interval) {
        case m2:
          meta.minorSecond = true;
          break;
        case M2:
          meta.majorSecond = true;
          break;
        case m3:
          meta.minorThird = true;
          break;
        case M3:
          meta.majorThird = true;
          break;
        case P4:
          meta.perfectFourth = true;
          break;
        case d5:
          meta.diminishedFifth = true;
          break;
        case P5:
          meta.perfectFifth = true;
          break;
        case m6:
          meta.minorSixth = true;
          break;
        case M6:
          meta.majorSixth = true;
          break;
        case m7:
          meta.minorSeventh = true;
          break;
        case M7:
          meta.majorSeventh = true;
          break;
        case M9:
          meta.majorNinth = true;
          break;
        case M11:
          meta.majorEleventh = true;
          break;
        default:
          break;
      }
    }
    meta.majorRange =
        (meta.majorThird || meta.perfectFifth) && !(meta.minorThird || meta.diminishedFifth);
    meta.minorRange = meta.minorThird && !meta.majorThird;
    meta.suspended =
        (!meta.majorThird && !meta.minorThird && (meta.perfectFourth || meta.majorSecond));

    return meta;
  }
}
