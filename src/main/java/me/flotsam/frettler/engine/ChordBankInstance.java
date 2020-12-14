package me.flotsam.frettler.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.flotsam.frettler.engine.ChordBank.ChordDefinition;

@AllArgsConstructor
public class ChordBankInstance {
  @Getter private Chord chord;
  @Getter private ChordDefinition chordDefinition;
}
