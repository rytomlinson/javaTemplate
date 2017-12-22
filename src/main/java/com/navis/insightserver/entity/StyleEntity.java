package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "style", schema = "insight", catalog = "test_navis")
public class StyleEntity {
    private Long id;
    private Date createdAt;
    private Long externalId;
    private Date updatedAt;
    private ImageEntity imageByImageId;
    private I18NStringEntity i18NStringByNameId;
    private I18NStringEntity i18NStringByDescriptionId;

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

        StyleEntity that = (StyleEntity) o;

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
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    public ImageEntity getImageByImageId() {
        return imageByImageId;
    }

    public void setImageByImageId(ImageEntity imageByImageId) {
        this.imageByImageId = imageByImageId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "name_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByNameId() {
        return i18NStringByNameId;
    }

    public void setI18NStringByNameId(I18NStringEntity i18NStringByNameId) {
        this.i18NStringByNameId = i18NStringByNameId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "description_id", referencedColumnName = "id")
    public I18NStringEntity getI18NStringByDescriptionId() {
        return i18NStringByDescriptionId;
    }

    public void setI18NStringByDescriptionId(I18NStringEntity i18NStringByDescriptionId) {
        this.i18NStringByDescriptionId = i18NStringByDescriptionId;
    }
}
