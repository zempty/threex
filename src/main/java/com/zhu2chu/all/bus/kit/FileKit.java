/**
 * 2017年4月1日 22:59:32
 * 该工具出至：http://www.cnblogs.com/interdrp/p/3523456.html
 */

package com.zhu2chu.all.bus.kit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateParameters;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;

import com.jfinal.core.Const;
import com.jfinal.log.Log;

/**
 * 文件操作工具
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class FileKit {

    private static String MESSAGE = "";

    private static final Log log = Log.getLog(FileKit.class);

    /**
     * 复制单个文件
     * 
     * @param srcFile
     *            待复制的文件
     * @param destFile
     *            目标文件
     * @param overlay
     *            目标文件存在是否覆盖
     * @return 复制成功返回true，失败返回false。
     */
    public static boolean copyFile(File srcFile, File destFile, boolean overlay) {
        String srcFilename = srcFile.getAbsolutePath();
        if (!srcFile.exists()) {
            MESSAGE = "源文件" + srcFilename + "不存在！";
            JOptionPane.showMessageDialog(null, MESSAGE);

            return false;
        } else if (!srcFile.isFile()) {
            MESSAGE = "复制文件失败，源文件：" + srcFilename + "不是一个文件！";
            JOptionPane.showMessageDialog(null, MESSAGE);

            return false;
        }

        String destFilename = destFile.getAbsolutePath();

        if (destFile.exists()) {
            if (overlay) {
                destFile.delete();
            }
        } else {
            File parentFile = destFile.getParentFile();
            if (!parentFile.exists()) {
                if (!parentFile.mkdirs()) {
                    MESSAGE = "目标文件或目录不存在，尝试创建并失败。请检测是否对" + destFilename + "有读写的权限？";
                    JOptionPane.showMessageDialog(null, MESSAGE);

                    return false;
                }
            }
        }

        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;
        long startTime = System.nanoTime();

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024]; // 缓冲区

            /**
             * in.read(buffer)。每次将读取的数据存入buffer，并返回读取的字节数量给byteread。
             * 如果没有读到数据就返回-1，如果传入的buffer字节数组长度为0，不读数据并返回0。
             */
            while ((byteread = in.read(buffer)) != -1) {
                /**
                 * out.write(buffer,0,byteread)。buffer字节数组存放着数据，意思是将buffer[0]~
                 * buffer[byteread]之间 的数据写入out流指向的文件。
                 */
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    long endTime = System.nanoTime();
                    if (log.isInfoEnabled()) {
                        log.info("复制文件" + srcFile.getName() + "至" + destFile.getName()
                                + TimeKit.calcTime(startTime / 1000000L, endTime / 1000000L));
                    }
                }
            }
        }
    }

    /**
     * 复制单个文件
     * 
     * @param srcFilePath
     * @param destFilePath
     * @param overlay
     * @return
     */
    public static boolean copyFile(String srcFilePath, String destFilePath, boolean overlay) {
        return copyFile(new File(srcFilePath), new File(destFilePath), overlay);
    }

    /**
     * 复制目录及子目录下所有文件
     * 
     * @param srcDirPath
     * @param destDirPath
     * @param overlay
     * @return
     */
    public static boolean copyDirectory(String srcDirPath, String destDirPath, boolean overlay) {
        return copyDirectory(new File(srcDirPath), new File(destDirPath), overlay);
    }

    /**
     * 复制目录及子目录下所有文件
     * 
     * @param srcDir
     * @param destDir
     * @param overlay
     * @return
     */
    public static boolean copyDirectory(File srcDir, File destDir, boolean overlay) {
        if (!srcDir.exists()) {
            MESSAGE = "复制目录失败：源目录" + srcDir.getAbsolutePath() + "不存在！";
            JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        } else if (!srcDir.isDirectory()) {
            MESSAGE = "复制目录失败：" + srcDir.getAbsolutePath() + "不是目录！";
            JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        }

        // 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
        String destDirName = destDir.getAbsolutePath();
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }

        // 如果目标文件夹存在
        if (destDir.exists()) {
            // 如果允许覆盖则删除已存在的目标目录
            if (overlay) {
                destDir.delete();
            } else {
                MESSAGE = "复制目录失败：目的目录" + destDir.getAbsolutePath() + "已存在！";
                JOptionPane.showMessageDialog(null, MESSAGE);
                return false;
            }
        } else {
            // 创建目的目录
            System.out.println("目的目录不存在，准备创建...");
            if (!destDir.mkdirs()) {
                System.out.println("复制目录失败：创建目的目录失败！");
                return false;
            }
        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 复制文件
            if (files[i].isFile()) {
                flag = copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            } else if (files[i].isDirectory()) {
                flag = copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            }
        }

        if (!flag) {
            MESSAGE = "复制目录" + srcDir.getAbsolutePath() + "至" + destDirName + "失败！";
            JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 不考虑多线程优化，单线程文件复制最快的方法是(文件越大该方法越有优势，一般比常用方法快30+%)
     * 
     * @param source
     *            源文件路径
     * @param target
     *            目标文件路径
     */
    public static void nioTransferCopy(String source, String target) {
        nioTransferCopy(new File(source), new File(target));
    }

    /**
     * 不考虑多线程优化，单线程文件复制最快的方法是(文件越大该方法越有优势，一般比常用方法快30+%)
     * 
     * @param source
     * @param target
     */
    public static void nioTransferCopy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream ins = null;
        FileOutputStream outs = null;

        long startTime = System.nanoTime();
        try {

            ins = new FileInputStream(source);
            outs = new FileOutputStream(target);
            in = ins.getChannel();
            out = outs.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(ins);
            close(in);
            close(outs);
            close(out);

            long endTime = System.nanoTime();
            if (log.isInfoEnabled()) {
                log.info("复制文件" + source.getName() + "至" + target.getName()
                        + TimeKit.calcTime(startTime / 1000000L, endTime / 1000000L));
            }
        }
    }

    /**
     * 如果需要监测复制进度，可以用第二快的方法(留意buffer的大小，对速度有很大影响)
     * 
     * @param source
     * @param target
     */
    public static void nioBufferCopy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while (in.read(buffer) != -1) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(inStream);
            close(in);
            close(outStream);
            close(out);
        }
    }

    /**
     * 缓冲流。常用复制文件方法1
     * 
     * @param source
     * @param target
     */
    public static void customBufferBufferedStreamCopy(File source, File target) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(source));
            fos = new BufferedOutputStream(new FileOutputStream(target));
            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fis);
            close(fos);
        }
    }

    /**
     * 直接字节输入输出。无缓冲区
     * 
     * @param source
     * @param target
     */
    public static void customBufferStreamCopy(File source, File target) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(target);
            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fis);
            close(fos);
        }
    }

    /**
     * 关闭流
     * 
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件内容并将其编码成base64保存到文本文件
     * 
     * @param srcFilePath
     *            要编码的源文件路径
     * @param destFilePath
     *            输出的目标文件路径
     */
    public static void writeBase64(String srcFilePath, String destFilePath) {
        writeBase64(new File(srcFilePath), new File(destFilePath), 1023);
    }

    /**
     * 读取文件内容并将其编码成base64保存到文本文件
     * 
     * @param srcFilePath
     *            要编码的源文件路径
     * @param destFilePath
     *            输出的目标文件路径
     * @param bufferSize
     *            缓冲区大小
     */
    public static void writeBase64(String srcFilePath, String destFilePath, int bufferSize) {
        writeBase64(new File(srcFilePath), new File(destFilePath), bufferSize);
    }

    /**
     * 读取文件内容并将其编码成base64保存到文本文件
     * 
     * @param srcFile
     *            要编码的源文件
     * @param destFile
     *            输出的目标文件
     * @param bufferSize
     *            缓冲区大小。
     */
    public static void writeBase64(File srcFile, File destFile, int bufferSize) {
        if (!srcFile.exists()) {
            log.info("源文件不存在！");
            return;
        }
        if (!srcFile.isFile()) {
            log.info("srcFile不是一文件！");
            return;
        }

        long startTime = System.nanoTime();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            if (bufferSize < 0) {
                bufferSize = 1023;
            }
            int mo = bufferSize % 3;
            if (mo != 0) {
                bufferSize -= mo;// 保证能被3整除
            }

            byte[] buf = new byte[bufferSize];// 长度必须为能被3整除的整数

            int byteread = 0;// 每次读到的字节数

            while ((byteread = fis.read(buf)) != -1) {
                /**
                 * 如果读取的字节数小于缓冲区的长度，就取读取的长度。
                 * 因为当读取最后一波的时候，大部分情况下最后一波字节的长度都会小于缓冲区长度，而此时整个缓冲区极可能都有内容的，
                 * 会导致写入多余的内容到文件。
                 */
                if (byteread < buf.length) {
                    buf = Arrays.copyOfRange(buf, 0, byteread);
                }
                fos.write(Base64.encodeBase64(buf));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
            close(fis);
            long endTime = System.nanoTime();
            if (log.isInfoEnabled()) {
                log.info("读取文件" + srcFile.getName() + "并base64编码"
                        + TimeKit.calcTime(startTime / 1000000L, endTime / 1000000L));
            }
        }
    }

    /**
     * 从文件中读取base64并解码成原始文件
     * 
     * @param srcFilePath
     *            待读取的文件路径
     * @param destFilePath
     *            输出的目标文件路径
     * @param bufferSize
     *            待缓冲区大小。
     */
    public static void readBase64(String srcFilePath, String destFilePath) {
        readBase64(new File(srcFilePath), new File(destFilePath), 1024);
    }

    /**
     * 从文件中读取base64并解码成原始文件
     * 
     * @param srcFilePath
     *            待读取的文件路径
     * @param destFilePath
     *            输出的目标文件路径
     * @param bufferSize
     *            待缓冲区大小。
     */
    public static void readBase64(String srcFilePath, String destFilePath, int bufferSize) {
        readBase64(new File(srcFilePath), new File(destFilePath), bufferSize);
    }

    /**
     * 从文件中读取base64并解码成原始文件
     * 
     * @param srcFile
     *            待读取的文件
     * @param destFile
     *            输出的目标文件
     * @param bufferSize
     *            待缓冲区大小。需符合 x*6/8余数为0
     */
    public static void readBase64(File srcFile, File destFile, int bufferSize) {
        if (!srcFile.exists()) {
            log.info("源文件不存在！");
            return;
        }
        if (!srcFile.isFile()) {
            log.info("srcFile不是一文件！");
            return;
        }

        long startTime = System.nanoTime();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            if (bufferSize < 0) {
                bufferSize = 1024;
            }
            int mo = bufferSize * 6 % 8;
            if (mo != 0) {// 如果余数不为0，缓冲区就减掉余数，使之能除尽。
                bufferSize -= mo;
            }

            byte[] buf = new byte[bufferSize];// 长度必须符合：x*6/8的余数为0

            int byteread = 0;

            while ((byteread = fis.read(buf)) != -1) {
                /**
                 * 如果读取的字节数小于缓冲区的长度，就取读取的长度。
                 * 因为当读取最后一波的时候，大部分情况下最后一波字节的长度都会小于缓冲区长度，而整个缓冲区都有内容的，
                 * 会导致写入多余的内容到文件。
                 */
                if (byteread < buf.length) {
                    buf = Arrays.copyOfRange(buf, 0, byteread);
                }
                fos.write(Base64.decodeBase64(buf));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
            close(fis);
            long endTime = System.nanoTime();
            if (log.isInfoEnabled()) {
                log.info("读取文件" + srcFile.getName() + "并base64解码"
                        + TimeKit.calcTime(startTime / 1000000L, endTime / 1000000L));
            }
        }
    }

    /**
     * 2017年5月7日 14:50:04 将内容输出到指定路径的文件
     * 
     * @param content
     * @param filePath
     */
    public static void contentToFile(String content, String filePath) {
        FileOutputStream fos = null;
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {// 如果目录不存在，则先创建父目录
            destFile.getParentFile().mkdirs();
        }

        try {
            fos = new FileOutputStream(destFile);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /****************************
     * 以下方法来自：
     ****************************/

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(File file) {
        BufferedReader reader = null;
        try {
            StringBuilder content = new StringBuilder();

            // System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                // System.out.println("line " + line + ": " + tempString);
                content.append(tempString);
                // line++;
            }
            reader.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * 
     * @param filename
     *            文件名
     */
    public static String readFileByLines(String filename) {
        return readFileByLines(new File(filename));
    }

    /**
     * 2017年7月29日 13:58:26 一次性读文件文本内容，<code>仅适用于小文件</code>。大文件要分次读取，否则会有内存溢出的风险
     * 这里的文件编码使用jfinal默认的文件编码，如果要用于其它非jfinal的地方，这得修改
     * 
     * @param filename
     * @return
     */
    public static String onceReadToString(File file) {
        String encoding = Const.DEFAULT_ENCODING;
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 一次性读文件文本内容，<code>仅适用于小文件</code>。大文件要分次读取，否则会有内存溢出的风险
     * 这里的文件编码使用jfinal默认的文件编码，如果要用于其它非jfinal的地方，这得修改
     * 
     * @param file
     * @return
     */
    public static String onceReadToString(String filename) {
        return onceReadToString(new File(filename));
    }

    /**
     * 2017年9月7日 12:27:18 以下是压缩相关的方法。摘自：http://www.jb51.net/article/95073.htm
     * 这波方法使用到了apache-commons-compress炸包
     */
    /**
     * zip格式
     * 
     * @param input
     * @param output
     * @param name
     * @throws IOException
     * @throws Exception
     */
    public static void zip(String input, String output, String name) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(output));
        String[] paths = input.split("\\|");
        File[] files = new File[paths.length];
        byte[] buffer = new byte[1024];
        for (int i = 0; i < paths.length; i++) {
            files[i] = new File(paths[i]);
        }
        for (int i = 0; i < files.length; i++) {
            FileInputStream fis = new FileInputStream(files[i]);
            if (files.length == 1 && name != null) {
                out.putNextEntry(new ZipEntry(name));
            } else {
                out.putNextEntry(new ZipEntry(files[i].getName()));
            }
            int len; // 读入需要下载的文件的内容，打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.closeEntry();
            fis.close();
        }
        out.close();
    }

    /**
     * gzip格式
     * 
     * @param input
     * @param output
     * @param name
     * @throws Exception
     */
    public static void gzip(String input, String output, String name) {
        String compress_name = null;
        if (name != null) {
            compress_name = name;
        } else {
            compress_name = new File(input).getName();
        }
        byte[] buffer = new byte[1024];
        try {
            GzipParameters gp = new GzipParameters(); // 设置压缩文件里的文件名
            gp.setFilename(compress_name);
            GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(new FileOutputStream(output), gp);
            FileInputStream fis = new FileInputStream(input);
            int length;
            while ((length = fis.read(buffer)) > 0) {
                gcos.write(buffer, 0, length);
            }
            fis.close();
            gcos.finish();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 7z格式
     * 
     * @param input
     *            来源文件的路径。如：D:\abc\a.avi
     * @param output
     *            目标文件的中径。如：D:\abc\wode.avi
     * @param name
     *            entryName，即文件在压缩包里面的名字
     */
    public static void z7z(String input, String output, String name) {
        try {
            SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File(output));
            SevenZArchiveEntry entry = null;
            String[] paths = input.split("\\|");
            File[] files = new File[paths.length];
            for (int i = 0; i < paths.length; i++) {
                files[i] = new File(paths[i].trim());
            }
            for (int i = 0; i < files.length; i++) {
                BufferedInputStream instream = null;
                instream = new BufferedInputStream(new FileInputStream(paths[i]));
                if (name != null) {
                    entry = sevenZOutput.createArchiveEntry(new File(paths[i]), name);
                } else {
                    entry = sevenZOutput.createArchiveEntry(new File(paths[i]), new File(paths[i]).getName());
                }
                sevenZOutput.putArchiveEntry(entry);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = instream.read(buffer)) > 0) {
                    sevenZOutput.write(buffer, 0, len);
                }
                instream.close();
                sevenZOutput.closeArchiveEntry();
            }
            sevenZOutput.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * zlib格式
     * 
     * @param input
     * @param output
     * @throws Exception
     */
    public static void zlib(String input, String output) {
        DeflateParameters dp = new DeflateParameters();
        dp.setWithZlibHeader(true);
        DeflateCompressorOutputStream dcos = null;
        FileInputStream fis = null;
        try {
            dcos = new DeflateCompressorOutputStream(new FileOutputStream(output), dp);
            fis = new FileInputStream(input);
            int length = (int) new File(input).length();
            byte data[] = new byte[length]; // int length;
            while ((length = fis.read(data)) > 0) {
                dcos.write(data, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dcos != null) {
                try {
                    dcos.finish();
                    dcos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 追加文件。
     * 将inFile追加进outFile中
     * 
     * @param inFile
     * @param outFile
     */
    public static void appendFile(File inFile, File outFile) {
        appendFile(inFile, outFile, 1024);
    }

    /**
     * 2017年9月12日 15:47:47
     * 追加文件。
     * 将inFile追加进outFile中
     * 
     * @param inFile
     * @param outFile
     * @param append
     */
    public static void appendFile(File inFile, File outFile, int buffer) {
        InputStream fis = null;
        OutputStream fos = null;

        try {
            fis = new BufferedInputStream(new FileInputStream(inFile), buffer);
            byte[] bs = new byte[buffer];

            if (outFile.exists()) {
                fos = new BufferedOutputStream(new FileOutputStream(outFile, true), buffer);
            } else {
                fos = new BufferedOutputStream(new FileOutputStream(outFile), buffer);
            }
            int len = 0;
            while ((len=fis.read(bs)) != -1) {
                fos.write(bs, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
            close(fis);
        }
    }

}
