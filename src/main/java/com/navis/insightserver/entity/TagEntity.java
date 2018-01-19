package com.navis.insightserver.entity;

import com.navis.insightserver.pgtypes.TagType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

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
    private Boolean deleted;
    private TagType type;

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

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "tag_type", columnDefinition = "tag_type")
    @Type(type = "com.navis.insightserver.pgtypes.PostgreSQLEnumType")

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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

    @Basic
    @Column(name = "deleted", nullable = false)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
