package edu.pucmm.url;

import edu.pucmm.url.Controllers.LoginController;
import edu.pucmm.url.Controllers.UsersController;
import edu.pucmm.url.Services.BootstrapService;
import edu.pucmm.url.Services.UsersServices;
import edu.pucmm.url.encapsulation.User;
import spark.Session;

import java.sql.SQLException;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Setting up the port
        port(getHerokuPort());

        staticFiles.location("/public");

        // Initializing the database
        BootstrapService.initDb();

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
    }

    static int getHerokuPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }


}
