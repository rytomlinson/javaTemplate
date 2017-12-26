package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.AccountDTO;
import com.navis.insightserver.dto.ProductDTO;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.dto.UserProfileDTO;
import com.navis.insightserver.service.ITagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 9/14/17.
 */

@Controller
@RequestMapping("secure")
@Api(value="Insight", description="Operations pertaining to Insight Settings")
public class SettingsController {
    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private ISecurity security;

    @Autowired
    private ITagService tagService;

    @RequestMapping(value = "properties/{propertyId}/tags", method = RequestMethod.GET)
    public ResponseEntity<List<TagDTO>> getTags(
            @PathVariable("propertyId") UUID propertyId,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Tags for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<TagDTO>>(tagService.getTags(propertyId), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/departmentTags", method = RequestMethod.GET)
    public ResponseEntity<List<TagDTO>> getDepartmentTags(
            @PathVariable("propertyId") UUID propertyId,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Department Tags for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<TagDTO>>(tagService.getDepartmentTags(propertyId), HttpStatus.OK);
    }

    @RequestMapping(value = "surveyTypeTags", method = RequestMethod.GET)
    public ResponseEntity<List<TagDTO>> getSurveyTypeTags(
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Survey Type Tags for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<TagDTO>>(tagService.getSurveyTypeTags(), HttpStatus.OK);
    }
}
