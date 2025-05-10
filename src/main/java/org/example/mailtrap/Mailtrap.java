package org.example.mailtrap;

import org.example.model.LectorDeDatos;
import org.example.model.Empleado;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

public class Mailtrap {

    private final String mailtrapUsername;
    private final String mailtrapPassword;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM"); // Formato para comparar día y mes

    public Mailtrap(String username, String password) {
        this.mailtrapUsername = username;
        this.mailtrapPassword = password;
    }

    public boolean enviarCorreo(String toEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Use TLS

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailtrapUsername, mailtrapPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cumpleanos@tuempresa.com")); // Dirección para cumpleaños
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Correo enviado a " + toEmail + " con asunto: " + subject);
            return true;

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo a " + toEmail + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void verificarCumpleanosYEnviarCorreo(List<Empleado> empleados) {
        LocalDate hoy = LocalDate.now();
        String hoyFormateado = hoy.format(DATE_FORMATTER);

        for (Empleado empleado : empleados) {
            String cumpleanosFormateado = empleado.getFechaNacimiento().format(DATE_FORMATTER);
            if (hoyFormateado.equals(cumpleanosFormateado)) {
                String recipientEmail = empleado.getEmail();
                String subject = "Feliz Cumpleanos, " + empleado.getNombre() + "!";
                String body = String.format("%s %s,\n\n" +
                        "Le deseamos un muy feliz cumpleanos\n\n" +
                        "Que tenga un día maravilloso\n\n" +
                        "Attte,\nFacundo", empleado.getNombre(), empleado.getApellido());
                enviarCorreo(recipientEmail, subject, body);
            }
        }
    }

    public void procesarArchivoYVerificarCumpleanos(String rutaArchivo) {
        LectorDeDatos dataReader = new LectorDeDatos();
        try {
            List<Empleado> empleados = dataReader.cargarEmpleados(new java.io.File(rutaArchivo));
            verificarCumpleanosYEnviarCorreo(empleados);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error en el formato del archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void Main(String[] args) {
        String mailtrapUsername = "11b41f4666962d";
        String mailtrapPassword = "7e86e591d3458f";
        String rutaArchivoEmpleados = "empleados.txt";

        Mailtrap mailtrap = new Mailtrap(mailtrapUsername, mailtrapPassword);
        mailtrap.procesarArchivoYVerificarCumpleanos(rutaArchivoEmpleados);
    }
}