#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>
#include "DFRobot_CCS811.h"

const char* ssid = "RaspberryPi";
const char* password = "raspberry";
const char* mqtt_server = "192.168.1.1";

WiFiClient espClient;
PubSubClient client(espClient);
unsigned long lastMsg = 0;
#define MSG_BUFFER_SIZE (62)
char msg[MSG_BUFFER_SIZE];
int value = 0;


/**
 * Configure and establish WiFi connection.
 *
 * This function is responsible for configuring and establishing the WiFi connection
 * to the network specified in the ssid and password variables.
 */
void setup_wifi() {
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  // Conexión WiFi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
	digitalWrite(LED_BUILTIN, HIGH);
    delay(2000);
	digitalWrite(LED_BUILTIN, LOW);
    Serial.println("Connecting to WiFi network...");
  }
  Serial.println("WiFi connected");
  digitalWrite(LED_BUILTIN, LOW);
}
/**
 * MQTT callback function.
 *
 * This function is executed when an MQTT message is received.
 * It prints the received message and performs an action based on its content.
 */
void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }

  // Switch on the LED if an 1 was received as first character
  if ((char)payload[0] == '1') {
    digitalWrite(BUILTIN_LED, LOW);
  } else {
    digitalWrite(BUILTIN_LED, HIGH);
  }
}

/**
 * Attempt to reconnect to the MQTT broker.
 *
 * This function attempts to reconnect to the MQTT broker if the connection is lost.
 * Upon successful reconnection, it subscribes to a topic and publishes an announcement message.
 * In case of failure, it performs an error sequence and waits before retrying.
 */
void reconnect() {
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    String clientId = "ESP8266Client-";
    clientId += String(random(0xffff), HEX);
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      client.publish("testTopic", "hello world");
      client.subscribe("inTopic");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      /**
       * Sequence of error for MQTT disconnection.
       * The LED is briefly turned off, then turned on for 2 seconds.
       * Next, the LED blinks twice, once every second.
       */
      digitalWrite(BUILTIN_LED, HIGH);
      delay(200);
      digitalWrite(BUILTIN_LED, LOW);
      delay(2000);
      for (byte i = 0; i < 2; i++) {
        digitalWrite(BUILTIN_LED, LOW);
        delay(1000);
        digitalWrite(BUILTIN_LED, HIGH);
        delay(1000);
      }
      digitalWrite(BUILTIN_LED, LOW);
      delay(5000);
    }
  }
}
/**
 * Setup function.
 *
 * Initializes the LED pins and I2C communication.
 * Check the connection and initializes it with the sensors.
 * Finally, it setups the Wi-Fi connection and MQTT connection.
 */
void setup() {
  Wire.begin();
  Serial.begin(115200);
  pinMode(LED_BUILTIN, OUTPUT);
  InitAirSensor();
  InitAmbiMate();
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

/**
 * Main loop function.
 *
 * This function is repeatedly executed in a loop after the setup is complete.
 * It checks the MQTT connection status and performs reconnection if necessary.
 * It also processes incoming MQTT messages, collects data, and publishes it to the MQTT broker.
 */
void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  char chipIdStr[9];
  sprintf(chipIdStr, "%d", ESP.getChipId());

  unsigned long now = millis();
  if (now - lastMsg > 60000) {
    lastMsg = now;
    ++value;

    char dfrobotValues[8];
    snprintf(dfrobotValues, sizeof(dfrobotValues), "%s", DFRobotValues());

    delay(2000);

    char ambimateValues[45];
    snprintf(ambimateValues, sizeof(ambimateValues), "%s;%s", chipIdStr, AmbiMateValues());

    snprintf(msg, MSG_BUFFER_SIZE, "%s;%s", ambimateValues, dfrobotValues);
    Serial.print("Publish message: ");
    Serial.println(msg);

    client.publish("airControlTopic", msg);
  }
}
