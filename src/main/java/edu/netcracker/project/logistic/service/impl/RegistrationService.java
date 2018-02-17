package edu.netcracker.project.logistic.service.impl;
//
//import edu.netcracker.project.logistic.dao.PersonCrudDao;
//import edu.netcracker.project.logistic.dao.RegistrationLinkDao;
//import edu.netcracker.project.logistic.dao.RoleCrudDao;
//import edu.netcracker.project.logistic.model.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@PropertySource("classpath:application.properties")
public class RegistrationService {
//    private PersonCrudDao personDao;
//    private RegistrationLinkDao registrationLinkDao;
//    private RoleCrudDao roleDao;
//
//    private JavaMailSender sender;
//    private TemplateEngine templateEngine;
//    private Environment env;
//    private HttpServletRequest request;
//    private ServletContext context;
//
//    @Autowired
//    public RegistrationService(JavaMailSender sender,
//                               TemplateEngine templateEngine, Environment env,
//                               HttpServletRequest request, ServletContext context,
//                               PersonCrudDao personDao, RegistrationLinkDao registrationLinkDao,
//                               RoleCrudDao roleDao) {
//        this.personDao = personDao;
//        this.registrationLinkDao = registrationLinkDao;
//        this.roleDao = roleDao;
//        this.sender = sender;
//        this.templateEngine = templateEngine;
//        this.env = env;
//        this.request = request;
//        this.context = context;
//    }
//
//    public Person register(RegistrationForm form) throws MessagingException {
//        Person personData = getPersonData(form);
//        Contact contactData = getContactData(form);
//
//        personDao.save(personData);
//
//        UUID id = generateId();
//        while (!registrationLinkDao.contains(id)) {
//            id = generateId();
//        }
//        RegistrationLink link = new RegistrationLink();
//        link.setRegistrationLinkId(id);
//        link.setPersonId(personData.getId());
//        registrationLinkDao.save(link);
//
//
//        sendConfirmationRequest(personData, link);
//        return personData;
//    }
//
//    private void sendConfirmationRequest(Person person, Contact contact, RegistrationLink link) throws MessagingException {
//        Context ctx = new Context();
//        ctx.setVariable("firstName", contact.getFirstName());
//        ctx.setVariable("lastName", contact.getLastName());
//        ctx.setVariable("id", link.getRegistrationLinkId());
//
//        String requestBaseAddress = request.getRequestURL().
//                substring(0, request.getRequestURL().length() - request.getServletPath().length());
//
//        String confirmationAddress = String.format(
//                "%s/registration/confirm?id=%s",
//                requestBaseAddress,
//                link.getRegistrationLinkId()
//        );
//        ctx.setVariable("confirmUrl", confirmationAddress);
//
//        String htmlContent = templateEngine.process(
//                "mail/verify_email", ctx);
//
//        MimeMessage mimeMessage = sender.createMimeMessage();
//        MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//        msg.setSubject("Verify email address");
//        String from = env.getProperty("spring.mail.username");
//        msg.setFrom(from);
//        msg.setTo(person.getEmail());
//        msg.setText(htmlContent, true);
//
//        sender.send(mimeMessage);
//    }
//
//    public void confirmAccount(UUID linkId) {
//        RegistrationLink data = registrationLinkDao.findOne(linkId).orElseThrow(
//                () -> new IllegalArgumentException("Can't find user with given id")
//        );
//
//        // Remove 'ROLE_UNCONFIRMED', ADD 'ROLE_CLIENT'
//        List<Role> roles = roleDao.getByPersonId(data.getPersonId());
//        Role unconfirmedRole =
//                roles
//                        .stream()
//                        .filter(r -> r.getRoleName().equals("ROLE_UNCONFIRMED"))
//                        .findFirst()
//                        .orElseThrow(() -> new IllegalStateException("User is already confirmed"));
//
//
//
//        Person person = fromRegistrationData(data);
//        personDao.save(person);
//    }
//
//    private Person getPersonData(RegistrationForm form) {
//        Person data = new Person();
//
//        data.setUserName(form.getUsername());
//        data.setPassword(form.getPassword());
//        data.setEmail(form.getEmail());
//        data.setRegistrationDate(LocalDateTime.now());
//
//        return data;
//    }
//
//    private Contact getContactData(RegistrationForm form) {
//        Contact data = new Contact();
//
//        data.setFirstName(form.getFirstName());
//        data.setLastName(form.getLastName());
//        data.setPhoneNumber(form.getPhoneNumber());
//
//        return data;
//    }
//
//    private UUID generateId() {
//        UUID id = UUID.randomUUID();
//        while (personDao.contains(id)) {
//            id = UUID.randomUUID();
//        }
//        return id;
//    }
}
