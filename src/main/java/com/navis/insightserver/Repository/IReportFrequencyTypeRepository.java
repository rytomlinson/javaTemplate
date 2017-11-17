package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.ReportFrequencyTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface IReportFrequencyTypeRepository extends CrudRepository<ReportFrequencyTypeEntity, Long> {

    List<ReportFrequencyTypeEntity> findAll();
}
