package ru.nsu.sberlab.recaptcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class ReCaptchaResponse {
    private boolean success;
    private float score;
    private String action;
    @JsonProperty("challenge_ts")
    private String challengeTs;
    private String hostname;
    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public List<String> getErrors() {
        if (getErrorCodes() != null) {
            return getErrorCodes().stream()
                    .map(ReCaptchaErrorCodes.RECAPTCHA_ERROR_CODES::get)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
