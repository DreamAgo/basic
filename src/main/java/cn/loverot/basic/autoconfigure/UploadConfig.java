package cn.loverot.basic.autoconfigure;

import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;

/**
 * @Author: 铭飞开源团队--huise
 * @Date: 2019/10/17 1:29
 */
public class UploadConfig {
    private String path="upload";
    private String mapping="/upload/**";
    private String denied=".exe,.jsp";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getDenied() {
        return denied;
    }

    public void setDenied(String denied) {
        this.denied = denied;
    }

}
