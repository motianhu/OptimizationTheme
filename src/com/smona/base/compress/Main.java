package com.smona.base.compress;

import com.smona.base.compress.action.IAction;
import com.smona.base.compress.action.UnZipAction;
import com.smona.base.compress.action.ZipAction;
import com.smona.base.compress.common.Logger;
import com.smona.base.compress.common.Util;

public class Main {

    public static void main(String[] args) {
        String encode = System.getProperty("file.encoding");
        Util.printDetail(encode);
        Logger.init();
        String path = System.getProperty("user.dir");
        Util.printDetail(path);
        action(args == null || args.length == 0 ? "unzip" : args[0], path);
    }

    private static void action(String cmd, String path) {
        if ("unzip".equals(cmd)) {
            IAction unzip = new UnZipAction();
            unzip.execute(path);
        } else if ("zip".equals(cmd)) {
            IAction zip = new ZipAction();
            zip.execute(path);
        }
    }
}
