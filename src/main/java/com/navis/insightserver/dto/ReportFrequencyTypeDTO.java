package com.navis.insightserver.dto;

import com.navis.insightserver.entity.ReportFrequencyTypeEntity;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class ReportFrequencyTypeDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "user.settings.stat.event.type.notnull")
    private Long id;
    private String code;
    private String description;

    public ReportFrequencyTypeDTO() {
    }


    public ReportFrequencyTypeDTO(ReportFrequencyTypeEntity reportFrequencyTypeEntity) {
        super();
        this.id = reportFrequencyTypeEntity.getId();
        this.code = reportFrequencyTypeEntity.getCode();
        this.description = reportFrequencyTypeEntity.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
