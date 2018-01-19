package com.navis.insightserver.dto;

import com.navis.insightserver.entity.TranslationEntity;
import com.navis.insightserver.pgtypes.LanguageLocale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrell-shofstall on 8/15/17.
 */
public class BaseDTO  implements java.io.Serializable {


   private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String returnTranslationForLocale(List<TranslationEntity> translationEntities, String locale) {
        String value = null;
        String adjustedLocale = locale.replaceAll("-", "_");

        if(null != translationEntities && !translationEntities.isEmpty()) {
            TranslationEntity translationEntity = translationEntities.stream().filter(e -> e.getLocale().equals(LanguageLocale.valueOf(adjustedLocale))).findFirst().orElse(null);
            value = (null != translationEntity) ? translationEntity.getLocalizedString() : null;
        }

        return value;
    }
}
