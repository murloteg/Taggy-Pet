package ru.nsu.sberlab.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.sberlab.exceptions.ReCaptchaException;
import ru.nsu.sberlab.recaptcha.ReCaptchaKeys;
import ru.nsu.sberlab.recaptcha.ReCaptchaResponse;

import java.net.URI;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReCaptchaService {
    private final ReCaptchaKeys reCaptchaKeys;

    @Value("${google.recaptcha.verify.url}")
    private String verifyURI;

    public void verify(String response, String request, @NonNull String redirectURI) {
        RestTemplate restTemplate = new RestTemplate();
        URI verifyURI = URI.create(String.format(this.verifyURI, reCaptchaKeys.getSecret(), response));
        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(verifyURI, ReCaptchaResponse.class);

        if (Objects.nonNull(reCaptchaResponse) && (!reCaptchaResponse.isSuccess() ||
                reCaptchaResponse.getScore() < reCaptchaKeys.getThreshold() ||
                !reCaptchaResponse.getAction().equals(request))) {
            throw new ReCaptchaException(reCaptchaResponse.getErrors(), redirectURI);
        }
    }
}
