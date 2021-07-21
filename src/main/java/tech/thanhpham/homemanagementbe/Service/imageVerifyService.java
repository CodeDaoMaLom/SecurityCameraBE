package tech.thanhpham.homemanagementbe.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyDTO;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyRequest;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyResponse;
import tech.thanhpham.homemanagementbe.Entity.ImageSetup;
import tech.thanhpham.homemanagementbe.Entity.ImageVerify;
import tech.thanhpham.homemanagementbe.Enum.imageVerifyEnum;
import tech.thanhpham.homemanagementbe.Repository.imageSetupRepository;
import tech.thanhpham.homemanagementbe.Repository.imageVerifyRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class imageVerifyService {
    @Value("${tech.thanhpham.ai}")
    private String aiUrl;

    @Value("${tech.thanhpham.image-path}")
    private String imagePath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private imageVerifyRepository imageVerifyRepository;

    @Autowired
    private imageSetupRepository imageSetupRepository;


    public imageVerifyDTO imageVerify(imageVerifyRequest imageVerifyRequest) {
        imageVerifyDTO imageVerifyDTO = new imageVerifyDTO();
        imageVerifyResponse imageVerifyResponse = new imageVerifyResponse();
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.postForObject(aiUrl + "/verify-face", imageVerifyRequest, String.class);
            imageVerifyResponse = objectMapper.readValue(result, imageVerifyResponse.class);
            imageVerifyDTO = this.saveImageToFolder(imageVerifyResponse, imageVerifyRequest.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageVerifyDTO;
    }

    public imageVerifyDTO saveImageToFolder(imageVerifyResponse imageVerifyResponse, String image) {
        DateTimeFormatter dtfPath = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String absolutePath = imagePath + "/verifies/";
        String personName = imageVerifyResponse.getPerson().length() > 0 ? imageVerifyResponse.getPerson().trim().replace(" ", "_") : "";

        try {
            if (!Files.exists(Paths.get(absolutePath))) {
                Files.createDirectories(Paths.get(absolutePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = Base64.getMimeDecoder().decode(image);
        String fileName = personName + "_" + dtfPath.format(LocalDateTime.now()) + ".jpg";
        try {
            FileOutputStream fos = new FileOutputStream(new File(absolutePath + fileName));
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageVerify imageVerify = new ImageVerify(fileName, LocalDateTime.now(), imageVerifyResponse.getPerson(), imageVerifyResponse.getProbabilityInt(), imageVerifyResponse.getProbabilityInt() >= 80 ? imageVerifyEnum.SUCCESS.name() : imageVerifyEnum.FAIL.name());
        imageVerifyDTO imageVerifyDTO = new imageVerifyDTO(imageVerify);
        imageVerifyRepository.save(imageVerify);

        return imageVerifyDTO;

    }

    public ResponseEntity<String> imageSetup(imageVerifyRequest imageVerifyRequest) {
        imageVerifyDTO imageVerifyDTO = new imageVerifyDTO();
        imageVerifyResponse imageVerifyResponse = new imageVerifyResponse();
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(aiUrl + "/setup-face", imageVerifyRequest, String.class);
            this.saveImageToFolderForSetup(imageVerifyRequest);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveImageToFolderForSetup(imageVerifyRequest imageVerifyRequest) {
        DateTimeFormatter dtfPath = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String personName = imageVerifyRequest.getName().length() > 0 ? imageVerifyRequest.getName().trim().replace(" ", "_") : "";
        String relativePath = "/setups/" + personName + "/";
        String absolutePath = imagePath + relativePath;
        try {
            if (!Files.exists(Paths.get(absolutePath))) {
                Files.createDirectories(Paths.get(absolutePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = Base64.getMimeDecoder().decode(imageVerifyRequest.getData());
        String fileName = personName + "_" + dtfPath.format(LocalDateTime.now()) + ".jpg";
        try {
            FileOutputStream fos = new FileOutputStream(new File(absolutePath + fileName));
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageSetup imageSetup = new ImageSetup(imageVerifyRequest.getName(), relativePath + fileName, LocalDateTime.now());
        imageSetupRepository.save(imageSetup);

    }

    public List<imageVerifyDTO> getImageVerifyDTOList() {
        List<imageVerifyDTO> imageVerifyDTOList = new ArrayList<>();
        imageVerifyRepository.findAllByOrderByTimeVerifyDesc().forEach(imageVerify -> imageVerifyDTOList.add(new imageVerifyDTO(imageVerify)));
        return imageVerifyDTOList;
    }

    public ResponseEntity<byte[]> getImage(String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(imagePath + "/verifies/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
