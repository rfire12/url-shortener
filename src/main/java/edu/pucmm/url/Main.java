package edu.pucmm.url;

import edu.pucmm.url.Controllers.LoginController;
import edu.pucmm.url.Services.RestApiService;
import edu.pucmm.url.Controllers.UrlsController;
import edu.pucmm.url.Controllers.UsersController;
import edu.pucmm.url.Services.BootstrapService;
import edu.pucmm.url.Services.DatabaseService;
import edu.pucmm.url.Services.UsersServices;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Soap.SoapInit;
import spark.Session;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Setting up the port
        port(getHerokuPort());

        staticFiles.location("/public");

        SoapInit.init();

        // Initializing the database
        BootstrapService.startDb();
        BootstrapService.initDb();

        // Testing connection
        DatabaseService.getInstance().testConnection();

        before((request, response) -> {
            User user = request.session().attribute("user");
            if (request.cookie("USER") != null && user == null) { //If the user is not logged, try to get the cookie to set a session
                String userUID = request.cookie("USER");
                user = UsersServices.getInstance().find(userUID);
                Session session = request.session(true);
                session.attribute("user", user);
            }
        });

        // Get routes
        UsersController.getRoutes();
        LoginController.getRoutes();
        UrlsController.getRoutes();
        RestApiService.getRoutes();
    }

    static int getHerokuPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Shutting down...");
        super.finalize();
    }


}
