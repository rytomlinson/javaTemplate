package com.navis.insightserver.service;

import com.navis.insightserver.dto.SelectionDTO;
import com.navis.insightserver.dto.SelectionListDTO;
import com.navis.insightserver.dto.TagDTO;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
public interface ISelectionListService {

    List<SelectionListDTO> getSelectionLists(UUID propertyId, String locale);

    SelectionListDTO getSelectionList(UUID propertyId, Long selectionListId, String locale);

    void deleteSelectionList(UUID propertyId, Long selectionListId);

    Long upsertSelectionList(UUID propertyID, SelectionListDTO selectionListDTO, String locale);

    SelectionDTO getSelectionListItem(UUID propertyId, Long selectionListId, Long itemId, String locale);

    List<SelectionDTO> getSelectionListItems(UUID propertyId, Long selectionListId, String locale);

}
