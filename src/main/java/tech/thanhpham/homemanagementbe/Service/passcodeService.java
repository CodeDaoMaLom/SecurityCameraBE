package tech.thanhpham.homemanagementbe.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.thanhpham.homemanagementbe.DTO.passcodeDTO;
import tech.thanhpham.homemanagementbe.Entity.Passcode;
import tech.thanhpham.homemanagementbe.Repository.passcodeRepository;

@Service
@RequiredArgsConstructor
public class passcodeService {
    private final passcodeRepository passcodeRepository;

    public passcodeDTO getPasscode(){
        Passcode passcode = passcodeRepository.findById("pass").get();
        return new passcodeDTO(passcode.getPasscode());
    }

    public void setPasscode(int pass){
        Passcode passcode = passcodeRepository.findById("pass").get();
        passcode.setPasscode(pass);
        passcodeRepository.save(passcode);
    }
}
