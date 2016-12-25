package com.aplaline.rpi.sht21;

import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

public class SHT21 {
	private static final int SHT21_I2C_ADDRESS = 0x40;

	private final I2CDevice device = initSHT21Device();

	public double readTemperature() {
		byte[] data = getSensorData(0xF3);
		return (((((data[0] & 0xFF) * 256) + (data[1] & 0xFF)) * 175.72) / 65536.0) - 46.85;
	}

	public double readHumidity() {
		byte[] data = getSensorData(0xF5);
		return (((((data[0] & 0xFF) * 256) + (data[1] & 0xFF)) * 125.0) / 65536.0) - 6;
	}

	private static I2CDevice initSHT21Device() {
		try {
			I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
			return bus.getDevice(SHT21_I2C_ADDRESS);
		} catch (UnsupportedBusNumberException | IOException e) {
			throw new ErrorWhileInitializingSHT21Device(e);
		}
	}

	private static class ErrorWhileInitializingSHT21Device extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ErrorWhileInitializingSHT21Device(Throwable cause) {
			super(cause);
		}
	}

	private byte[] getSensorData(int address) {
		try {
			device.write((byte) address);
			Thread.sleep(100);
			byte[] data = new byte[2];
			device.read(data, 0, 2);
			return data;
		} catch (IOException | InterruptedException e) {
			throw new ErrorReadingSensorDataException(e);
		}
	}

	private static class ErrorReadingSensorDataException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ErrorReadingSensorDataException(Throwable cause) {
			super(cause);
		}
	}
}
