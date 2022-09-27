package com.axreng.backend.environmentVariables;

/**
 * @author Matheus Longo
 *
 */

public interface Essential {

    final String BASE_URL = System.getenv("BASE_URL");
    final String KEYWORD = System.getenv("KEYWORD");
    final String MAX_RESULTS = System.getenv("MAX_RESULTS");

    /*
    final String BASE_URL = "http://hiring.axreng.com/";
    final String KEYWORD = "four";
    final String MAX_RESULTS = System.getenv("MAX_RESULTS");

     */
}
