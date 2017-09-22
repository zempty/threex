package com.zhu2chu.all.taxonomy.front.uploader;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.zhu2chu.all.bus.kit.FileKit;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * 测试上传控件Controller
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
@URIMapping(uri="/uploader",routeClass=FrontRoutes.class,viewPath="/uploader")
public class UploaderController extends Controller {

    private static final String serverPath = JFinal.me().getConstants().getBaseUploadPath();

    public void index() {
        render("webuploader.html");
    }

    public void uploader() {
        render("zuiplupload.html");
    }

    public void uploaderResume() {
        render("webuploader_resume.html");
    }

    public void plupload() {
        render("plupload.html");
    }

    public void importData() {
        List<UploadFile> uploadFiles = getFiles();
        File file = uploadFiles.get(0).getFile();

        String fileMd5 = getPara("fileMd5");
        String chunk = getPara("chunk");
        File file0 = new File(serverPath + "/" + fileMd5);
        if (!file0.exists()) {
            file0.mkdirs();
        }
        // 保存文件
        File chunkFile = new File(serverPath + "/" + fileMd5 + "/" + chunk);
        FileKit.copyFile(file, chunkFile, true);
        renderJson(Ret.ok("msg", "不能同意更多"));

        file.delete();
    }

    /**
     * 分块全部上传完成后合并
     * 
     * @throws IOException
     */
    public void mergeChunks() throws IOException {
        String fileMd5 = getPara("fileMd5");
        //String postfix = getPara("filePostfix");
        String filename = getPara("filename");

        // 读取目录所有文件
        File f = new File(serverPath + "/" + fileMd5);
        File[] fileArray = f.listFiles(new FileFilter() {
            // 排除目录，只要文件
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return false;
                }
                return true;
            }
        });

        // 转成集合，便于排序
        List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
        // 从小到大排序
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                    return -1;
                }
                return 1;
            }
        });

        // 新建保存文件
        File outputFile = new File(serverPath+File.separator+filename);

        // 创建文件
        outputFile.createNewFile();

        // 输出流
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        FileChannel outChannel = fileOutputStream.getChannel();

        // 合并
        FileChannel inChannel;
        for (File file : fileList) {
            inChannel = new FileInputStream(file).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();

            // 删除分片
            file.delete();
        }

        // 关闭流
        fileOutputStream.close();
        outChannel.close();

        // 清除文件加
        File tempFile = new File(serverPath + "/" + fileMd5);
        if (tempFile.isDirectory() && tempFile.exists()) {
            tempFile.delete();
        }

        System.out.println("合并文件成功");
        renderJson(Ret.ok("msg", "合并成功"));
    }

    /**
     * 检查分块是否已上传完成。用于断点续传
     * @throws IOException 
     */
    public void checkChunk() throws IOException {
        // 校验文件是否已经上传并返回结果给前端
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();

        // 文件唯一表示                               
        String fileMd5 = request.getParameter("fileMd5");
        // 当前分块下标
        String chunk = request.getParameter("chunk");
        // 当前分块大小
        String chunkSize = request.getParameter("chunkSize");

        // 找到分块文件
        File checkFile = new File(serverPath + "/" + fileMd5 + "/" + chunk);

        // 检查文件是否存在，且大小一致
        response.setContentType("text/html;charset=utf-8");
        if (checkFile.exists() && checkFile.length() == Integer.parseInt((chunkSize))) {
            response.getWriter().write("{\"ifExist\":1}");
        } else {
            response.getWriter().write("{\"ifExist\":0}");
        }
    }

    /**
     * pluploadUpload的上传方法
     */
    public void pluploadUpload() {
        UploadFile uploadFile = getFile();
        File file = uploadFile.getFile();
        System.out.println(file);
    }

}
