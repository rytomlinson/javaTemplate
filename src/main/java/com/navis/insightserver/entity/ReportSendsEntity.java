package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/17/17.
 */
@Entity
@Table(name = "report_sends", schema = "insight", catalog = "test_navis")
public class ReportSendsEntity {
    private long id;
    private Date createdAt;
    private boolean success;
    private String errorMessage;
    private Date updatedAt;
    private EmailEntity emailByEmailId;
    private SurveyEntity surveyBySurveyId;
    private ReportTypeEntity reportTypeByReportTypeId;
    private SurveyRequestEntity surveyRequestBySurveyRequestId;

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
    @Column(name = "success", nullable = false)
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Basic
    @Column(name = "error_message", nullable = false, length = -1)
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

        ReportSendsEntity that = (ReportSendsEntity) o;

        if (id != that.id) return false;
        if (success != that.success) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (success ? 1 : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "email_id", referencedColumnName = "id", nullable = false)
    public EmailEntity getEmailByEmailId() {
        return emailByEmailId;
    }

    public void setEmailByEmailId(EmailEntity emailByEmailId) {
        this.emailByEmailId = emailByEmailId;
    }

    @ManyToOne
    @JoinColumn(name = "survey_id", referencedColumnName = "id", nullable = false)
    public SurveyEntity getSurveyBySurveyId() {
        return surveyBySurveyId;
    }

    public void setSurveyBySurveyId(SurveyEntity surveyBySurveyId) {
        this.surveyBySurveyId = surveyBySurveyId;
    }

    @ManyToOne
    @JoinColumn(name = "report_type_id", referencedColumnName = "id", nullable = false)
    public ReportTypeEntity getReportTypeByReportTypeId() {
        return reportTypeByReportTypeId;
    }

    public void setReportTypeByReportTypeId(ReportTypeEntity reportTypeByReportTypeId) {
        this.reportTypeByReportTypeId = reportTypeByReportTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "survey_request_id", referencedColumnName = "id", nullable = false)
    public SurveyRequestEntity getSurveyRequestBySurveyRequestId() {
        return surveyRequestBySurveyRequestId;
    }

    public void setSurveyRequestBySurveyRequestId(SurveyRequestEntity surveyRequestBySurveyRequestId) {
        this.surveyRequestBySurveyRequestId = surveyRequestBySurveyRequestId;
    }
}
