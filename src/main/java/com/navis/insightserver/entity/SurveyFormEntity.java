package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "survey_form", schema = "insight", catalog = "test_navis")
public class SurveyFormEntity {
    private Long id;
    private Integer rank;
    private Boolean deleted;
    private Boolean required;
    private Date updatedAt;
    private SurveyEntity surveyBySurveyId;
    private FormEntity formByFormId;
    private I18NStringEntity i18NStringByPrefaceId;
    private Collection<SurveyFormFormFieldEntity> surveyFormFormFieldsById;
    private Collection<SurveyRequestFormFieldAnswerEntity> surveyRequestFormFieldAnswersById;

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
    @Column(name = "rank", nullable = false)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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
    @Column(name = "required", nullable = false)
    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
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

        SurveyFormEntity that = (SurveyFormEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (required != null ? !required.equals(that.required) : that.required != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "survey_id", referencedColumnName = "id", nullable = false)
    public SurveyEntity getSurveyBySurveyId() {
        return surveyBySurveyId;
    }

    public void setSurveyBySurveyId(SurveyEntity surveyBySurveyId) {
        this.surveyBySurveyId = surveyBySurveyId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "form_id", referencedColumnName = "id", nullable = false)
    public FormEntity getFormByFormId() {
        return formByFormId;
    }

    public void setFormByFormId(FormEntity formByFormId) {
        this.formByFormId = formByFormId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "preface_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByPrefaceId() {
        return i18NStringByPrefaceId;
    }

    public void setI18NStringByPrefaceId(I18NStringEntity i18NStringByPrefaceId) {
        this.i18NStringByPrefaceId = i18NStringByPrefaceId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyFormBySurveyFormId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyFormFormFieldEntity> getSurveyFormFormFieldsById() {
        return surveyFormFormFieldsById;
    }

    public void setSurveyFormFormFieldsById(Collection<SurveyFormFormFieldEntity> surveyFormFormFieldsById) {
        this.surveyFormFormFieldsById = surveyFormFormFieldsById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyFormBySurveyFormId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestFormFieldAnswerEntity> getSurveyRequestFormFieldAnswersById() {
        return surveyRequestFormFieldAnswersById;
    }

    public void setSurveyRequestFormFieldAnswersById(Collection<SurveyRequestFormFieldAnswerEntity> surveyRequestFormFieldAnswersById) {
        this.surveyRequestFormFieldAnswersById = surveyRequestFormFieldAnswersById;
    }
}
