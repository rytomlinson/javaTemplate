package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.SurveyDTO;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.dto.UserProfileDTO;
import com.navis.insightserver.service.ISurveysService;
import io.swagger.annotations.Api;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/14/17.
 */

@Controller
@RequestMapping("secure")
@Api(value="Insight", description="Operations pertaining to Insight Surveys")
public class SurveysController {
    private static final Logger log = LoggerFactory.getLogger(SurveysController.class);

    @Autowired
    private ISecurity security;

    @Autowired
    private ISurveysService surveysService;

    @RequestMapping(value = "properties/{propertyId}/surveys", method = RequestMethod.GET)
    public ResponseEntity<List<SurveyDTO>> getSurveys(@PathVariable("propertyId") UUID propertyId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Surveys for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<SurveyDTO>>(surveysService.getSurveys(propertyId, locale, includeDeleted), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/surveys/{surveyId}", method = RequestMethod.GET)
    public ResponseEntity<SurveyDTO> getSurveyById(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("surveyId") Long surveyId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("Return a Insight Survey for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<SurveyDTO>(surveysService.getSurveyById(propertyId, locale, surveyId), HttpStatus.OK);
    }
}
