package me.flotsam.frettler.view.console;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColourMap {

  private Map<Object, Colour> objColourMap = new HashMap<>();
  private List<Colour> colourList = Arrays.asList(
      Colour.DARK_ORANGE,
      Colour.GREEN3,
      Colour.SKY_BLUE1,
      Colour.LIGHT_STEEL_BLUE1,
      Colour.RED1,
      Colour.HOT_PINK,
      Colour.GOLD1,
      Colour.SKY_BLUE2,
      Colour.YELLOW2,
      Colour.THISTLE,
      Colour.YELLOW3,
      Colour.MAGENTA2
 ) ;

  private int colourNum = -1;

  public Colour get(Object obj) {
    Colour colour = objColourMap.get(obj);
    if (colour == null) {
      if (++colourNum == colourList.size()) {
        colourNum = 0;
      }
      colour = colourList.get(colourNum);
      objColourMap.put(obj, colour);
    }
    return colour;
  }

}
