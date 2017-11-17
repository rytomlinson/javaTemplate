package com.navis.insightserver.service;

import com.navis.insightserver.dto.ReportFrequencyTypeDTO;
import com.navis.insightserver.dto.ReportTypeDTO;
import com.navis.insightserver.dto.TagDTO;

import java.util.List;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
public interface IAlertsService {

    List<ReportTypeDTO> getReportTypes();
    List<ReportFrequencyTypeDTO> getReportFrequencyTypes();
}
