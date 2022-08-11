package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.service.MailSenderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailSenderServiceImpl implements MailSenderService {
    final Environment environment;

    @Override
    public Boolean sendMail(String email) {
        Transport transport = null;
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", environment.getProperty("spring.mail.host"));
            props.put("mail.smtp.port", environment.getProperty("spring.mail.port"));
            props.put("mail.smtp.auth", environment.getProperty("spring.mail.auth"));
//            props.put("mail.smtp.starttls.enable", environment.getProperty("spring.mail.starttls.enable"));


            String mail = "markomolo498@gmail.com";
            String password = "sgymipassdqwpppo";


            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            mail, password
                    );
                }
            };


            Session session = Session.getInstance(props, auth);
            transport = session.getTransport();
            transport.connect();

            Message message = new MimeMessage(session);
            message.setSubject("Your salary");
            message.setFrom(new InternetAddress("noreply@ab.kg"));
            message.setText("Nice work");

            try {
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse("")
                );
                transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                transport.close();
            } catch (Exception ignored) {
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
