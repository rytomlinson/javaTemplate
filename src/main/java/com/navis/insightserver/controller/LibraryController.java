package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.*;
import com.navis.insightserver.service.IQuestionService;
import com.navis.insightserver.service.ISelectionListService;
import com.navis.insightserver.service.ITagService;
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
@RequestMapping("secure")
@Api(value = "Insight", description = "Operations pertaining to Insight Library")
public class LibraryController {
    private static final Logger log = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private ISecurity security;

    @Autowired
    private IQuestionService questionService;

    @RequestMapping(value = "properties/{propertyId}/questions", method = RequestMethod.GET)
    public ResponseEntity<List<QuestionDTO>> getQuestions(
            @PathVariable("propertyId") UUID propertyId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Questions for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<QuestionDTO>>(questionService.getQuestions(propertyId, locale), HttpStatus.OK);
    }


}
