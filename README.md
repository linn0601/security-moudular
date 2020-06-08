# security-moudular
多模块应用，基于security为web端和app提供可重用的安全中心
```
接口文档 : https://www.showdoc.cc/linn0601security?page_id=4625568719093193

账号随意 列如: admin
密码: 123456
```

#### security-app 提供json作为交互的接口(前后端分离)
#### security-browser 提供给浏览器使用(基于重定向跳转)
#### security-core 提供给app、browser通用的功能组件
#### security-demo 提供测试

1.使用StringUtils 对字符串操作效率会较高
2.spring提供对HttpServletRequest 和HttpServletResponse的封装类 ServletWebRequest类
3.URI 与 URL
4.对路径匹配可以使用AntPathMatcher来进行
5.ObjectMapper可以将对象直接转为JSON

6.private final ValidateCodeGenerator validateCodeGenerator;
    private final ValidateCodeGenerator smsCodeGenerator;       
    可以使用spring中依赖查找，spring会将他们封装成Map
 ==>  private final Map<String, ValidateCodeGenerator> validateCodeGenerators;

7.ServletRequestUtils.getRequiredStringParameter工具类可以从请求参数中查找，如果没有找到就抛出异常    

8.security-core 对验证码 图片|手机验证 进行分层封装系统可能发生的变化。

总的来说:

===>基于security5.2对 oauth2颁发token移植到手机登录中,按照security对oauth2的实现自己造轮子并进行了整体的封装,代码复用上提高.
===>使用@ConditionOnMissBean对许多功能都可以重写(开闭原则)
===>模块化,支持json和session,对token加入redis/jwt支持

