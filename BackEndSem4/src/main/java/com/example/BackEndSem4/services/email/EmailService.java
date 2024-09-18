package com.example.BackEndSem4.services.email;

import com.example.BackEndSem4.dtos.EmailMedicalResultDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Email;
import com.example.BackEndSem4.models.Prescription;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(Email email) throws DataNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("novena.clinics@gmail.com");
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());

            // Đọc nội dung tệp HTML từ thư mục resources/templates
            String htmlTemplate = new String(Files.readAllBytes(Paths.get(new ClassPathResource("templates/EmailBooking.html").getURI())));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Thay thế các phần tử động trong nội dung HTML với thông tin từ EmailBookingDTO
            htmlTemplate = htmlTemplate.replace("${fullName}", email.getEmailBookingDTO().getFullName());
            htmlTemplate = htmlTemplate.replace("${email}", email.getEmailBookingDTO().getEmail());
            htmlTemplate = htmlTemplate.replace("${phoneNumber}", email.getEmailBookingDTO().getPhoneNumber());


            htmlTemplate = htmlTemplate.replace("${doctorName}", email.getEmailBookingDTO().getDoctorName());
            htmlTemplate = htmlTemplate.replace("${specialtyName}", email.getEmailBookingDTO().getSpecialtyName());
            htmlTemplate = htmlTemplate.replace("${clinicName}", email.getEmailBookingDTO().getClinicName());
            htmlTemplate = htmlTemplate.replace("${clinicAddress}", email.getEmailBookingDTO().getClinicAddress());

            htmlTemplate = htmlTemplate.replace("${dateSchedule}", email.getEmailBookingDTO().getDateSchedule().format(dateFormatter));
            htmlTemplate = htmlTemplate.replace("${startTime}", email.getEmailBookingDTO().getStartTime().format(timeFormatter));
            htmlTemplate = htmlTemplate.replace("${endTime}", email.getEmailBookingDTO().getEndTime().format(timeFormatter));

            helper.setText(htmlTemplate, true); // true để chỉ định nội dung là HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new DataNotFoundException("Send Email Failed.");
        }
    }

    public void sendEmailMedicalResult(Email email) throws DataNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("novena.clinics@gmail.com");
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());

            // Đọc nội dung tệp HTML từ thư mục resources/templates
            String htmlTemplate = new String(Files.readAllBytes(Paths.get(new ClassPathResource("templates/EmailMedicalResult.html").getURI())));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Thay thế các phần tử động trong nội dung HTML với thông tin từ EmailMedicalResultDTO
            EmailMedicalResultDTO dto = email.getEmailMedicalResultDTO();

            htmlTemplate = htmlTemplate.replace("${fullName}", dto.getFullName());
            htmlTemplate = htmlTemplate.replace("${email}", dto.getEmail());
            htmlTemplate = htmlTemplate.replace("${phoneNumber}", dto.getPhoneNumber());
            htmlTemplate = htmlTemplate.replace("${doctorName}", dto.getDoctorName());
            htmlTemplate = htmlTemplate.replace("${specialtyName}", dto.getSpecialtyName());
            htmlTemplate = htmlTemplate.replace("${clinicName}", dto.getClinicName());
            htmlTemplate = htmlTemplate.replace("${dateSchedule}", dto.getDateSchedule().format(dateFormatter));

            htmlTemplate = htmlTemplate.replace("${diagnosis}", dto.getDiagnosis());

            // Nếu có thông tin về đơn thuốc (prescriptions)
            if (dto.getPrescriptions() != null && !dto.getPrescriptions().isEmpty()) {
                StringBuilder prescriptionDetails = new StringBuilder();

                // Bắt đầu bảng HTML
                prescriptionDetails.append("<table style='border-collapse: collapse; width: 100%;'>")
                        .append("<thead>")
                        .append("<tr>")
                        .append("<th style='border: 1px solid #ddd; padding: 8px;'>Medicine</th>")
                        .append("<th style='border: 1px solid #ddd; padding: 8px;'>Unit</th>")
                        .append("<th style='border: 1px solid #ddd; padding: 8px;'>Usage</th>")
                        .append("</tr>")
                        .append("</thead>")
                        .append("<tbody>");

                // Thêm các đơn thuốc vào bảng
                for (Prescription prescription : dto.getPrescriptions()) {
                    prescriptionDetails.append("<tr>")
                            .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(prescription.getMedicine()).append("</td>")
                            .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(prescription.getUnit()).append("</td>")
                            .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(prescription.getDesciptionUsage()).append("</td>")
                            .append("</tr>");
                }

                // Kết thúc bảng HTML
                prescriptionDetails.append("</tbody></table>");

                // Thay thế phần tử trong template bằng bảng đã tạo
                htmlTemplate = htmlTemplate.replace("${prescriptions}", prescriptionDetails.toString());
            } else {
                htmlTemplate = htmlTemplate.replace("${prescriptions}", "No prescriptions available.");
            }


            helper.setText(htmlTemplate, true); // true để chỉ định nội dung là HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new DataNotFoundException("Send Email Failed.");
        }
    }




    public void sendEmailReplyContact(String toEmail,  String reply) throws DataNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("alishop.brands@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Thank You for Contacting Ali Shop – Here's Our Response");
            helper.setText(reply);
            mailSender.send(message);
        } catch (Exception e) {
            throw new DataNotFoundException("Send Email Failed.");
        }
    }


}
