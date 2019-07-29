package org.evelyn.services.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.evelyn.services.email.api.EmailService;
import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Component
class EvelynEmailService implements EmailService {
    private final JavaMailSender mailSender;

    private final Configuration freeMarkerConfiguration;

    public EvelynEmailService(JavaMailSender mailSender, Configuration freeMarkerConfiguration) {
        this.mailSender = mailSender;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Override
    public void onUserCreated(UserCreatedMessage userMessage) {
        try {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

                    messageHelper.setTo(userMessage.email);
                    messageHelper.setSubject("Welcome to Evelyn");

                    Template template = freeMarkerConfiguration.getTemplate("sign-up-template.ftl");
                    Map<String, Object> params = new HashMap<>();
                    params.put("confirmKey", userMessage.confirmKey);
                    String processedTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);

                    messageHelper.setText(processedTemplate, true);
                }
            };

            mailSender.send(preparator);
        }
        catch (Exception e) {
            System.out.println("Failed to send email");
            e.printStackTrace();
        }
    }
}
