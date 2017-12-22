package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "survey_report_recipients", schema = "insight", catalog = "test_navis")
public class SurveyReportRecipientsEntity {
    private Long id;
    private Date createdAt;
    private String email;
    private Date updatedAt;
    private SurveyEntity surveyBySurveyId;
    private ReportTypeEntity reportTypeByReportTypeId;

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
    @Column(name = "email", nullable = false, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        SurveyReportRecipientsEntity that = (SurveyReportRecipientsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
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
    @JoinColumn(name = "report_type_id", referencedColumnName = "id", nullable = false)
    public ReportTypeEntity getReportTypeByReportTypeId() {
        return reportTypeByReportTypeId;
    }

    public void setReportTypeByReportTypeId(ReportTypeEntity reportTypeByReportTypeId) {
        this.reportTypeByReportTypeId = reportTypeByReportTypeId;
    }
}
