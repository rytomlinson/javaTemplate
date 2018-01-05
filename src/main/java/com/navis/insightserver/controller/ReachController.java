package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.ReachSurveysDTO;
import com.navis.insightserver.dto.SurveyDTO;
import com.navis.insightserver.dto.UserProfileDTO;
import com.navis.insightserver.service.ISurveysService;
import com.navis.insightserver.service.SecurityService;
import io.swagger.annotations.Api;
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
@RequestMapping("/")
@Api(value = "Insight", description = "Operations pertaining to REACH Insight innerop")
public class ReachController {
    private static final Logger log = LoggerFactory.getLogger(ReachController.class);

    @Autowired
    private ISurveysService surveysService;

    @RequestMapping(value = "surveys", method = RequestMethod.GET)
    public ResponseEntity<ReachSurveysDTO> getReachSurveys(@RequestHeader(value = "accountnumber") String accountId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        log.info("View a list of Reach Insight Surveys for AccountId: ", accountId);

        return new ResponseEntity<ReachSurveysDTO>(surveysService.getReachSurveys(accountId, locale, includeDeleted), HttpStatus.OK);
    }
}

