package com.example.demo.model.request;

import com.example.demo.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Preconditions;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String roles;

    public void validate() {
        if (roles.compareTo("ROLE_ADMIN") != 0 && roles.compareTo("ROLE_USER") !=0) {
            throw new IllegalArgumentException(Constants.INCORRECT_ROLE);
        }
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getUsername()), Constants.NAME_MUST_NOT_NULL);
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getPassword()), Constants.PASSWORD_MUST_NOT_NULL);
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getRoles()), Constants.ROLE_MUST_NOT_NULL);
    }
}
