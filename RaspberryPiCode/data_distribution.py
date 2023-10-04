from temperature import temperature_request
from humidity import humidity_request
from co import co_request
from voc import voc_request
from connectivity import connection_stable
from datetime import datetime, timedelta
from mqtt_data import MQTTData
import string
#2999662: classroom_1(data),


def treat_data(values):
	data = values.split(';')
	if data[0] == '3001443':
		classroom_1(data, get_timestamp())
	elif data[0] == '6966598':
		classroom_2(data, get_timestamp())
	elif data[0] == '2999662':
		classroom_3(data, get_timestamp())
		
def classroom_1(data, timestamp):
	if(connection_stable()):
		
		temperature_request('1',data[2], timestamp)
		humidity_request('1',data[3], timestamp)
		co_request('1',data[-2], timestamp)
		voc_request('1',data[-1], timestamp)
		
		while(connection_stable() and MQTTData.db.verify_table(1)):
			
			old_data = MQTTData.db.get_oldest_value(1)
			temperature_request('1',old_data['temperature'], old_data['timestamp'])
			humidity_request('1',old_data['humidity'], old_data['timestamp'])
			co_request('1',old_data['co'], old_data['timestamp'])
			voc_request('1',old_data['voc'], old_data['timestamp'])
			MQTTData.db.del_data(old_data['timestamp'], 1)
	else:
		
		MQTTData.db.insert_data(data[2],data[3],data[-2],data[-1], timestamp, 1)
		

def classroom_2(data, timestamp):
	if(connection_stable()):
		
		temperature_request('2',data[2], timestamp)
		humidity_request('2',data[3], timestamp)
		co_request('2',data[-2], timestamp)
		voc_request('2',data[-1], timestamp)
		
		while(connection_stable() and MQTTData.db.verify_table(2)):
			
			old_data = MQTTData.db.get_oldest_value(2)
			temperature_request('2',old_data['temperature'], old_data['timestamp'])
			humidity_request('2',old_data['humidity'], old_data['timestamp'])
			co_request('2',old_data['co'], old_data['timestamp'])
			voc_request('2',old_data['voc'], old_data['timestamp'])
			MQTTData.db.del_data(old_data['timestamp'], 2)
	else:
		
		MQTTData.db.insert_data(data[2],data[3],data[-2],data[-1], timestamp, 2)


def classroom_3(data, timestamp):
	if(connection_stable()):
		
		temperature_request('3',data[2], timestamp)
		humidity_request('3',data[3], timestamp)
		co_request('3',data[-2], timestamp)
		voc_request('3',data[-1], timestamp)
		
		while(connection_stable() and MQTTData.db.verify_table(3)):
			
			old_data = MQTTData.db.get_oldest_value(3)
			temperature_request('3',old_data['temperature'], old_data['timestamp'])
			humidity_request('3',old_data['humidity'], old_data['timestamp'])
			co_request('3',old_data['co'], old_data['timestamp'])
			voc_request('3',old_data['voc'], old_data['timestamp'])
			MQTTData.db.del_data(old_data['timestamp'], 3)
	else:
		
		MQTTData.db.insert_data(data[2],data[3],data[-2],data[-1], timestamp, 3)

'''def classroom_4(data):
	temperature_request('4',data[2])'''


def get_timestamp():
	current_date = datetime.now().strftime("%Y-%m-%d")
	timezone_adaptation = (datetime.now() - timedelta(hours=2)).strftime("%H:%M:%S")
	timestamp = f"{current_date}T{timezone_adaptation}Z"
	
	return timestamp
