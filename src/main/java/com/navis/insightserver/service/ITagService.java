package com.navis.insightserver.service;

import com.navis.insightserver.dto.TagDTO;

import java.util.List;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
public interface ITagService {

    List<TagDTO> getTags();
}
