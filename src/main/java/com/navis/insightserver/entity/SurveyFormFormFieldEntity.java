package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "survey_form_form_field", schema = "insight", catalog = "test_navis")
public class SurveyFormFormFieldEntity {
    private Long id;
    private Date createdAt;
    private Boolean required;
    private Boolean deleted;
    private Date updatedAt;
    private FormFieldEntity formFieldByFormFieldId;
    private SurveyFormEntity surveyFormBySurveyFormId;

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
    @Column(name = "deleted", nullable = false)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

        SurveyFormFormFieldEntity that = (SurveyFormFormFieldEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (required != null ? !required.equals(that.required) : that.required != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "form_field_id", referencedColumnName = "id", nullable = false)
    public FormFieldEntity getFormFieldByFormFieldId() {
        return formFieldByFormFieldId;
    }

    public void setFormFieldByFormFieldId(FormFieldEntity formFieldByFormFieldId) {
        this.formFieldByFormFieldId = formFieldByFormFieldId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "survey_form_id", referencedColumnName = "id", nullable = false)
    public SurveyFormEntity getSurveyFormBySurveyFormId() {
        return surveyFormBySurveyFormId;
    }

    public void setSurveyFormBySurveyFormId(SurveyFormEntity surveyFormBySurveyFormId) {
        this.surveyFormBySurveyFormId = surveyFormBySurveyFormId;
    }
}
