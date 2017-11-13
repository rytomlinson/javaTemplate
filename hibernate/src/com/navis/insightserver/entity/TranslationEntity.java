package com.navis.insightserver.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/10/17.
 */
@Entity
@Table(name = "translation", schema = "insight", catalog = "test_navis")
public class TranslationEntity {
    private long id;
    private Date createdAt;
    private String locale;
    private String localizedString;
    private Date updatedAt;
    private I18NStringEntity i18NStringByI18NStringId;

    public void setLocale(Date locale) {
        this.locale = locale;
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "locale")
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Basic
    @Column(name = "localized_string")
    public String getLocalizedString() {
        return localizedString;
    }

    public void setLocalizedString(String localizedString) {
        this.localizedString = localizedString;
    }

    @Basic
    @Column(name = "updated_at")
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

        TranslationEntity that = (TranslationEntity) o;

        if (id != that.id) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (locale != null ? !locale.equals(that.locale) : that.locale != null) return false;
        if (localizedString != null ? !localizedString.equals(that.localizedString) : that.localizedString != null)
            return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (localizedString != null ? localizedString.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "i18n_string_id", referencedColumnName = "id", nullable = false, table = ""), @JoinColumn(name = "i18n_string_id", referencedColumnName = "id", nullable = false)})
    @JoinColumns(@JoinColumn(name = "i18n_string_id", referencedColumnName = "id", nullable = false))
    public I18NStringEntity getI18NStringByI18NStringId() {
        return i18NStringByI18NStringId;
    }

    public void setI18NStringByI18NStringId(I18NStringEntity i18NStringByI18NStringId) {
        this.i18NStringByI18NStringId = i18NStringByI18NStringId;
    }
}
