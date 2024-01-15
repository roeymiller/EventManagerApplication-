package com.example.EventApplication.model.request;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class UserBuilder {
    private String userName;
    private String password;
    private String email;
    private String phone;

}
