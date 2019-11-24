package edu.pucmm.url;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(getHerokuPort());
    }

    static int getHerokuPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
