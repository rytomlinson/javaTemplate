package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "range_question", schema = "insight", catalog = "test_navis")
public class RangeQuestionEntity {
    private Long id;
    private Date createdAt;
    private Boolean invertedDisplay;
    private Integer maximumValue;
    private Integer minimumValue;
    private Date updatedAt;
    private I18NStringEntity i18NStringByLowRangeLabelId;
    private I18NStringEntity i18NStringByMediumRangeLabelId;
    private I18NStringEntity i18NStringByHighRangeLabelId;
    private QuestionEntity questionByQuestionId;

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
    @Column(name = "inverted_display", nullable = true)
    public Boolean getInvertedDisplay() {
        return invertedDisplay;
    }

    public void setInvertedDisplay(Boolean invertedDisplay) {
        this.invertedDisplay = invertedDisplay;
    }

    @Basic
    @Column(name = "maximum_value", nullable = true)
    public Integer getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(Integer maximumValue) {
        this.maximumValue = maximumValue;
    }

    @Basic
    @Column(name = "minimum_value", nullable = true)
    public Integer getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(Integer minimumValue) {
        this.minimumValue = minimumValue;
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

        RangeQuestionEntity that = (RangeQuestionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (invertedDisplay != null ? !invertedDisplay.equals(that.invertedDisplay) : that.invertedDisplay != null)
            return false;
        if (maximumValue != null ? !maximumValue.equals(that.maximumValue) : that.maximumValue != null) return false;
        if (minimumValue != null ? !minimumValue.equals(that.minimumValue) : that.minimumValue != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (invertedDisplay != null ? invertedDisplay.hashCode() : 0);
        result = 31 * result + (maximumValue != null ? maximumValue.hashCode() : 0);
        result = 31 * result + (minimumValue != null ? minimumValue.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "low_range_label_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByLowRangeLabelId() {
        return i18NStringByLowRangeLabelId;
    }

    public void setI18NStringByLowRangeLabelId(I18NStringEntity i18NStringByLowRangeLabelId) {
        this.i18NStringByLowRangeLabelId = i18NStringByLowRangeLabelId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "medium_range_label_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByMediumRangeLabelId() {
        return i18NStringByMediumRangeLabelId;
    }

    public void setI18NStringByMediumRangeLabelId(I18NStringEntity i18NStringByMediumRangeLabelId) {
        this.i18NStringByMediumRangeLabelId = i18NStringByMediumRangeLabelId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "high_range_label_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByHighRangeLabelId() {
        return i18NStringByHighRangeLabelId;
    }

    public void setI18NStringByHighRangeLabelId(I18NStringEntity i18NStringByHighRangeLabelId) {
        this.i18NStringByHighRangeLabelId = i18NStringByHighRangeLabelId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    public QuestionEntity getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(QuestionEntity questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }
}
