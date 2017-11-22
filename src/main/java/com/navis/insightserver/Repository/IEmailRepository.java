package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.EmailEntity;
import com.navis.insightserver.entity.ReportTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface IEmailRepository extends CrudRepository<EmailEntity, Long> {
}
