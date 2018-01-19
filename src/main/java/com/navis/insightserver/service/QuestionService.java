package com.navis.insightserver.service;

import com.navis.insightserver.Repository.*;
import com.navis.insightserver.dto.QuestionDTO;
import com.navis.insightserver.dto.QuestionTypeDTO;
import com.navis.insightserver.dto.ResourceNotFoundExceptionDTO;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.entity.QuestionEntity;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;
import com.navis.insightserver.entity.TranslationEntity;
import com.navis.insightserver.pgtypes.QuestionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */

@Service
@Transactional
public class QuestionService implements IQuestionService {
    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

    @Value("${navis.prop.uuid}")
    private UUID uuid;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TranslationRepository translationRepository;

    @Override
    public List<QuestionDTO> getQuestions(UUID propertyId, String locale) {
        log.debug("In getQuestions Service:");
        return buildQuestionsDTO(propertyId, locale);
    }

    @Override
    public List<QuestionTypeDTO> getQuestionTypes(UUID propertyId, String locale) {
        log.debug("In getQuestionTypess Service:");
        return buildQuestionTypesDTO(propertyId, locale);
    }


    private List<QuestionDTO> buildQuestionsDTO(UUID propertyId, String locale) {
        List<UUID> owners = new ArrayList<>();
        owners.add(propertyId);
        owners.add(uuid);


        List<QuestionEntity> questionEntities = (List<QuestionEntity>) questionRepository.findByOwnerInAndDeletedFalseAndTemplateTrueAndQuestionBySourceId_IdIsNull(owners);

        List<QuestionDTO> listDto = questionEntities.stream()
                .filter(item -> !item.getType().equals(QuestionType.range_group_member))
                .map(item -> convertToDto(item, locale)).collect(Collectors.toList());

        return listDto;
    }

    private List<QuestionTypeDTO> buildQuestionTypesDTO(UUID propertyId, String locale) {

        List<String> questionTypeObjects = questionRepository.getQuestionTypes();

        List<QuestionTypeDTO> listDto = questionTypeObjects.stream().map(questionType -> convertToDto(questionType)).collect(Collectors.toList());

        return listDto;
    }

    private QuestionDTO convertToDto(Object[] questionObject, String locale) {
        BigInteger displayTitleId = (BigInteger) questionObject[2];
        BigInteger shortLabelId = (BigInteger) questionObject[3];
        List<TranslationEntity> displayTitleEntities = translationRepository.findByI18NStringByI18NStringId_Id(displayTitleId.longValue());
        List<TranslationEntity> shortLabelEntities = translationRepository.findByI18NStringByI18NStringId_Id(shortLabelId.longValue());
        QuestionDTO questionDTO = new QuestionDTO(questionObject, displayTitleEntities, shortLabelEntities, locale);
        return questionDTO;
    }

    private QuestionDTO convertToDto(QuestionEntity questionEntity, String locale) {
        QuestionDTO questionDTO = new QuestionDTO(questionEntity, locale);
        return questionDTO;
    }

    private  QuestionTypeDTO convertToDto(String questionTypeObject) {
        QuestionTypeDTO questionTypeDTO = new QuestionTypeDTO();
        questionTypeDTO.setQuestionType(questionTypeObject);

        return questionTypeDTO;
    }
}
