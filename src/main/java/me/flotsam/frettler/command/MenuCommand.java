/*
 * Copyright (C) 2020 Philip Whiles
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package me.flotsam.frettler.command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jline.terminal.TerminalBuilder;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Pitch;
import me.flotsam.frettler.instrument.Banjo;
import me.flotsam.frettler.instrument.BassGuitar;
import me.flotsam.frettler.instrument.CustomInstrument;
import me.flotsam.frettler.instrument.Guitar;
import me.flotsam.frettler.instrument.Mandolin;
import me.flotsam.frettler.instrument.Ukelele;
import me.flotsam.frettler.view.Colour;
import picocli.CommandLine.Command;

/**
 * Handles the initial GUITAR command/param
 * 
 * @author philwhiles
 *
 */
@Command(name = "menu", description = "Enter menu mode")
public class MenuCommand extends FrettedInstrumentCommand implements Runnable {

  private static String[] instrumentArgs =
      new String[] {"CUSTOM", "GUITAR", "BASSGUITAR", "UKELELE", "MANDOLIN", "BANJO"};
  private static String[] viewArgs = new String[] {"HORIZONTAL", "VERTICAL", "CHORD"};
  private static String[] pitchArgs = Arrays.stream(Pitch.values()).map(ip -> ip.name())
      .collect(Collectors.toList()).toArray(new String[] {});

  private static String[] allPatternArgs =
      Arrays.stream(IntervalPattern.values()).filter(ip -> ip != IntervalPattern.SCALE_CHROMATIC)
          .map(ip -> ip.name()).sorted().collect(Collectors.toList()).toArray(new String[] {});

  private static String[] chordPatternArgs =
      Arrays.stream(IntervalPattern.values()).filter(ip -> ip != IntervalPattern.SCALE_CHROMATIC)
          .map(ip -> ip.name()).sorted().collect(Collectors.toList()).toArray(new String[] {});

  private static String[] patternArgs = allPatternArgs;

  private static final int KEY_ESC = 0x1B;
  private static final int KEY_TAB = 0x09;
  private static final int KEY_OPEN_BRACKET = 0x5B;
  private static final int KEY_UP = 0x41;
  private static final int KEY_DOWN = 0x42;
  private static final int KEY_LEFT = 0x44;
  private static final int KEY_RIGHT = 0x43;
  private static final int KEY_C = 0x63;
  private static final int KEY_I = 0x69;
  private static final int KEY_Q = 0x71;
  private static final int KEY_V = 0x76;
  private static final int KEY_ENTER = 0x0D;

  private String[][] args = {instrumentArgs, viewArgs, pitchArgs, patternArgs};

  @Override
  public void run() {
    oneMode = true;
    boolean exit = false;
    System.out.println("In menu mode:");
    System.out.println(" - arrow keys move between fields");
    System.out.println(" - enter displays that combo");
    System.out.println(" - tab jumps within the current field");
    System.out.println("");
    System.out.println(" - Toggle options:");
    System.out.println(" - (i)ntervals");
    System.out.println(" - (c)hords");
    System.out.println(" - (v)erbose");
    System.out.println("");
    System.out.println(" - and (q)uit");
    System.out.println("");
    try {
      var terminal = TerminalBuilder.terminal();
      terminal.enterRawMode();
      var reader = terminal.reader();

      int argN = 0;
      int[] argV = new int[args.length];

      boolean enterDisabled = false;
      do {
        printMenu(argN, argV);
        int key = reader.read();

        String instrument = args[0][argV[0]];
        view = View.valueOf(args[1][argV[1]]);
        root = Note.forPitch(Pitch.valueOf(args[2][argV[2]]));
        intervalPattern = IntervalPattern.valueOf(args[3][argV[3]]);
        enterDisabled = view == View.CHORD
            && intervalPattern.getPatternType() != IntervalPattern.PatternType.CHORD ? true
                : false;


        switch (key) {
          case KEY_ESC:
            int key2 = reader.read();
            if (key2 == KEY_OPEN_BRACKET) {
              key2 = reader.read();
              switch (key2) {
                case KEY_UP:
                  argV[argN] = argV[argN] < args[argN].length - 1 ? argV[argN] + 1 : 0;
                  break;
                case KEY_DOWN:
                  argV[argN] = argV[argN] > 0 ? argV[argN] - 1 : args[argN].length - 1;
                  break;
                case KEY_LEFT:
                  argN = argN > 0 ? argN - 1 : argN;
                  break;
                case KEY_RIGHT:
                  argN = argN < args.length - 1 ? argN + 1 : argN;
                  break;
              }
            }
            break;
          case KEY_TAB:
            String current = args[argN][argV[argN]];
            do {
              argV[argN] = argV[argN] < args[argN].length - 1 ? argV[argN] + 1 : 0;
              String next = args[argN][argV[argN]];
              if (next.charAt(0) != current.charAt(0)) {
                break;
              }
            } while (true);
            break;
          case KEY_C:
            chordMode = !chordMode;
            display(instrument);
            break;
          case KEY_I:
            intervals = !intervals;
            display(instrument);
            break;
          case KEY_Q:
            System.out.println();
            System.out.println();
            exit = true;
            break;
          case KEY_V:
            verbose = !verbose;
            display(instrument);
            break;
          case KEY_ENTER:
            if (!enterDisabled) {
              System.out.print("\r");
              System.out.println(StringUtils.repeat(' ', 70));
              display(instrument);
            }
            break;

          default:
            break;
        }
      } while (!exit);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void display(String instrument) {
    switch (instrument) {
      case "GUITAR":
        exec(new Guitar(strings, frets));
        break;
      case "BASSGUITAR":
        exec(new BassGuitar(strings, frets));
        break;
      case "BANJO":
        exec(new Banjo(strings, frets));
        break;
      case "MANDOLIN":
        exec(new Mandolin(strings, frets));
        break;
      case "UKELELE":
        exec(new Ukelele(strings, frets));
        break;
      case "CUSTOM":
        exec(new CustomInstrument());
        break;
    }
  }

  private void printMenu(int argN, int[] argV) {
    System.out.print("\r");
    for (int i = 0; i < argV.length; i++) {
      System.out.print("" + (isMono() ? "" : (argN == i ? Colour.DARK_ORANGE : Colour.RESET))
          + args[i][argV[i]] + " " + (isMono() ? "" : Colour.RESET));
    }
    System.out.print("(intervals:" + (intervals ? (isMono() ? "" : Colour.GREEN) + "on" : "off")
        + (isMono() ? "" : Colour.RESET));
    System.out.print(" chords:" + (chordMode ? (isMono() ? "" : Colour.GREEN) + "on" : "off")
        + (isMono() ? "" : Colour.RESET));
    System.out.print(" verbose:" + (verbose ? (isMono() ? "" : Colour.GREEN) + "on" : "off")
        + (isMono() ? "" : Colour.RESET) + ")");

    System.out.print("                                  ");
    System.out.print("\r");
    for (int i = 0; i < argN; i++) {
      System.out.print("" + args[i][argV[i]] + " ");
    }
  }
}
