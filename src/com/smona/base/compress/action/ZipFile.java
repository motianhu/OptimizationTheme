package com.smona.base.compress.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.smona.base.compress.common.FileOperator;
import com.smona.base.compress.common.Util;
import com.smona.base.compress.common.ValidateImageFormat;
import com.smona.base.compress.common.ZipFileAction;

public class ZipFile {

    private static final HashMap<String, String> PNG_TO_JPG = new HashMap<String, String>();
    static {
        PNG_TO_JPG.put("pre_face_small.png", "pre_face_small.jpg");
        PNG_TO_JPG.put("pre_face.png", "pre_face.jpg");
        PNG_TO_JPG.put("pre_icon1.png", "pre_icon1.jpg");
        PNG_TO_JPG.put("pre_icon2.png", "pre_icon2.jpg");
        PNG_TO_JPG.put("home_bg.png", "home_bg.jpg");
        PNG_TO_JPG.put("pre_lockscreen.png", "pre_lockscreen.jpg");
        PNG_TO_JPG.put("zzzzz_gn_brief_lockscreen_lockpaper.png",
                "zzzzz_gn_brief_lockscreen_lockpaper.jpg");
    }

    private String mThemeName;
    private String mThemePath;
    private String mRootPath;

    public String getThemeName() {
        return mThemeName;
    }

    public ZipFile(String themeName, String themePath, String rootPath) {
        mThemeName = themeName;
        mThemePath = themePath;
        mRootPath = rootPath;
    }

    public boolean unzipFile() {
        ZipFileAction action = new ZipFileAction();
        String unZipPath = mRootPath + Util.DIR_SPLIT + Util.UNZIP
                + Util.DIR_SPLIT + mThemeName;
        createDir(unZipPath);

        try {
            action.unZip(mThemePath + Util.DIR_SPLIT + mThemeName + ".gnz",
                    unZipPath);
            return true;
        } catch (Exception e) {
            Util.printDetail("unzipFile: " + e);
            e.printStackTrace();
        }
        return false;
    }

    public void copyFileFrom() throws IOException {
        String fromSource = mRootPath + Util.DIR_SPLIT + Util.UNZIP
                + Util.DIR_SPLIT + mThemeName;
        File root = new File(fromSource);
        if (!root.exists()) {
            Util.printDetail("ERROR[copyFileFrom]: fromSource[" + fromSource
                    + "]: not exists!");
            return;
        }
        if (root.isFile()) {
            Util.printDetail("ERROR[copyFileFrom]: fromSource[" + fromSource
                    + "]: is File!");
            return;
        }

        Util.printDetail("copyFileFrom: fromSource[" + fromSource + "]");
        copyFile(root);

    }

    private void copyFile(File dir) throws IOException {
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].isDirectory()) {
                copyFile(fs[i]);
            } else if (fs[i].isFile()) {
                String path = fs[i].getPath();
                System.out.println("path: " + path);
                String format = ValidateImageFormat.validateImageType(path);
                boolean isNormalType = ValidateImageFormat.isSupportImageScaleType(path);
                if (format.equals("JPG")) {
                    ZipFileAction.copyFile(path, mRootPath + Util.DIR_SPLIT
                            + Util.PROCESS + Util.DIR_SPLIT + Util.PRE_JPG,
                            path.substring(mRootPath.length(), path.length())
                                    .replace(Util.DIR_SPLIT, Util.DIR_REPLACE)
                                    .replace(".png", ".jpg"));
                } else if (isNormalType && format.equals("PNG")) {
                    ZipFileAction.copyFile(path, mRootPath + Util.DIR_SPLIT
                            + Util.PROCESS + Util.DIR_SPLIT + Util.PRE_PNG,
                            path.substring(mRootPath.length(), path.length())
                                    .replace(Util.DIR_SPLIT, Util.DIR_REPLACE)
                                    .replace(".jpg", ".png"));
                } else {
                    String desTempFile = path.replace(Util.DIR_SPLIT
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
    }

    public void copyFileTo() {

    }

    public void zip() {

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
