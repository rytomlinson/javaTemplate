package com.navis.insightserver.controller;

import com.navis.insightserver.dto.GuestStayDTO;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.service.ITagService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Created by darrell-shofstall on 11/28/17.
 */

@Controller
@RequestMapping("reports")
@Api(value = "Insight", description = "Operations pertaining to Insight Reporting")
public class ReportsController {
    private static final Logger log = LoggerFactory.getLogger(ReportsController.class);

    @Value("${reach.api.endpoint}")
    private String endpoint;
    @Autowired
    private ITagService tagService;

    @RequestMapping(value = "guestStay", method = RequestMethod.GET)
    public ResponseEntity<GuestStayDTO> getGuestStay(
            @RequestParam(value = "stayId") String stayId
            , @RequestParam(value = "accountNumber") String accountId
    ) {
        log.info("View Guest Stay information for a given stayId and accountId: ");

        URI uri = URI.create(UriComponentsBuilder.fromUriString(endpoint).pathSegment("GuestStay")
                .queryParam("accountNumber", accountId)
                .queryParam("stayId", stayId).toUriString());


        RestTemplate restTemplate = new RestTemplate();
        Object guestStay = restTemplate.getForObject(uri.toString(), Object.class);
        GuestStayDTO guestStayDTO = convertGuestStayToDto(guestStay);

        return new ResponseEntity<GuestStayDTO>(guestStayDTO, HttpStatus.OK);


    }

    private GuestStayDTO convertGuestStayToDto(Object guestStay) {
        GuestStayDTO guestStayDTO = new GuestStayDTO(guestStay);
        return guestStayDTO;
    }
}
