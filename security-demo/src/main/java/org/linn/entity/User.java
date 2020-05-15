package org.linn.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
public class User {

    public interface UserSimpView {
    }

    public interface UserDetailView extends UserSimpView {
    }

    @JsonView(UserSimpView.class)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @JsonView(UserDetailView.class)
    @Length(max = 20, min = 8, message = "字符串必须在8-20之间")
    private String password;

    @JsonView(UserSimpView.class)
    @DecimalMin(value = "20", inclusive = false, message = "age最小值为20")
    private String age;

}
