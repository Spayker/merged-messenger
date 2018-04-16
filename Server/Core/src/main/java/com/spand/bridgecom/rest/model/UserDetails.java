package com.spand.bridgecom.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {

    String name;
    String login;
    String password;
    String address;

}
