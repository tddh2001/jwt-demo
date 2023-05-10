package com.example.demo.model.request;

import com.example.demo.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateStudentRequest {
    private Integer id;
    private String name;
    private String classRoom;
    private String phone;
    private String email;
    private String description;

    public void validate() {
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getName()), Constants.NAME_MUST_NOT_NULL);
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getClassRoom()), Constants.CLASS_ROOM_MUST_NOT_NULL);
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getPhone()), Constants.PHONE_MUST_NOT_NULL);
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getEmail()), Constants.EMAIL_MUST_NOT_NULL);
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.getDescription()), Constants.DESCRIPTION_MUST_NOT_NULL);
    }
}
