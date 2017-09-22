package com.zhu2chu.all.taxonomy.front.uploader.plupload;

import java.io.File;
import java.io.IOException;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;
import com.zhu2chu.all.bus.kit.FileKit;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * plupload上传控制者
 * 
 * @author ThreeX
 *
 */
@URIMapping(uri="/plupload",routeClass=FrontRoutes.class,viewPath="/uploader")
public class PluploadController extends Controller {

    private static final String uploadPath = JFinal.me().getConstants().getBaseUploadPath();

    public void index() {
        render("plupload.html");
    }

    /**
     * 接收分块
     * @throws IOException 
     */
    public void uploadChunk() throws IOException {
        UploadFile uploadFile = getFile();
        File file = uploadFile.getFile();
        String name = getPara("name");
        String chunk = getPara("chunk");
        String chunks = getPara("chunks");

        File fileDir = new File(uploadPath+File.separator+"files");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File finalName = new File(fileDir, name);
        FileKit.appendFile(file, finalName);

        System.out.println("当前第"+(Integer.parseInt(chunk)+1)+"个分块");
        renderNull();

        file.delete();
    }

}
