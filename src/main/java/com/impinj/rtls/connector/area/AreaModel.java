package com.impinj.rtls.connector.area;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by ralemy on 8/11/16.
 * The responsibility of this object is to represent the area node received from Locate.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaModel {
    @JsonProperty
    @NotNull
    private String areaId;
    @JsonProperty
    private String parent;
    @JsonProperty
    private int[][] boundary;
    @JsonProperty
    private String level;

    public AreaModel(){}
    public String getAreaId() {
        return areaId;
    }

    public String getParent() {
        return parent;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int[][] getBoundary() {
        return boundary;
    }

    public void setBoundary(int[][] boundary) {
        this.boundary = boundary;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
