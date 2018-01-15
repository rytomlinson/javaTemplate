package com.navis.insightserver.service;

import com.navis.insightserver.Repository.SurveyRepository;
import com.navis.insightserver.dto.SurveyDTO;
import com.navis.insightserver.entity.I18NStringEntity;
import com.navis.insightserver.entity.SurveyEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
public class SurveysServiceTest {

    @Mock
    SurveyRepository surveyRepository;

    @InjectMocks
    SurveysService surveysService;

    @MockBean
    private SurveyEntity surveyEntity;

    @Before
    public void setUp() {
        //Mock a Survey Entity List
        surveyEntity = new SurveyEntity();
        surveyEntity.setId(2L);
        surveyEntity.setEnabled(true);
        surveyEntity.setCreateDate(new Date());
        I18NStringEntity i18NStringEntityTitle = new I18NStringEntity();
        i18NStringEntityTitle.setId(3L);
        surveyEntity.setI18NStringByDisplayTitleId(i18NStringEntityTitle);
        I18NStringEntity i18NStringEntityDescription = new I18NStringEntity();
        i18NStringEntityTitle.setId(34L);
        surveyEntity.setI18NStringByDescriptionId(i18NStringEntityDescription);
        Mockito.when(surveyRepository.findByOwnerAndId(null, null)).thenReturn(surveyEntity);
    }

    @Test
    public void testGetSurveyById() {
        SurveyDTO surveyEntity = surveysService.getSurveyById(null, null, null);
        Assert.assertNotNull(surveyEntity);

    }


}
