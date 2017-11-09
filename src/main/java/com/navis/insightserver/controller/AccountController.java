package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.AccountDTO;
import com.navis.insightserver.dto.UserProfileDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by darrell-shofstall on 9/14/17.
 */

@Controller
@RequestMapping("")
@Api(value="QVue", description="Operations pertaining to QVue Account Users")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);



    @Autowired
    private ISecurity security;

    @RequestMapping(value = "accounts/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "View a list of Qvue Accounts")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(@PathVariable("id") String id) {
        log.debug("View a list of Insight Accounts for UserProfileDTO: " + id);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://iam-rest.navisdrive.com/api/properties/insight-properties-for-user?user_uuid=57742801-954a-44f9-8a3e-157ba304ea8f",
                String.class);
        return response;
//        return new ResponseEntity<>(accountService.getAccountsByUserId(id), HttpStatus.OK);
    }

    @RequestMapping(value = "accounts", method = RequestMethod.GET)
    @ApiOperation(value = "View a list of Qvue Accounts")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Qvue Accounts for UserProfileDTO: " + user.getUserId());

        return null;
    }
}
