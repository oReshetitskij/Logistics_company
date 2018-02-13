package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.dao.RegistrationDataDao;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.RegistrationData;
import edu.netcracker.project.logistic.model.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class RegistrationService {
    private RegistrationDataDao dao;
    private PersonCrudDao personDao;

    private JavaMailSender sender;
    private TemplateEngine templateEngine;
    private Environment env;
    private HttpServletRequest request;
    private ServletContext context;

    @Autowired
    public RegistrationService(RegistrationDataDao dao, JavaMailSender sender,
                               TemplateEngine templateEngine, Environment env,
                               HttpServletRequest request, ServletContext context,
                               PersonCrudDao personDao) {
        this.dao = dao;
        this.sender = sender;
        this.templateEngine = templateEngine;
        this.env = env;
        this.request = request;
        this.context = context;
        this.personDao = personDao;
    }

    public RegistrationData register(RegistrationForm form) throws MessagingException {
        OffsetDateTime now = OffsetDateTime.now();

        RegistrationData regData = fromForm(form);
        UUID id = generateId();
        regData.setRegistrationDataId(id);
        regData.setRegistrationDate(now);

        dao.save(regData);
        sendConfirmationRequest(regData);
        return regData;
    }

    private void sendConfirmationRequest(RegistrationData data) throws MessagingException {
        WebContext ctx = new WebContext(request, null, context);
        ctx.setVariable("firstName", data.getFirstName());
        ctx.setVariable("lastName", data.getLastName());
        ctx.setVariable("id", data.getRegistrationDataId());

        String requestBaseAddress = request.getRequestURL().
                substring(0, request.getRequestURL().length() - request.getServletPath().length());

        String confirmationAddress = String.format("%s/registration/confirm?id=%s", requestBaseAddress, data.getRegistrationDataId());
        ctx.setVariable("confirmUrl", confirmationAddress);

        String htmlContent = templateEngine.process(
                "mail/verify_email", ctx);

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        msg.setSubject("Verify email address");
        String from = env.getProperty("spring.mail.username");
        msg.setFrom(from);
        msg.setTo(data.getEmail());
        msg.setText(htmlContent, true);

        sender.send(mimeMessage);
    }

    public void confirmAccount(UUID userId) {
        RegistrationData data = dao.findOne(userId).orElseThrow(
                () -> new IllegalArgumentException("Can't find user with given id")
        );
        dao.delete(userId);

        Person person = fromRegistrationData(data);
        personDao.save(person);
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

    private Person fromRegistrationData(RegistrationData data) {
        Person person = new Person();
        person.setFirstName(data.getFirstName());
        person.setLastName(data.getLastName());
        person.setNickName(data.getUsername());
        person.setPassword(data.getPassword());
        person.setPhoneNumber(data.getPhoneNumber());
        person.setEmail(data.getEmail());
        person.setRegistrationDate(data.getRegistrationDate().toLocalDate());

        return person;
    }

    private UUID generateId() {
        UUID id = UUID.randomUUID();
        while (dao.contains(id)) {
            id = UUID.randomUUID();
        }
        return id;
    }
}
