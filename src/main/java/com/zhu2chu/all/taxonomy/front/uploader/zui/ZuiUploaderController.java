package com.zhu2chu.all.taxonomy.front.uploader.zui;

import java.io.File;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * 2017年9月12日 17:11:03
 * zui文件上传
 * 
 * @author ThreeX
 *
 */
@URIMapping(uri="/zuiuploader",routeClass=FrontRoutes.class,viewPath="/uploader")
public class ZuiUploaderController extends Controller {

    private ZuiUploaderService srv = ZuiUploaderService.me;

    public void index() {
        render("zuiplupload.html");
    }

    public void uploadChunk() {
        UploadFile uploadFile = getFile();
        File file = uploadFile.getFile();
        String name = getPara("name");
        int chunk = getParaToInt("chunk");
        int chunks = getParaToInt("chunks");
        String uuid = getPara("uuid");

        Ret ret0 = srv.uploadChunk(file, name, chunk, chunks, uuid);

        renderJson(ret0);

        file.delete();
    }

}
