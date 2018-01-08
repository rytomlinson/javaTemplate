package com.navis.insightserver.service;

import com.navis.insightserver.dto.SelectionListDTO;
import com.navis.insightserver.dto.TagDTO;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
public interface ISelectionListService {

    List<SelectionListDTO> getSelectionLists(UUID propertyId);
}
