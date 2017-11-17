package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.ReportTypeEntity;
import com.navis.insightserver.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface IReportTypeRepository extends CrudRepository<ReportTypeEntity, Long> {

    List<ReportTypeEntity> findAll();
}
