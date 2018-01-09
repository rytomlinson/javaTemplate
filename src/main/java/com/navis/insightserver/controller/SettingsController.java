package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.*;
import com.navis.insightserver.service.ISelectionListService;
import com.navis.insightserver.service.ITagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@Api(value = "Insight", description = "Operations pertaining to Insight Settings")
public class SettingsController {
    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private ISecurity security;

    @Autowired
    private ITagService tagService;

    @Autowired
    private ISelectionListService selectionListService;

    @RequestMapping(value = "properties/{propertyId}/tags", method = RequestMethod.GET)
    public ResponseEntity<List<TagDTO>> getTags(
            @PathVariable("propertyId") UUID propertyId,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Tags for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<TagDTO>>(tagService.getTags(propertyId), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/tags", method = RequestMethod.POST)
    public ResponseEntity<Void> upsertTag(@PathVariable("propertyId") UUID owner
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , @Validated @RequestBody TagDTO tagDTO
            , UriComponentsBuilder builder
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("Upsert a Insight Tag for UserProfileDTO: " + user.getUserId());

        Long tagId = tagService.upsertTag(owner, tagDTO, locale);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("secure/properties/{propertyId}/tags/{id}").buildAndExpand(owner, tagId).toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "properties/{propertyId}/tags/{id}", method = RequestMethod.GET)
    public ResponseEntity<TagDTO> getTag(
            @PathVariable("propertyId") UUID propertyId,
            @PathVariable("id") Long id,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a Insight Tag for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<TagDTO>(tagService.getTag(propertyId, id), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/tags/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTag(
            @PathVariable("propertyId") UUID propertyId,
            @PathVariable("id") Long id,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a Insight Tag for UserProfileDTO: " + user.getUserId());

        tagService.deleteTag(propertyId, id);
        return new ResponseEntity<Void>(HttpStatus.OK);
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

    @RequestMapping(value = "properties/{propertyId}/selectionLists", method = RequestMethod.GET)
    public ResponseEntity<List<SelectionListDTO>> getSelectionLists(
            @PathVariable("propertyId") UUID propertyId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Selection Lists for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<SelectionListDTO>>(selectionListService.getSelectionLists(propertyId, locale), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/selectionLists", method = RequestMethod.POST)
    public ResponseEntity<Void> upsertSelectionList(
            @PathVariable("propertyId") UUID propertyId
            , @Validated @RequestBody SelectionListDTO selectionListDTO
            , UriComponentsBuilder builder
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("Upsert a Insight Selection List for UserProfileDTO: " + user.getUserId());

        Long selectionListId = selectionListService.upsertSelectionList(propertyId, selectionListDTO, locale);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("secure/properties/{propertyId}/surveys/{surveyId}").buildAndExpand(propertyId, selectionListId).toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "properties/{propertyId}/selectionLists/{selectionListId}", method = RequestMethod.GET)
    public ResponseEntity<SelectionListDTO> getSelectionList(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("selectionListId") Long selectionListId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a Insight Selection List for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<SelectionListDTO>(selectionListService.getSelectionList(propertyId, selectionListId, locale), HttpStatus.OK);
    }
    @RequestMapping(value = "properties/{propertyId}/selectionLists/{selectionListId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSelectionList(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("selectionListId") Long selectionListId
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a Insight Selection List for UserProfileDTO: " + user.getUserId());

        selectionListService.deleteSelectionList(propertyId, selectionListId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/selectionLists/{selectionListId}/items", method = RequestMethod.GET)
    public ResponseEntity<List<SelectionDTO>> getSelectionListItems(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("selectionListId") Long selectionListId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a Insight Selection List Items for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<SelectionDTO>>(selectionListService.getSelectionListItems(propertyId, selectionListId, locale), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/selectionLists/{selectionListId}/items/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<SelectionDTO> getSelectionListItem(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("selectionListId") Long selectionListId
            , @PathVariable("itemId") Long itemId
            , @RequestParam(value = "locale", required = false, defaultValue = "en-US") String locale
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a Insight Selection List Item for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<SelectionDTO>(selectionListService.getSelectionListItem(propertyId, selectionListId, itemId, locale), HttpStatus.OK);
    }

    @RequestMapping(value = "properties/{propertyId}/selectionLists/{selectionListId}/items/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSelectionListItem(
            @PathVariable("propertyId") UUID propertyId
            , @PathVariable("selectionListId") Long selectionListId
            , @PathVariable("itemId") Long itemId
            , HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("Delete a Insight Selection List Item for UserProfileDTO: " + user.getUserId());

        selectionListService.deleteSelectionListItem(propertyId, selectionListId, itemId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
