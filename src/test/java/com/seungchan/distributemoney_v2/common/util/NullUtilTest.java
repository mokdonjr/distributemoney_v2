package com.seungchan.distributemoney_v2.common.util;

import com.seungchan.distributemoney_v2.TestBaseWithoutContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class NullUtilTest extends TestBaseWithoutContext {

    @Test
    public void testGetOrDefaultByte() {
        Byte v = null;
        Assertions.assertEquals(0, NullUtil.getOrDefault(v));
        byte v2 = 1;
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }

    @Test
    public void testGetOrDefaultShort() {
        Short v = null;
        Assertions.assertEquals(0, NullUtil.getOrDefault(v));
        short v2 = 1;
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }

    @Test
    public void testGetOrDefaultInteger() {
        Integer v = null;
        Assertions.assertEquals(0, NullUtil.getOrDefault(v));
        int v2 = 1;
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }

    @Test
    public void testGetOrDefaultLong() {
        Long v = null;
        Assertions.assertEquals(0L, NullUtil.getOrDefault(v));
        long v2 = 1L;
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }

    @Test
    public void testGetOrDefaultDouble() {
        Double v = null;
        Assertions.assertEquals(0d, NullUtil.getOrDefault(v));
        double v2 = 1.0d;
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }

    @Test
    public void testGetOrDefaultFloat() {
        Float v = null;
        Assertions.assertEquals(0f, NullUtil.getOrDefault(v));
        float v2 = 1.0f;
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }

    @Test
    public void testGetOrDefaultString() {
        String v = null;
        Assertions.assertEquals("", NullUtil.getOrDefault(v));
        String v2 = "aa";
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }

    @Test
    public void testGetOrDefaultCharacter() {
        Character v = null;
        Assertions.assertEquals(0, NullUtil.getOrDefault(v));
        Character v2 = 'a';
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }

    @Test
    public void testGetOrDefaultDate() {
        Date v = null;
        Assertions.assertEquals(new Date(0L), NullUtil.getOrDefault(v));
        Date v2 = new Date();
        Assertions.assertEquals(v2, NullUtil.getOrDefault(v, v2));
    }
}