package com.smona.base.compress.common;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ValidateImageFormat {

    public static String validateImageType(String filePath) {
        try {
            // get image format in a file
            File file = new File(filePath);
            // create an image input stream from the specified file
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> itr = ImageIO.getImageReaders(iis);
            while (itr.hasNext()) {
                ImageReader reader = (ImageReader) itr.next();
                String imageName = reader.getClass().getSimpleName();
                if (imageName != null) {
                    if ("JPEGImageReader".equals(imageName)) {
                        return "JPG";
                    } else if ("PNGImageReader".equals(imageName)) {
                        return "PNG";
                    } else if ("GIFImageReader".equals(imageName)) {
                        return "GIF";
                    } else if ("BMPImageReader".equals(imageName)) {
                        return "BMP";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
        }
        return "";
    }
}
