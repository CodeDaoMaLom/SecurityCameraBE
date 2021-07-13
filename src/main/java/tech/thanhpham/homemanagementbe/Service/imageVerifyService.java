package tech.thanhpham.homemanagementbe.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyRequest;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyResponse;

@Service
public class imageVerifyService {
    @Value("${tech.thanhpham.ai}")
    private String aiUrl;

    @Autowired
    private ObjectMapper objectMapper;

    private static String errorPath = "/error-by-date/";

    private static String successPath = "/success-by-date/";

    public imageVerifyResponse imageVerify(imageVerifyRequest imageVerifyRequest) {
        imageVerifyResponse imageVerifyResponse = new imageVerifyResponse();
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.postForObject(aiUrl + "/verify-face", imageVerifyRequest, String.class);
            imageVerifyResponse = objectMapper.readValue(result, imageVerifyResponse.class);

        } catch (Exception e) {
            System.out.println("Error: " + e);

        }


        return imageVerifyResponse;
    }
}
