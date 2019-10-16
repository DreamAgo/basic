package cn.loverot.basic.action.web;

import cn.loverot.basic.action.BaseAction;
import cn.loverot.basic.bean.FileUploadBean;
import cn.loverot.basic.entity.FileUploadEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 上传文件
 * @author huise
 */
@Api("上传文件接口")
@Controller
@RequestMapping("/file")
public class FileAction extends BaseAction {

	/**
	 * 处理post请求上传文件
	 * 
	 * @param req
	 *            HttpServletRequest对象
	 * @param res
	 *            HttpServletResponse 对象
	 * @throws ServletException
	 *             异常处理
	 * @throws IOException
	 *             异常处理
	 */
	@ApiOperation(value = "处理post请求上传文件")
	@PostMapping("/upload")
	@ResponseBody
	public void upload(FileUploadEntity fileUploadEntity, HttpServletRequest req, HttpServletResponse res) throws IOException {
		FileUploadBean config = new FileUploadBean(fileUploadEntity.getUploadPath(),fileUploadEntity.getFile(),null);
		this.outString(res,this.upload(config));
	}
}
