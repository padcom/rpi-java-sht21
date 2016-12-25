package com.aplaline.rpi.sht21;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class TemperatureReaderTask implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(TemperatureReaderTask.class);

	private final SHT21 sensor;
	private final JdbcTemplate db;

	public TemperatureReaderTask(SHT21 sensor, JdbcTemplate db) {
		this.sensor = sensor;
		this.db = db;
	}

	private void readSensorData() {
		double temperature = sensor.readTemperature();
		double humidity = sensor.readHumidity();

		log.debug(String.format("Temperature: %.2f, Humidity: %.2f", temperature, humidity));

		db.update("INSERT INTO SensorData (timestamp, temperature, humidity) VALUES (?, ?, ?)",
				new Date().getTime(),
				new BigDecimal(temperature),
				new BigDecimal(humidity));
	}

	@Override
	public void run() {
		readSensorData();
	}
}
