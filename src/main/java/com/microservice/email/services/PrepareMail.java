package com.microservice.email.services;

import com.microservice.email.dtos.EmailDto;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PrepareMail {

    private static String templateId;



    public static Mail prepareMail(EmailDto emailDto) {

        Mail mail = new Mail();

        Email fromEmail = new Email();
        Email toEmail = new Email();

        fromEmail.setEmail(emailDto.getEmailFrom());
        toEmail.setEmail(emailDto.getEmailTo());

        mail.setFrom(fromEmail);

        Personalization personalization = new Personalization();

        personalization.addTo(toEmail);
        personalization.setSubject(emailDto.getSubject());
        mail.addPersonalization(personalization);
        mail.setTemplateId(templateId);

        return mail;

    }

}
