package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "selection_list", schema = "insight", catalog = "test_navis")
public class SelectionListEntity {
    private Long id;
    private Date createdAt;
    private Boolean isCustom;
    private Boolean isLibrary;
    private UUID owner;
    private Long externalId;
    private Date updatedAt;
    private I18NStringEntity i18NStringByDescriptionId;
    private I18NStringEntity i18NStringByPlaceholderId;
    private Boolean deleted;
    private Collection<SelectQuestionEntity> selectQuestionsById;
    private Collection<SelectionEntity> selectionsById;

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
    @Column(name = "is_custom", nullable = true)
    public Boolean getCustom() {
        return isCustom;
    }

    public void setCustom(Boolean custom) {
        isCustom = custom;
    }

    @Basic
    @Column(name = "is_library", nullable = true)
    public Boolean getLibrary() {
        return isLibrary;
    }

    public void setLibrary(Boolean library) {
        isLibrary = library;
    }

    @Basic
    @Column(name = "owner", nullable = false)
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

        SelectionListEntity that = (SelectionListEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (isCustom != null ? !isCustom.equals(that.isCustom) : that.isCustom != null) return false;
        if (isLibrary != null ? !isLibrary.equals(that.isLibrary) : that.isLibrary != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (isCustom != null ? isCustom.hashCode() : 0);
        result = 31 * result + (isLibrary != null ? isLibrary.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "description_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByDescriptionId() {
        return i18NStringByDescriptionId;
    }

    public void setI18NStringByDescriptionId(I18NStringEntity i18NStringByDescriptionId) {
        this.i18NStringByDescriptionId = i18NStringByDescriptionId;
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

    @Basic
    @Column(name = "deleted", nullable = false)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @OneToMany(mappedBy = "selectionListBySelectionListId")
    public Collection<SelectQuestionEntity> getSelectQuestionsById() {
        return selectQuestionsById;
    }

    public void setSelectQuestionsById(Collection<SelectQuestionEntity> selectQuestionsById) {
        this.selectQuestionsById = selectQuestionsById;
    }

    @OneToMany(mappedBy = "selectionListBySelectionListId")
    public Collection<SelectionEntity> getSelectionsById() {
        return selectionsById;
    }

    public void setSelectionsById(Collection<SelectionEntity> selectionsById) {
        this.selectionsById = selectionsById;
    }
}
