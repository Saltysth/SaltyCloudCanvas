package com.salty.canvas.service.impl;

import cn.hutool.core.date.DateUtil;
import com.salty.canvas.constants.Constants;
import com.salty.canvas.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * @author wangcong
 * @description
 * @since 2023/12/07 14:25
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${canvas.config.url}")
    public String url;

    @Value("${canvas.path}")
    public String localPath;

    @Override
    public String putImage(MultipartFile image) {
        //日期，将图片按照年月文件夹，比如2022，1月上传的图保存到202201文件夹下
        String format = DateUtil.format(new Date(), "yyyy_MM");
        String prePath = localPath + "/" + format;
        File f = new File(prePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        //获取上传图片名称
        String filename = image.getOriginalFilename();
        //拼接的图片路径
        String filepath = prePath + "/" + filename;
        File file = new File(filepath);
        //上传图片
        try {
            image.transferTo(file);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //返回图片访问url
        String res = url + format + "/" + filename;
        log.info("生成URL：" + res);
        return res;
    }

    @Override
    public byte[] getImage(String url) {
        String fullPath = localPath + url;
        File file = new File(fullPath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            inputStream.close();
            return bytes;
        } catch (IOException e) {
            log.error("GetImageError! FullPath: " + fullPath);
            throw new RuntimeException(e);
        }
    }
}
