package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.RegistrationDataDao;
import edu.netcracker.project.logistic.model.RegistrationData;
import edu.netcracker.project.logistic.model.RegistrationForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class RegistrationService {
    private RegistrationDataDao dao;

    private JavaMailSender sender;
    private TemplateEngine templateEngine;
    private Environment env;

    @Autowired
    public RegistrationService(RegistrationDataDao dao, JavaMailSender sender,
                               TemplateEngine templateEngine, Environment env) {
        this.dao = dao;
        this.sender = sender;
        this.templateEngine = templateEngine;
        this.env = env;
    }

    public RegistrationData register(RegistrationForm form) throws MessagingException {
        RegistrationData regData = fromForm(form);
        UUID id = generateId();
        regData.setRegistrationDataId(id);

        dao.save(regData);
        sendConfirmationRequest(regData);
        return regData;
    }

    private void sendConfirmationRequest(RegistrationData data) throws MessagingException {
        Context ctx = new Context();
        ctx.setVariable("firstName", data.getFirstName());
        ctx.setVariable("lastName", data.getLastName());
        ctx.setVariable("id", data.getRegistrationDataId());
        String htmlContent = templateEngine.process(
                "templates/confirmation_email.html", ctx);

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        msg.setSubject("Verify email address");
        String from = env.getProperty("spring.mail.username");
        msg.setFrom(from);
        msg.setText(htmlContent, true);

        sender.send(mimeMessage);
    }

    private RegistrationData fromForm(RegistrationForm form) {
        RegistrationData data = new RegistrationData();
        data.setFirstName(form.getFirstName());
        data.setLastName(form.getLastName());
        data.setUsername(form.getUsername());
        data.setPassword(form.getPassword());
        data.setPhoneNumber(form.getPhoneNumber());
        data.setEmail(form.getEmail());

        return data;
    }

    private UUID generateId() {
        UUID id = UUID.randomUUID();
        while (dao.contains(id)) {
            id = UUID.randomUUID();
        }
        return id;
    }
}
