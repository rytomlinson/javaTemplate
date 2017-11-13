package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/10/17.
 */
@Entity
@Table(name = "i18n_string", schema = "insight", catalog = "test_navis")
public class I18NStringEntity {
    private long id;
    private Date createdAt;
    private Date updatedAt;
    private Collection<TagEntity> tagsById;
    private Collection<TagEntity> tagsById_0;
    private Collection<TranslationEntity> translationsById;

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

        I18NStringEntity that = (I18NStringEntity) o;

        if (id != that.id) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "i18NStringByNameId")
    public Collection<TagEntity> getTagsById() {
        return tagsById;
    }

    public void setTagsById(Collection<TagEntity> tagsById) {
        this.tagsById = tagsById;
    }

    @OneToMany(mappedBy = "i18NStringByDescriptionId")
    public Collection<TagEntity> getTagsById_0() {
        return tagsById_0;
    }

    public void setTagsById_0(Collection<TagEntity> tagsById_0) {
        this.tagsById_0 = tagsById_0;
    }

    @OneToMany(mappedBy = "i18NStringByI18NStringId")
    public Collection<TranslationEntity> getTranslationsById() {
        return translationsById;
    }

    public void setTranslationsById(Collection<TranslationEntity> translationsById) {
        this.translationsById = translationsById;
    }
}
