package pe.com.softlite.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.softlite.notifications.business.TramiteNotifica;

@RestController
@RequestMapping("/api")
public class NotificacionsController {
	
	@Autowired
	private TramiteNotifica tramiteNotifica;
	
	@GetMapping("/sendNotifications")
	public ResponseEntity<String> sendNotifications() {
		try {
			return new ResponseEntity<>(tramiteNotifica.notifica(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al ejecutar el proceso. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
