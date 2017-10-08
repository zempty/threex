package com.zhu2chu.all.taxonomy.front.uploader.zui;

import java.io.File;

import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import com.zhu2chu.all.bus.kit.FileKit;

/**
 * 2017年9月12日 17:11:52
 * ZuiUploader服务员
 * 
 * @author ThreeX
 *
 */
public class ZuiUploaderService {

    public static final ZuiUploaderService me = new ZuiUploaderService();

    private static final String uploadPath = JFinal.me().getConstants().getBaseUploadPath()+File.separator+"files"+File.separator;

    /**
     * 保存分块
     * 
     * @param file jfinal解析出的文件对象
     * @param name
     * @param chunk
     * @param chunks
     * @param uuid
     * @return
     */
    public Ret uploadChunk(File file, String name, int chunk, int chunks, String uuid) {
        File fileDir = new File(uploadPath+uuid);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File tmp0 = new File(fileDir, String.valueOf(chunk));
        FileKit.copyFile(file, tmp0, true);

        File finalFile = new File(uploadPath, name);
        if (chunk==0 && finalFile.exists()) {//如果是第一个分片上来时删除原有的文件
            finalFile.delete();
        }

        FileKit.appendFile(file, finalFile);
        tmp0.delete();

        return Ret.ok();
    }

}
