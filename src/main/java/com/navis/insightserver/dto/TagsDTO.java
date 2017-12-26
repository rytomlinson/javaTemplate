package com.navis.insightserver.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class TagsDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private List<TagDTO> tags = new ArrayList<>();


    public TagsDTO() {
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }
}
