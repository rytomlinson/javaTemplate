package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/17/17.
 */
@Entity
@Table(name = "report_type", schema = "insight", catalog = "test_navis")
public class ReportTypeEntity {
    private long id;
    private Date createdAt;
    private String code;
    private String description;
    private Date updatedAt;
    private Collection<ReportSendsEntity> reportSendssById;
    private ReportFrequencyTypeEntity reportFrequencyTypeByReportFrequencyTypeId;
    private Collection<SurveyReportRecipientsEntity> surveyReportRecipientssById;

    public void setId(Long id) {
        this.id = id;
    }

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
    @Column(name = "code", nullable = false, length = -1)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        ReportTypeEntity that = (ReportTypeEntity) o;

        if (id != that.id) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reportTypeByReportTypeId")
    public Collection<ReportSendsEntity> getReportSendssById() {
        return reportSendssById;
    }

    public void setReportSendssById(Collection<ReportSendsEntity> reportSendssById) {
        this.reportSendssById = reportSendssById;
    }

    @ManyToOne
    @JoinColumn(name = "report_frequency_type_id", referencedColumnName = "id", nullable = false)
    public ReportFrequencyTypeEntity getReportFrequencyTypeByReportFrequencyTypeId() {
        return reportFrequencyTypeByReportFrequencyTypeId;
    }

    public void setReportFrequencyTypeByReportFrequencyTypeId(ReportFrequencyTypeEntity reportFrequencyTypeByReportFrequencyTypeId) {
        this.reportFrequencyTypeByReportFrequencyTypeId = reportFrequencyTypeByReportFrequencyTypeId;
    }

    @OneToMany(mappedBy = "reportTypeByReportTypeId")
    public Collection<SurveyReportRecipientsEntity> getSurveyReportRecipientssById() {
        return surveyReportRecipientssById;
    }

    public void setSurveyReportRecipientssById(Collection<SurveyReportRecipientsEntity> surveyReportRecipientssById) {
        this.surveyReportRecipientssById = surveyReportRecipientssById;
    }
}
