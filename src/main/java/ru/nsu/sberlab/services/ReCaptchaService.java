package ru.nsu.sberlab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.sberlab.exceptions.ReCaptchaException;
import ru.nsu.sberlab.recaptcha.ReCaptchaKeys;
import ru.nsu.sberlab.recaptcha.ReCaptchaResponse;

import java.net.URI;

@Service
public class ReCaptchaService {
    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final ReCaptchaKeys reCaptchaKeys;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public ReCaptchaService(ReCaptchaKeys reCaptchaKeys) {
        this.reCaptchaKeys = reCaptchaKeys;
    }

    public void verify(String response, String request, String redirectURI) {
        URI verifyURI = URI.create(String.format(VERIFY_URL, reCaptchaKeys.getSecret(), response));
        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(verifyURI, ReCaptchaResponse.class);

        if (reCaptchaResponse != null && (!reCaptchaResponse.isSuccess() ||
                reCaptchaResponse.getScore() < reCaptchaKeys.getThreshold() ||
                !reCaptchaResponse.getAction().equals(request))) {
            throw new ReCaptchaException(reCaptchaResponse.getErrors(), redirectURI);
        }
    }
}
