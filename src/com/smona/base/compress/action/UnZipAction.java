package com.smona.base.compress.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.smona.base.compress.common.FileOperator;
import com.smona.base.compress.common.Util;

public class UnZipAction implements IAction {

    private List<ZipFile> zipFiles = new ArrayList<ZipFile>();

    public void execute(String path) {
        createDirs(path);
        unzip(path);
    }

    private void createDirs(String path) {
        createUnzip(path);
        createZip(path);
    }

    private void createZip(String path) {
        createDir(path + Util.DIR_SPLIT + Util.ZIP);
        createDir(path + Util.DIR_SPLIT + Util.TARGET);
    }

    private void createUnzip(String path) {
        createDir(path + Util.DIR_SPLIT + Util.UNZIP);
        createDir(path + Util.DIR_SPLIT + Util.PROCESS + Util.DIR_SPLIT
                + Util.PRE_JPG); // /process/prejpg
        createDir(path + Util.DIR_SPLIT + Util.PROCESS + Util.DIR_SPLIT
                + Util.PRE_PNG);// /process/prepng
    }

    private void unzip(String path) {
        listTheme(path);
        unzips();
    }

    private void listTheme(String path) {
        String themeResPath = path + Util.DIR_SPLIT + Util.SOURCE;

        Util.printDetail("listTheme themeResPath: " + themeResPath);
        Util.printDetail("listTheme path: " + path);

        File rootFile = new File(themeResPath);
        File[] files = rootFile.listFiles();
        ZipFile zipFile;
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }

            if (file.isFile() && file.getName().endsWith(".gnz")) {
                zipFile = new ZipFile(file.getName().substring(0,
                        file.getName().length() - 4), themeResPath, path);
                zipFiles.add(zipFile);
            }
        }
    }

    private void unzips() {
        for (ZipFile zipFile : zipFiles) {
            zipFile.unzipFile();
            try {
                zipFile.copyFileFrom();
                Util.printReport("SUCCESS[unzips copyFileFrom]: "
                        + zipFile.getThemeName());
            } catch (IOException e) {
                Util.printDetail("ERROR[unzips copyFileFrom]: "
                        + zipFile.getThemeName());
                e.printStackTrace();
            }
        }
    }

    private static void createDir(String path) {
        FileOperator.deleteDirectory(new File(path));
        mkdirs(path);
    }

    private static File mkdirs(String target) {
        File tempDir = new File(target);
        tempDir.mkdirs();
        return tempDir;
    }
}
