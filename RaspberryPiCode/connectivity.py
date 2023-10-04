import requests

def connection_stable():
	url = "http://www.google.com"
	
	try:
		response = requests.get(url,timeout=5)
		response.raise_for_status()
		return True
	except requests.RequestException:
		return False
