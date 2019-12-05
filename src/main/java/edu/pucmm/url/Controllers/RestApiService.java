package edu.pucmm.url.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.pucmm.url.Entities.Info;
import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.InfoServices;
import edu.pucmm.url.Services.UsersServices;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static spark.Spark.*;
public class RestApiService {
    public static void getRoutes() {
        get("/rest", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", user);
            return TemplatesController.renderFreemarker(obj, "rest.ftl");
        });

        post("/generate-jwt", (request, response) -> {
            User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
            String token = null;
            System.out.println(request.queryParams("jwt-date"));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Date expirationDate = df.parse(request.queryParams("jwt-date"));
            try {
                Algorithm algorithm = Algorithm.HMAC256("KJFhJHNnHn1dsd433dofmK");
                token = JWT.create()
                        .withIssuer("auth0")
                        .withClaim("user", user.getUid())
                        .withExpiresAt(expirationDate)
                        .sign(algorithm);
            } catch (JWTCreationException exception){
                token = null;
            }
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", user);
            obj.put("jwt", token);
            obj.put("jwt_exp", JWT.decode(token).getExpiresAt().toString());
            return TemplatesController.renderFreemarker(obj, "rest.ftl");
        });

        path("/rest-api/v1", () -> {
            get("/urls", (request, response) -> {
                String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTU3NTQzMjAwMCwidXNlciI6IjI0ODdmYjc2LTc1NzEtNDJlMS05MjJjLTY1MGZmMjk5MTI2MyJ9.baFofqrx9ZkH0SmgrRc6zMcWBxrpMyTJWAdulh6dC8M";
                Algorithm algorithm = Algorithm.HMAC256("KJFhJHNnHn1dsd433dofmK");
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("auth0")
                        .build();
                DecodedJWT jwt = verifier.verify(token);



                User user = UsersServices.getInstance().findByObject(((User) request.session().attribute("user")));
                List<Url> myUrls = user.getMyUrls();
                Map<String, String> url;
                List<Map> urls = new ArrayList<>();
                for(Url myUrl: myUrls){
                    url = new HashMap<>();
                    url.put("original_version", myUrl.getOriginalVersion());
                    url.put("short_version", request.scheme() + "://" + request.host() + "/s/" + myUrl.getShortVersion());
                    url.put("link_use_count", Integer.toString(InfoServices.getInstance().getInfoListByUrl(myUrl.getShortVersion()).size()));
                    urls.add(url);
                }
                Map<String, List> mapUrl = new HashMap<>();
                mapUrl.put("urls", urls);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

               return gson.toJson(mapUrl);
            });
        });
    }
}
