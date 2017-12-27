package com.navis.insightserver.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "translation", schema = "insight", catalog = "test_navis")

@NamedStoredProcedureQuery(
        name = "createTranslation"
        , procedureName = "createtranslation"
        , parameters = {
        @StoredProcedureParameter( name = "localized_string" , type = String.class , mode = ParameterMode.IN)
        , @StoredProcedureParameter( name = "locale" , type = String.class , mode = ParameterMode.IN)
        , @StoredProcedureParameter( name = "id" , type = Long.class , mode = ParameterMode.OUT)
}
)

public class TranslationEntity {
    private Long id;
    private Date createdAt;
    private String locale;
    private String localizedString;
    private Date updatedAt;
    private I18NStringEntity i18NStringByI18NStringId;

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
    @Column(name = "locale", nullable = false)
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Basic
    @Column(name = "localized_string", nullable = false, length = -1)
    public String getLocalizedString() {
        return localizedString;
    }

    public void setLocalizedString(String localizedString) {
        this.localizedString = localizedString;
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

        TranslationEntity that = (TranslationEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (locale != null ? !locale.equals(that.locale) : that.locale != null) return false;
        if (localizedString != null ? !localizedString.equals(that.localizedString) : that.localizedString != null)
            return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (localizedString != null ? localizedString.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "i18n_string_id", referencedColumnName = "id", nullable = false)
    public I18NStringEntity getI18NStringByI18NStringId() {
        return i18NStringByI18NStringId;
    }

    public void setI18NStringByI18NStringId(I18NStringEntity i18NStringByI18NStringId) {
        this.i18NStringByI18NStringId = i18NStringByI18NStringId;
    }
}
