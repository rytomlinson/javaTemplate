package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "tag", schema = "insight", catalog = "test_navis")
public class TagEntity {
    private Long id;
    private Date createdAt;
//    private String type;
    private Boolean isMarketSegment;
    private Boolean isSurvey;
    private Boolean isSurveyType;
    private Boolean isQuestion;
    private UUID owner;
    private Long externalId;
    private Date updatedAt;
    private I18NStringEntity i18NStringByNameId;
    private I18NStringEntity i18NStringByDescriptionId;
    private Integer minimumValue;
    private Integer maximumValue;
    private Collection<TagTagEntity> tagTagsById_0;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

//    @Basic
//    @Column(name = "type", nullable = true)
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    @Basic
    @Column(name = "is_market_segment", nullable = false)
    public Boolean getMarketSegment() {
        return isMarketSegment;
    }

    public void setMarketSegment(Boolean marketSegment) {
        isMarketSegment = marketSegment;
    }

    @Basic
    @Column(name = "is_survey", nullable = false)
    public Boolean getSurvey() {
        return isSurvey;
    }

    public void setSurvey(Boolean survey) {
        isSurvey = survey;
    }

    @Basic
    @Column(name = "is_survey_type", nullable = false)
    public Boolean getSurveyType() {
        return isSurveyType;
    }

    public void setSurveyType(Boolean surveyType) {
        isSurveyType = surveyType;
    }

    @Basic
    @Column(name = "is_question", nullable = false)
    public Boolean getQuestion() {
        return isQuestion;
    }

    public void setQuestion(Boolean question) {
        isQuestion = question;
    }

    @Basic
    @Column(name = "owner", nullable = false)
    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
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

        if (id != null ? !id.equals(tagEntity.id) : tagEntity.id != null) return false;
        if (createdAt != null ? !createdAt.equals(tagEntity.createdAt) : tagEntity.createdAt != null) return false;
//        if (type != null ? !type.equals(tagEntity.type) : tagEntity.type != null) return false;
        if (isMarketSegment != null ? !isMarketSegment.equals(tagEntity.isMarketSegment) : tagEntity.isMarketSegment != null)
            return false;
        if (isSurvey != null ? !isSurvey.equals(tagEntity.isSurvey) : tagEntity.isSurvey != null) return false;
        if (isSurveyType != null ? !isSurveyType.equals(tagEntity.isSurveyType) : tagEntity.isSurveyType != null)
            return false;
        if (isQuestion != null ? !isQuestion.equals(tagEntity.isQuestion) : tagEntity.isQuestion != null) return false;
        if (owner != null ? !owner.equals(tagEntity.owner) : tagEntity.owner != null) return false;
        if (externalId != null ? !externalId.equals(tagEntity.externalId) : tagEntity.externalId != null) return false;
        if (updatedAt != null ? !updatedAt.equals(tagEntity.updatedAt) : tagEntity.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
//        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isMarketSegment != null ? isMarketSegment.hashCode() : 0);
        result = 31 * result + (isSurvey != null ? isSurvey.hashCode() : 0);
        result = 31 * result + (isSurveyType != null ? isSurveyType.hashCode() : 0);
        result = 31 * result + (isQuestion != null ? isQuestion.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "name_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByNameId() {
        return i18NStringByNameId;
    }

    public void setI18NStringByNameId(I18NStringEntity i18NStringByNameId) {
        this.i18NStringByNameId = i18NStringByNameId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "description_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByDescriptionId() {
        return i18NStringByDescriptionId;
    }

    public void setI18NStringByDescriptionId(I18NStringEntity i18NStringByDescriptionId) {
        this.i18NStringByDescriptionId = i18NStringByDescriptionId;
    }

    @Basic
    @Column(name = "minimum_value", nullable = true)
    public Integer getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(Integer minimumValue) {
        this.minimumValue = minimumValue;
    }

    @Basic
    @Column(name = "maximum_value", nullable = true)
    public Integer getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(Integer maximumValue) {
        this.maximumValue = maximumValue;
    }

    @OneToMany(mappedBy = "tagByParentTagId")
    public Collection<TagTagEntity> getTagTagsById_0() {
        return tagTagsById_0;
    }

    public void setTagTagsById_0(Collection<TagTagEntity> tagTagsById_0) {
        this.tagTagsById_0 = tagTagsById_0;
    }
}
