package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/16/17.
 */
@Entity
@Table(name = "email", schema = "insight", catalog = "test_navis")
public class EmailEntity {
    private long id;
    private Date createdAt;
    private String email;
    private Date updatedAt;
    private Collection<ReportSendsEntity> reportSendssById;
    private Collection<SurveyReportRecipientsEntity> surveyReportRecipientssById;

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

        EmailEntity that = (EmailEntity) o;

        if (id != that.id) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "emailByEmailId")
    public Collection<ReportSendsEntity> getReportSendssById() {
        return reportSendssById;
    }

    public void setReportSendssById(Collection<ReportSendsEntity> reportSendssById) {
        this.reportSendssById = reportSendssById;
    }

    @OneToMany(mappedBy = "emailByEmailId")
    public Collection<SurveyReportRecipientsEntity> getSurveyReportRecipientssById() {
        return surveyReportRecipientssById;
    }

    public void setSurveyReportRecipientssById(Collection<SurveyReportRecipientsEntity> surveyReportRecipientssById) {
        this.surveyReportRecipientssById = surveyReportRecipientssById;
    }
}
