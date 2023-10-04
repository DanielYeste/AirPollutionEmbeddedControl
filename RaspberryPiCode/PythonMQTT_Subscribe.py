"""
Python MQTT Subscription client
"""
import paho.mqtt.client as mqtt
import requests
from data_distribution import treat_data
from database import TinyDBManager
from mqtt_data import MQTTData

mqtt_topic = "airControlTopic"
mqtt_broker_ip = "192.168.1.1"

client = mqtt.Client()
#client.username_pw_set(mqtt_username, mqtt_password)
last_temp = 0
def on_connect(client, userdata, flags, rc):
    # rc is the error code returned when connecting to the broker
    print ("Connected!"), str(rc)
    
    # Once the client has connected to the broker, subscribe to the topic
    client.subscribe(mqtt_topic)
    MQTTData.db = TinyDBManager() #Initializes the database
    
def on_message(client, userdata, msg):
    temp = str(msg.payload)[2:-1]
    if temp[0] != 'h':
        print("Message received : "  + temp + " on " + msg.topic)
        treat_data(temp)
    
    # The message itself is stored in the msg variable
    # and details about who sent it are stored in userdata
	

# Here, we are telling the client which functions are to be run
# on connecting, and on receiving a message
client.on_connect = on_connect
client.on_message = on_message

# Once everything has been set up, we can (finally) connect to the broker
# 1883 is the listener port that the MQTT broker is using
client.connect(mqtt_broker_ip, 1883)

# Once we have told the client to connect, let the client object run itself
client.loop_forever()
client.disconnect()
