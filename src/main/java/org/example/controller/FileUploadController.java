package org.example.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.example.entity.Result;
import org.example.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        //把文件的内容存储到本地磁盘上
        String originalFileName = file.getOriginalFilename();
        //保证文件的名字是唯一的，从而防止文件覆盖
        String filename = UUID.randomUUID().toString()+originalFileName.substring(originalFileName.lastIndexOf("."));//随机生成的UUID.原文件后缀名(利用substring根据最后一个字符"."来截取原文件后缀名)
//        file.transferTo(new File("C:\\Users\\AsMuin\\IdeaProjects\\project233\\files\\"+filename));
          String url = AliOssUtil.uploadFile(filename,file.getInputStream());
        return Result.success(url);
    }
}
