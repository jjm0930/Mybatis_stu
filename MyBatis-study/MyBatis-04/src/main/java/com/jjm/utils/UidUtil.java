package com.jjm.utils;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author jjm
 * @version 1.0
 */
public class UidUtil {
    public static String getUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
    @Test
    public void getId(){
        System.out.println(getUID());
    }
}
