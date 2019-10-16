package cn.loverot.basic.bean;

import cn.loverot.basic.entity.FileUploadEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 铭飞开源团队--huise
 * @Date: 2019/10/17 1:14
 */
public class FileUploadBean extends FileUploadEntity {
    /**
     * 上传根目录，由业务决定
     */
    private String rootPath;
    /**
     * 是否重定向到项目目录,针对老版本兼容的临时处理
     */
    private boolean uploadFloderPath;
    public FileUploadBean() {
    }
    public FileUploadBean(String fileName, String rootPath) {
        this.rootPath = rootPath;
    }

    public FileUploadBean(String uploadPath, MultipartFile file, String rootPath, boolean uploadFloderPath) {
        this.rootPath = rootPath;
        this.uploadFloderPath = uploadFloderPath;
        this.setUploadPath(uploadPath);
        this.setFile(file);
    }
    public FileUploadBean(String uploadPath, MultipartFile file, String rootPath) {
        this.rootPath = rootPath;
        this.setUploadPath(uploadPath);
        this.setFile(file);
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public boolean isUploadFloderPath() {
        return uploadFloderPath;
    }

    public void setUploadFloderPath(boolean uploadFloderPath) {
        this.uploadFloderPath = uploadFloderPath;
    }



}