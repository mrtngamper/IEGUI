package com.example.iegui.util;

import java.io.File;

/**
 * Utility class to create platform independent paths
 */
public abstract class paths {
    public static String independent(String path){
        return new File(path).getAbsolutePath();
    }
}
