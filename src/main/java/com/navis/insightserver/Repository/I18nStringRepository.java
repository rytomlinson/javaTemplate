package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.I18NStringEntity;
import com.navis.insightserver.entity.ReportTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface I18nStringRepository extends CrudRepository<I18NStringEntity, Long> {

}
