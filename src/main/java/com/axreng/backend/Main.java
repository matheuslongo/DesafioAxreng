package com.axreng.backend;

import com.axreng.backend.crawling.Crawling;
import com.axreng.backend.environmentVariables.Essential;
import static spark.Spark.*;

import java.io.IOException;

/**
 * @author Matheus Longo
 *
 */

public class Main implements Essential {

    public static void main(String[] args) throws IOException {
        Crawling crawlingProcessor = new Crawling(BASE_URL, KEYWORD, MAX_RESULTS);
        System.out.println("INICIADO A PESQUINA NA URI: " + BASE_URL + " COM A PALAVRA-CHAVE: '" + KEYWORD + "'" );
        crawlingProcessor.executeCrawling();


    }

}

