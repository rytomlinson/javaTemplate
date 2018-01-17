package com.navis.insightserver.entity;

import com.navis.insightserver.pgtypes.PostgreSQLEnum;
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
@Table(name = "question", schema = "insight", catalog = "test_navis")
public class QuestionEntity {
    private Long id;
    private Date createdAt;
    private Boolean required;
    private Integer rank;
    private Boolean isLibrary;
    private Boolean deleted;
    private Boolean isTemplate;
    private UUID owner;
    private Long externalId;
    private Date updatedAt;
    private I18NStringEntity i18NStringByDisplayTitleId;
    private I18NStringEntity i18NStringByShortLabelId;
    private I18NStringEntity i18NStringBySemanticTitleId;
    private I18NStringEntity i18NStringByBenefitId;
    private I18NStringEntity i18NStringByTipId;
    private ConditionEntity conditionByConditionId;
    private QuestionEntity questionBySourceId;
    private Collection<BooleanQuestionEntity> booleanQuestionsById;
    private Collection<RangeQuestionEntity> rangeQuestionsById;
    private Collection<SelectQuestionEntity> selectQuestionsById;
    private Collection<SurveyRequestBoolAnswerEntity> surveyRequestBoolAnswersById;
    private Collection<SurveyRequestRangeAnswerEntity> surveyRequestRangeAnswersById;
    private Collection<SurveyRequestSelectAnswerEntity> surveyRequestSelectAnswersById;
    private Collection<SurveyRequestTextAnswerEntity> surveyRequestTextAnswersById;
    private Collection<TextQuestionEntity> textQuestionsById;
    private Collection<QuestionGroupMemberQuestionEntity> questionGroupMemberQuestionsById_0;
    private QuestionType type;

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
    @Column(name = "required", nullable = false)
    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Basic
    @Column(name = "rank", nullable = true)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "is_library", nullable = false)
    public Boolean getLibrary() {
        return isLibrary;
    }

    public void setLibrary(Boolean library) {
        isLibrary = library;
    }

    @Basic
    @Column(name = "deleted", nullable = false)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "is_template", nullable = false)
    public Boolean getTemplate() {
        return isTemplate;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }

    @Basic
    @Column(name = "owner", nullable = false)
//    @Column(name = "owner", nullable = false, columnDefinition = "pg-uuid")
//    @Type(type = "pg-uuid")
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

        QuestionEntity that = (QuestionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (required != null ? !required.equals(that.required) : that.required != null) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (isLibrary != null ? !isLibrary.equals(that.isLibrary) : that.isLibrary != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (isTemplate != null ? !isTemplate.equals(that.isTemplate) : that.isTemplate != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (isLibrary != null ? isLibrary.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (isTemplate != null ? isTemplate.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "display_title_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByDisplayTitleId() {
        return i18NStringByDisplayTitleId;
    }

    public void setI18NStringByDisplayTitleId(I18NStringEntity i18NStringByDisplayTitleId) {
        this.i18NStringByDisplayTitleId = i18NStringByDisplayTitleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "short_label_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByShortLabelId() {
        return i18NStringByShortLabelId;
    }

    public void setI18NStringByShortLabelId(I18NStringEntity i18NStringByShortLabelId) {
        this.i18NStringByShortLabelId = i18NStringByShortLabelId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "semantic_title_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringBySemanticTitleId() {
        return i18NStringBySemanticTitleId;
    }

    public void setI18NStringBySemanticTitleId(I18NStringEntity i18NStringBySemanticTitleId) {
        this.i18NStringBySemanticTitleId = i18NStringBySemanticTitleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "benefit_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByBenefitId() {
        return i18NStringByBenefitId;
    }

    public void setI18NStringByBenefitId(I18NStringEntity i18NStringByBenefitId) {
        this.i18NStringByBenefitId = i18NStringByBenefitId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "tip_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByTipId() {
        return i18NStringByTipId;
    }

    public void setI18NStringByTipId(I18NStringEntity i18NStringByTipId) {
        this.i18NStringByTipId = i18NStringByTipId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "condition_id", referencedColumnName = "id")
    public ConditionEntity getConditionByConditionId() {
        return conditionByConditionId;
    }

    public void setConditionByConditionId(ConditionEntity conditionByConditionId) {
        this.conditionByConditionId = conditionByConditionId;
    }

    @ManyToOne
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    public QuestionEntity getQuestionBySourceId() {
        return questionBySourceId;
    }

    public void setQuestionBySourceId(QuestionEntity questionBySourceId) {
        this.questionBySourceId = questionBySourceId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<BooleanQuestionEntity> getBooleanQuestionsById() {
        return booleanQuestionsById;
    }

    public void setBooleanQuestionsById(Collection<BooleanQuestionEntity> booleanQuestionsById) {
        this.booleanQuestionsById = booleanQuestionsById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<RangeQuestionEntity> getRangeQuestionsById() {
        return rangeQuestionsById;
    }

    public void setRangeQuestionsById(Collection<RangeQuestionEntity> rangeQuestionsById) {
        this.rangeQuestionsById = rangeQuestionsById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SelectQuestionEntity> getSelectQuestionsById() {
        return selectQuestionsById;
    }

    public void setSelectQuestionsById(Collection<SelectQuestionEntity> selectQuestionsById) {
        this.selectQuestionsById = selectQuestionsById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestBoolAnswerEntity> getSurveyRequestBoolAnswersById() {
        return surveyRequestBoolAnswersById;
    }

    public void setSurveyRequestBoolAnswersById(Collection<SurveyRequestBoolAnswerEntity> surveyRequestBoolAnswersById) {
        this.surveyRequestBoolAnswersById = surveyRequestBoolAnswersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestRangeAnswerEntity> getSurveyRequestRangeAnswersById() {
        return surveyRequestRangeAnswersById;
    }

    public void setSurveyRequestRangeAnswersById(Collection<SurveyRequestRangeAnswerEntity> surveyRequestRangeAnswersById) {
        this.surveyRequestRangeAnswersById = surveyRequestRangeAnswersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestSelectAnswerEntity> getSurveyRequestSelectAnswersById() {
        return surveyRequestSelectAnswersById;
    }

    public void setSurveyRequestSelectAnswersById(Collection<SurveyRequestSelectAnswerEntity> surveyRequestSelectAnswersById) {
        this.surveyRequestSelectAnswersById = surveyRequestSelectAnswersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestTextAnswerEntity> getSurveyRequestTextAnswersById() {
        return surveyRequestTextAnswersById;
    }

    public void setSurveyRequestTextAnswersById(Collection<SurveyRequestTextAnswerEntity> surveyRequestTextAnswersById) {
        this.surveyRequestTextAnswersById = surveyRequestTextAnswersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<TextQuestionEntity> getTextQuestionsById() {
        return textQuestionsById;
    }

    public void setTextQuestionsById(Collection<TextQuestionEntity> textQuestionsById) {
        this.textQuestionsById = textQuestionsById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionByGroupQuestionId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<QuestionGroupMemberQuestionEntity> getQuestionGroupMemberQuestionsById_0() {
        return questionGroupMemberQuestionsById_0;
    }

    public void setQuestionGroupMemberQuestionsById_0(Collection<QuestionGroupMemberQuestionEntity> questionGroupMemberQuestionsById_0) {
        this.questionGroupMemberQuestionsById_0 = questionGroupMemberQuestionsById_0;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = true, columnDefinition = "question_type")
    @Type(type = "com.navis.insightserver.pgtypes.PostgreSQLEnumType")

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public enum QuestionType implements PostgreSQLEnum {
        range ,
        select,
        text,
        range_group,
        range_group_member,
        yes_no;
    }


}
