package com.simple.service;

import com.aliyun.oss.model.OSSObjectSummary;
import com.simple.common.result.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface OssService
{
    FileUploadResult upload(MultipartFile uploadFile);
    List<OSSObjectSummary> list();
    FileUploadResult delete(String objectName);
    void exportOssFile(OutputStream os, String objectName) throws IOException;
}
