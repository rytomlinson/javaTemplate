package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.*;
import com.navis.insightserver.service.IAlertsService;
import com.navis.insightserver.service.ISurveysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/14/17.
 */

@Controller
@RequestMapping("secure")
@Api(value="Insight", description="Operations pertaining to Insight Alerts")
public class AlertsController {
    private static final Logger log = LoggerFactory.getLogger(AlertsController.class);

    @Autowired
    private ISecurity security;

    @Autowired
    private ISurveysService surveysService;

    @Autowired
    private IAlertsService alertsService;

    @RequestMapping(value = "properties/{propertyId}/surveys/{surveyId}/reportTypes/{reportTypeId}/recipients", method = RequestMethod.GET)
    public ResponseEntity<SurveyAlertDTO> getSurveys(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("surveyId") Long surveyId
            , @PathVariable("reportTypeId") Long reportTypeId
            , @RequestParam(value = "frequency", required = false, defaultValue = "HOURLY") String frequency
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Email Recipients for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<SurveyAlertDTO>(alertsService.getSurveyAlerts(propertyId, surveyId, reportTypeId, locale), HttpStatus.OK);


    }

    @RequestMapping(value = "reportTypes", method = RequestMethod.GET)
    public ResponseEntity<List<ReportTypeDTO>> getReportTypes( HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        log.info("View a list of Insight Report Types: ");

        return new ResponseEntity<List<ReportTypeDTO>>(alertsService.getReportTypes(), HttpStatus.OK);
    }

    @RequestMapping(value = "reportFrequencyTypes", method = RequestMethod.GET)
    public ResponseEntity<List<ReportFrequencyTypeDTO>> getReportFrequencyTypes(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        log.info("View a list of Insight Report Frequency Types: ");

        return new ResponseEntity<List<ReportFrequencyTypeDTO>>(alertsService.getReportFrequencyTypes(), HttpStatus.OK);
    }

    @RequestMapping(value = "surveyReportRecipients", method = RequestMethod.POST)
    @ApiOperation(value = "Add survey/reportType specific email recipient Setting")
    public ResponseEntity addSurveyReportRecipients(@RequestBody SurveyAlertDTO surveyAlertDTO) {
        log.debug("Add an account/user specific StatDTO Event Threshold Setting");

        alertsService.addSurveyReportRecipients(surveyAlertDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
