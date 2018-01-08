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
    public List<SelectionListDTO> getSelectionLists(UUID propertyId) {
        log.debug("In getSelectionLists Service:");
        return buildSelectionListsDTO(propertyId);
    }



    private List<SelectionListDTO> buildSelectionListsDTO(UUID propertyId) {
        List<UUID> owners = new ArrayList<>();
        owners.add(propertyId);
        owners.add(uuid);

        List<SelectionListEntity> list = selectionListRepository.findByOwnerInAndDeletedFalse(owners);

        List<SelectionListDTO> listDto = list.stream().map(item -> convertToDto(item)).collect(Collectors.toList());

        return listDto;
    }



    private SelectionListDTO convertToDto(SelectionListEntity selectionListEntity) {


        SelectionListDTO selectionListDTO = new SelectionListDTO(selectionListEntity);
        return selectionListDTO;
    }

}
