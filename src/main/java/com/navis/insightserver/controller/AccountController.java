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
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 9/14/17.
 */

@Controller
@RequestMapping("secure")
@Api(value="QVue", description="Operations pertaining to QVue Account Users")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Value("${navis.properties.api.propertiesEndpoint}")
    private String propertiesEndpoint;

    @Value("${reach.api.endpoint}")
    private String endpoint;

    @Autowired
    private ISecurity security;

    @Autowired
    private ITagService tagService;

    @RequestMapping(value = "accounts", method = RequestMethod.GET)
    @ApiOperation(value = "View a list of Insight Accounts")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Accounts for UserProfileDTO: " + user.getUserId());

        URI uri = URI.create(UriComponentsBuilder.fromUriString(propertiesEndpoint).queryParam("user_uuid",user.getUserId()).toUriString());

        RestTemplate restTemplate = new RestTemplate();
        Object[]  accountsObjectList = restTemplate.getForObject(uri.toString(), Object[].class);
        List<AccountDTO> listDto = Arrays.asList(accountsObjectList).stream().map(item -> convertAccountsToDto(item)).collect(Collectors.toList());
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }

    @RequestMapping(value = "products/accounts/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "View a list of NAVIS products")
    public ResponseEntity<List<ProductDTO>> getAccountsByUserId(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Accounts for UserProfileDTO: " + user.getUserId());

        URI uri = URI.create(UriComponentsBuilder.fromUriString(endpoint).pathSegment("UserProducts")
                .queryParam("accountNumber", id )
                .queryParam("userId",user.getUserId()).toUriString());

        RestTemplate restTemplate = new RestTemplate();
        Object[]  accountsObjectList = restTemplate.getForObject(uri.toString(), Object[].class);
        List<ProductDTO> listDto = Arrays.asList(accountsObjectList).stream().map(item -> convertProductsToDto(item)).collect(Collectors.toList());
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }

    private AccountDTO convertAccountsToDto(Object account) {
        AccountDTO accountDTO = new AccountDTO(account);
        return accountDTO;
    }

    private ProductDTO convertProductsToDto(Object product) {
        ProductDTO productDTO = new ProductDTO(product);
        return productDTO;
    }

    @RequestMapping(value = "tags", method = RequestMethod.GET)
    public ResponseEntity<List<TagDTO>> getTags(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        UserProfileDTO user = security.GetUserProfile(context);
        log.info("View a list of Insight Tags for UserProfileDTO: " + user.getUserId());

        return new ResponseEntity<List<TagDTO>>(tagService.getTags(), HttpStatus.OK);


    }
}
