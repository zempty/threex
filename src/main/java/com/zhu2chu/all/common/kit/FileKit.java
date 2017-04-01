/**
 * 2017年4月1日 22:59:32
 * 该工具出至：http://www.cnblogs.com/interdrp/p/3523456.html
 */

package com.zhu2chu.all.common.kit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.JOptionPane;

public class FileKit {

    private static String MESSAGE = "";

    /**
     * 复制单个文件
     * @param srcFilename 待复制的文件名
     * @param destFilename 目标文件名
     * @param overlay 目标文件存在是否覆盖
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

        int byteread = 0; //读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024]; //缓冲区

            /**
             * in.read(buffer)。每次将读取的数据存入buffer，并返回读取的字节数量给byteread。
             * 如果没有读到数据就返回-1，如果传入的buffer字节数组长度为0，不读数据并返回0。
             */
            while ((byteread = in.read(buffer)) != -1) {
                /**
                 * out.write(buffer,0,byteread)。buffer字节数组存放着数据，意思是将buffer[0]~buffer[byteread]之间
                 * 的数据写入out流指向的文件。
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
                }
            }
        }
    }

    public static boolean copyFile(String srcFilename, String destFilename, boolean overlay) {
        return copyFile(new File(srcFilename), new File(destFilename), overlay);
    }

    public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
        return copyDirectory(new File(srcDirName), new File(destDirName), overlay);
    }

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
                flag = copyFile(files[i].getAbsolutePath(),
                        destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            } else if (files[i].isDirectory()) {
                flag = copyDirectory(files[i].getAbsolutePath(),
                        destDirName + files[i].getName(), overlay);
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
     * @param target
     */
    private static void nioTransferCopy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream ins = null;
        FileOutputStream outs = null;

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
        }
    }

    /**
     * 如果需要监测复制进度，可以用第二快的方法(留意buffer的大小，对速度有很大影响)
     * 
     * @param source
     * @param target
     */
    private static void nioBufferCopy(File source, File target) {
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
    private static void customBufferBufferedStreamCopy(File source, File target) {
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
    private static void customBufferStreamCopy(File source, File target) {
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

}
