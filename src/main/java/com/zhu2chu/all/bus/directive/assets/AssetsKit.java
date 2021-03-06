package com.zhu2chu.all.bus.directive.assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * 压缩工具方法
 * 为了修改Assets指令而修改这玩意儿
 * 
 * @author ThreeX
 *
 */
public class AssetsKit {

    private static final Log log = Log.getLog(AssetsKit.class);
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final String JS_EXT = ".js", CSS_EXT = ".css";
    private static final String PROTOCOL = "^https?://.+$";

    // 考虑到线上环境基本不会频繁更改css,js文件为了性能故缓存
    private static ConcurrentMap<String, String> COMBO_MAP = new ConcurrentHashMap<String, String>();

    /**
     * 压缩工具
     * @param filename 待压缩的文件列表文件 /assets/assets.jjs
     * @return String 返回压缩完成之后的路径
     * @throws IOException 文件不存在时异常
     */
    public static String getPath(String filename) throws IOException {
        return getPath(filename, null);
    }

    /**
     * 压缩工具
     * @param filename 待压缩的文件列表文件 /assets/assets.jjs
     * @param relativePath 相对于filename所在路径的路径
     * @return String 返回压缩完成之后的路径
     * @throws IOException 文件不存在时异常
     */
    public static String getPath(String filename, String relativePath) throws IOException {
        String rootPath = PathKit.getWebRootPath();
        // 路径判读
        if (!filename.startsWith("/")) {
            filename = "/" + filename;
        }

        return combo(rootPath, filename, relativePath);
    }

    /**
     * 压缩css,js帮助
     * @param rootPath 项目路径
     * @param fileList 合并压缩的文件列表
     * @param isCss 是否是css
     * @param out 输出流
     * @throws IOException Io异常
     */
    private static void compressorHelper(String rootPath, List<String> fileList, boolean isCss, Writer out) throws IOException {
        Reader in = null;
        InputStream input = null;
        try {
            if (isCss) {
                for (String path : fileList) {
                    boolean isRomte = isRomte(path);
                    input = isRomte ? new URL(path).openStream() : new FileInputStream(rootPath + path);
                    // css 文件内容,并处理路径问题
                    String context = repairCss(IOUtils.toString(input, UTF_8), path);
                    if(path.indexOf(".min.") > 0 || isRomte){// 对.min.css的css放弃压缩
                        out.append(context);
                    }else{
                        CssCompressor css = new CssCompressor(new StringReader(context));
                        css.compress(out, -1);
                    }
                    input.close(); input = null;
                }
            }else{
                // nomunge: 混淆,verbose：显示信息消息和警告,preserveAllSemiColons：保留所有的分号 ,disableOptimizations 禁止优化
                boolean munge = true, verbose = false, preserveAllSemiColons = false, disableOptimizations = false;
                for (String path : fileList) {
                    boolean isRomte = isRomte(path);
                    input = isRomte ? new URL(path).openStream() : new FileInputStream(rootPath + path);
                    in = new InputStreamReader(input, UTF_8);
                    if(path.indexOf(".min.") > 0 || isRomte){ // 对.min.js,和远程js放弃压缩
                        out.append(IOUtils.toString(in));
                    }else{
                        JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {
                            public void warning(String message, String sourceName,
                                  int line, String lineSource, int lineOffset) {
                                if (line < 0) {
                                    log.error("\n[WARNING] " + message);
                                } else {
                                    log.error("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
                                }
                            }
                            public void error(String message, String sourceName,
                                  int line, String lineSource, int lineOffset) {
                                if (line < 0) {
                                    log.error("\n[ERROR] " + message);
                                } else {
                                    log.error("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
                                }
                            }
                            public EvaluatorException runtimeError(String message, String sourceName,
                                  int line, String lineSource, int lineOffset) {
                                error(message, sourceName, line, lineSource, lineOffset);
                                return new EvaluatorException(message);
                            }
                        });
                        compressor.compress(out, -1, munge, verbose, preserveAllSemiColons, disableOptimizations);
                    }
                    input.close(); input = null;
                    in.close(); in = null;
                }
            }
            out.flush();
        }catch(IOException e){
            throw e;
        }finally{
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(input);
        }
    }
    
    /**
     * 将css文件里的图片相对路径修改为绝对路径
     * @param content 内容
     * @param path 路径
     * @return String css
     */
    private static String repairCss(String content, String path){
        Pattern p = Pattern.compile("url\\([\\s]*['\"]?((?!['\"]?https?://|['\"]?data:|['\"]?/).*?)['\"]?[\\s]*\\)"); // 感谢Code Life(程式人生)的正则
        Matcher m = p.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String url = m.group(1).trim();
            StringBuffer cssPath = new StringBuffer("url(").append(FilenameUtils.getFullPath(path)).append(url).append(")");
            m.appendReplacement(sb, cssPath.toString());
        }
        m.appendTail(sb);
        content = sb.toString();
        return content;
    }

    /**
     * 压缩工具
     * @param filename 待压缩的文件列表文件 /assets/assets.jjs
     * @param relativePath 相对于filename所在路径的路径
     * @param rootPath 项目路径
     * @return String 返回压缩完成之后的路径
     * @throws IOException 文件不存在时异常
     */
    private static String combo(String rootPath, String filename, String relativePath) throws IOException {
        File assetsConfig = new File(rootPath + filename);
        // 待压缩的文件列表不存在时抛出异常
        if (!assetsConfig.exists()) {
            throw new IOException(filename + " not found...");
        }
        // 读取文件中的js或者css路径
        List<String> list = FileUtils.readLines(assetsConfig, UTF_8);
        // 文件内容md5
        StringBuilder fileMd5s = new StringBuilder();
        StringBuilder config = new StringBuilder();
        Iterator<String> it = list.iterator();
        String lines;
        while (it.hasNext()) {
            lines = it.next();
            config.append(lines);
            if (StrKit.isBlank(lines)) {
                it.remove();
                continue;
            }
            // 去除首尾空格
            lines = lines.trim();
            // #开头的行注释
            if (lines.startsWith("#")) {
                it.remove();
                continue;
            }
            // 远程服务器上的资源文件,不参与MD5
            if (isRomte(lines)) {
                continue;
            }
            // 对错误地址修复
            if (!lines.startsWith("/")) {
                lines = "/" + lines;
            }
            String filePath = rootPath + lines;
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IOException(file.getName() + " not found...");
            }
            String content = FileUtils.readFileToString(file, UTF_8);
            fileMd5s.append(HashKit.md5(content));
        }
        fileMd5s.append(HashKit.md5(config.toString()));

        // 文件更改时间集合hex，MD5取中间8位
        String hex = HashKit.md5(fileMd5s.toString()).substring(8, 16);
        boolean isCss = true;
        if (filename.endsWith(".jjs")) {
            isCss = false;
        }
        // /assets/assets.jjs
        String comboName = null;
        String newFileName = null;
        if (StrKit.isBlank(relativePath)) {
            comboName = filename.substring(0, filename.indexOf('.'));
            newFileName = comboName + '-'  + hex + (isCss ? CSS_EXT : JS_EXT);
        } else {
            //如果不以/开头，就加/
            if (!relativePath.startsWith("/")) {
                relativePath += '/';
            }
            //.jss、.jcss的文件名，没有后缀
            String compressName = filename.substring(filename.lastIndexOf('/'), filename.indexOf('.'));
            comboName = filename.substring(0, filename.lastIndexOf('/'))+relativePath+compressName;

            newFileName = comboName + '-' + hex + (isCss ? CSS_EXT : JS_EXT);
        }

        String newPath = rootPath + newFileName;
        File file = new File(newPath);
        // 判断文件是否已存在，已存在直接返回
        if (file.exists()) {
            return newFileName;
        }
        // 将合并的结果写入文件，异常时将文件删除
        OutputStream output = null;
        Writer out = null;
        try {
            output = new FileOutputStream(newPath);
            out = new OutputStreamWriter(output, UTF_8);
            compressorHelper(rootPath, list, isCss, out);
            // 装载文件路径
            COMBO_MAP.put(filename, newFileName);
        } catch (Exception e) {
            FileUtils.deleteQuietly(file);
            log.error(e.getMessage(), e);
            throw new RuntimeException(filename + " 压缩异常，请检查是否有依赖问题！");
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(out);
        }
        return newFileName;
    }

    /**
     * 判断文件是否为远程资源文件,远程资源文件不进行压缩
     * @param path
     * @return
     */
    private static boolean isRomte(String path){
        if(StrKit.isBlank(path)){
            return false;
        }
        return path.trim().matches(PROTOCOL);
    }

}
