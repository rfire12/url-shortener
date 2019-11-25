package edu.pucmm.url;

import edu.pucmm.url.Controllers.UsersController;
import edu.pucmm.url.Services.BootstrapService;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        // Setting up the port
        port(getHerokuPort());

        staticFiles.location("/public");

        // Initializing the database
        BootstrapService.initDb();

        // Get routes
        UsersController.getRoutes();
    }

    static int getHerokuPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
