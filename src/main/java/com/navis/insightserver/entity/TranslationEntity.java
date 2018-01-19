package com.navis.insightserver.entity;

import com.navis.insightserver.pgtypes.LanguageLocale;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

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
    private LanguageLocale locale;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "locale", columnDefinition = "language_locale")
    @Type(type = "com.navis.insightserver.pgtypes.PostgreSQLEnumType")
    public LanguageLocale getLocale() {
        return locale;
    }

    public void setLocale(LanguageLocale locale) {
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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
