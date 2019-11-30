package edu.pucmm.url.Controllers;

import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.UrlServices;
import edu.pucmm.url.Services.UsersServices;

import java.util.*;

import static spark.Spark.*;

public class UrlsController {
    public static void getRoutes() {
        get("/", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            String annonymousUser = request.cookie("ANONYMOUSUSER");
            List<Url> urls = user != null ? user.getMyUrls() : UrlServices.getInstance().getMyAnnonymousUrl(annonymousUser);

            Map<String, Object> obj = new HashMap<>();
            obj.put("latest", urls);
            obj.put("latestSize", urls.size());
            obj.put("user", user);
            obj.put("host", request.url());
            return TemplatesController.renderFreemarker(obj, "main.ftl");
        });

        get("/info/:id", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            Url url = UrlServices.getInstance().find(request.params("id"));
            Map<String, Object> obj = new HashMap<>();

            obj.put("user", user);
            return TemplatesController.renderFreemarker(obj, "url-info.ftl");
        });

        get("/s/:id", (request, response) -> {
            Url url = UrlServices.getInstance().find(request.params("id"));
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));

            response.redirect(url.getOriginalVersion());
            return "";
        });

        post("/shortify", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            String shortUrl = UUID.randomUUID().toString().split("-")[0];
            String annonymousUser = "";
            String newCookie = UUID.randomUUID().toString();
            String userCookie = request.cookie("ANONYMOUSUSER");

            if (userCookie == null && user == null) { //If it's a new user and not a registered one
                annonymousUser = newCookie;
            } else if (userCookie != null && user == null) {
                annonymousUser = userCookie;
            }


            Url url = new Url(shortUrl, request.queryParams("url"), "", user, annonymousUser);
            UrlServices.getInstance().create(url);

            if (user != null) {
                List<Url> myUrls = user.getMyUrls();
                myUrls.add(url);
                user.setMyUrls(myUrls);
                UsersServices.getInstance().update(user);
            } else if (userCookie == null) {
                response.cookie("ANONYMOUSUSER", newCookie, 604800);
            }
            response.redirect("/");
            return "";
        });

        get("/delete-url", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            obj.put("user", user);
            return TemplatesController.renderFreemarker(obj, "delete-url.ftl");
        });

        post("/delete-url", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            obj.put("user", user);
            if (UrlServices.getInstance().find(request.queryParams("url")) != null) {
                UrlServices.getInstance().delete(request.queryParams("url"));
                obj.put("type", "success");
                obj.put("alert", "URL deleted!");
            } else {
                obj.put("type", "danger");
                obj.put("alert", "URL not found");
            }

            return TemplatesController.renderFreemarker(obj, "delete-url.ftl");
        });
    }
}
