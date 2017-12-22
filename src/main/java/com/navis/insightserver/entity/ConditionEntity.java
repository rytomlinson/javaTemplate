package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "condition", schema = "insight", catalog = "test_navis")
public class ConditionEntity {
    private Long id;
    private Date createdAt;
    private Boolean equality;
    private String selection;
    private Boolean booleanQuestionSelection;
    private Integer rangeQuestionMinimumValue;
    private Integer rangeQuestionMaximumValue;
    private Long externalId;
    private Boolean deleted;
    private Date updatedAt;

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
    @Column(name = "equality", nullable = true)
    public Boolean getEquality() {
        return equality;
    }

    public void setEquality(Boolean equality) {
        this.equality = equality;
    }

    @Basic
    @Column(name = "selection", nullable = true)
    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    @Basic
    @Column(name = "boolean_question_selection", nullable = true)
    public Boolean getBooleanQuestionSelection() {
        return booleanQuestionSelection;
    }

    public void setBooleanQuestionSelection(Boolean booleanQuestionSelection) {
        this.booleanQuestionSelection = booleanQuestionSelection;
    }

    @Basic
    @Column(name = "range_question_minimum_value", nullable = true)
    public Integer getRangeQuestionMinimumValue() {
        return rangeQuestionMinimumValue;
    }

    public void setRangeQuestionMinimumValue(Integer rangeQuestionMinimumValue) {
        this.rangeQuestionMinimumValue = rangeQuestionMinimumValue;
    }

    @Basic
    @Column(name = "range_question_maximum_value", nullable = true)
    public Integer getRangeQuestionMaximumValue() {
        return rangeQuestionMaximumValue;
    }

    public void setRangeQuestionMaximumValue(Integer rangeQuestionMaximumValue) {
        this.rangeQuestionMaximumValue = rangeQuestionMaximumValue;
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

        ConditionEntity that = (ConditionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (equality != null ? !equality.equals(that.equality) : that.equality != null) return false;
        if (selection != null ? !selection.equals(that.selection) : that.selection != null) return false;
        if (booleanQuestionSelection != null ? !booleanQuestionSelection.equals(that.booleanQuestionSelection) : that.booleanQuestionSelection != null)
            return false;
        if (rangeQuestionMinimumValue != null ? !rangeQuestionMinimumValue.equals(that.rangeQuestionMinimumValue) : that.rangeQuestionMinimumValue != null)
            return false;
        if (rangeQuestionMaximumValue != null ? !rangeQuestionMaximumValue.equals(that.rangeQuestionMaximumValue) : that.rangeQuestionMaximumValue != null)
            return false;
        if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (equality != null ? equality.hashCode() : 0);
        result = 31 * result + (selection != null ? selection.hashCode() : 0);
        result = 31 * result + (booleanQuestionSelection != null ? booleanQuestionSelection.hashCode() : 0);
        result = 31 * result + (rangeQuestionMinimumValue != null ? rangeQuestionMinimumValue.hashCode() : 0);
        result = 31 * result + (rangeQuestionMaximumValue != null ? rangeQuestionMaximumValue.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}
