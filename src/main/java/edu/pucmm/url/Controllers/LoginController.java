package edu.pucmm.url.Controllers;

import edu.pucmm.url.Services.UsersServices;
import edu.pucmm.url.Entities.User;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static spark.Spark.*;

public class LoginController {
    public static void getRoutes() {

        before("/login", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            if (user != null) {
                response.redirect("/");
            }
        });

        before("/create-user", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));

            if (user == null || !user.isAdmin()) {
                response.redirect("/");
            }

        });

        get("/login", (request, response) -> {
            return TemplatesController.renderFreemarker(null, "login.ftl");
        });

        post("/login", (request, response) -> {
            request.queryParams("username");
            User user = UsersServices.getInstance().validateCredentials(request.queryParams("username"), request.queryParams("password"));
            Boolean rememberMe = false;

            if (request.queryParams("remember-me") != null) {
                rememberMe = true;
            }

            if (user != null) {
                Session session = request.session(true);
                session.attribute("user", user);
                if (rememberMe) {
                    response.cookie("USER", user.getUid(), 604800);
                }

                response.redirect("/");

            } else {
                response.redirect("/login");
            }
            return "";
        });

        get("/create-user", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", request.session().attribute("user"));
            return TemplatesController.renderFreemarker(obj, "new-user.ftl");
        });

        post("/create-user", (request, response) -> {
//            User user = new User(UUID.randomUUID().toString(), request.queryParams("username"), request.queryParams("name"), request.queryParams("password"), request.queryParams("role"));
//            Boolean result = UsersServices.getInstance().create(user);
            boolean result = false;
            if (result) {
                response.redirect("/");
            } else {
                response.redirect("/create-user");
            }

            return "";
        });

        get("/logout", (request, response) -> {
            request.session().removeAttribute("user");
            response.removeCookie("USER");
            response.redirect("/login");
            return "";
        });


    }
}

