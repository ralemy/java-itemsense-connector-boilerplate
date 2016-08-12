package com.impinj.rtls.connector.area;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by ralemy on 8/11/16.
 * The responsibility of this class is to test the area node in Locate hierarchy
 */
public class AreaModelTest {
    private ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(AreaModelTest.class);
    @Test
    public void shouldBeAbleToDeserializeFromString() throws Exception {
        String areaStr = "{\"areaId\":\"blue\",\"parent\":\"white\",\"level\":\"room\",\"boundary\":[[1,1],[2,2],[3,3]]}";
        AreaModel area = mapper.readValue(areaStr, AreaModel.class);
        assertEquals("areaId", area.getAreaId(), "blue");
    }
    //Todo: Write test that make sure empty areaId will throw an exception.
    //Todo: Write test that makes sure empty level will throw an exception.
}