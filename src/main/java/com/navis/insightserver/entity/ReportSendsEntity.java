package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/17/17.
 */
@Entity
@Table(name = "report_sends")
public class ReportSendsEntity {
    private long id;
    private Date createdAt;
    private boolean success;
    private String errorMessage;
    private Date updatedAt;
    private String email;
    private SurveyEntity surveyBySurveyId;
    private ReportTypeEntity reportTypeByReportTypeId;
    private SurveyRequestEntity surveyRequestBySurveyRequestId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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
