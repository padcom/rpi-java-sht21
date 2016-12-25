package com.aplaline.rpi.sht21;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	private final JdbcTemplate db;

	@Autowired
	public IndexController(JdbcTemplate db) {
		this.db = db;
	}

	@RequestMapping("/")
	public String index() {
		Map<String, Object> data = db.queryForMap("SELECT TOP 1 temperature, humidity FROM SensorData ORDER BY id DESC");
		return String.format("Temperature: %.2fC \nHumidity: %.2f%% \n", data.get("TEMPERATURE"), data.get("HUMIDITY"));
	}
}
