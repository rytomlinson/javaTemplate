package com.navis.insightserver.service;

import com.navis.insightserver.Repository.SurveyRepository;
import com.navis.insightserver.dto.SurveyDTO;
import com.navis.insightserver.entity.I18NStringEntity;
import com.navis.insightserver.entity.SurveyEntity;
import org.javatuples.Unit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
public class SurveysServiceTest {

    List<SurveyEntity> surveyEntityList = new ArrayList<>();
    String responseKey;

    @Mock
    SurveyRepository surveyRepository;

    @InjectMocks
    SurveysService surveysService;

    @MockBean
    SecurityService securityService;

    @MockBean
    private SurveyEntity surveyEntity;


    @Before
    public void setUp() {
        //Mock a Survey Entity
        surveyEntity = new SurveyEntity();
        surveyEntity.setId(2L);
        surveyEntity.setEnabled(false);
        surveyEntity.setCreateDate(new Date());
        I18NStringEntity i18NStringEntityTitle = new I18NStringEntity();
        i18NStringEntityTitle.setId(3L);
        surveyEntity.setI18NStringByDisplayTitleId(i18NStringEntityTitle);
        I18NStringEntity i18NStringEntityDescription = new I18NStringEntity();
        i18NStringEntityTitle.setId(34L);
        surveyEntity.setI18NStringByDescriptionId(i18NStringEntityDescription);
        Mockito.when(surveyRepository.findByOwnerAndId(null, null)).thenReturn(surveyEntity);

        //Mock a Survey List
        surveyEntityList.add(surveyEntity);
        Mockito.when(surveyRepository.findByOwner(null)).thenReturn(surveyEntityList);
        Mockito.when(surveyRepository.findByOwnerAndDeletedFalse(null)).thenReturn(surveyEntityList);

        //Mock Exist and Find Survey
        Mockito.when(surveyRepository.exists(null)).thenReturn(true);
        Mockito.when(surveyRepository.findOne(null)).thenReturn(surveyEntity);
        Mockito.when(surveyRepository.getCurrentSurveyQuestionCount(null)).thenReturn(1L);

        // Mock Response Key
        responseKey = "RESPONSE_KEY_TEST";
        Mockito.when(securityService.generateSurveyResponseKey(null, 0L, null, null)).thenReturn(responseKey);

    }

    @Test
    public void testMockCreation() {
        Assert.assertNotNull(responseKey);
    }

    @Test
    public void testGetSurveyById() {
        SurveyDTO surveyEnt = surveysService.getSurveyById(null, null, null);
        Assert.assertNotNull(surveyEnt);
        Assert.assertEquals(surveyEnt.getEnabled(), false);
    }


    @Test
    public void testGetSurveysIncludeDeleted() {
        List<SurveyDTO> surveyEntityList = surveysService.getSurveys(null, null, true);
        Assert.assertNotNull(surveyEntityList);
        Assert.assertEquals(surveyEntityList.size() > 0, true);
    }

    @Test
    public void testGetSurveysNotIncludeDeleted() {
        List<SurveyDTO> surveyEntityList = surveysService.getSurveys(null, null, true);
        Assert.assertNotNull(surveyEntityList);
        Assert.assertEquals(surveyEntityList.size() > 0, true);
    }

    @Test
    public void testUpdateSurveyPublishStatus_PUBLISH() {
        surveysService.updateSurveyPublishStatus(null, null, true);
        Assert.assertNotNull(surveyEntity);
        Assert.assertEquals(surveyEntity.getEnabled(), true);

    }

    @Test
    public void testGenerateAnonymousSurveyLink() {
        Unit<String> unit = surveysService.generateAnonymousSurveyLink(null, null, null,
                null);
        Assert.assertNotNull(unit);
    }

}
