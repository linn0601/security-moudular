package org.linn.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties smsCode = new SmsCodeProperties();

}
