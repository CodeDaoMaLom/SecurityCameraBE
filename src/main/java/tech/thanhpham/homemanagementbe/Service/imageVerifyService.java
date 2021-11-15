package tech.thanhpham.homemanagementbe.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import tech.thanhpham.homemanagementbe.DTO.*;
import tech.thanhpham.homemanagementbe.Entity.ImageSetup;
import tech.thanhpham.homemanagementbe.Entity.ImageVerify;
import tech.thanhpham.homemanagementbe.Enum.imageVerifyEnum;
import tech.thanhpham.homemanagementbe.Repository.imageSetupRepository;
import tech.thanhpham.homemanagementbe.Repository.imageVerifyRepository;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Autowired
    private  VideoStreamingService videoStreamingService;


    public static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    @Async
    public void imageVerifyAsync(imageVerifyRequest imageVerifyRequest) {
        imageVerifyResponse imageVerifyResponse = new imageVerifyResponse();
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.postForObject(aiUrl + "/verify-face", imageVerifyRequest, String.class);
            imageVerifyResponse = objectMapper.readValue(result, imageVerifyResponse.class);
            System.out.println(imageVerifyResponse.getProbabilityInt());
            if (imageVerifyResponse.getProbabilityInt() > 70) {
                this.saveImageToFolder(imageVerifyResponse, imageVerifyRequest.getData());
            } else {
                videoStreamingService.VideoUploader(imageVerifyRequest.getName(),"false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    public ResponseEntity<byte[]> getImageSetup(String pathVariable, String filename) {
        byte[] image = new byte[0];
        System.out.println(imagePath + "/setups/" + pathVariable + "/" + filename);
        try {
            image = FileUtils.readFileToByteArray(new File(imagePath + "/setups/" + pathVariable + "/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
    public imageVerifyRequest base64String(formDataVerifyDTO formDataVerifyDTO) throws IOException {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        File imagePath = multipartToFile(formDataVerifyDTO.getImage(), formDataVerifyDTO.getName());
        try {
            BufferedImage bufferedImage = ImageIO.read(imagePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            String base64String = base64Encoder.encode(byteArrayOutputStream.toByteArray());
            imageVerifyRequest imageVerifyRequest = new imageVerifyRequest(formDataVerifyDTO.getName(), base64String);
            return imageVerifyRequest;
        } catch (IOException e) {
            e.printStackTrace();
            return new imageVerifyRequest();
        }
    }

    public imageVerifyDTO verifyFromMultiPath(formDataVerifyDTO formDataVerifyDTO) throws IOException {

        return imageVerify(base64String(formDataVerifyDTO));
    }
    public ResponseEntity<String> setupFromMultiPath(formDataVerifyDTO formDataVerifyDTO) throws IOException {

        return imageSetup(base64String(formDataVerifyDTO));
    }

    public List<FacialSetupDTO> getFacialSetupDTOList(){
        List<FacialSetupDTO> facialSetupDTOList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<FacialSetupDTO>> response = restTemplate.exchange(new URI(aiUrl + "/get-trained-faces"), HttpMethod.GET, null, new ParameterizedTypeReference<List<FacialSetupDTO>>() {});
            facialSetupDTOList = response.getBody();
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        for(FacialSetupDTO person : facialSetupDTOList){
            List<String> imagesList = new ArrayList<>();
            imageSetupRepository.findAllByName(person.getName()).forEach(imageVerify -> imagesList.add(imageVerify.getPath()));
            person.setImages(imagesList);
        }
        return facialSetupDTOList;
    }

    public void deleteFacialSetup(String param){
        System.out.println(param);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForEntity(new URI(aiUrl + "/delete-trained-faces?ids=" + param), null);
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
    }
}
