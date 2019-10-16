package cn.loverot.basic.entity;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 铭飞开源团队--huise
 * @Date: 2019/10/17 1:56
 */
public class FileUploadEntity {
    /**
     * 上传文件夹
     */
    private String uploadPath;
    private MultipartFile file;
    /**
     * 文件名
     */
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
