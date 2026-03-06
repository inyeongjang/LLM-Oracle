package org.imdea.fixcheck.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * AssertionGeneratorOptions class: handle the options for the assertion generator.
 * @author Facundo Molina
 */
public class AssertionGeneratorProperty {

  // Declare and initialize the static map
  private static final Map<String, String> Options = createMap();

  // Method to initialize the map with values
  private static Map<String, String> createMap() {
    Map<String, String> map = new HashMap<>();
    
    // GPT-4o & GPT-4o-mini
    map.put("gpt-baseline", "org.imdea.fixcheck.assertion.GPT_Baseline");
    map.put("gpt-h1",       "org.imdea.fixcheck.assertion.GPT_H1");
    map.put("gpt-h2a",      "org.imdea.fixcheck.assertion.GPT_H2a");
    map.put("gpt-h2b",      "org.imdea.fixcheck.assertion.GPT_H2b");
    map.put("gpt-h3a",      "org.imdea.fixcheck.assertion.GPT_H3a");
    map.put("gpt-h3b",      "org.imdea.fixcheck.assertion.GPT_H3b");
    map.put("gpt-h3c",      "org.imdea.fixcheck.assertion.GPT_H3c");
    map.put("gpt-h4",       "org.imdea.fixcheck.assertion.GPT_H4");
    map.put("gpt-h5",       "org.imdea.fixcheck.assertion.GPT_H5");
    map.put("gpt-h6",       "org.imdea.fixcheck.assertion.GPT_H6");

    // Llama 3.2 3B
    map.put("llama-baseline", "org.imdea.fixcheck.assertion.Llama_Baseline");
    map.put("llama-h1",       "org.imdea.fixcheck.assertion.Llama_H1");
    map.put("llama-h2a",      "org.imdea.fixcheck.assertion.Llama_H2a");
    map.put("llama-h2b",      "org.imdea.fixcheck.assertion.Llama_H2b");
    map.put("llama-h3a",      "org.imdea.fixcheck.assertion.Llama_H3a");
    map.put("llama-h3b",      "org.imdea.fixcheck.assertion.Llama_H3b");
    map.put("llama-h3c",      "org.imdea.fixcheck.assertion.Llama_H3c");
    map.put("llama-h4",       "org.imdea.fixcheck.assertion.Llama_H4");
    map.put("llama-h5",       "org.imdea.fixcheck.assertion.Llama_H5");
    map.put("llama-h6",       "org.imdea.fixcheck.assertion.Llama_H6");

    return map;
  }

  public static String parseOption(String option) {
    if (!Options.containsKey(option)) {
      System.out.println("Error: Invalid option for assertion generator: " + option);
      System.out.println("Valid options are: " + Options.keySet());
      System.exit(1);
    }
    return Options.get(option);
  }
}
