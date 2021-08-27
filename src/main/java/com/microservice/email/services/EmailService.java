package com.microservice.email.services;

import com.microservice.email.dtos.EmailDto;
import com.microservice.email.enums.StatusEmail;
import com.microservice.email.models.EmailModel;
import com.microservice.email.repositories.EmailRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class EmailService {


    @Autowired
    EmailRepository emailRepository;

    @Autowired
    SendGrid sendGrid;

    EmailModel emailModel = new EmailModel();

    public EmailModel sendEmail(EmailDto emailDto) {
        try {
            Mail mail = PrepareMail.prepareMail(emailDto);
            emailModel.setSendDateEmail(LocalDateTime.now());
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            if (response.getStatusCode() != 202) {

                throw new IOException("Email not sent");
            }

            BeanUtils.copyProperties(emailDto, emailModel);

            emailModel.setStatusEmail(StatusEmail.SENT);

        } catch (IOException e) {

            e.printStackTrace();
            emailModel.setStatusEmail(StatusEmail.ERROR);

        }

        return emailRepository.save(emailModel);
    }
}
