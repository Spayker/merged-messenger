package com.spand.bridgecom.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString()
public class UserRequest {

    String name;
    String login;
    String password;
    String address;

}
