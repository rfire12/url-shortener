package edu.pucmm.url.Controllers;

import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.UsersServices;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class UsersController {
    public static void getRoutes() {

        before("/users", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            if(user == null || user.isAdmin() == false){
                response.redirect("/", 302);
            }
        });

        get("/users", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            User user = UsersServices.getInstance().findByObject(request.session().attribute("user"));
            obj.put("user", user);
            obj.put("users", UsersServices.getInstance().findAll());
            return TemplatesController.renderFreemarker(obj, "users.ftl");
        });

        post("/:id/upgrade-user", (request, response) -> {
            User user = UsersServices.getInstance().find(request.params("id"));
            user.setAdmin(true);
            UsersServices.getInstance().update(user);
            response.redirect("/users", 302);
            return "";
        });

        post("/:id/downgrade-user", (request, response) -> {
            User user = UsersServices.getInstance().find(request.params("id"));
            user.setAdmin(false);
            UsersServices.getInstance().update(user);
            response.redirect("/users", 302);
            return "";
        });

        post("/:id/delete-user", (request, response) -> {
            UsersServices.getInstance().delete(request.params("id"));
            response.redirect("/users", 302);
            return "";
        });
    }
}
