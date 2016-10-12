package com.smona.base.compress.action;

import java.io.File;
import java.io.IOException;

import com.smona.base.compress.common.Util;
import com.smona.base.compress.common.ZipFileAction;

public class ZipAction implements IAction {
    
    public void execute(String path) {
        try {
            copyFile(path);
            zip(path);
        } catch (IOException e) {
            Util.printDetail("ERROR[executeCompress-IOException]: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            Util.printDetail("ERROR[executeCompress-Exception]: " + e);
            e.printStackTrace();
        }
    }

    private void copyFile(String path) throws IOException {
        copyJpg(path);
        copyPng(path);
    }

    private void copyJpg(String path) throws IOException {
        String postJng = path + Util.DIR_SPLIT + Util.PROCESS + Util.DIR_SPLIT
                + Util.PRE_JPG;

        File dir = new File(postJng);
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].isFile()) {
                String srcFile = fs[i].getName().replace(Util.DIR_REPLACE,
                        Util.DIR_SPLIT);
                String desTempFile = srcFile.replace(Util.DIR_SPLIT
                        + Util.UNZIP + Util.DIR_SPLIT, Util.DIR_SPLIT
                        + Util.ZIP + Util.DIR_SPLIT);

                int position = desTempFile.lastIndexOf(Util.DIR_SPLIT);
                String desFile = desTempFile.substring(position + 1,
                        desTempFile.length());
                String desFolder = desTempFile.substring(0, position);

                String filePath = fs[i].getPath();
                ZipFileAction.copyFileWithDir(filePath, desFolder, desFile);
            }
        }
    }

    private void copyPng(String path) throws IOException {
        String postJng = path + Util.DIR_SPLIT + Util.PROCESS + Util.DIR_SPLIT
                + Util.PRE_PNG;

        File dir = new File(postJng);
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].isFile()) {
                String srcFile = fs[i].getName().replace(Util.DIR_REPLACE,
                        Util.DIR_SPLIT);
                String desTempFile = srcFile.replace(Util.DIR_SPLIT
                        + Util.UNZIP + Util.DIR_SPLIT, Util.DIR_SPLIT
                        + Util.ZIP + Util.DIR_SPLIT);

                int position = desTempFile.lastIndexOf(Util.DIR_SPLIT);
                String desFile = desTempFile.substring(position + 1,
                        desTempFile.length());
                String desFolder = desTempFile.substring(0, position);

                String filePath = fs[i].getPath();
                ZipFileAction.copyFileWithDir(filePath, desFolder, desFile);
            }
        }
    }

    private void zip(String path) throws Exception {
        String target = path + Util.DIR_SPLIT + Util.TARGET;
        String zipRes = path + Util.DIR_SPLIT + Util.ZIP;

        File dir = new File(zipRes);
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].isDirectory()) {
                ZipFileAction action = new ZipFileAction();
                action.zip(fs[i].getPath(),
                        target + Util.DIR_SPLIT + fs[i].getName() + ".gnz");
            }
        }

    }
}
