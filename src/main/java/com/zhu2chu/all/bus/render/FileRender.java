package com.zhu2chu.all.bus.render;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;

import com.jfinal.core.JFinal;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.jfinal.render.RenderFactory;

/**
 * 2017年7月18日 16:29:22
 * 解决renderFile()在ie下文件名乱码的问题<br/>
 * 
 * 使用方法：render(new FileRender());
 * 摘自：http://www.verydemo.com/demo_c230_i3708.html
 * 
 * @author ThreeX
 *
 */
public class FileRender extends Render {

    private File file;
    private ServletContext servletContext;
    private static String DEFAULT_FILE_CONTENT_TYPE = "application/octet-stream";

    public FileRender(File file) {
        this.file = file;
        this.servletContext =  JFinal.me().getServletContext();
    }

    @Override
    public void render() {

        if (file == null || !file.isFile() || file.length() > Integer.MAX_VALUE) {
            new RenderFactory().getErrorRender(404).setContext(request, response).render();
            return;
        }
        // 源码中的代码
        // response.addHeader("Content-disposition", "attachment; filename=" +
        // file.getName());
        // 修改后的代码 解决中文乱码问题
        try {
            response.addHeader("Content-disposition",
                    "attachment; filename=" + new String(file.getName().getBytes("GBK"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String contentType = servletContext.getMimeType(file.getName());
        if (contentType == null) {
            contentType = DEFAULT_FILE_CONTENT_TYPE; // "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setContentLength((int) file.length());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            for (int n = -1; (n = inputStream.read(buffer)) != -1;) {
                outputStream.write(buffer, 0, n);
            }
            outputStream.flush();
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
