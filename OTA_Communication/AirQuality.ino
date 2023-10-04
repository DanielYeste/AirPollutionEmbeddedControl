/*!
 * @file readData.ino
 * @brief Read the concentration of carbon dioxide and TVOC
 * @n Experiment phenomenon: read data every 0.5s, and print it out on serial port.
 * @copyright	Copyright (c) 2010 DFRobot Co.Ltd (http://www.dfrobot.com)
 * @license     The MIT License (MIT)
 * @author [LuoYufeng](yufeng.luo@dfrobot.com)
 * @maintainer  [fary](feng.yang@dfrobot.com)
 * @version  V0.1
 * @date  2019-07-19
 * @url https://github.com/DFRobot/DFRobot_CCS811
 */
DFRobot_CCS811 CCS811;
char* robotOutput = new char[8];
char* DFRobotValues() {
    if(CCS811.checkDataReady() == true){
        sprintf(robotOutput, "%d;%d", CCS811.getCO2PPM(), CCS811.getTVOCPPB());

    } else {
        Serial.println("Data is not ready!");
    }

    CCS811.writeBaseLine(0xB3B5);
    return robotOutput;
}

void InitAirSensor(){
	while(CCS811.begin() != 0){
		  /**
		   * The error code for a failed to init airsensor will be one blink
		   * for one second and two short blinks for half a second.
		   */
		  digitalWrite(LED_BUILTIN, HIGH); // Turn on the LED
		  delay(1000); // Wait for 1 second

		  digitalWrite(LED_BUILTIN, LOW); // Turn off the LED
		  delay(1000); // Wait for 1 second

		  // Blink the LED twice quickly
		  digitalWrite(LED_BUILTIN, HIGH);
		  delay(500);
		  digitalWrite(LED_BUILTIN, LOW);
		  delay(500);
		  digitalWrite(LED_BUILTIN, HIGH);
		  delay(500);
		  digitalWrite(LED_BUILTIN, LOW);
		  delay(1000);
	}
}

