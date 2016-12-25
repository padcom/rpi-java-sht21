package commands

import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext

import com.aplaline.rpi.sht21.SHT21

class sht21 {
    @Usage("Read SHT21 sensor values")
    @Command
    def main(InvocationContext context) {
		SHT21 sht21 = context.attributes['spring.beanfactory'].getBean(SHT21)
		return "Temperature ${sht21.readTemperature()}\nHumidity ${sht21.readHumidity()}" 
    }
}
