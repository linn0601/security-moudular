package org.linn.social.qq.conect;

import org.linn.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;


public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {


    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
