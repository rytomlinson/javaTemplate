package com.navis.insightserver.dto;

import com.navis.insightserver.entity.ReportFrequencyTypeEntity;
import com.navis.insightserver.entity.ReportTypeEntity;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class ReportTypeDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "user.settings.stat.event.type.notnull")
    private Long id;
    private String code;
    private String description;
    private ReportFrequencyTypeDTO reportFrequencyTypeDTO;

    public ReportTypeDTO() {
    }


    public ReportTypeDTO(ReportTypeEntity reportTypeEntity) {
        super();
        ReportFrequencyTypeEntity reportFrequencyTypeEntity;

        this.id = reportTypeEntity.getId();
        this.code = reportTypeEntity.getCode();
        this.description = reportTypeEntity.getDescription();
        reportFrequencyTypeEntity = reportTypeEntity.getReportFrequencyTypeByReportFrequencyTypeId();
        this.reportFrequencyTypeDTO = new ReportFrequencyTypeDTO(reportFrequencyTypeEntity);
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

    public ReportFrequencyTypeDTO getReportFrequencyTypeDTO() {
        return reportFrequencyTypeDTO;
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

    public void setReportFrequencyTypeDTO(ReportFrequencyTypeDTO reportFrequencyTypeDTO) {
        this.reportFrequencyTypeDTO = reportFrequencyTypeDTO;
    }
}
