package com.aplaline.rpi.sht21;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	private final JdbcTemplate db;

	@Autowired
	public IndexController(JdbcTemplate db) {
		this.db = db;
	}

	@RequestMapping(path = "/", produces = "text/html")
	public String index(Model model) {
		populateModelWithSensorData(model, getLatestSensorData());
		return "index";
	}

	@RequestMapping(path = "/", produces = "application/json")
	@ResponseBody
	public Map<String, Object> indexJSON() {
		return getLatestSensorData();
	}

	private void populateModelWithSensorData(Model model, Map<String, Object> data) {
		model.addAttribute("temperature", data.get("TEMPERATURE"));
		model.addAttribute("humidity", data.get("HUMIDITY"));
	}

	private Map<String, Object> getLatestSensorData() {
		return db.queryForMap("SELECT TOP 1 temperature, humidity FROM SensorData ORDER BY id DESC");
	}
}
