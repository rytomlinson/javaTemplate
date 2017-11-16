package com.navis.insightserver.service;

import com.navis.insightserver.dto.SurveyDTO;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
public interface ISurveysService {

    List<SurveyDTO> getSurveys(UUID owner, String locale, Boolean includeDeleted);
}
