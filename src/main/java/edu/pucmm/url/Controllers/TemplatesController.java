package edu.pucmm.url.Controllers;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.Map;

public class TemplatesController {
    public static String renderFreemarker(Map<String, Object> model, String templatePath) {
        return new FreeMarkerEngine().render(new ModelAndView(model, templatePath));
    }
}
