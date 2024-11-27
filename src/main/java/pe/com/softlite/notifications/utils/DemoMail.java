package pe.com.softlite.notifications.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DemoMail {

	private static void enviarConGMail(String destinatario, String asunto_, String cuerpo) {
		// La dirección de correo de envío
		String remitente = "particularpruebatest@gmail.com";
		// La clave de aplicación obtenida según se explica en este artículo:
		String claveemail = "skcqmijwlxtwwnik";

		String asunto = "SISTRADOC. Notificación - Trámites por derivar retrasados";
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", claveemail); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(remitente));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("noelizana@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario)); // Se podrían añadir varios de la misma manera
			message.addRecipient(Message.RecipientType.CC, new InternetAddress("noelizana@gmail.com"));
//			message.addRecipient(Message.RecipientType.CC, new InternetAddress("juanfranciscolizanayajahuanca@gmail.com"));
			
			message.setSubject(asunto);
			
			//Correo simple
//			message.setText(cuerpo);

			//Correo en formato html
			String cuerpoHtml ="<html> \r\n"
							+ "   <body> \r\n"
							+ "      <h3 style=\"color: #3f7320; text-align: left;\">Notificaci&oacute;n del Proceso de Tr&aacute;mite documento</h3> \r\n"
							+ "      <h3 style=\"color: #3f7320; text-align: left;\"><strong style=\"color: #000000; font-size: 16px;\">Estimado Usuario(s).</strong></h3> \r\n"
							+ "      <p>Se hace de conocimiento que existe unos tramites que aun no han sido enviados a las siguientes dependencias y que han excedido el tiempo establecido.</p> \r\n"
							+ "      <p>Las dependencias donde hay tramites por derivar son las siguientes(s):</p> \r\n"
							+ "      <table class=\"demoTable\" style=\"height: 90px; border-style: dotted; border-color: #000000;\" border=\"1\"> \r\n"
							+ "         <thead> \r\n"
							+ "            <tr style=\"height: 18px;\"> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\"><span style=\"color: #0000ff;\"><strong><span style=\"color: #c82828;\">Dependencias</span></strong></span></td> \r\n"
							+ "               <td style=\"height: 18px; width: 336.95px;\"><span style=\"color: #c82828;\">Cantidad de Tr&aacute;mites</span></td> \r\n"
							+ "            </tr> \r\n"
							+ "         </thead> \r\n"
							+ "         <tbody> \r\n"
							+ "            <tr style=\"height: 18px;\"> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\">Mesa de Partes</td> \r\n"
							+ "               <td style=\"height: 18px; width: 336.95px;\">18</td> \r\n"
							+ "            </tr> \r\n"
							+ "            <tr style=\"height: 18px;\"> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\">Rentas</td> \r\n"
							+ "               <td style=\"height: 18px; width: 336.95px;\">14</td> \r\n"
							+ "            </tr> \r\n"
							+ "            <tr style=\"height: 18px;\"> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\">Registro Civil</td> \r\n"
							+ "               <td style=\"height: 18px; width: 336.95px;\">11</td> \r\n"
							+ "            </tr> \r\n"
							+ "            <tr style=\"height: 18px;\"> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\">Asesoria Legal</td> \r\n"
							+ "               <td style=\"height: 18px; width: 336.95px;\">3</td> \r\n"
							+ "            </tr> \r\n"
							+ "         </tbody> \r\n"
							+ "      </table> \r\n"
							+ "      <p>&nbsp;Para mayor detalle de los tr&aacute;mites se puede visualizar en la siguiente ruta.</p> \r\n"
							+ "      <p><span style=\"color: #0000ff;\"><span style=\"text-decoration: underline;\">D:\\Tools\\Tramite.excel\\Input\\Deriver</span></span></p> \r\n"
							+ "   </body> \r\n"
							+ "</html>";
			
			message.setContent(cuerpoHtml, "text/html");
			
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", remitente, claveemail);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException me) {
			me.printStackTrace(); // Si se produce un error
		}catch (Exception e) {
			e.printStackTrace(); // Si se produce un error
		}
	}

	public static void main(String[] args) {
		String destinatario = "luisnoe6@hotmail.com"; // A quien le quieres escribir.
		String asunto = "SISTRADOC. Notificación - Trámites por derivar retrasados";
		String cuerpo = "Esta es una prueba de correo...";

		enviarConGMail(destinatario, asunto, cuerpo);
		System.out.println("Correo enviado, con fe con fe");
	}

}
