package com.corning.jsondumps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Slf4j
public class JavaJsonDumpsTest {

    private Map<String, Object> objectMap;

    @Before
    public void setUp() throws Exception {
        objectMap = new HashMap<>();
        objectMap.put("id", 1);
        objectMap.put("name", "demoName");
        objectMap.put("values", Lists.newArrayList(1, 2, 3, 4));
    }

    @Test
    public void jsonDumps() throws JsonProcessingException {
        String result = JavaJsonDumps.dumps(objectMap);
        assertEquals("{\"id\": 1, \"name\": \"demoName\", \"values\": [1, 2, 3, 4]}", result);
    }

    @Test
    public void dumpsChinese() throws JsonProcessingException {
        objectMap.put("chinese", "中文");
        String result = JavaJsonDumps.dumps(objectMap);
        assertEquals("{\"chinese\": \"\\u4e2d\\u6587\", \"id\": 1, \"name\": \"demoName\", \"values\": [1, 2, 3, 4]}", result);
    }
}