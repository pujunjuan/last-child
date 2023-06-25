package com.simple.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.simple.common.result.FileUploadResult;
import com.simple.config.OssConfig;
import com.simple.service.OssService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class OssServiceImpl implements OssService {

        // 允许上传的格式
        private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
                ".jpeg", ".gif", ".png","blob"};
        @Autowired
        private OSS ossClient;
        @Autowired
        private OssConfig ossConfig;



    /**
         *
         * @desc 文件上传
         * 文档链接 https://help.aliyun.com/document_detail/84781.html?spm=a2c4g.11186623.6.749.11987a7dRYVSzn
         * @date 2019-07-31 11:31
         */
        public FileUploadResult upload(MultipartFile uploadFile) {
            // 校验图片格式
            boolean isLegal = false;
            System.out.println(uploadFile.getOriginalFilename());
            for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(),
                        type)) {
                    isLegal = true;
                    break;
                }
            }

            //封装Result对象，并且将文件的byte数组放置到result对象中
            FileUploadResult fileUploadResult = new FileUploadResult();
            if (!isLegal) {
                fileUploadResult.setStatus("error");
                return fileUploadResult;
            }
            //文件新路径
            String fileName = uploadFile.getOriginalFilename();
            String filePath = getFilePath(fileName);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));

            // 上传到阿里云
            try {
                ossClient.putObject(ossConfig.getBucketName(), filePath, new
                        ByteArrayInputStream(uploadFile.getBytes()),metadata);

            } catch (Exception e) {
                e.printStackTrace();
                //上传失败
                fileUploadResult.setStatus("error");
                return fileUploadResult;
            }
            // 关闭OSSClient。
//            ossClient.shutdown();

            fileUploadResult.setStatus("done");
            fileUploadResult.setResponse("success");
            //this.aliyunConfig.getUrlPrefix() + filePath 文件路径需要保存到数据库
            fileUploadResult.setName(this.ossConfig.getUrlPrefix() +"/"+ filePath);
            fileUploadResult.setUid(String.valueOf(System.currentTimeMillis()));
            return fileUploadResult;
        }

        /**
         *
         * @desc 生成路径以及文件名 例如：//images/2019/04/28/15564277465972939.jpg
         * @date 2019-07-31 11:31
         */
        private String getFilePath(String sourceFileName) {
            DateTime dateTime = new DateTime();
            return "images/" + dateTime.toString("yyyy")
                    + "/" + dateTime.toString("MM") + "/"
                    + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                    RandomUtils.nextInt(100, 9999) + "." +
                    StringUtils.substringAfterLast(sourceFileName, ".");
        }

    /**
     * 返回contentType
     * @param
     * @return
     */
    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx"))
        {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }


    /**
         * @author 团子
         * @desc 查看文件列表
         * 文档链接 https://help.aliyun.com/document_detail/84841.html?spm=a2c4g.11186623.2.13.3ad5b5ddqxWWRu#concept-84841-zh
         * @date 2019-07-31 11:31
         */
        public List<OSSObjectSummary> list() {
            // 设置最大个数。
            final int maxKeys = 200;
            // 列举文件。
            ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(ossConfig.getBucketName()).withMaxKeys(maxKeys));
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            return sums;
        }

        /**
         *
         * @desc 删除文件
         * 文档链接 https://help.aliyun.com/document_detail/84842.html?spm=a2c4g.11186623.6.770.4f9474b4UYlCtr
         * @date 2019-07-31 11:31
         */
        public FileUploadResult delete(String objectName) {
            // 根据BucketName,objectName删除文件
            ossClient.deleteObject(ossConfig.getBucketName(), objectName);
            FileUploadResult fileUploadResult = new FileUploadResult();
            fileUploadResult.setName(objectName);
            fileUploadResult.setStatus("removed");
            fileUploadResult.setResponse("success");
            return fileUploadResult;
        }

        /**
         *
         * @desc 下载文件
         * 文档链接 https://help.aliyun.com/document_detail/84823.html?spm=a2c4g.11186623.2.7.37836e84ZIuZaC#concept-84823-zh
         * @date 2019-07-31 11:31
         */
        public void exportOssFile(OutputStream os, String objectName) throws IOException {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(ossConfig.getBucketName(), objectName);
            // 读取文件内容。
            BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
            BufferedOutputStream out = new BufferedOutputStream(os);
            byte[] buffer = new byte[1024];
            int lenght = 0;
            while ((lenght = in.read(buffer)) != -1) {
                out.write(buffer, 0, lenght);
            }
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
}