package cn.loverot.basic.controller.web;

import cn.loverot.basic.controller.BaseController;
import cn.loverot.basic.bean.FileUploadBean;
import cn.loverot.basic.entity.FileUploadEntity;
import cn.loverot.common.entity.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 上传文件
 * @author huise
 */
@Api("上传文件接口")
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

	/**
	 * 处理post请求上传文件
	 *
	 * @throws ServletException
	 *             异常处理
	 * @throws IOException
	 *             异常处理
	 * @return
	 */
	@ApiOperation(value = "处理post请求上传文件")
	@PostMapping("/upload")
	@ResponseBody
	public ResultResponse upload(@Valid FileUploadEntity fileUploadEntity) throws IOException {
		FileUploadBean config = new FileUploadBean(fileUploadEntity.getUploadPath(),fileUploadEntity.getFile(),null);
		return ResultResponse.build().ok().data(this.upload(config));
	}
}
