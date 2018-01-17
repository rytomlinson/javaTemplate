package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.SurveyDTO;
import com.navis.insightserver.dto.UserProfileDTO;
import com.navis.insightserver.service.ISurveysService;
import com.navis.insightserver.service.SecurityService;
import io.swagger.annotations.Api;
import org.javatuples.Unit;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
@Api(value = "Insight", description = "Operations pertaining to Insight Surveys")
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
        log.info("View a list of Insight Surveys for UserProfileDTO: " + (user != null ? user.getUserId() : ""));

        return new ResponseEntity<List<SurveyDTO>>(surveysService.getSurveys(propertyId, locale, includeDeleted), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/surveys", method = RequestMethod.POST)
    public ResponseEntity<Void> getSurveys(@PathVariable("propertyId") UUID owner
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , @Validated @RequestBody SurveyDTO surveyDTO
            , UriComponentsBuilder builder
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("Upsert a Insight Survey for UserProfileDTO: " +(user != null ? user.getUserId() : ""));

        Long surveyId = surveysService.upsertSurvey(owner, surveyDTO, locale);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("secure/properties/{propertyId}/surveys/{surveyId}").buildAndExpand(owner, surveyId).toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "properties/{propertyId}/surveys/{surveyId}", method = RequestMethod.GET)
    public ResponseEntity<SurveyDTO> getSurveyById(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("surveyId") Long surveyId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("Return a Insight Survey for UserProfileDTO: " + (user != null ? user.getUserId() : ""));

        return new ResponseEntity<SurveyDTO>(surveysService.getSurveyById(propertyId, locale, surveyId), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/surveys/{surveyId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSurveyById(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("surveyId") Long surveyId
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("Delete a Insight Survey for UserProfileDTO: " + (user != null ? user.getUserId() : ""));

        surveysService.deleteSurvey(propertyId, surveyId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/surveys/{surveyId}/publish/{status}", method = RequestMethod.POST)
    public ResponseEntity<Void> updateSurveyPublishStatus(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("surveyId") Long surveyId
            , @PathVariable("status") Boolean status
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("Update a Insight Survey publish status for UserProfileDTO: " + (user != null ? user.getUserId() : ""));

        surveysService.updateSurveyPublishStatus(propertyId, surveyId, status);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/surveys/{surveyId}/links/anonymous", method = RequestMethod.GET)
    public ResponseEntity<Unit<String>> getSurveyLinkPreview(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("surveyId") Long surveyId
            , @RequestParam(value = "source", required = false, defaultValue = "") String source
            , @RequestParam(value = "isDemoSurveyMode", required = false, defaultValue = "false") boolean isDemoSurveyMode
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("List a Insight Survey Link for UserProfileDTO: " + (user != null ? user.getUserId() : ""));

        String surveyMode = (isDemoSurveyMode) ? SecurityService.surveyModeDemo : SecurityService.surveyModeNormal;

        return new ResponseEntity<Unit<String>>(surveysService.generateAnonymousSurveyLink(propertyId, surveyId, source, surveyMode), HttpStatus.OK);
    }

}

