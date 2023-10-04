import requests
from mqtt_data import MQTTData
import time

def voc_request(classroom_id, value, timestamp):
	feed_key = "aula"+classroom_id+".voc"
	
	url = f"https://io.adafruit.com/api/v2/{MQTTData.username}/feeds/{feed_key}/data"
	
	headers = {"X-AIO-Key": MQTTData.io_key, 'Connection':'close'}
	data = {"value" : value, "created_at": timestamp}
	
	try:
		response = requests.post(url,headers=headers,data=data,timeout=15)
		response.close()
		response.raise_for_status()
	except requests.exceptions.RequestException as e:
		print("Error",str(e))
		MQTTData.lost_packets += 1
		print(MQTTData.lost_packets)
