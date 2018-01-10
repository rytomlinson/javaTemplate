package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 11/16/17.
 */
@Entity
@Table(name = "survey", schema = "insight", catalog = "test_navis")
public class SurveyEntity {
    private long id;
    private Date createdAt;
    private Boolean enabled;
    private Date launchDate;
    private Date createDate;
    private UUID createdBy;
    private boolean deleted;
    private UUID owner;
    private Long externalId;
    private Date updatedAt;
    private I18NStringEntity i18NStringByDescriptionId;
    private I18NStringEntity i18NStringByDisplayTitleId;
    private Collection<ReportSendsEntity> reportSendssById;
    private Collection<SurveyReportRecipientsEntity> surveyReportRecipientssById;
    private Collection<SurveyReportSchedulerEntity> surveyReportSchedulersById;
    private Collection<SurveyTagEntity> surveyTagsById;
    private Collection<SurveyRequestEntity> surveyRequestsById;

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
    @Column(name = "enabled", nullable = true)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "launch_date", nullable = true)
    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "created_by", nullable = true)
    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "deleted", nullable = false)
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "owner", nullable = false)
    @Type(type = "pg-uuid")
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

        SurveyEntity that = (SurveyEntity) o;

        if (id != that.id) return false;
        if (deleted != that.deleted) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
        if (launchDate != null ? !launchDate.equals(that.launchDate) : that.launchDate != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (launchDate != null ? launchDate.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "description_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByDescriptionId() {
        return i18NStringByDescriptionId;
    }

    public void setI18NStringByDescriptionId(I18NStringEntity i18NStringByDescriptionId) {
        this.i18NStringByDescriptionId = i18NStringByDescriptionId;
    }

    @ManyToOne
    @JoinColumn(name = "display_title_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByDisplayTitleId() {
        return i18NStringByDisplayTitleId;
    }

    public void setI18NStringByDisplayTitleId(I18NStringEntity i18NStringByDisplayTitleId) {
        this.i18NStringByDisplayTitleId = i18NStringByDisplayTitleId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyBySurveyId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<ReportSendsEntity> getReportSendssById() {
        return reportSendssById;
    }

    public void setReportSendssById(Collection<ReportSendsEntity> reportSendssById) {
        this.reportSendssById = reportSendssById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyBySurveyId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyReportRecipientsEntity> getSurveyReportRecipientssById() {
        return surveyReportRecipientssById;
    }

    public void setSurveyReportRecipientssById(Collection<SurveyReportRecipientsEntity> surveyReportRecipientssById) {
        this.surveyReportRecipientssById = surveyReportRecipientssById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyBySurveyId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyReportSchedulerEntity> getSurveyReportSchedulersById() {
        return surveyReportSchedulersById;
    }

    public void setSurveyReportSchedulersById(Collection<SurveyReportSchedulerEntity> surveyReportSchedulersById) {
        this.surveyReportSchedulersById = surveyReportSchedulersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyBySurveyId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyTagEntity> getSurveyTagsById() {
        return surveyTagsById;
    }

    public void setSurveyTagsById(Collection<SurveyTagEntity> surveyTagsById) {
        this.surveyTagsById = surveyTagsById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyBySurveyId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestEntity> getSurveyRequestsById() {
        return surveyRequestsById;
    }

    public void setSurveyRequestsById(Collection<SurveyRequestEntity> surveyRequestsById) {
        this.surveyRequestsById = surveyRequestsById;
    }
}
