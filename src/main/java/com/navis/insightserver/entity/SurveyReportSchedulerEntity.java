package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 12/15/17.
 */
@Entity
@Table(name = "survey_report_scheduler", schema = "insight", catalog = "test_navis")
public class SurveyReportSchedulerEntity {
    private Long id;
    private String runStatus;
    private Date lastRunAt;
    private Date createdAt;
    private Date updatedAt;
    private ReportTypeEntity reportTypeByReportTypeId;
    private SurveyEntity surveyBySurveyId;

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
    @Column(name = "run_status", nullable = true, length = -1)
    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    @Basic
    @Column(name = "last_run_at", nullable = true)
    public Date getLastRunAt() {
        return lastRunAt;
    }

    public void setLastRunAt(Date lastRunAt) {
        this.lastRunAt = lastRunAt;
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

        SurveyReportSchedulerEntity that = (SurveyReportSchedulerEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (runStatus != null ? !runStatus.equals(that.runStatus) : that.runStatus != null) return false;
        if (lastRunAt != null ? !lastRunAt.equals(that.lastRunAt) : that.lastRunAt != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (runStatus != null ? runStatus.hashCode() : 0);
        result = 31 * result + (lastRunAt != null ? lastRunAt.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
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
    @JoinColumn(name = "survey_id", referencedColumnName = "id", nullable = false)
    public SurveyEntity getSurveyBySurveyId() {
        return surveyBySurveyId;
    }

    public void setSurveyBySurveyId(SurveyEntity surveyBySurveyId) {
        this.surveyBySurveyId = surveyBySurveyId;
    }
}
