package com.navis.insightserver.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


/**
 * Created by chris dagostino on 9/14/17.
 */

@Controller
@RequestMapping("secure")
@Api(value="Insight", description="Operations pertaining retrieving the Insight client manifest from the CDN")
public class ManifestController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Value("${insightclient.cdn}")
    private
    String sourcePath;

    @RequestMapping(value = "/manifest", method = RequestMethod.GET)
    public ResponseEntity<String> manifest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                sourcePath + "manifest.json",
                String.class);
        return response;
    }
}
