package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/10/17.
 */
@Entity
@Table(name = "tag", schema = "insight", catalog = "test_navis")
public class TagEntity {
    private long id;
    private Date createdAt;
    private String type;
    private boolean isMarketSegment;
    private boolean isSurvey;
    private boolean isSurveyType;
    private boolean isQuestion;
    private String owner;
    private Long externalId;
    private Date updatedAt;
    private long nameId;
    private Long descriptionId;
    private I18NStringEntity i18NStringByNameId;
    private I18NStringEntity i18NStringByDescriptionId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "is_market_segment", nullable = false)
    public boolean isMarketSegment() {
        return isMarketSegment;
    }

    public void setMarketSegment(boolean marketSegment) {
        isMarketSegment = marketSegment;
    }

    @Basic
    @Column(name = "is_survey", nullable = false)
    public boolean isSurvey() {
        return isSurvey;
    }

    public void setSurvey(boolean survey) {
        isSurvey = survey;
    }

    @Basic
    @Column(name = "is_survey_type", nullable = false)
    public boolean isSurveyType() {
        return isSurveyType;
    }

    public void setSurveyType(boolean surveyType) {
        isSurveyType = surveyType;
    }

    @Basic
    @Column(name = "is_question", nullable = false)
    public boolean isQuestion() {
        return isQuestion;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    @Basic
    @Column(name = "owner", nullable = false)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "external_id", nullable = true)
    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    @Basic
    @Column(name = "updated_at", nullable = false)
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEntity tagEntity = (TagEntity) o;

        if (id != tagEntity.id) return false;
        if (isMarketSegment != tagEntity.isMarketSegment) return false;
        if (isSurvey != tagEntity.isSurvey) return false;
        if (isSurveyType != tagEntity.isSurveyType) return false;
        if (isQuestion != tagEntity.isQuestion) return false;
        if (createdAt != null ? !createdAt.equals(tagEntity.createdAt) : tagEntity.createdAt != null) return false;
        if (type != null ? !type.equals(tagEntity.type) : tagEntity.type != null) return false;
        if (owner != null ? !owner.equals(tagEntity.owner) : tagEntity.owner != null) return false;
        if (externalId != null ? !externalId.equals(tagEntity.externalId) : tagEntity.externalId != null) return false;
        if (updatedAt != null ? !updatedAt.equals(tagEntity.updatedAt) : tagEntity.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isMarketSegment ? 1 : 0);
        result = 31 * result + (isSurvey ? 1 : 0);
        result = 31 * result + (isSurveyType ? 1 : 0);
        result = 31 * result + (isQuestion ? 1 : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }



    @ManyToOne
    @JoinColumn(name = "name_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByNameId() {
        return i18NStringByNameId;
    }

    public void setI18NStringByNameId(I18NStringEntity i18NStringByNameId) {
        this.i18NStringByNameId = i18NStringByNameId;
    }

    @ManyToOne
    @JoinColumn(name = "description_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByDescriptionId() {
        return i18NStringByDescriptionId;
    }

    public void setI18NStringByDescriptionId(I18NStringEntity i18NStringByDescriptionId) {
        this.i18NStringByDescriptionId = i18NStringByDescriptionId;
    }
}
