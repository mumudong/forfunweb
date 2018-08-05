# forfunweb   http://www.itjc8.com/thread-3039-1-1.html

springsecuritystudy
请求处理链：Filter --> Interceptor --> ControllerAdvice --> Aspect --> Controller
      返回：Filter <-- Interceptor <-- ControllerAdvice <-- Aspect <-- Controller

spring security认证
请求---->                                                         异常处理              验证逻辑        
       UsernamePassword可配        BasicAuthentication       ExceptionTranstation    FilterSecurity                                             
       AuthenticationFilter           Filter可配                  Filter              Interceptor          restAPI                                   
响应  <----




短信登陆：
    使用ValidateCodeFilter，过滤/authentication/mobile请求，验证验证码，位于UsernamePasswordAuthenticationFilter之前
    使用SmsCodeAuthenticationFilter,SmsCodeAuthenticationProvider验证短信登陆，位于UsernamePasswordAuthenticationFilter之后
    UsernamePasswordAuthenticationFilter--> authenticationManager --> authenticationProvider --> userDetailservice --> 验证成功

security social        
                                            UserConnectionRepository
                                           (JdbcUsersConnectionRepository)
                                                                                     db
     serviceProvider                         connection                          UserConnection
  (AbstractOAuth2ServiceProvider)        (OAuth2Connection)
    OAuth2Operations                         
    (OAuth2Template)                       connectionFactory
        API                              (OAuth2ConnectionFactory)
    (AbstractOAuth2APIBinding)              ServiceProvider
                                             ApiAdapter
                                             
                                             
jwt username登录
    /oauth/token                                             
                                             
                                             
                                             
                                             
                                             
                                             
                                             
                                             
                                             
                                             
                                             
                                             