package com.navis.insightserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.navis.insightserver.entity.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class SelectionListDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "tag.id.notnull")
    private Long id;
    private String name;
    private Boolean isLibrary;
    private Boolean isActive;
    private UUID ownerId;
    @Valid
    private List<SelectionDTO> answers = new ArrayList<>();

    public SelectionListDTO() {
    }

    public SelectionListDTO(SelectionListEntity selectionListEntity, String locale) {
        super();
       buildDto(selectionListEntity, locale);
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

    @JsonProperty(value = "isActive")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @JsonProperty(value = "isLibrary")
    public Boolean getLibrary() {
        return isLibrary;
    }

    public void setLibrary(Boolean library) {
        isLibrary = library;
    }

    public List<SelectionDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<SelectionDTO> answers) {
        this.answers = answers;
    }

    private void buildDto(SelectionListEntity selectionListEntity, String locale ) {
        this.id = selectionListEntity.getId();

        List<TranslationEntity> displayTitleEntities = (List<TranslationEntity>) selectionListEntity.getI18NStringByDescriptionId().getTranslationsById();

        this.name = super.returnTranslationForLocale(displayTitleEntities, locale);
        this.ownerId = selectionListEntity.getOwner();
        this.isLibrary = selectionListEntity.getLibrary();
        this.isActive = (null != selectionListEntity.getSelectQuestionsById() && !selectionListEntity.getSelectQuestionsById().isEmpty()) ? true : false;

        List<SelectionEntity> selectionEntityList = (List<SelectionEntity>) selectionListEntity.getSelectionsById();

        List<SelectionDTO> selectionDTOList = (null != selectionEntityList)
                ? selectionEntityList.stream().filter(item -> !item.getDeleted()).map(item -> convertToDto(item, locale)).collect(Collectors.toList())
                : null;

        this.answers = selectionDTOList;
    }

    private SelectionDTO convertToDto(SelectionEntity selectionEntity, String locale) {
        SelectionDTO selectionDTO = new SelectionDTO(selectionEntity, locale);

        return selectionDTO;
    }
}
