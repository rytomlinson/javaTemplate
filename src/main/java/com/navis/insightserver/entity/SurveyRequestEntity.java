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
@Table(name = "survey_request", schema = "insight", catalog = "test_navis")
public class SurveyRequestEntity {
    private Long id;
    private Date createdAt;
    private Date activeUntil;
    private Date completionDate;
    private String completionStatus;
    private String completionTask;
    private String accountId;
    private String crmContactId;
    private Long crmStayId;
    private String email;
    private Date sentDate;
    private Date updatedAt;
    private Long externalId;
    private SurveyEntity surveyBySurveyId;
    private SurveyRequestReachableQuestionsEntity reachableQuestionsEntity;
    private Collection<SurveyRequestReachableQuestionsEntity> surveyRequestReachableQuestionssById;
    private Collection<SurveyRequestFormFieldAnswerEntity> surveyRequestFormFieldAnswersById;
    private Collection<SurveyRequestBoolAnswerEntity> surveyRequestBoolAnswersById;
    private Collection<SurveyRequestRangeAnswerEntity> surveyRequestRangeAnswersById;
    private Collection<SurveyRequestSelectAnswerEntity> surveyRequestSelectAnswersById;
    private Collection<SurveyRequestTextAnswerEntity> surveyRequestTextAnswersById;

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
    @Column(name = "active_until", nullable = true)
    public Date getActiveUntil() {
        return activeUntil;
    }

    public void setActiveUntil(Date activeUntil) {
        this.activeUntil = activeUntil;
    }

    @Basic
    @Column(name = "completion_date", nullable = true)
    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    @Basic
    @Column(name = "completion_status", nullable = true)
    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    @Basic
    @Column(name = "completion_task", nullable = true)
    public String getCompletionTask() {
        return completionTask;
    }

    public void setCompletionTask(String completionTask) {
        this.completionTask = completionTask;
    }

    @Basic
    @Column(name = "account_id", nullable = true, length = -1)
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "crm_contact_id", nullable = true, length = -1)
    public String getCrmContactId() {
        return crmContactId;
    }

    public void setCrmContactId(String crmContactId) {
        this.crmContactId = crmContactId;
    }

    @Basic
    @Column(name = "crm_stay_id", nullable = true)
    public Long getCrmStayId() {
        return crmStayId;
    }

    public void setCrmStayId(Long crmStayId) {
        this.crmStayId = crmStayId;
    }

    @Basic
    @Column(name = "email", nullable = true, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "sent_date", nullable = true)
    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
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
    @Column(name = "external_id", nullable = true)
    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveyRequestEntity that = (SurveyRequestEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (activeUntil != null ? !activeUntil.equals(that.activeUntil) : that.activeUntil != null) return false;
        if (completionDate != null ? !completionDate.equals(that.completionDate) : that.completionDate != null)
            return false;
        if (completionStatus != null ? !completionStatus.equals(that.completionStatus) : that.completionStatus != null)
            return false;
        if (completionTask != null ? !completionTask.equals(that.completionTask) : that.completionTask != null)
            return false;
        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (crmContactId != null ? !crmContactId.equals(that.crmContactId) : that.crmContactId != null) return false;
        if (crmStayId != null ? !crmStayId.equals(that.crmStayId) : that.crmStayId != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (sentDate != null ? !sentDate.equals(that.sentDate) : that.sentDate != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
        if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (activeUntil != null ? activeUntil.hashCode() : 0);
        result = 31 * result + (completionDate != null ? completionDate.hashCode() : 0);
        result = 31 * result + (completionStatus != null ? completionStatus.hashCode() : 0);
        result = 31 * result + (completionTask != null ? completionTask.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (crmContactId != null ? crmContactId.hashCode() : 0);
        result = 31 * result + (crmStayId != null ? crmStayId.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    public SurveyEntity getSurveyBySurveyId() {
        return surveyBySurveyId;
    }

    public void setSurveyBySurveyId(SurveyEntity surveyBySurveyId) {
        this.surveyBySurveyId = surveyBySurveyId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyRequestBySurveyRequestId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestReachableQuestionsEntity> getSurveyRequestReachableQuestionssById() {
        return surveyRequestReachableQuestionssById;
    }

    public void setSurveyRequestReachableQuestionssById(Collection<SurveyRequestReachableQuestionsEntity> surveyRequestReachableQuestionssById) {
        this.surveyRequestReachableQuestionssById = surveyRequestReachableQuestionssById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyRequestBySurveyRequestId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestFormFieldAnswerEntity> getSurveyRequestFormFieldAnswersById() {
        return surveyRequestFormFieldAnswersById;
    }

    public void setSurveyRequestFormFieldAnswersById(Collection<SurveyRequestFormFieldAnswerEntity> surveyRequestFormFieldAnswersById) {
        this.surveyRequestFormFieldAnswersById = surveyRequestFormFieldAnswersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyRequestBySurveyRequestId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestBoolAnswerEntity> getSurveyRequestBoolAnswersById() {
        return surveyRequestBoolAnswersById;
    }

    public void setSurveyRequestBoolAnswersById(Collection<SurveyRequestBoolAnswerEntity> surveyRequestBoolAnswersById) {
        this.surveyRequestBoolAnswersById = surveyRequestBoolAnswersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyRequestBySurveyRequestId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestRangeAnswerEntity> getSurveyRequestRangeAnswersById() {
        return surveyRequestRangeAnswersById;
    }

    public void setSurveyRequestRangeAnswersById(Collection<SurveyRequestRangeAnswerEntity> surveyRequestRangeAnswersById) {
        this.surveyRequestRangeAnswersById = surveyRequestRangeAnswersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyRequestBySurveyRequestId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestSelectAnswerEntity> getSurveyRequestSelectAnswersById() {
        return surveyRequestSelectAnswersById;
    }

    public void setSurveyRequestSelectAnswersById(Collection<SurveyRequestSelectAnswerEntity> surveyRequestSelectAnswersById) {
        this.surveyRequestSelectAnswersById = surveyRequestSelectAnswersById;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyRequestBySurveyRequestId")
    @Fetch(value = FetchMode.SELECT)
    public Collection<SurveyRequestTextAnswerEntity> getSurveyRequestTextAnswersById() {
        return surveyRequestTextAnswersById;
    }

    public void setSurveyRequestTextAnswersById(Collection<SurveyRequestTextAnswerEntity> surveyRequestTextAnswersById) {
        this.surveyRequestTextAnswersById = surveyRequestTextAnswersById;
    }
}
