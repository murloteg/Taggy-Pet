package ru.nsu.sberlab.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class UserEditDto {
    private String firstName;
    private String email;
    private String phoneNumber;
    private Boolean hasPermitToShowEmail;
    private Boolean hasPermitToShowPhoneNumber;
    private String password;
    private List<SocialNetworkRegistrationDto> socialNetworks;

    public boolean isPermitToShowPhoneNumber() {
        return Objects.nonNull(hasPermitToShowPhoneNumber);
    }

    public boolean isPermitToShowEmail() {
        return Objects.nonNull(hasPermitToShowEmail);
    }
}
