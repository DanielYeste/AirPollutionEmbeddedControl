# Wireless Environmental Control System

## Overview

This project implements a wireless environmental control system using an ESP8266 microcontroller connected to environmental control modules. The system is orchestrated by a Raspberry Pi acting as a server, leveraging MQTT for communication.

## Features

- Wireless control of environmental parameters (e.g., temperature, humidity, light intensity).
- MQTT-based communication for real-time updates and control.
- Web-based dashboard for monitoring and controlling the system.

## Hardware Requirements

- ESP8266 microcontroller (NodeMCU, Wemos D1 Mini, etc.)
- Environmental control modules (e.g., temperature sensor, humidity sensor, light sensor, etc.)
- Raspberry Pi (any model with Wi-Fi capabilities)
- Power supply for ESP8266 and Raspberry Pi
- Wi-Fi network for communication

## Software Requirements

- [Arduino IDE](https://www.arduino.cc/en/Main/Software) for ESP8266 programming
- Raspberry Pi OS with Python 3.x installed
- MQTT broker (e.g., [Mosquitto](https://mosquitto.org/))
- Node.js and npm for the web-based dashboard

## Setup Instructions

1. **ESP8266 Setup**
   - Connect the ESP8266 to the environmental control modules (e.g., sensors, actuators) following the provided schematics.
   - Upload the provided Arduino sketch (`esp8266_control.ino`) to the ESP8266 using the Arduino IDE.

2. **Raspberry Pi Setup**
   - Install the required Python packages using `pip install paho-mqtt`.
   - Run the `mqtt_server.py` script on the Raspberry Pi.

3. **MQTT Broker Setup**
   - Install and configure an MQTT broker (e.g., Mosquitto) on a device in the network.

4. **Web-Based Dashboard Setup**
   - Navigate to the `dashboard` directory and run `npm install` to install the required dependencies.
   - Modify the MQTT broker address in the `dashboard.js` file to match your setup.
   - Start the dashboard using `npm start`.

## Usage

- Access the web-based dashboard on your preferred web browser.
- Connect to the MQTT broker by providing the appropriate credentials.
- Monitor and control environmental parameters through the dashboard.

## Architecture

The system architecture consists of the following components:

- ESP8266 microcontroller: Collects data from environmental modules and sends it to the MQTT broker.
- Raspberry Pi: Acts as the server, subscribing to MQTT topics and controlling the modules based on received messages.
- MQTT Broker: Facilitates communication between the ESP8266 and Raspberry Pi.
- Web-Based Dashboard: Provides a user interface for monitoring and controlling the system.
