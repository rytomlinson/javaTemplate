package com.navis.insightserver.controller;

import com.navis.insightserver.Utils.ISecurity;
import com.navis.insightserver.dto.ReportFrequencyTypeDTO;
import com.navis.insightserver.dto.ReportTypeDTO;
import com.navis.insightserver.service.IAlertsService;
import com.navis.insightserver.service.ISurveysService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrell-shofstall on 11/24/17.
 */
@RunWith(SpringRunner.class)
public class AlertsControllerTest {

    List<ReportTypeDTO> reportTypeDTOList;
    List<ReportFrequencyTypeDTO> reportFrequencyTypeDTOList;
    ReportTypeDTO reportTypeDTO;
    ReportFrequencyTypeDTO reportFrequencyTypeDTO;

    @Before
    public void setUp() {
        //DTO's

        reportFrequencyTypeDTO = new ReportFrequencyTypeDTO();
        reportFrequencyTypeDTO.setId(1L);
        reportFrequencyTypeDTO.setCode("HOURLY");
        reportFrequencyTypeDTO.setDescription("Hourly alert notification");
        reportFrequencyTypeDTOList = new ArrayList<>();
        reportFrequencyTypeDTOList.add(reportFrequencyTypeDTO);

        reportTypeDTO = new ReportTypeDTO();
        reportTypeDTO.setId(1L);
        reportTypeDTO.setCode("INDIVIDUAL_RESPONSE");
        reportTypeDTO.setDescription("Individual response report");
        reportTypeDTO.setReportFrequencyTypeDTO(reportFrequencyTypeDTO);
        reportTypeDTOList = new ArrayList<>();
        reportTypeDTOList.add(reportTypeDTO);

        // Mocking service layer
        Mockito.when(alertsService.getReportFrequencyTypes()).thenReturn(reportFrequencyTypeDTOList);
        Mockito.when(alertsService.getReportTypes()).thenReturn(reportTypeDTOList);
    }

    @TestConfiguration
    static class AlertsControllerTestContextConfiguration {

        @Bean
        public AlertsController alertsController() {
            return new AlertsController();
        }
    }

    @Autowired
    private AlertsController alertsController;

    @MockBean
    private IAlertsService alertsService;

    @MockBean
    ISurveysService surveysService;


    @MockBean
    private ISecurity security;

    @Test
    public void getReportFrequencyTypes_ReturnAllRows_ifRowsExist() {
        ResponseEntity<List<ReportFrequencyTypeDTO>> found = alertsController.getReportFrequencyTypes(null, null, null);
        Assert.assertNotNull("List shouldn't be null", found);
        Assert.assertEquals("Status should be 200", HttpStatus.OK, found.getStatusCode());
        Assert.assertNotNull("Response entity body shouldn't be null", found.getBody());
        Assert.assertEquals("wrong report frequency type id", 1, found.getBody().get(0).getId().intValue());
        Assert.assertEquals("wrong report frequency type name", "HOURLY", found.getBody().get(0).getCode());
    }

    @Test
    public void getReportTypes_ReturnAllRows_ifRowsExist() {
        ResponseEntity<List<ReportTypeDTO>> found = alertsController.getReportTypes(null, null, null);
        Assert.assertNotNull("List shouldn't be null", found);
        Assert.assertEquals("Status should be 200", HttpStatus.OK, found.getStatusCode());
        Assert.assertNotNull("Response entity body shouldn't be null", found.getBody());
        Assert.assertEquals("wrong report type id", 1, found.getBody().get(0).getId().intValue());
        Assert.assertEquals("wrong report type name", "INDIVIDUAL_RESPONSE", found.getBody().get(0).getCode());
    }
}
