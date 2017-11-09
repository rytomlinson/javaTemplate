package com.navis.insightserver.dto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class AccountDTO extends BaseDTO{

    private static final long serialVersionUID = 1L;

private String propertiesName;
private String propertiesPropertyUuid;
private String realmsName;
private String realmsRealmUuid;
private String realmsLegacyAcctNbr;

    public String getPropertiesName() {
        return propertiesName;
    }

    public String getPropertiesPropertyUuid() {
        return propertiesPropertyUuid;
    }

    public String getRealmsRealmUuid() {
        return realmsRealmUuid;
    }

    public String getRealmsLegacyAcctNbr() {
        return realmsLegacyAcctNbr;
    }

    public String getRealmsName() {
        return realmsName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public void setPropertiesPropertyUuid(String propertiesPropertyUuid) {
        this.propertiesPropertyUuid = propertiesPropertyUuid;
    }

    public void setRealmsRealmUuid(String realmsRealmUuid) {
        this.realmsRealmUuid = realmsRealmUuid;
    }

    public void setRealmsLegacyAcctNbr(String realmsLegacyAcctNbr) {
        this.realmsLegacyAcctNbr = realmsLegacyAcctNbr;
    }

    public void setRealmsName(String realmsName) {
        this.realmsName = realmsName;
    }

    public AccountDTO() {
    }

    public AccountDTO(Object account) {
        LinkedHashMap entry = (LinkedHashMap) account;
        setPropertiesName((String) entry.get("properties/name"));
        setPropertiesPropertyUuid((String) entry.get("properties/property_uuid"));
        setRealmsName((String) entry.get("realms/name"));
        setRealmsRealmUuid((String) entry.get("realms/realm_uuid"));
        setRealmsLegacyAcctNbr((String) entry.get("realms/legacy_acct_nbr"));
    }
}
