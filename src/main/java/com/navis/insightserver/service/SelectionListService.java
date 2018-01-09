package com.navis.insightserver.service;

import com.navis.insightserver.Repository.*;
import com.navis.insightserver.dto.ResourceNotFoundExceptionDTO;
import com.navis.insightserver.dto.SelectionDTO;
import com.navis.insightserver.dto.SelectionListDTO;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.entity.*;
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
    private SelectionRepository selectionRepository;

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

    @Override
    public void deleteSelectionList(UUID propertyId, Long selectionListId) {
        log.debug("In deleteSelectionList Service:");

        SelectionListEntity selectionListEntity = validateSelectionList(propertyId, selectionListId);

        selectionListEntity.setDeleted(true);
        selectionListRepository.save(selectionListEntity);
    }

    @Override
    public Long upsertSelectionList(UUID propertyID, SelectionListDTO selectionListDTO, String locale) {
        log.debug("In upsertSelectionList Service:");
        Long selectionListId = selectionListDTO.getId();
        Long descriptionId;
        SelectionListEntity selectionListEntity;

        if(null != selectionListId) {
            selectionListEntity = validateSelectionList(propertyID, selectionListId);

            List<TranslationEntity> nameEntities = (List<TranslationEntity>) selectionListEntity.getI18NStringByDescriptionId().getTranslationsById();
            TranslationEntity nameEntity = nameEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null);

            descriptionId = (!selectionListDTO.getName().equals(nameEntity.getLocalizedString())
                    ? translationRepository.createTranslation(selectionListDTO.getName())
                    : selectionListEntity.getI18NStringByDescriptionId().getId());
            selectionListEntity.setI18NStringByDescriptionId(i18nStringRepository.findOne(descriptionId));
        } else {
            descriptionId = translationRepository.createTranslation(selectionListDTO.getName());

            selectionListEntity = convertToEntity(propertyID, selectionListDTO, locale);
            selectionListEntity.setI18NStringByDescriptionId(i18nStringRepository.findOne(descriptionId));
        }

        selectionListRepository.save(selectionListEntity);

        return selectionListEntity.getId();
    }

    @Override
    public SelectionDTO getSelectionListItem(UUID propertyId, Long selectionListId, Long itemId, String locale) {
        log.debug("In getSelectionListItem Service:");
        SelectionEntity selectionEntity = validateSelection(selectionListId, itemId);
        return convertToDto(selectionEntity);
    }

    @Override
    public List<SelectionDTO> getSelectionListItems(UUID propertyId, Long selectionListId, String locale) {
        log.debug("In getSelectionListItems Service:");
        SelectionListEntity selectionListEntity = validateSelectionList(propertyId, selectionListId);

        List<SelectionEntity> selectionEntityList = (List<SelectionEntity>) selectionListEntity.getSelectionsById();

        List<SelectionDTO> selectionDTOList = selectionEntityList.stream()
                .filter(item -> !item.getDeleted())
                .map(item -> convertToDto(item, locale)).collect(Collectors.toList());

        return selectionDTOList;
    }

    @Override
    public void deleteSelectionListItem(UUID propertyId, Long selectionListId, Long itemId) {
        log.debug("In deleteSelectionListItem Service:");
        SelectionEntity selectionEntity = validateSelection(selectionListId, itemId);
        selectionEntity.setDeleted(true);
        selectionRepository.save(selectionEntity);
    }

    @Override
    public Long upsertSelectionListItem(UUID propertyID, Long selectionListId, SelectionDTO selectionDTO, String locale) {
        log.debug("In upsertSelectionListItem Service:");
        Long selectionId = selectionDTO.getId();
        Long descriptionId;
        SelectionEntity selectionEntity;

        if(null != selectionId) {
            selectionEntity = validateSelection(selectionListId, selectionId);

            List<TranslationEntity> nameEntities = (List<TranslationEntity>) selectionEntity.getI18NStringByDisplayTitleId().getTranslationsById();
            TranslationEntity nameEntity = nameEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null);

            descriptionId = (!selectionDTO.getName().equals(nameEntity.getLocalizedString())
                    ? translationRepository.createTranslation(selectionDTO.getName())
                    : selectionEntity.getI18NStringByDisplayTitleId().getId());
            selectionEntity.setI18NStringByDisplayTitleId(i18nStringRepository.findOne(descriptionId));
        } else {
            descriptionId = translationRepository.createTranslation(selectionDTO.getName());

            selectionEntity = convertToEntity(selectionListId, selectionDTO, locale);
            selectionEntity.setI18NStringByDisplayTitleId(i18nStringRepository.findOne(descriptionId));
        }

        selectionRepository.save(selectionEntity);

        return selectionEntity.getId();
    }

    private SelectionEntity convertToEntity(Long selectionListId, SelectionDTO selectionDTO, String locale) {

            Date now = new Date();
            SelectionEntity selectionEntity = new SelectionEntity();
            selectionEntity.setCreatedAt(now);
            selectionEntity.setUpdatedAt(now);
            selectionEntity.setDeleted(false);
            selectionEntity.setSelectionListBySelectionListId(selectionListRepository.findOne(selectionListId));

            return selectionEntity;
    }

    private SelectionDTO convertToDto(SelectionEntity selectionEntity) {

        SelectionDTO selectionDTO = new SelectionDTO(selectionEntity);

        return selectionDTO;
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

    private SelectionDTO convertToDto(SelectionEntity selectionEntity, String locale) {


        SelectionDTO selectionDTO = new SelectionDTO(selectionEntity, locale);
        return selectionDTO;
    }

    private SelectionListEntity validateSelectionList(UUID owner , Long selectionListId) {

        SelectionListEntity selectionListEntity = selectionListRepository.findByOwnerAndId(owner, selectionListId);

        if (null == selectionListEntity) {

            throw new ResourceNotFoundExceptionDTO(selectionListId.toString(), "selectionList.id.invalid");
        } else {
            return selectionListEntity;
        }
    }

    private SelectionEntity validateSelection(Long selectionListId, Long itemId) {

        SelectionEntity selectionEntity = selectionRepository.findBySelectionListBySelectionListId_IdAndId(selectionListId, itemId);

        if (null == selectionEntity) {

            throw new ResourceNotFoundExceptionDTO(itemId.toString(), "selection.id.invalid");
        } else {
            return selectionEntity;
        }
    }

    private SelectionListEntity convertToEntity(UUID owner, SelectionListDTO selectionListDTO, String locale) {
        Date now = new Date();
        Boolean isNavis = (uuid.equals(owner)) ? true : false;
        SelectionListEntity selectionListEntity = new SelectionListEntity();
        selectionListEntity.setOwner(owner);
        selectionListEntity.setCreatedAt(now);
        selectionListEntity.setUpdatedAt(now);
        selectionListEntity.setLibrary(isNavis);
        selectionListEntity.setDeleted(false);
        selectionListEntity.setCustom(false);

        return selectionListEntity;
    }

}
