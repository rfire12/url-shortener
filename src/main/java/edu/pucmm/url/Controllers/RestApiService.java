package edu.pucmm.url.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.NullClaim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.reflect.TypeToken;
import edu.pucmm.url.Entities.Info;
import edu.pucmm.url.Entities.Url;
import edu.pucmm.url.Entities.User;
import edu.pucmm.url.Services.InfoServices;
import edu.pucmm.url.Services.UsersServices;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
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
                        .withClaim("user", user.getUsername())
                        .withExpiresAt(expirationDate)
                        .sign(algorithm);
            } catch (JWTCreationException exception){
                token = null;
            }
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", user);
            obj.put("jwt", token);
            obj.put("jwt_exp", JWT.decode(token).getExpiresAt().toString());
            obj.put("protocol", request.scheme());
            obj.put("host", request.host());
            return TemplatesController.renderFreemarker(obj, "rest.ftl");
        });

        path("/rest-api/v1", () -> {
            get("/urls", (request, response) -> {
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
                Map<String,String> data = gson.fromJson(request.body(), stringStringMap);
                String token;
                try {
                    token = data.get("access-key");
                }catch(Exception e){
                    return "{\"data\": \"Invalid token\"}";
                }
                DecodedJWT jwt;
                try {
                    Algorithm algorithm = Algorithm.HMAC256("KJFhJHNnHn1dsd433dofmK");
                    JWTVerifier verifier = JWT.require(algorithm)
                            .build();
                    jwt = verifier.verify(token);
                } catch (JWTVerificationException exception){
                    return "{\"data\": \"Invalid token\"}";
                }


                User user = UsersServices.getInstance().findByUsername(jwt.getClaim("user").asString());
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
                gson = new GsonBuilder().setPrettyPrinting().create();

               return gson.toJson(mapUrl);
            });
        });
    }
}
