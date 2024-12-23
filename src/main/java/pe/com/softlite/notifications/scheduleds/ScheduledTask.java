package pe.com.softlite.notifications.scheduleds;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

import pe.com.softlite.notifications.controller.NotificacionsController;

@Component
@EnableScheduling
public class ScheduledTask {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);
	
	@Autowired
	private NotificacionsController controller;
	
	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long HORA = MINUTO * 60;

//	@Scheduled(fixedDelay = MINUTO)
	// Se ejecutará De lunes a vierntes (1-5), a horas 8 y 12, con 30 minutos.
	@Scheduled(cron = "0 30 08,12 ? * 1-5")
	public void taskNotification() {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(correlationId + ":::: Proceso notificacion programada. Inicio :::: '{}' ", ScheduledTask.class.getName());
		LOGGER.info(correlationId + ":::: Proceso notificacion programada. Hora: :::: '{}' ", new Date());
		ResponseEntity<String> result = controller.sendNotifications();
		LOGGER.info(correlationId + ":::: Proceso notificacion programada. Resultado: :::: '{}' ", result);
		LOGGER.info(correlationId + ":::: Proceso notificacion programada. Fin :::: '{}' ", "Hecho");
	}
	
}
