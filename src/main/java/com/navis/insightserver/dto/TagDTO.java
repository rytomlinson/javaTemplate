package com.navis.insightserver.dto;

import com.navis.insightserver.entity.TagEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class TagDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "user.settings.stat.event.type.notnull")
    private Long id;
    private String type;
    private String name;

    public TagDTO() {
    }

    public TagDTO(TagEntity tagEntity) {

        super();
        this.id = tagEntity.getId();
        this.type = tagEntity.getType();
        this.name = tagEntity.getI18NStringByNameId().getTranslationsById().iterator().next().getLocalizedString();
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
