#ifdef __IN_ECLIPSE__
//This is a automatic generated file
//Please do not modify this file
//If you touch this file your change will be overwritten during the next build
//This file has been generated on 2023-06-08 14:08:23

#include "Arduino.h"
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>
#include "DFRobot_CCS811.h"

char* DFRobotValues() ;
void InitAirSensor();
void InitAmbiMate() ;
char* AmbiMateValues();
void setup_wifi() ;
void callback(char* topic, byte* payload, unsigned int length) ;
void reconnect() ;
void setup() ;
void loop() ;


#include "AirQuality.ino"
#include "AmbiMate.ino"
#include "Main.ino"

#endif
