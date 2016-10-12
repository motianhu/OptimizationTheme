package com.smona.base.compress.common;

public class Util {

    public static final String SOURCE = "source";
    public static final String TARGET = "target";

    public static final String PROCESS = "process";
    
    public static final String PRE_JPG = "prejpg";
    public static final String POST_JPG = "postjpg";

    public static final String PRE_PNG = "prepng";
    public static final String POST_PNG = "postpng";

    public static final String UNZIP = "unzip";
    public static final String ZIP = "zip";

    public static final String DIR_SPLIT = "/";

    public static final String DIR_REPLACE = "--";
    
    public static void printDetail(String msg) {
        Logger.printDetail(msg);
    }
    
    public static void printReport(String msg) {
        Logger.printReport(msg);
    }
}
