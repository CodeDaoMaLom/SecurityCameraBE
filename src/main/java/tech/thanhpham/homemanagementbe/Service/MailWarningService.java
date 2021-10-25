package tech.thanhpham.homemanagementbe.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.thanhpham.homemanagementbe.Entity.MailWarning;
import tech.thanhpham.homemanagementbe.Repository.MailWarningRepository;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailWarningService {
    private final MailWarningRepository mailWarningRepository;
    private final JavaMailSender emailSender;

    public void sendMail(String email, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        String template = "" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                "" +
                "<head>" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                "    <style>" +
                "        body {" +
                "            background-color: #f0f0f0;" +
                "            font-family: Arial, sans-serif;" +
                "            color: #404040;" +
                "        }" +
                "" +
                "        .center {" +
                "            text-align: center;" +
                "        }" +
                "" +
                "        small," +
                "        .small {" +
                "            font-size: 12px;" +
                "        }" +
                "" +
                "        a," +
                "        a:hover," +
                "        a:visited {" +
                "            color: #000000;" +
                "            text-decoration: underline;" +
                "        }" +
                "" +
                "        h1," +
                "        h2 {" +
                "            font-size: 22px;" +
                "            color: #404040;" +
                "            font-weight: normal;" +
                "        }" +
                "" +
                "        p {" +
                "            font-size: 15px;" +
                "            color: #606060;" +
                "        }" +
                "" +
                "        .general {" +
                "            background-color: white;" +
                "        }" +
                "        .icon {" +
                "            width: 32px;" +
                "            height: 32px;" +
                "            line-height: 32px;" +
                "            display: inline-block;" +
                "            text-align: center;" +
                "            border-radius: 16px;" +
                "            margin-right: 10px;" +
                "        }" +
                "" +
                "        .warning {" +
                "            border-top: 20px #c08040 solid;" +
                "            background-color: #e0c4aa;" +
                "        }" +
                "" +
                "        .warning p {" +
                "            color: #44311c;" +
                "        }" +
                "" +
                "        .warning .icon {" +
                "            background-color: #c08040;" +
                "            color: #ffffff;" +
                "            font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif;" +
                "        }" +
                "" +
                "        .content {" +
                "            width: 600px;" +
                "        }" +
                "" +
                "        @media only screen and (max-width: 600px) {" +
                "            .content {" +
                "                width: 100%;" +
                "            }" +
                "        }" +
                "" +
                "        @media only screen and (max-width: 400px) {" +
                "" +
                "            h1," +
                "            h2 {" +
                "                font-size: 20px;" +
                "            }" +
                "" +
                "            p {" +
                "                font-size: 13px;" +
                "            }" +
                "" +
                "            small," +
                "            .small {" +
                "                font-size: 11px;" +
                "            }" +
                "" +
                "            .icon {" +
                "                display: block;" +
                "                margin: 0 auto 10px auto;" +
                "            }" +
                "        }" +
                "    </style>" +
                "</head>" +
                "" +
                "<body>" +
                "" +
                "<div class=\"warning\" align=\"center\">" +
                "                            <h1><span class=\"icon\">?</span>Warning</h1>" +
                "                            <p>Hi, " + email + "!</p>" +
                "                            <p>I noticed a stranger showing up at your house. </p>" +
                "                            <p>Access the link to follow: <a href=\"" + link + "\">" + link + "</a></p>" +
                "                        </div>" +
                "  " +
                "</body>" +
                "" +
                "</html>";

        helper.setFrom(new InternetAddress("smartcamera.kma@gmail.com", "Smart Camera"));
        helper.setTo(email);
        helper.setSubject("Smart Camera Warning");
        message.setContent(template, "text/html");

        this.emailSender.send(message);
    }

    public void sendAllMail(String link) throws MessagingException, UnsupportedEncodingException {
        List<MailWarning> mailWarningList = mailWarningRepository.findAll();
        for (MailWarning mailWarning : mailWarningList) {
            this.sendMail(mailWarning.getEmail(), link);
        }
    }

    public List<MailWarning> getAllMail(){
        List<MailWarning> mailWarningList = mailWarningRepository.findAll();
        return mailWarningList;
    }

    public void deleteMail(String id){
        mailWarningRepository.deleteById(UUID.fromString(id));
    }

    public void addMail(String mail){
        mailWarningRepository.save(new MailWarning(mail));
    }

    public void editMail(String id, String mail){
        MailWarning mailWarning = mailWarningRepository.findById(UUID.fromString(id)).get();
        mailWarning.setEmail(mail);
        mailWarningRepository.save(mailWarning);
    }

}
