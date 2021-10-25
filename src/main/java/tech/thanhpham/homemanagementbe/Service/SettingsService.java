package tech.thanhpham.homemanagementbe.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.thanhpham.homemanagementbe.Entity.Settings;
import tech.thanhpham.homemanagementbe.Repository.SettingsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final SettingsRepository settingsRepository;
    @Value("${tech.thanhpham.camera}")
    private String camera;
    @Value("${tech.thanhpham.aicamera}")
    private String aiCamera;

    public Map<String, Boolean> getConfig() {
        Map<String, Boolean> settingsMap = new HashMap<String, Boolean>();
        List<Settings> settingsList = settingsRepository.findAll();
        for (Settings settings : settingsList) {
            settingsMap.put(settings.getKey(), settings.getBooleanValue());
        }
        return settingsMap;
    }

    public void setConfig(String key) {
        Settings settings = settingsRepository.findById(key).get();
        settings.setValue(String.valueOf(!settings.getBooleanValue()));
        settingsRepository.save(settings);
    }

    public String getVideoMode() {
        Settings config = settingsRepository.findById("ai_mode").get();
        if (config.getBooleanValue()) {
            return aiCamera;
        } else {
            return camera;
        }
    }
    public Boolean getVideoRecorder() {
        Settings config = settingsRepository.findById("video_recorder").get();
        return config.getBooleanValue();
    }
    public Boolean getMailNotification() {
        Settings config = settingsRepository.findById("email_warning").get();
        return config.getBooleanValue();
    }
}
