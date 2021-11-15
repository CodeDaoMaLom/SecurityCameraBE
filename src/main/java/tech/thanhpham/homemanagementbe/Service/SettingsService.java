package tech.thanhpham.homemanagementbe.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void setConfig(String key) {
        Settings aiMode = settingsRepository.findById("ai_mode").get();
        if(!key.equals("ai_mode") & aiMode.getBooleanValue()) {
            Settings settings = settingsRepository.findById(key).get();
            settings.setValue(String.valueOf(!settings.getBooleanValue()));
            if (key.equals("video_recorder") & !settings.getBooleanValue()) {
                this.setStaticConfig("email_warning", Boolean.FALSE);
                this.setStaticConfig("white_list", Boolean.FALSE);
            }
            if (key.equals("email_warning") & !settings.getBooleanValue()) {
                this.setStaticConfig("white_list", Boolean.FALSE);
            }

            settingsRepository.save(settings);
        }
        if (key.equals("ai_mode")){
            aiMode.setValue(String.valueOf(!aiMode.getBooleanValue()));
            if (!aiMode.getBooleanValue()) {
                this.setStaticConfig("video_recorder", Boolean.FALSE);
                this.setStaticConfig("email_warning", Boolean.FALSE);
                this.setStaticConfig("white_list", Boolean.FALSE);
            }
            settingsRepository.save(aiMode);

        }
    }
    public void setStaticConfig(String key, Boolean value) {
        Settings settings = settingsRepository.findById(key).get();
        settings.setValue(String.valueOf(value));
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
    public Boolean getWhiteList() {
        Settings config = settingsRepository.findById("white_list").get();
        return config.getBooleanValue();
    }
}
