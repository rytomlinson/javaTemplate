package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "selection", schema = "insight", catalog = "test_navis")
public class SelectionEntity {
    private Long id;
    private Date createdAt;
    private Long externalId;
    private Date updatedAt;
    private I18NStringEntity i18NStringByDisplayTitleId;
    private I18NStringEntity i18NStringBySemanticTitleId;
    private Boolean deleted;

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

        SelectionEntity that = (SelectionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "display_title_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByDisplayTitleId() {
        return i18NStringByDisplayTitleId;
    }

    public void setI18NStringByDisplayTitleId(I18NStringEntity i18NStringByDisplayTitleId) {
        this.i18NStringByDisplayTitleId = i18NStringByDisplayTitleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "semantic_title_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringBySemanticTitleId() {
        return i18NStringBySemanticTitleId;
    }

    public void setI18NStringBySemanticTitleId(I18NStringEntity i18NStringBySemanticTitleId) {
        this.i18NStringBySemanticTitleId = i18NStringBySemanticTitleId;
    }

    @Basic
    @Column(name = "deleted", nullable = false)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
