package com.company.productname.controller;

import com.company.productname.dto.LeagueDTO;
import com.company.productname.service.LeagueService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by darrell-shofstall on 9/14/17.
 */

@Controller
//@RequestMapping("secure")
@Api(value = "Insight", description = "Operations pertaining to Insight Settings")
public class SettingsController {
    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private LeagueService leagueService;

    @RequestMapping(value = "javaTemplate/test", method = RequestMethod.GET)
    public ResponseEntity<List<LeagueDTO>> testFunction() {
        log.info("Hello World.");

        return new ResponseEntity<List<LeagueDTO>>(leagueService.getLeagues(), HttpStatus.OK);
    }
}
