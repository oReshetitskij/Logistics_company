package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.*;
import edu.netcracker.project.logistic.exception.NonUniqueRecordException;
import edu.netcracker.project.logistic.model.*;
import edu.netcracker.project.logistic.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class RegistrationServiceImpl implements RegistrationService {
    private PersonCrudDao personDao;
    private RegistrationLinkDao registrationLinkDao;
    private RoleCrudDao roleDao;
    private ContactDao contactDao;
    private PersonRoleDao personRoleDao;

    private JavaMailSender sender;
    private TemplateEngine templateEngine;
    private Environment env;
    private HttpServletRequest request;

    @Autowired
    public RegistrationServiceImpl(JavaMailSender sender,
                                   TemplateEngine templateEngine, Environment env,
                                   HttpServletRequest request, PersonCrudDao personDao,
                                   RegistrationLinkDao registrationLinkDao, RoleCrudDao roleDao,
                                   ContactDao contactDao, PersonRoleDao personRoleDao) {
        this.personDao = personDao;
        this.registrationLinkDao = registrationLinkDao;
        this.personRoleDao = personRoleDao;
        this.roleDao = roleDao;
        this.contactDao = contactDao;
        this.sender = sender;
        this.templateEngine = templateEngine;
        this.env = env;
        this.request = request;
    }

    @Transactional
    public void register(RegistrationForm form) throws MessagingException, NonUniqueRecordException {
        Person personData = getPersonData(form);
        Contact contactData = getContactData(form);

        Set<String> duplicates = personDao.findDuplicateFields(personData);
        if (!duplicates.isEmpty()) {
            throw new NonUniqueRecordException(duplicates);
        }

        personDao.save(personData);
        contactDao.save(contactData);

        Role unconfirmedRole = roleDao.getByName("ROLE_UNCONFIRMED")
                .orElseThrow(() -> new IllegalStateException("Can't find 'Unconfired person' role"));

        PersonRole unconfirmedPerson = new PersonRole();
        unconfirmedPerson.setPersonId(personData.getId());
        unconfirmedPerson.setRoleId(unconfirmedRole.getRoleId());
        personRoleDao.save(unconfirmedPerson);

        UUID id = generateId();
        while (registrationLinkDao.contains(id)) {
            id = generateId();
        }
        RegistrationLink link = new RegistrationLink();
        link.setRegistrationLinkId(id);
        link.setPersonId(personData.getId());
        registrationLinkDao.save(link);

        sendConfirmationRequest(contactData, personData.getEmail(), link);
    }

    private void sendConfirmationRequest(Contact contact, String email, RegistrationLink link) throws MessagingException {
        Context ctx = new Context();
        ctx.setVariable("firstName", contact.getFirstName());
        ctx.setVariable("lastName", contact.getLastName());
        ctx.setVariable("id", link.getRegistrationLinkId());

        String requestBaseAddress = request.getRequestURL().
                substring(0, request.getRequestURL().length() - request.getServletPath().length());

        String confirmationAddress = String.format(
                "%s/registration/confirm?id=%s",
                requestBaseAddress,
                link.getRegistrationLinkId()
        );
        ctx.setVariable("confirmUrl", confirmationAddress);

        String htmlContent = templateEngine.process(
                "mail/verify_email", ctx);

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        msg.setSubject("Verify email address");
        String from = env.getProperty("spring.mail.username");
        msg.setFrom(from);
        msg.setTo(email);
        msg.setText(htmlContent, true);

        sender.send(mimeMessage);
    }

    @Transactional
    public void confirmAccount(UUID linkId) {
        RegistrationLink data = registrationLinkDao.findOne(linkId).orElseThrow(
                () -> new IllegalArgumentException("Can't find user with given id")
        );

        List<Role> roles = roleDao.getByPersonId(data.getPersonId());
        Role unconfirmedRole =
                roles
                        .stream()
                        .filter(r -> r.getRoleName().equals("ROLE_UNCONFIRMED"))
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        String.format(
                                                "Account #%d is already confirmed",
                                                data.getPersonId())
                                )
                        );

        PersonRole personRole = new PersonRole();
        personRole.setPersonId(data.getPersonId());
        personRole.setRoleId(unconfirmedRole.getRoleId());
        personRoleDao.delete(personRole);

        Role clientRole = roleDao.getByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Can't find 'User' role"));

        PersonRole client = new PersonRole();
        client.setPersonId(data.getPersonId());
        client.setRoleId(clientRole.getRoleId());
        personRoleDao.save(client);
    }

    private Person getPersonData(RegistrationForm form) {
        Person data = new Person();

        data.setUserName(form.getUsername());
        data.setPassword(form.getPassword());
        data.setEmail(form.getEmail());
        data.setRegistrationDate(LocalDateTime.now());

        return data;
    }

    private Contact getContactData(RegistrationForm form) {
        Contact data = new Contact();

        data.setFirstName(form.getFirstName());
        data.setLastName(form.getLastName());
        data.setPhoneNumber(form.getPhoneNumber());

        return data;
    }

    private UUID generateId() {
        UUID id = UUID.randomUUID();
        while (registrationLinkDao.contains(id)) {
            id = UUID.randomUUID();
        }
        return id;
    }
}
