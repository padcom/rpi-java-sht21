package com.aplaline.rpi.sht21;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	@RequestMapping(path = "/admin")
	@Secured("ROLE_ADMIN")
	public String admin() {
		return "admin";
	}
}
