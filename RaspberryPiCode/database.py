from tinydb import TinyDB, Query
import os
import json

def format_data(temperature,humidity,co,voc,timestamp):
	data = {
		'temperature' : temperature,
		'humidity' : humidity,
		'co' : co,
		'voc' : voc,
		'timestamp': timestamp
	}
	return data

class TinyDBManager:	
	def __init__(self):
		if os.path.exists('data.json'):
			os.remove('data.json')
		else:
			with open('data.json', 'w') as f:
				pass
		self.db = TinyDB('data.json')
		self.classroom_table1 = self.db.table('classroom1')
		self.classroom_table2 = self.db.table('classroom2')
		self.classroom_table3 = self.db.table('classroom3')
			

	def insert_data(self, temperature, humidity, co, voc, timestamp, classroom_id):
		if classroom_id == 1:
			self.classroom_table1.insert(format_data(temperature,humidity,co,voc,timestamp))
		elif classroom_id == 2:
			jsonify_data = format_data(temperature,humidity,co,voc,timestamp)
			self.classroom_table2.insert(format_data(temperature,humidity,co,voc,timestamp))
		elif classroom_id == 3:
			jsonify_data = format_data(temperature,humidity,co,voc,timestamp)
			self.classroom_table3.insert(format_data(temperature,humidity,co,voc,timestamp))
	
	def del_data(self, timestamp, classroom_id):
		if classroom_id == 1:
			self.classroom_table1.remove(Query().timestamp == timestamp)
		elif classroom_id == 2:
			self.classroom_table2.remove(Query().timestamp == timestamp)
		elif classroom_id == 3:
			self.classroom_table3.remove(Query().timestamp == timestamp)

	def verify_table(self, classroom_id):
		if classroom_id == 1:
			return len(self.classroom_table1) > 0
		elif classroom_id == 2:
			return len(self.classroom_table2) > 0
		elif classroom_id == 3:
			return len(self.classroom_table3) > 0
	
	def get_oldest_value(self, classroom_id):
		if classroom_id == 1:
			records = self.classroom_table1.all()
			return records[0]
		elif classroom_id == 2:
			records = self.classroom_table2.all()
			return records[0]
		elif classroom_id == 3:
			records = self.classroom_table3.all()
			return records[0]	
