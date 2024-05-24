package com.project.resiRed.service;

import com.project.resiRed.entity.PasswordResetToken;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.PasswordResetTokenRepository;
import com.project.resiRed.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserRepository userRepository;


    public void sendEmail(String toEmail) {
        Optional<User> userOptional = userRepository.findUserByEmail(toEmail);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        User user = userOptional.get();

        String resetCode = generatePasswordResetCode();
        Optional<PasswordResetToken> existingToken = passwordResetTokenRepository.findByUser(user);
        PasswordResetToken token = existingToken.orElse(new PasswordResetToken());

        token.setToken(resetCode);
        token.setUser(user);
        token.setExpirationDate(getExpirationDate(5));  // 5 minutes from now
        passwordResetTokenRepository.save(token);

        Context context = new Context();
        context.setVariable("message", "Este es el código para restablecer tu contraseña: " + resetCode);
        context.setVariable("username", user.getFirstName());

        String processedHtml = templateEngine.process("emailPasswordResetTemplate", context);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("fitlynx@outlook.com");
            helper.setTo(toEmail);
            helper.setSubject("Cambio de Contraseña");
            helper.setText(processedHtml, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }


    private Date getExpirationDate(int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static String generatePasswordResetCode() {
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);
    }
}
