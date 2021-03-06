package com.mumu.core.social.weixin.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/1.
 */
public class WeiXinOAuth2Template extends OAuth2Template {
    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    private Logger logger = LoggerFactory.getLogger(getClass());
    public WeiXinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }
    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    public String buildAuthenticateUrl(OAuth2Parameters parameters){
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid=" + clientId + "&scope=snsapi_login";
        return url;
    }
    /**
     * 微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
     */
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);
        accessTokenRequestUrl.append("?appid="+clientId);
        accessTokenRequestUrl.append("&secret="+clientSecret);
        accessTokenRequestUrl.append("&code="+authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri="+redirectUri);

        return getAccessToken(accessTokenRequestUrl);
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);
        refreshTokenUrl.append("?appid="+clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token="+refreshToken);

        return getAccessToken(refreshTokenUrl);
    }

    private AccessGrant getAccessToken(StringBuilder accessTokenResultUrl){
        logger.info("获取access_token,请求URL:{}",accessTokenResultUrl.toString());
        String response = getRestTemplate().getForObject(accessTokenResultUrl.toString(),String.class);
        logger.info("获取token信息：{}",response);
        Map<String,Object> result = null;
        try{
            result = new ObjectMapper().readValue(response,Map.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回错误码时直接返回空
        if(StringUtils.isNotBlank(MapUtils.getString(result,"errcode"))){
            String errcode = MapUtils.getString(result,"errcode");
            String errmsg = MapUtils.getString(result,"errmsg");
            throw new RuntimeException(String.format("获取access_token失败，errcode:%s,errmsg:%s",errcode,errmsg));
        }
        WeiXinAccessGrant accessToken = new WeiXinAccessGrant(
                MapUtils.getString(result,"access_token"),
                MapUtils.getString(result,"scope"),
                MapUtils.getString(result,"refresh_token"),
                MapUtils.getLong(result,"expires_in"));
        accessToken.setOpenId(MapUtils.getString(result,"openid"));
        return accessToken;
    }
}
