package edu.pucmm.url.Controllers;

import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Services.UrlServices;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static spark.Spark.*;

public class UrlsController {
    public static void getRoutes() {
        get("/", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("latest", UrlServices.getInstance().getLatest());
            obj.put("latestSize", UrlServices.getInstance().getLatest().size());
            return TemplatesController.renderFreemarker(obj, "main.ftl");
        });

        post("/shortify", (request, response) -> {
            String shortUrl = UUID.randomUUID().toString().split("-")[0];
            Url url = new Url(shortUrl, request.queryParams("url"), "", "", "", null);
            UrlServices.getInstance().create(url);
            response.redirect("/");
            return "";
        });
    }
}
