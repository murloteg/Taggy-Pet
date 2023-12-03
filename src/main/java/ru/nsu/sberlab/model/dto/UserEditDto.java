package ru.nsu.sberlab.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserEditDto {
    private String firstName;
    private String email;
    private String phoneNumber;
    private boolean hasPermitToShowEmail;
    private boolean hasPermitToShowPhoneNumber;
    private String password;
    private List<SocialNetworkEditDto> socialNetworks;
}
