package org.linn.validate.code.image;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.linn.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class ImageCode extends ValidateCode {

    /**
     * 验证码
     */
    private transient BufferedImage image;

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }
}
