package com.navis.insightserver.dto;

import com.navis.insightserver.entity.SelectionEntity;
import com.navis.insightserver.entity.TranslationEntity;

import java.util.List;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class SelectionDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "tag.id.notnull")
    private Long id;
    private String name;

    public SelectionDTO() {
    }

    public SelectionDTO(SelectionEntity selectionEntity) {
        super();
       buildDto(selectionEntity);
    }

    public SelectionDTO(SelectionEntity selectionEntity, String locale) {
        super();
        buildDto(selectionEntity, locale);
    }

    public SelectionDTO(Long copySelectionListId, SelectionEntity selectionEntity, String locale) {
        super();
        List<TranslationEntity> displayTitleEntities = (List<TranslationEntity>) selectionEntity.getI18NStringByDisplayTitleId().getTranslationsById();
        TranslationEntity displayTitleEntity = displayTitleEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null);
        this.name = displayTitleEntity.getLocalizedString();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void buildDto(SelectionEntity selectionEntity ) {
        this.id = selectionEntity.getId();
        this.name = selectionEntity.getI18NStringByDisplayTitleId().getTranslationsById().iterator().next().getLocalizedString();
    }

    private void buildDto(SelectionEntity selectionEntity, String locale) {
        List<TranslationEntity> displayTitleEntities = (List<TranslationEntity>) selectionEntity.getI18NStringByDisplayTitleId().getTranslationsById();
        TranslationEntity displayTitleEntity = displayTitleEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null);

        this.name = displayTitleEntity.getLocalizedString();
        this.id = selectionEntity.getId();
    }
}
