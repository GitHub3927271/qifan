package com.yifan.oa.Base;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yifan.oa.Entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ModelAttribute;


public class BaseController {
    // 数据集
    protected Map<String, Object> result;
    // 返回页面成功码
    protected final Integer SUCCESS_CODE = 200;
    // 返回页面失败码
    protected final Integer ERROR_CODE = 500;
    private final String SUCCESS_STR = "success";
    private final String ERROR_STR = "error";


    //ceshi

    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected HttpHeaders headers;


    @ModelAttribute
    public void resetResult(HttpServletRequest request, HttpServletResponse response) {
        this.result.clear();
        this.response = response;
        this.request = request;
    }


    public BaseController() {
        if (null == result) {
            result = new HashMap<String, Object>();
        }
    }

    public String getToken() {
        String token = request.getHeader("token");
        if (null == token || "".equals(token)) {
            //部分旧页面从请求拿token
            token = request.getParameter("token");
            if (null == token || "".equals(token)) {
                throw new RuntimeException("请重新登陆");
            }
        }
        return token;
    }

    public User getUser() {
        User user = null;
        try {
            JSONObject userjosn = JSON.parseObject(this.getToken());
            user = JSON.toJavaObject(userjosn, User.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("登陆超时");
        } finally {
            return user;
        }


    }


    /**
     * @param retCode
     * @return retcode, retmsg, result
     */
    protected String toPageJson(int retCode) {
        if (retCode == SUCCESS_CODE) {
            return toPageJson(SUCCESS_CODE, SUCCESS_STR);
        }
        return toPageJson(ERROR_CODE, ERROR_STR);
    }

    /**
     * @param retCode
     * @param retMsg
     * @return retcode, retmsg, result
     */
    protected String toPageJson(int retCode, String retMsg) {
        if (null == retMsg) {
            retMsg = "";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("retcode", retCode);
        map.put("retmsg", retMsg);
        map.put("result", result);
        return JSON.toJSONString(map);
    }


}