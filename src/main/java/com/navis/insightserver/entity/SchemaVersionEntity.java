package com.navis.insightserver.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by darrell-shofstall on 11/29/17.
 */
@Entity
@Table(name = "schema_version", schema = "insight", catalog = "test_navis")
public class SchemaVersionEntity {
    private Integer installedRank;
    private String version;
    private String description;
    private String type;
    private String script;
    private Integer checksum;
    private String installedBy;
    private Timestamp installedOn;
    private Integer executionTime;
    private Boolean success;

    @Id
    @Column(name = "installed_rank", nullable = false)
    public Integer getInstalledRank() {
        return installedRank;
    }

    public void setInstalledRank(Integer installedRank) {
        this.installedRank = installedRank;
    }

    @Basic
    @Column(name = "version", nullable = true, length = 50)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "script", nullable = false, length = 1000)
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Basic
    @Column(name = "checksum", nullable = true)
    public Integer getChecksum() {
        return checksum;
    }

    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }

    @Basic
    @Column(name = "installed_by", nullable = false, length = 100)
    public String getInstalledBy() {
        return installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    @Basic
    @Column(name = "installed_on", nullable = false)
    public Timestamp getInstalledOn() {
        return installedOn;
    }

    public void setInstalledOn(Timestamp installedOn) {
        this.installedOn = installedOn;
    }

    @Basic
    @Column(name = "execution_time", nullable = false)
    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }

    @Basic
    @Column(name = "success", nullable = false)
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchemaVersionEntity that = (SchemaVersionEntity) o;

        if (installedRank != null ? !installedRank.equals(that.installedRank) : that.installedRank != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (script != null ? !script.equals(that.script) : that.script != null) return false;
        if (checksum != null ? !checksum.equals(that.checksum) : that.checksum != null) return false;
        if (installedBy != null ? !installedBy.equals(that.installedBy) : that.installedBy != null) return false;
        if (installedOn != null ? !installedOn.equals(that.installedOn) : that.installedOn != null) return false;
        if (executionTime != null ? !executionTime.equals(that.executionTime) : that.executionTime != null)
            return false;
        if (success != null ? !success.equals(that.success) : that.success != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = installedRank != null ? installedRank.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (checksum != null ? checksum.hashCode() : 0);
        result = 31 * result + (installedBy != null ? installedBy.hashCode() : 0);
        result = 31 * result + (installedOn != null ? installedOn.hashCode() : 0);
        result = 31 * result + (executionTime != null ? executionTime.hashCode() : 0);
        result = 31 * result + (success != null ? success.hashCode() : 0);
        return result;
    }
}
