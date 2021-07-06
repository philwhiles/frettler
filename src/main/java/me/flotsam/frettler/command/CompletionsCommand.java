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

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.instrument.Tuning;
import picocli.CommandLine.Command;

/**
 * Uses Freemarker to parse the template in src/main/resources/templates and generate the bash
 * completions script for frettler, sent to console. Picocli provides generation of completions, but
 * it has a problem with positional args and basically does not work, hence this solution
 * 
 * @author philwhiles
 *
 */
@Command(name = "completions",
    description = "Generates bash tab completion script. Try 'source <(./frettler completions)'")
public class CompletionsCommand implements Runnable {


  @Override
  public void run() {
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
    try {

      cfg.setClassForTemplateLoading(CompletionsCommand.class, "/templates");

      cfg.setIncompatibleImprovements(new Version(2, 3, 20));
      cfg.setDefaultEncoding("UTF-8");
      cfg.setLocale(Locale.US);
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      cfg.setLogTemplateExceptions(false);
      cfg.setWrapUncheckedExceptions(true);
      cfg.setFallbackOnNullLoopVariable(false);

      Map<String, Object> input = new HashMap<String, Object>();
      input.put("noteOpts", Arrays.asList(Note.values()).stream().map(n -> n.toString())
          .collect(Collectors.joining(" ")));
      input.put("patternOpts",
          Arrays.asList(IntervalPattern.values()).stream()
              .filter(ip -> ip != IntervalPattern.SCALE_CHROMATIC)
              .map(ip -> ip.toString().toLowerCase()).collect(Collectors.joining(" ")));
      input.put("tuningOpts", Arrays.asList(Tuning.values()).stream()
          .map(ip -> ip.toString().toLowerCase()).collect(Collectors.joining(" ")));

      Template template = cfg.getTemplate("frettler_completions.ftl");

      Writer out = new OutputStreamWriter(System.out);
      template.process(input, out);
    } catch (Exception e) {
      System.err.println("Failed to process completions " + e);
      System.exit(-1);
    }
  }
}
