/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cn.loverot.basic.controller;

import cn.hutool.core.io.FileUtil;
import cn.loverot.basic.autoconfigure.BasicProperties;
import cn.loverot.basic.bean.FileUploadBean;
import cn.loverot.basic.constant.e.SessionConstEnum;
import cn.loverot.basic.utils.BasicUtil;
import cn.loverot.common.constant.Const;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * 基础应用层的父类base
 * 
 * @author 铭飞开发团队
 * @version 版本号：100-000-000<br/>
 *          创建日期：2015-7-19<br/>
 *          历史修订：<br/>
 */
@Api("基础应用层的父类base")
public abstract class BaseController extends cn.loverot.common.controller.BaseController {
	@Autowired
	private BasicProperties basicProperties;
	public final String RAND_CODE="randCode";

	/**
	 * 获取验证码
	 *
	 * @return 返回验证码，获取不到返回null
	 */
	protected String getRandCode() {
		return BasicUtil.getSession(SessionConstEnum.CODE_SESSION) + "";
	}

	/**
	 * 验证验证码
	 * 
	 * @param param   表单验证码参数名称
	 * @return 如果相同，返回true，否则返回false
	 */
	protected boolean checkRandCode(String param) {
		String sessionCode = this.getRandCode();
		String requestCode = BasicUtil.getString(param);
		LOG.debug("session_code:" + sessionCode + " requestCode:" + requestCode);
		if (sessionCode.equalsIgnoreCase(requestCode)) {
			return true;
		}
		return false;
	}/**
	 * 验证验证码
	 *
	 * @return 如果相同，返回true，否则返回false
	 */
	protected boolean checkRandCode() {
		return checkRandCode(RAND_CODE);
	}

	/**
	 * 统一上传文件方法
	 * @param config
	 * @return
	 * @throws IOException
	 */
	public String upload(FileUploadBean config) throws IOException {
		// 过滤掉的文件类型
		String[] errorType = basicProperties.getUpload().getDenied().split(",");
		//文件上传类型限制
		String fileName=config.getFile().getOriginalFilename();
		String fileType=fileName.substring(fileName.lastIndexOf("."));
		String uploadFloderPath=basicProperties.getUpload().getPath(),
				uploadMapping=basicProperties.getUpload().getMapping();
		boolean isReal = new File(uploadFloderPath).isAbsolute();
		//根据是否是绝对路径判断是否要加mapping
		uploadMapping = isReal?uploadMapping:config.isUploadFloderPath()?"":uploadFloderPath;
		//绝对路径
		String realPath = isReal? uploadFloderPath:config.isUploadFloderPath()?BasicUtil.getRealPath(""):BasicUtil.getRealPath(uploadFloderPath) ;
		//修改上传物理路径
		if(StringUtils.isNotBlank(config.getRootPath())){
			realPath=config.getRootPath();
		}
		//修改文件名
		if(StringUtils.isNotBlank(config.getFileName())){
			fileName=config.getFileName();
			fileType=fileName.substring(fileName.lastIndexOf("."));
		}else {
			//取随机名
			fileName=System.currentTimeMillis()+fileType;
		}
		for (String type : errorType) {
			if((fileType).equals(type)){
				LOG.info("文件类型被拒绝:{}",fileType);
				return "";
			}
		}
		if(fileName.contains("/") || fileName.contains("\\")){
			LOG.info("文件名非法:{}",fileName);
			return "";
		}
		// 上传的文件路径,判断是否填的绝对路径
		String uploadFolder = realPath +  File.separator;
		//修改upload下的上传路径
		if(StringUtils.isNotBlank(config.getUploadPath())){
			uploadFolder+=config.getUploadPath()+ File.separator;
		}
		//保存文件
		File saveFolder = new File(uploadFolder);
		File saveFile=new File(uploadFolder,fileName);
		if(!saveFolder.exists()){
			FileUtil.mkdir(saveFolder);
		}
		config.getFile().transferTo(saveFile);
		//绝对映射路径处理
		String path=uploadMapping.replace("**","")
				//转为相对路径
				+ uploadFolder.replace(realPath,"")
				//添加文件名
				+  Const.SEPARATOR + fileName;
		//替换多余
		return new File(Const.SEPARATOR + path).getPath().replace("\\","/").replace("//","/");
	}


}