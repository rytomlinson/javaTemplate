package com.navis.insightserver.controller;

/**
 * Created by ChrisDAgostino on 9/18/17.
 */


import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;
import java.util.Map;


@Controller
public class takerController {

    @Value("${insightclient.sourcepath}")
    private String sourcePath;

    @RequestMapping("/survey.html")
    public String TakeSurvey(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, Model model) throws HTTPException {
        final WebContext context = new J2EContext(request, response);
        model.addAttribute("sourcePath", sourcePath);
        return "taker";
    }


}

