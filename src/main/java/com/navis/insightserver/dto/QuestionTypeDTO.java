package com.navis.insightserver.dto;

import com.navis.insightserver.entity.QuestionEntity;
import com.navis.insightserver.entity.TranslationEntity;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class QuestionTypeDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String questionType;

    public QuestionTypeDTO() {
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
