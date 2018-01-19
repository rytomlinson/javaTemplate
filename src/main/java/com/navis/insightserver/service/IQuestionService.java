package com.navis.insightserver.service;

import com.navis.insightserver.dto.QuestionDTO;
import com.navis.insightserver.dto.QuestionTypeDTO;
import com.navis.insightserver.dto.TagDTO;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
public interface IQuestionService {

    List<QuestionDTO> getQuestions(UUID propertyId, String locale);
    List<QuestionTypeDTO> getQuestionTypes(UUID propertyId, String locale);
    void deleteQuestion(UUID owner, Long id);

}
