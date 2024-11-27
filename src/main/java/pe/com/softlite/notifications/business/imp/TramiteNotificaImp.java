package pe.com.softlite.notifications.business.imp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.softlite.notifications.business.TramiteNotifica;
import pe.com.softlite.notifications.dto.ResponseResumen;

@Service
public class TramiteNotificaImp implements TramiteNotifica {
	
	@Value("${api.ws.sistradoc.listTramitesDelayed}")
	private String apiUrlSistradocGetListDelayed;
	
	@Value("${mail.smtp.host}")
	private String mailHost;
	
	@Value("${mail.smtp.user}")
	private String mailUser;
	
	@Value("${mail.smtp.clave}")
	private String mailPass;
	
	@Value("${mail.smtp.port}")
	private String mailPort;
	
//	@Value("${mail.emision.configuration.subject}")
	private String mailSubject="SISTRADOC. Notificación - Trámites demorados. ";
	
	@Value("${mail.emision.configuration.recipient.to}")
	private String strListDestiny;
	
	@Value("${mail.emision.configuration.recipient.cc}")
	private String strListDestinyCC;
	
	@Value("${mail.emision.configuration.recipient.cco}")
	private String strListDestinyCCO;
	
	@Override
	public String notifica() throws IOException, InterruptedException {
		
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create(apiUrlSistradocGetListDelayed))
//			    .uri(URI.create("http://localhost:8090/ms-app-ws-sistradoc/querys/getListTramiteByDeriverDelayed"))
			    .header("Content-Type", "application/json")
			    .version(HttpClient.Version.HTTP_1_1)
			    .GET()
			    .build();
		
		HttpResponse<String> httpResponse = null;
		try {
			httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			ObjectMapper objectMapper = new ObjectMapper();
			ResponseResumen[] listReporteResumen = objectMapper.readValue(httpResponse.body(), ResponseResumen[].class);
			String responseBody = httpResponse.body();
			
			if(responseBody.contains("{")) {
				sendMailWithGMail(Arrays.asList (listReporteResumen));
				System.out.println("Tiene tramites");
				return "Tiene tramites, se notificará a los involucrados";
			}else {
				System.out.println("No tiene tramites");
				return "No hay Tiene tramites retrasados";
			}
		} catch (IOException | InterruptedException e) {
			
			e.printStackTrace();
		}
		return "Generó error";
		
	}
	
	private void sendMailWithGMail(List<ResponseResumen> detailResumen) {
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", mailHost); // El servidor SMTP de Google
		props.put("mail.smtp.user", mailUser); // El usuario de la cuenta de Google
		props.put("mail.smtp.clave", mailPass); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", mailPort); // El puerto SMTP seguro de Google
		
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(mailUser));

			String[] listDestinyTo = strListDestiny.split(";");
			for(int i=0;i<listDestinyTo.length;i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(listDestinyTo[i]));				
			}
			
			String[] listDestinyCC = strListDestinyCC.split(";");
			for(int i=0;i<listDestinyCC.length;i++) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(listDestinyCC[i]));				
			}
			
			String[] listDestinyBCC = strListDestinyCCO.split(";");
			for(int i=0;i<listDestinyBCC.length;i++) {
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(listDestinyBCC[i]));				
			}

			String dateMail = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
			message.setSubject(mailSubject + dateMail);
			
			//Correo en formato html
			String cuerpoHtml =
					          "<html> \r\n"
							+ "   <body> \r\n"
							+ "      <h3 style=\"color: #3f7320; text-align: left;\">Notificaci&oacute;n del Proceso de Tr&aacute;mite documento</h3> \r\n"
							+ "      <h3 style=\"color: #3f7320; text-align: left;\"><strong style=\"color: #000000; font-size: 16px;\">Estimado Usuario(s).</strong></h3> \r\n"
							+ "      <p>Se hace de conocimiento que existe unos tramites que aun no han sido enviados a las siguientes dependencias y que han excedido el tiempo establecido.</p> \r\n"
							+ "      <p>Las dependencias donde hay tramites por derivar son las siguientes(s):</p> \r\n"
							+ "      <table class=\"demoTable\" style=\"height: 90px; border-style: dotted; border-color: #000000;\" border=\"1\"> \r\n"
							+ "         <thead> \r\n"
							+ "            <tr style=\"height: 18px;\"> \r\n"
							+ "               <td style=\"height: 18px; width: 200.45px;\"><span style=\"color: #0000ff;\"><strong><span style=\"color: #c82828;\">TipoTramite</span></strong></span></td> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\"><span style=\"color: #0000ff;\"><strong><span style=\"color: #c82828;\">Area</span></strong></span></td> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\"><span style=\"color: #0000ff;\"><strong><span style=\"color: #c82828;\">Dependencias</span></strong></span></td> \r\n"
							+ "               <td style=\"height: 18px; width: 120.95px;\"><span style=\"color: #c82828;\">Cant. Tr&aacute;mites</span></td> \r\n"
							+ "            </tr> \r\n"
							+ "         </thead> \r\n"
							+ "         <tbody> \r\n";
							
							for (int i=0;i<detailResumen.size();i++) {
								cuerpoHtml = cuerpoHtml    
							+ "            <tr style=\"height: 18px;\"> \r\n"
							+ "               <td style=\"height: 18px; width: 200.45px;\">"+detailResumen.get(i).getTipoTramite()+"</td> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\">"+detailResumen.get(i).getArea()+"</td> \r\n"
							+ "               <td style=\"height: 18px; width: 221.45px;\">"+detailResumen.get(i).getDependenciaActual()+"</td> \r\n"
							+ "               <td style=\"height: 18px; width: 120.95px;\">"+detailResumen.get(i).getCantidadTramites()+"</td> \r\n"
							+ "          </tr> \r\n";
							}
							
							cuerpoHtml = cuerpoHtml  
							+ "         </tbody> \r\n"
							+ "      </table> \r\n"
							+ "      <p>&nbsp;Para mayor detalle de los tr&aacute;mites se puede visualizar en la siguiente ruta.</p> \r\n"
							+ "      <p><span style=\"color: #0000ff;\"><span style=\"text-decoration: underline;\">D:\\Tools\\Tramite.excel\\Input\\Deriver</span></span></p> \r\n"
							+ "   </body> \r\n"
							+ "</html>";
			
			message.setContent(cuerpoHtml, "text/html");
			
			Transport transport = session.getTransport("smtp");
			transport.connect(mailHost, mailUser, mailPass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException me) {
			me.printStackTrace(); // Si se produce un error
		}catch (Exception e) {
			e.printStackTrace(); // Si se produce un error
		}
	}
	
}
