package com.mumu.core.social.weixin;


import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/1.
 */
public class WeiXinBinding extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        if(map.get("connection") == null){
            httpServletResponse.getWriter().write("<h2>解绑成功<h2>");
        }else{
            httpServletResponse.getWriter().write("<h2>绑定成功<h2>");
        }
    }
}
