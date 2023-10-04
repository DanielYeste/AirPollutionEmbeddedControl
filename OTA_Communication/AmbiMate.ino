unsigned char buf[20];
char* ambiMateValues = new char[35];

void InitAmbiMate() {
	/**
	 * The first read will be 0 at all sensors, so we check it twice
	 * after a delay to ensure the data has been read.
	 */
	AmbiMateValues();
	delay(5000);
	char* values = AmbiMateValues();
	char* delimiter = strchr(values, ';');
	char* temperatureStr = delimiter + 1;
	if (strcmp(temperatureStr, "0.0") == 0) {
	  for (byte i = 0; i < 3; i++) {
		/**
		 * Error message for ambimate malfunction. Three short blinks
		 */
		digitalWrite(LED_BUILTIN, HIGH);
		delay(500);
		digitalWrite(LED_BUILTIN, LOW);
		delay(500);
	  }
	  delay(1000);
	  InitAmbiMate();
	}
}

char* AmbiMateValues(){
	/**
	 * Based on AmbiMate sensors, we will get the following data:
	 * temperature, battery, audio, light, humidity and temperature.
	 * This will be passed as unsigned char with the whole data.
	 * */
	  Wire.beginTransmission(0x2A); // transmit to device
	  // Device address is specified in datasheet
	  Wire.write(byte(0xC0));       // sends instruction to read sensors in next byte
	  Wire.write(0x7F);             // 0xFF indicates to read all connected sensors
	  Wire.endTransmission();       // stop transmitting
	  // Delay to make sure all sensors are scanned by the AmbiMate
	  delay(100);

	  Wire.beginTransmission(0x2A); // transmit to device
	  Wire.write(byte(0x00));       // sends instruction to read sensors in next byte
	  Wire.endTransmission();       // stop transmitting
	  Wire.requestFrom(0x2A, 15);    // request 6 bytes from slave device
	  delay(100);
	  Wire.endTransmission();

	  // Acquire the Raw Data
	  unsigned int i = 0;
	  while (Wire.available()) { // slave may send less than requested
	    buf[i] = Wire.read(); // receive a byte as character and store in buffer
	    i++;
	  }
	  unsigned int status = buf[0];
	  float temperatureC = (buf[1] * 256.0 + buf[2]) / 10.0;
	  float temperatureF = ((temperatureC * 9.0) / 5.0) + 32.0;
	  float humidity = (buf[3] * 256.0 + buf[4]) / 10.0;
	  unsigned int light = (buf[5] * 256.0 + buf[6]);
	  unsigned int audio = (buf[7] * 256.0 + buf[8]);
	  float batVolts = ((buf[9] * 256.0 + buf[10]) / 1024.0) * (3.3 / 0.330);

	  char output[35];
	  snprintf(output, sizeof(output), "%u;%.1f;%.2f;%.1f;%u;%.2f",
			  buf[0], (float)((int)(temperatureC * 10))/10,
			  (float)((int)(temperatureF * 100))/100, (float)((int)(humidity * 10))/10,
			  buf[5] * 256 + buf[6],
			  buf[7] * 256 + buf[8], batVolts);
	  // allocate memory for the result and copy the string
	  strcpy(ambiMateValues, output);

	  // return the result
	  return ambiMateValues;

}
