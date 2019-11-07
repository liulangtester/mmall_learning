package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by root on 10/22/19.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    /**
     * 上传文件到FTP服务器
     * @param file
     * @param path
     * @return
     */
    public String upload(MultipartFile file, String path) {
        //文件名
        String fileName = file.getOriginalFilename();
        //获取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //上传文件名，防止文件名一样，造成覆盖
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        //目录不存在就创建
        if (!fileDir.exists()) {
            fileDir.setWritable(true);//设置写的权限
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);

        try {
            //将文件传到目标路径(还在本地的tomcat中)并且有新的文件名
            file.transferTo(targetFile);

            //将targetFile上传到我们的FTP服务器上
            boolean flag = FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            if (flag == false) {
                return "";
            }

            //上传完以后，删除upload下面的文件 upload目录没删
            targetFile.delete();


        } catch (IOException e) {
           logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }
}