package com.zhu2chu.all.bus.render;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.jfinal.render.Render;

/**
 * 2018年2月1日 20:08:15<br>
 * 图片渲染器
 * 
 * @author ThreeX
 *
 */
public class ImageRender extends Render {

    /**要渲染到客户端去的图像文件*/
    private File imgFile;

    private static final String[] ACCEPTSUFFIX = {
        "png", "jpg", "gif"
    };

    public ImageRender(File imgFile) {
        super();
        this.imgFile = imgFile;
    }
    public ImageRender(String imgFilePath) {
        super();
        File imgFile = new File(imgFilePath);
        this.imgFile = imgFile;
    }
 
    public File getImgFile() {
        return imgFile;
    }
 
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    @Override
    public void render() {
        String suffix = checkSuffix(imgFile.getName());
        response.setContentType("image/"+suffix+";charset=UTF-8");
        try {
            FileUtils.copyFile(imgFile, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String checkSuffix(String filename) {
        if (filename!=null && !"".equals(filename)) {
            for (int i=0; i<ACCEPTSUFFIX.length; i++) {
                String a = ACCEPTSUFFIX[i];
                if (filename.contains(a)) {
                    if ("jpg".equalsIgnoreCase(a)) {
                        return "jpeg";
                    } else {
                        return a;
                    }
                }
            }
        }

        return filename;
    }

}
