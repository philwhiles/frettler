package me.flotsam.frettler.view;

import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Scale;

public interface View {
  public default void initColourMap(Scale scale) {
    scale.getScaleNotes().forEach(sn->ColourMap.get(sn.getNote().getPitch()));
  }
  public default void initColourMap(Chord chord) {
    chord.getChordNotes().forEach(cn->ColourMap.get(cn.getNote().getPitch()));
  }
}
