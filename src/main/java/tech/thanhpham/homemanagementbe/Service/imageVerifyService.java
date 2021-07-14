package tech.thanhpham.homemanagementbe.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyDTO;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyRequest;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyResponse;
import tech.thanhpham.homemanagementbe.Entity.ImageVerify;
import tech.thanhpham.homemanagementbe.Enum.imageVerifyEnum;
import tech.thanhpham.homemanagementbe.Repository.imageVerifyRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class imageVerifyService {
    @Value("${tech.thanhpham.ai}")
    private String aiUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private imageVerifyRepository imageVerifyRepository;

    private static String errorPath = "/error-by-date/";

    private static String successPath = "/success-by-date/";

    public imageVerifyDTO imageVerify(imageVerifyRequest imageVerifyRequest) {
        imageVerifyDTO imageVerifyDTO = new imageVerifyDTO();
        imageVerifyResponse imageVerifyResponse = new imageVerifyResponse();
        DateTimeFormatter dtfPath = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.postForObject(aiUrl + "/verify-face", imageVerifyRequest, String.class);
            imageVerifyResponse = objectMapper.readValue(result, imageVerifyResponse.class);
            String path = (imageVerifyResponse.getProbabilityInt() >= 80 ? successPath : errorPath) + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "/" + imageVerifyResponse.getPerson() + "_" + dtfPath.format(LocalDateTime.now()) + ".jpg";
            ImageVerify imageVerify = new ImageVerify(path, LocalDateTime.now(), imageVerifyResponse.getPerson(), imageVerifyResponse.getProbabilityInt(), imageVerifyResponse.getProbabilityInt() >= 80 ? imageVerifyEnum.SUCCESS.name() : imageVerifyEnum.FAIL.name());
            imageVerifyRepository.save(imageVerify);
            imageVerifyDTO = new imageVerifyDTO(imageVerify);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }


        return imageVerifyDTO;
    }
}
