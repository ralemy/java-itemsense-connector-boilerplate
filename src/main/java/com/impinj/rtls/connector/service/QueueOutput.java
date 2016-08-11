package com.impinj.rtls.connector.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ralemy on 8/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueOutput {
    @JsonProperty
    private String epc;
    @JsonProperty
    private String area;
    @JsonProperty
    private int x;
    @JsonProperty
    private int y;
    @JsonProperty
    private String timestamp;

    public QueueOutput(String epc, String area, String timestamp,int x, int y){
        this.epc =epc;
        this.area = area;
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
    }
    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
