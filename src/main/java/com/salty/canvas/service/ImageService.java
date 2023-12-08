package com.salty.canvas.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangcong
 * @description
 * @since 2023/12/07 14:25
 */
public interface ImageService {
    String putImage(MultipartFile image);

    byte[] getImage(String url);
}
