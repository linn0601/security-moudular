# security-moudular
多模块应用，基于security为web端和app提供可重用的安全中心

## security-app 提供json作为交互的接口(前后端分离)
## security-browser 提供给浏览器使用(基于重定向跳转)
## security-core 提供给app、browser通用的功能组件
## security-demo 提供测试

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
