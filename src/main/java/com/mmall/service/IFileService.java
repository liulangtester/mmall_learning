package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by root on 10/22/19.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
