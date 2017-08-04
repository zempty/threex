package com.zhu2chu.all.taxonomy.video;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.zhu2chu.all.bus.kit.PathKit;

/**
 * 2017年8月4日 22:39:11 FFmpeg.exe工具的操作类
 * 
 * 1.分离视频音频流
 * ffmpeg -i input_file -vcodec copy -an output_file_video //分离视频流 ffmpeg -i
 * input_file -acodec copy -vn output_file_audio //分离音频流
 * 
 * 2.视频解复用
 * ffmpeg –i test.mp4 –vcodec copy –an –f m4v test.264 ffmpeg –i test.avi
 * –vcodec copy –an –f m4v test.264
 * 
 * 3.视频转码
 * ffmpeg –i test.mp4 –vcodec h264 –s 352*278 –an –f m4v test.264   //转码为码流原始文件
 * ffmpeg –i test.mp4 –vcodec h264 –bf 0 –g 25 –s 352*278 –an –f m4v test.264   //转码为码流原始文件
 * ffmpeg –i test.avi -vcodec mpeg4 –vtag xvid –qsame test_xvid.avi //转码为封装文件 //-bf B帧数目控制，-g 关键帧间隔控制，-s 分辨率控制
 * 
 * 
 * 4.视频封装
 * ffmpeg –i video_file –i audio_file –vcodec copy –acodec copy output_file
 * 
 * 5.视频剪切
 * ffmpeg –i test.avi –r 1 –f image2 image-%3d.jpeg //提取图片 ffmpeg -ss 0:1:30 -t
 * 0:0:20 -i input.avi -vcodec copy -acodec copy output.avi //剪切视频 //-r 提取图像的频率，-ss 开始时间，-t 持续时间
 * 
 * 6.视频录制
 * ffmpeg –i rtsp://192.168.3.205:5555/test –vcodec copy out.avi
 * 
 * 7.YUV序列播放
 * ffplay -f rawvideo -video_size 1920x1080 input.yuv
 * 
 * 8.YUV序列转AVI
 * ffmpeg –s w*h –pix_fmt yuv420p –i input.yuv –vcodec mpeg4 output.avi
 * 
 * 常用参数说明：
 * 主要参数：
 * -i 设定输入流
 * -f 设定输出格式
 * -ss 开始时间
 * 
 * 视频参数：
 * -b 设定视频流量，默认为200Kbit/s
 * -r 设定帧速率，默认为25
 * -s 设定画面的宽与高
 * -aspect 设定画面的比例
 * -vn 不处理视频
 * -vcodec 设定视频编解码器，未设定时则使用与输入流相同的编解码器
 * 
 * 音频参数：
 * -ar 设定采样率
 * -ac 设定声音的Channel数
 * -acodec 设定声音编解码器，未设定时则使用与输入流相同的编解码器
 * -an 不处理音频
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class FFmpegKit {

    public static void makeScreenCut(String ffmpegPath, String videoRealPath, String imageRealName) {
        List<String> command = new ArrayList<String>();
        command.add(ffmpegPath);
        command.add("-i");
        command.add(videoRealPath);
        command.add("-y");
        command.add("-f");
        command.add("image2");
        command.add("-ss");
        command.add("250");//截取哪个时间点的帧
        command.add("-t");
        command.add("0.001");
        command.add(imageRealName);

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.redirectErrorStream(true);
        System.out.println("视频截图开始...");

        try {
            Process process = builder.start();
            InputStream in = process.getInputStream();
            byte[] bytes = new byte[1024];
            System.out.println("正在进行截图，请稍候...");
            while (in.read(bytes) != -1) {
                //System.out.println(".");
            }
            System.out.println();
            System.out.println("视频截取完成");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("视频截取失败！");
        }

    }

    public static void main(String[] args) {
        String crtPath = PathKit.getCurrentDirectory(FFmpegKit.class);
        makeScreenCut(crtPath + "FFmpeg.exe", crtPath + "苏尤01.avi", crtPath + "suyou.jpg");
    }

}
