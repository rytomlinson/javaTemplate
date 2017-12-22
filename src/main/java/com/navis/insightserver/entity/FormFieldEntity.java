package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "form_field", schema = "insight", catalog = "test_navis")
public class FormFieldEntity {
    private Long id;
    private Date createdAt;
    private String name;
    private String validationType;
    private Integer maxLength;
    private Integer rank;
    private Boolean required;
    private Boolean deleted;
    private Date updatedAt;
    private FormEntity formByFormId;
    private I18NStringEntity i18NStringByDisplayNameId;

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
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "validation_type", nullable = true)
    public String getValidationType() {
        return validationType;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    @Basic
    @Column(name = "max_length", nullable = true)
    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Basic
    @Column(name = "rank", nullable = false)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "required", nullable = false)
    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
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

        FormFieldEntity that = (FormFieldEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (validationType != null ? !validationType.equals(that.validationType) : that.validationType != null)
            return false;
        if (maxLength != null ? !maxLength.equals(that.maxLength) : that.maxLength != null) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (required != null ? !required.equals(that.required) : that.required != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (validationType != null ? validationType.hashCode() : 0);
        result = 31 * result + (maxLength != null ? maxLength.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "form_id", referencedColumnName = "id", nullable = false)
    public FormEntity getFormByFormId() {
        return formByFormId;
    }

    public void setFormByFormId(FormEntity formByFormId) {
        this.formByFormId = formByFormId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "display_name_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByDisplayNameId() {
        return i18NStringByDisplayNameId;
    }

    public void setI18NStringByDisplayNameId(I18NStringEntity i18NStringByDisplayNameId) {
        this.i18NStringByDisplayNameId = i18NStringByDisplayNameId;
    }
}
