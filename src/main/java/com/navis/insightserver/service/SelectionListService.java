package com.navis.insightserver.service;

import com.navis.insightserver.Repository.*;
import com.navis.insightserver.dto.ResourceNotFoundExceptionDTO;
import com.navis.insightserver.dto.SelectionListDTO;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.entity.SelectionListEntity;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;
import com.navis.insightserver.entity.TranslationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */

@Service
@Transactional
public class SelectionListService implements ISelectionListService {
    private static final Logger log = LoggerFactory.getLogger(SelectionListService.class);

    @Value("${navis.prop.uuid}")
    private UUID uuid;

    @Autowired
    private SelectionListRepository selectionListRepository;

    @Autowired
    private SelectQuestionRepository selectQuestionRepository;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private I18nStringRepository i18nStringRepository;

    @Override
    public List<SelectionListDTO> getSelectionLists(UUID propertyId, String locale) {
        log.debug("In getSelectionLists Service:");
        return buildSelectionListsDTO(propertyId, locale);
    }

    @Override
    public SelectionListDTO getSelectionList(UUID propertyId, Long selectionListId, String locale) {
        log.debug("In getSelectionList Service:");
        return buildSelectionListDTO(propertyId, selectionListId, locale);
    }

    private SelectionListDTO buildSelectionListDTO(UUID propertyId, Long selectionListId, String locale) {

        SelectionListEntity selectionListEntity = validateSelectionList(propertyId, selectionListId);



        return convertToDto(selectionListEntity, locale);
    }


    private List<SelectionListDTO> buildSelectionListsDTO(UUID propertyId, String locale) {
        List<UUID> owners = new ArrayList<>();
        owners.add(propertyId);
        owners.add(uuid);

        List<SelectionListEntity> list = selectionListRepository.findByOwnerInAndDeletedFalse(owners);

        List<SelectionListDTO> listDto = list.stream().map(item -> convertToDto(item, locale)).collect(Collectors.toList());

        return listDto;
    }



    private SelectionListDTO convertToDto(SelectionListEntity selectionListEntity, String locale) {


        SelectionListDTO selectionListDTO = new SelectionListDTO(selectionListEntity, locale);
        return selectionListDTO;
    }

    private SelectionListEntity validateSelectionList(UUID owner , Long selectionListId) {

        SelectionListEntity selectionListEntity = selectionListRepository.findByOwnerAndId(owner, selectionListId);

        if (null == selectionListEntity) {

            throw new ResourceNotFoundExceptionDTO(selectionListId.toString(), "selectionList.id.invalid");
        } else {
            return selectionListEntity;
        }
    }

}
