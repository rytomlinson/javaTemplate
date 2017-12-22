package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "question_group_member_question", schema = "insight", catalog = "test_navis")
public class QuestionGroupMemberQuestionEntity {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private QuestionEntity questionByGroupMemberQuestionId;
    private QuestionEntity questionByGroupQuestionId;

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

        QuestionGroupMemberQuestionEntity that = (QuestionGroupMemberQuestionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "group_member_question_id", referencedColumnName = "id", nullable = false)
    public QuestionEntity getQuestionByGroupMemberQuestionId() {
        return questionByGroupMemberQuestionId;
    }

    public void setQuestionByGroupMemberQuestionId(QuestionEntity questionByGroupMemberQuestionId) {
        this.questionByGroupMemberQuestionId = questionByGroupMemberQuestionId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "group_question_id", referencedColumnName = "id", nullable = false)
    public QuestionEntity getQuestionByGroupQuestionId() {
        return questionByGroupQuestionId;
    }

    public void setQuestionByGroupQuestionId(QuestionEntity questionByGroupQuestionId) {
        this.questionByGroupQuestionId = questionByGroupQuestionId;
    }
}
