package pe.com.softlite.notifications.scheduleds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pe.com.softlite.notifications.controller.NotificacionsController;

@Component
@EnableScheduling
public class ScheduledTask {
	
	@Autowired
	private NotificacionsController controller;
	
	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long HORA = MINUTO * 60;

//	@Scheduled(fixedDelay = MINUTO)
	// Se ejecutará De lunes a vierntes (1-5), a horas 8 y 12, con 30 minutos.
	@Scheduled(cron = "0 30 08,12 ? * 1-5")
	public void taskNotification() {
		ResponseEntity<String> result = controller.sendNotifications();
		System.out.println(result.getBody());
		System.out.println("Ejecutando scheduling...");
	}
	
}
