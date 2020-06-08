package org.linn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;

/**
 * 认证服务器
 * create by linn 2020/06/02
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService myUserDetailsService;

    @Resource
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 密码模式必须要加入一个authenticationManager
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService)
                .tokenStore(tokenStore);
        if (null  != jwtAccessTokenConverter){
            endpoints.accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("test")
                .secret(new BCryptPasswordEncoder().encode("test1234"))
                .accessTokenValiditySeconds(36000)//有效时间
                .refreshTokenValiditySeconds(3600 * 2)
                .redirectUris("http://www.baidu.com")//http://example.com
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .scopes("all");
    }

    // =======================> 说明

    /**
     * 授权码模式
     * 获取认证中心的code
     * GET =>  http://localhost:8080/oauth/authorize?response_type=code&client_id=test&redirect_uri=http://www.baidu.com&scope=all
     * 通过Code 请求token
     *
     * POST => localhost:8080/oauth/token
     * 在Authorization中带上 withClient  secret 分别是username password
     * Body中加上
     * grant_type:authorization_code
     * client_id:test
     * code:YKJZLr
     * scope:all
     * redirect_uri:http://www.baidu.com
     * 完成token 获取
     */

    /**
     * 密码模式,获取token
     * 在Authorization中带上 withClient  secret 分别是username password
     * POST => localhost:8080/oauth/token
     * Body中加上
     * grant_type:password
     * username:admin
     * password:123456
     * scope:all
     *
     */
    /**
     * 通过token请求数据
     *
     * 在Headers 中带入Authorization : bearer 05ff9eae-123b-4821-8221-a4485f504dcb
     */
    // =======================> 结束
}
