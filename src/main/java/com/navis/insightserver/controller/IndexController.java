package com.navis.insightserver.controller;

/**
 * Created by ChrisDAgostino on 9/18/17.
 */

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.UserProfileDTO;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    ISecurity security;

    @Value("${openid.endsessionuri}")
    private
    String endSessionUri;

    @Value("${openid.logoutredirecturi}")
    private
    String logoutRedirectUri;

    @Value("${insightclient.sourcepath}")
    private
    String sourcePath;

    @RequestMapping("/")
    public String root(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, Model model) throws HttpAction {
        return "redirect:/secure/";
    }

    @RequestMapping("/secure")
    public String secure(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, Model model) throws HttpAction {
        return "redirect:/secure/";
    }

    @RequestMapping("/secure/")
    public String Index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, Model model) throws HttpAction {
        final WebContext context = new J2EContext(request, response);
        model.addAttribute("sourcePath", sourcePath);
        return "index";
    }

    @RequestMapping(value = "/secure/responseAlerts/{surveyId}/{reportTypeId}")
    public String StatsWithId(@PathVariable("surveyId") String surveyId, @PathVariable("reportTypeId") String reportTypeId, Model model) throws HttpAction {
        model.addAttribute("sourcePath", sourcePath);
        return "index";
    }

    @RequestMapping("/loggedOut/")
    protected String loggedOut(Model model) {
        model.addAttribute("sourcePath", sourcePath);
        return "index";
    }

    public String BuildEndSessionRedirectUri(WebContext context) {

        UserProfileDTO userProfileDTO = security.GetUserProfile(context);

        UriComponentsBuilder ub = UriComponentsBuilder.fromHttpUrl(endSessionUri);
        ub.queryParam("post_logout_redirect_uri", logoutRedirectUri);
        ub.queryParam("id_token_hint", userProfileDTO.getToken());
        ub.queryParam("state", userProfileDTO.getState());

        return ub.toUriString();


    }

    @RequestMapping("/logout.html")
    public RedirectView Logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);

        String url = BuildEndSessionRedirectUri(context);

        request.getSession().invalidate();
        return new RedirectView(url);
    }


}

