# forfunweb
springsecuritystudy
请求处理链：Filter --> Interceptor --> ControllerAdvice --> Aspect --> Controller
      返回：Filter <-- Interceptor <-- ControllerAdvice <-- Aspect <-- Controller

spring security认证
请求---->                                                         异常处理              验证逻辑        
       UsernamePassword可配        BasicAuthentication       ExceptionTranstation    FilterSecurity                                             
       AuthenticationFilter           Filter可配                  Filter              Interceptor          restAPI                                   
响应  <----


