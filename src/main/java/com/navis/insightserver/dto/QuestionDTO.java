package com.navis.insightserver.dto;

import com.navis.insightserver.entity.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class QuestionDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String displayTitle;
    private String shortLabel;
    private UUID owner;
    private Boolean isLibrary;
    private String type;

    public QuestionDTO() {
    }

    public QuestionDTO(Object[] question, List<TranslationEntity> displayTitleEntities, List<TranslationEntity> shortLabelEntities, String locale) {
        super();

        BigInteger questionId = (BigInteger) question[0];
        this.id = questionId.longValue();
        owner = UUID.fromString((String) question[1]);
        this.isLibrary = (Boolean) question[4];
        this.displayTitle = super.returnTranslationForLocale(displayTitleEntities, locale);
        this.shortLabel = super.returnTranslationForLocale(shortLabelEntities, locale);
    }

    public QuestionDTO(QuestionEntity questionEntity, String locale) {
        super();
        List<TranslationEntity> displayTitleEntities = (List<TranslationEntity>) questionEntity.getI18NStringByDisplayTitleId().getTranslationsById();
        List<TranslationEntity> shortLabelEntities = (null != questionEntity.getI18NStringByShortLabelId())
                ? (List<TranslationEntity>) questionEntity.getI18NStringByShortLabelId().getTranslationsById() : null;

        this.id = questionEntity.getId();
        this.displayTitle = super.returnTranslationForLocale(displayTitleEntities, locale);
        this.shortLabel = super.returnTranslationForLocale(shortLabelEntities, locale);
        this.type = questionEntity.getType().toString();
        this.owner = questionEntity.getOwner();
        this.isLibrary = questionEntity.getLibrary();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Boolean getLibrary() {
        return isLibrary;
    }

    public void setLibrary(Boolean library) {
        isLibrary = library;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
