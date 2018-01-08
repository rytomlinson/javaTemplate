package com.navis.insightserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.navis.insightserver.entity.SelectionEntity;
import com.navis.insightserver.entity.SelectionListEntity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}
