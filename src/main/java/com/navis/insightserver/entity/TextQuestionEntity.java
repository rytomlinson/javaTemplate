package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "text_question", schema = "insight", catalog = "test_navis")
public class TextQuestionEntity {
    private Long id;
    private Date createdAt;
    private Integer textColumns;
    private Integer textRows;
    private Date updatedAt;
    private I18NStringEntity i18NStringByPlaceholderId;
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
    @Column(name = "text_columns", nullable = true)
    public Integer getTextColumns() {
        return textColumns;
    }

    public void setTextColumns(Integer textColumns) {
        this.textColumns = textColumns;
    }

    @Basic
    @Column(name = "text_rows", nullable = true)
    public Integer getTextRows() {
        return textRows;
    }

    public void setTextRows(Integer textRows) {
        this.textRows = textRows;
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

        TextQuestionEntity that = (TextQuestionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (textColumns != null ? !textColumns.equals(that.textColumns) : that.textColumns != null) return false;
        if (textRows != null ? !textRows.equals(that.textRows) : that.textRows != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (textColumns != null ? textColumns.hashCode() : 0);
        result = 31 * result + (textRows != null ? textRows.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "placeholder_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByPlaceholderId() {
        return i18NStringByPlaceholderId;
    }

    public void setI18NStringByPlaceholderId(I18NStringEntity i18NStringByPlaceholderId) {
        this.i18NStringByPlaceholderId = i18NStringByPlaceholderId;
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
