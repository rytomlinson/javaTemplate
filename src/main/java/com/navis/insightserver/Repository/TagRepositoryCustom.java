package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 11/20/17.
 */
public interface TagRepositoryCustom {

   List<TagTagEntity> getDepartmentTagsByOwner(UUID owner, Long departmentTagId);

   TagEntity getDepartmentTag(UUID owner);
}
