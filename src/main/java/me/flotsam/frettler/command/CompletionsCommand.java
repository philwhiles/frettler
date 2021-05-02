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

import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Pitch;
import me.flotsam.frettler.engine.Sequence;
import me.flotsam.frettler.instrument.Tuning;
import picocli.CommandLine.Command;

/**
 * Handles the initial BANJO command/param
 * 
 * @author philwhiles
 *
 */
@Command(name = "completions",
    description = "Generates bash tab completion script. Try 'source <(./frettler completions)'")
public class CompletionsCommand implements Runnable {


  @Override
  public void run() {
    StringBuilder sb = new StringBuilder();
    sb.append("#/usr/bin/env bash\n");
    sb.append("_frettler_completions()\n");
    sb.append("{\n");
    sb.append("    local instr_opts\n");
    sb.append("    instr_opts=\"tunings menu lookup patterns fifths custom guitar bassguitar ukelele mandolin banjo\"\n");

    sb.append("    local view_opts\n");
    sb.append("    view_opts=\"horizontal vertical chord progression find display box tab find\"\n");

    sb.append("    local note_opts\n");
    sb.append("    note_opts=\"");
    for (Pitch note : Pitch.values()) {
      sb.append(note.name()).append(" ");
    }
    sb.replace(sb.length() - 1, sb.length(), "");
    sb.append("\"\n");

    sb.append("    local pattern_opts\n");
    sb.append("    pattern_opts=\"");
    for (IntervalPattern pattern : IntervalPattern.values()) {
      if (pattern != IntervalPattern.SCALE_CHROMATIC) {
        sb.append(pattern.name().toLowerCase()).append(" ");
      }
    }
    sb.replace(sb.length() - 1, sb.length(), "");
    sb.append("\"\n");

    //@formatter:off
    sb.append("    case $COMP_CWORD in\n");
    sb.append("        1)\n");
    sb.append( "            COMPREPLY=( $(compgen -W \"${instr_opts}\" -- \"${COMP_WORDS[COMP_CWORD]}\") )\n");
    sb.append("            ;;\n");
    sb.append("        2)\n");
    sb.append("            if [ ${COMP_WORDS[1]} == \"chord\" ]; then\n");
    sb.append("                COMPREPLY=( $(compgen -W \"${note_opts}\" -- \"${COMP_WORDS[COMP_CWORD]}\") )\n");
    sb.append("              elif [ ${COMP_WORDS[1]} == \"patterns\" ]; then\n");
    sb.append("                return 0\n");
    sb.append("              elif [ ${COMP_WORDS[1]} == \"fifths\" ]; then\n");
    sb.append("                return 0\n");
    sb.append("              elif [ ${COMP_WORDS[1]} == \"lookup\" ]; then\n");
    sb.append("                return 0\n");
    sb.append("              else\n");
    sb.append("                COMPREPLY=( $(compgen -W \"${view_opts}\" -- \"${COMP_WORDS[COMP_CWORD]}\") )\n");
    sb.append("              fi\n");
    sb.append("            ;;\n");
    sb.append("        3)\n");
    sb.append( "            COMPREPLY=( $(compgen -W \"${note_opts}\" -- \"${COMP_WORDS[COMP_CWORD]}\") )\n");
    sb.append("            ;;\n");
    sb.append("        4)\n");
    sb.append( "            COMPREPLY=( $(compgen -W \"${pattern_opts}\" -- \"${COMP_WORDS[COMP_CWORD]}\") )\n");
    sb.append("            ;;\n");
    sb.append("    esac\n");
    sb.append("    return 0\n");

    sb.append("}\n");
    sb.append("complete -F _frettler_completions frettler\n");
    //@formatter:on
    System.out.println(sb);
  }
}
