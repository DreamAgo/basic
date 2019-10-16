package cn.loverot.basic.utils;

import cn.hutool.core.convert.Convert;
import cn.loverot.base.constant.e.BaseSessionEnum;
import cn.loverot.basic.constant.e.SessionConstEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 基础工具类
 * @Author: 铭飞开源团队--huise
 * @Date: 2019/10/16 21:10
 */
@Component
public class BasicUtil {
    /**
     *当前页数
     */
    private final static String PAGE_NUMBER = "pageNumber";

    /**
     * 默认一页显示数量
     */
    private final static String PAGE_SIZE = "pageSize";

    /**
     * 分页开始方法，必须配合BasicUtil.endPage()一起使用
     */
    public static void startPage() {
        int pageNumber = BasicUtil.getInt(PAGE_NUMBER, 1);
        PageHelper.startPage(pageNumber, BasicUtil.getInt(PAGE_SIZE, 10));
    }
    public static PageInfo endPage(List list) {
        PageInfo page = new PageInfo(list);
        return page;
    }

    /**
     * 获取整型值
     *
     * @param param
     *            参数名称
     * @param def
     *            默认值，如果参数不存在或不符合规则就用默认值替代
     * @return 返回整型值
     */
    public static Integer getInt(String param, int def) {
        String value = SpringUtil.getRequest().getParameter(param);
        if (NumberUtils.isCreatable(value)) {
            return Integer.parseInt(value);
        } else {
            return def;
        }
    }
    public static Integer getInt(String param) {
        return BasicUtil.getInt(param, 0);
    }
    /**
     * 获取布尔值
     *
     * @param param
     *            参数名称
     * @return 返回布尔值，没找到返回null
     */
    public static Boolean getBoolean(String param) {
        String value = SpringUtil.getRequest().getParameter(param);
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取字符串值
     *
     * @param param
     *            参数名称
     * @param def
     *            默认值，如果参数不存在或不符合规则就用默认值替代
     * @return 返回整型值
     */
    public static String getString(String param, String def) {
        String value = SpringUtil.getRequest().getParameter(param);
        if (StringUtils.isEmpty(value)) {
            value = def;
        }
        return value;
    }

    /**
     * 获取字符串值
     *
     * @param param
     *            参数名称
     * @return 返回整型值
     */
    public static String getString(String param) {
        return BasicUtil.getString(param, "");
    }
    /**
     * 获取整型值数组
     *
     * @param param
     *            参数名称
     * @return 不存在返回null
     */
    public static Integer[] getInts(String param) {
        String[] value = SpringUtil.getRequest().getParameterValues(param);
        if (ArrayUtils.isNotEmpty(value)) {
            try {
                return Convert.toIntArray(value);
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取整型值数组
     *
     * @param param
     *            参数名称，如果param参数的值本身就是数组，将会忽略split参数
     * @param split
     *            分割符号
     * @return 不存在返回null
     */
    public static Integer[] getInts(String param, String split) {
        if (BasicUtil.getInts(param) != null) {
            return BasicUtil.getInts(param);
        }
        String value = SpringUtil.getRequest().getParameter(param);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        if (ArrayUtils.isNotEmpty(value.split(split))) {
            return Convert.toIntArray(value.split(split));
        } else {
            return null;
        }
    }


    /**
     * 获取session的值
     *
     * @param key
     *            枚举类中的值
     * @return session中获取的对象
     */
    public static Object getSession(BaseSessionEnum key) {
        return SpringUtil.getRequest().getSession().getAttribute(key.toString());
    }

    /**
     * 设置session的值
     *
     * @param key
     *            枚举类中的值
     */
    public static void setSession(BaseSessionEnum key, Object value) {
        SpringUtil.getRequest().getSession().setAttribute(key.toString(), value);
    }

    /**
     * 设置session的值
     *
     *            HttpServletRequest对象
     * @param key
     *            枚举类中的值
     */
    public static void setSession(String key, Object value) {
        SpringUtil.getRequest().getSession().setAttribute(key, value);
    }
    /**
     * 获取filePath的物理路径
     *
     * @param filePath
     *            文件
     * @return 物理路径
     */
    public static String getRealPath(String filePath) {
        String classPath = getClassPath(filePath);
        if(!classPath.startsWith("file")){
            //Tomcat部署方式和开发
            HttpServletRequest request = SpringUtil.getRequest();
            String path = request.getServletContext().getRealPath("/");// 该方法存在平台因素，存在有时后面有斜杠有时会没有
            if(!StringUtils.isEmpty(filePath)){
                String last = path.charAt(path.length() - 1) + ""; // 取最后一个字符串，判断是否存在斜杠
                String frist = filePath.charAt(0) + ""; // 取第一个字符串，判断用户传递过来的参数是否有斜杠
                if (last.equals(File.separator)) {
                    if (frist.equals("\\") || frist.equals("/")) {// 当前平台可以获取到最后的斜杠，那么就判断一下用户传递的参数是否有斜杠，有就截取掉
                        path = path + filePath.substring(1);
                    } else {
                        path = path + filePath; // 当前平台可以获取到最后的斜杠，用户传递的参数没有斜杠，就直接返回
                    }
                } else {
                    if (frist.equals("\\") || frist.equals("/")) {// 当前平台没有斜杠，用户传递的参数有斜杠，有就放回
                        path = path + filePath;
                    } else {
                        path = path + File.separator + filePath; // 平台也米有斜杠，参数也没有斜杠，增加拼接放回
                    }
                }
            }
            return path;
        }else {
            //打包为jar包的时候
            return System.getProperty("user.dir")+ File.separator+ filePath;
        }
    }

    /**
     * 获取spingboot 对应下的资源文件
     * @param filePath
     * @return
     */
    public static String getClassPath(String filePath) {
        String os = System.getProperty("os.name");
        String temp = ClassUtils.getDefaultClassLoader().getResource("").getPath()+filePath;
        if(os.toLowerCase().startsWith("win")){
            return temp.replace("/", "\\");
        } else {
            return temp;
        }


    }
}
