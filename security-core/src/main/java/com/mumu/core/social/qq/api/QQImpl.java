package com.mumu.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * Created by Administrator on 2018/7/31.
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{
    private Logger logger = LoggerFactory.getLogger(getClass());
    //获取openID的请求地址
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    //获取用户信息的请求地址
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken,String appId){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);//将accessToken加入请求链接中去
        this.appId = appId;
        String urlOpenId = String.format(URL_GET_OPENID,accessToken);
        String result = getRestTemplate().getForObject(urlOpenId,String.class);
        logger.info("请求OpenId结果：{}",result);
        this.openId = StringUtils.substringBetween(result,"\"openid\":\"","\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String urlUserInfo = String.format(URL_GET_USERINFO,appId,openId);
        String result = getRestTemplate().getForObject(urlUserInfo,String.class);
        logger.info("用户信息返回结果：{}",result);
        QQUserInfo userInfo = null;
        try{
            userInfo = objectMapper.readValue(result,QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        }catch (Exception e){
            throw new RuntimeException("获取用户信息失败！",e);
        }
    }
}
