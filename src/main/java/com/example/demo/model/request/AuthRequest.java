package com.example.demo.model.request;

import com.example.demo.constants.Constants;
import com.google.common.base.Preconditions;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    String username;
    String password;

    public void validate() {
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getUsername()), Constants.NAME_MUST_NOT_NULL);
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getPassword()), Constants.PASSWORD_MUST_NOT_NULL);
    }
}
