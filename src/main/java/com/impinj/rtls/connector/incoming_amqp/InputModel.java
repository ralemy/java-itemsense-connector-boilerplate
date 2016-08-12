package com.impinj.rtls.connector.incoming_amqp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.impinj.rtls.connector.service.DateDeserializer;
import com.impinj.rtls.connector.service.ItemsenseApi;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to represent the data contract for messsages received from Itemsense AMQP broker.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputModel {
    @JsonProperty
    private String epc;

    @JsonProperty
    private String tagId;

    @JsonProperty
    private String fromZone;

    @JsonProperty
    private String toZone;

    @JsonProperty
    private String fromFloor;

    @JsonProperty
    private String toFloor;

    @JsonProperty
    private String fromFacility;

    @JsonProperty
    private String toFacility;

    @JsonProperty
    private String fromX;

    @JsonProperty
    private String fromY;

    @JsonProperty
    private String toX;

    @JsonProperty
    private String toY;

    @JsonSerialize(using = ItemsenseApi.dateSerializer.class, as = String.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty
    private Date observationTime;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @Override
    public String toString() {
        return String.format("AMQP Message: %s at %s\n", epc, formatter.format(observationTime)) +
                String.format("    from: facility %s, floor %s, zone %s , x %s, y %s\n", fromFacility, fromFloor, fromZone, fromX, fromY) +
                String.format("    To: facility %s, floor %s, zone %s , x %s, y %s\n", toFacility, toFloor, toZone, toX, toY);
    }


    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getFromZone() {
        return fromZone;
    }

    public void setFromZone(String fromZone) {
        this.fromZone = fromZone;
    }

    public String getToZone() {
        return toZone;
    }

    public void setToZone(String toZone) {
        this.toZone = toZone;
    }

    public String getFromFloor() {
        return fromFloor;
    }

    public void setFromFloor(String fromFloor) {
        this.fromFloor = fromFloor;
    }

    public String getToFloor() {
        return toFloor;
    }

    public void setToFloor(String toFloor) {
        this.toFloor = toFloor;
    }

    public String getFromFacility() {
        return fromFacility;
    }

    public void setFromFacility(String fromFacility) {
        this.fromFacility = fromFacility;
    }

    public String getToFacility() {
        return toFacility;
    }

    public void setToFacility(String toFacility) {
        this.toFacility = toFacility;
    }

    public String getFromX() {
        return fromX;
    }

    public void setFromX(String fromX) {
        this.fromX = fromX;
    }

    public String getFromY() {
        return fromY;
    }

    public void setFromY(String fromY) {
        this.fromY = fromY;
    }

    public String getToX() {
        return toX;
    }

    public void setToX(String toX) {
        this.toX = toX;
    }

    public String getToY() {
        return toY;
    }

    public void setToY(String toY) {
        this.toY = toY;
    }

    public Date getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(Date observationTime) {
        this.observationTime = observationTime;
    }
}
