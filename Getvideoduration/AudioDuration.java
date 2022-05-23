package com.example.springboot3.web;

import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 李永强
 * @date 2022/5/21 15:38
 */
@Slf4j
public class AudioDuration {
    public static void main(String[] args) throws Exception {
        ReadVideoTime("自己的文件存放目录 ");
    }

    private static String ReadVideoTime(String fileUrl) throws Exception {
        //URL url = new URL(fileUrl); //视频链接
        File file = new File(fileUrl);
        String length = "";
        try {
            MultimediaObject instance = new MultimediaObject(file);
            MultimediaInfo result = instance.getInfo();
            long ls = result.getDuration() / 1000;//时
            Integer hour = (int) (ls / 3600);//分
            Integer minute = (int) (ls % 3600) / 60;//秒
            Integer second = (int) (ls - hour * 3600 - minute * 60);

            String miao=second.toString();
            length = miao;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(length+ "秒");
        return length;

    }
}
