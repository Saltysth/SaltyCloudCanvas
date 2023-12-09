package com.salty.canvas.controller;

import com.salty.canvas.model.URL;
import com.salty.canvas.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author SaltyFish
 * @description
 * @since 2023/12/07 14:24
 */
@RestController
@Slf4j
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/putImage")
    @CrossOrigin(origins = "*")
    // 注意使用URL类是需要JSON格式，你也可以不用
    public ResponseEntity<URL> putImage(@RequestBody MultipartFile image) {
        return ResponseEntity.ok().body(imageService.putImage(image));
    }
}
