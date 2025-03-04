package pb.wi.mmw.e_sejm.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.email.EmailTemplateName;

public interface EmailService {

    void sendEmail(
            String to,
            String usrnmae,
            EmailTemplateName emailTemplate,
            String confirmationURL,
            String subject
    ) throws MessagingException;

}
