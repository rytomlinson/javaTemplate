package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.SurveyDTO;
import com.navis.insightserver.dto.UserProfileDTO;
import com.navis.insightserver.service.ISurveysService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RunWith(SpringRunner.class)
public class SurveysControllerTest extends Mockito {

    SurveyDTO surveyDTO;
    List<SurveyDTO> surveyDTOList = new ArrayList<>();


    @Autowired
    private SurveysController surveysController;

    @MockBean
    ISurveysService isurveysService;

    @MockBean
    private ISecurity security;

    @MockBean
    private UserProfileDTO userProfileDTO;

    @Before
    public void setUp() throws Exception {
        // mock a user
        HttpServletRequest request;
        HttpServletResponse response;
        userProfileDTO = new UserProfileDTO("UserId_Test", "Name_Test", "Token_Test", "State_Test");
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getParameter("username")).thenReturn("me");
        when(request.getParameter("password")).thenReturn("secret");
        final WebContext context = new J2EContext(request, response);
        Mockito.when(security.GetUserProfile(context)).thenReturn(userProfileDTO);


        // mock a survey
        surveyDTO = new SurveyDTO();
        surveyDTO.setDescription("description_test");
        surveyDTO.setDisplayTitle("title_test");
        surveyDTO.setId(1L);
        surveyDTOList.add(surveyDTO);
        surveyDTO.setEnabled(true);
        Mockito.when(isurveysService.getSurveys(null, null, null)).thenReturn(surveyDTOList);
        Mockito.when(isurveysService.getSurveyById(null, null, null)).thenReturn(surveyDTO);
    }

    @TestConfiguration
    static class SurveysControllerTestContextConfiguration {

        @Bean
        public SurveysController surveysController() {
            return new SurveysController();
        }
    }

    @Test
    public void testMockCreation() {
        Assert.assertNotNull(userProfileDTO);
    }

    @Test
    public void testGetSurveys_ReturnAll() {
        ResponseEntity<List<SurveyDTO>> found = surveysController.getSurveys(null, null, null, null, null, null);
        Assert.assertNotNull("List shouldn't be null", found);
        Assert.assertEquals("Status should be 200", HttpStatus.OK, found.getStatusCode());
        Assert.assertNotNull("Response entity body shouldn't be null", found.getBody());
    }

    @Test
    public void testGetSurveysById() {
        ResponseEntity<SurveyDTO> surveyDTOResponseEntity = surveysController.getSurveyById(null, null, null, null, null, null);
        Assert.assertNotNull("Shouldn't be null", surveyDTOResponseEntity);
        Assert.assertEquals("Status should be 200", HttpStatus.OK, surveyDTOResponseEntity.getStatusCode());
        Assert.assertNotNull("Response entity body shouldn't be null", surveyDTOResponseEntity.getBody());

    }

    @Test
    public void testUpdateSurveyPublishStatus() {
        ResponseEntity<Void> responseEntity = surveysController.updateSurveyPublishStatus(null, null, null, null, null, null);
        Assert.assertNotNull("Shouldn't be null", responseEntity);
        Assert.assertEquals("Status should be 200", HttpStatus.OK, responseEntity.getStatusCode());
    }

}
