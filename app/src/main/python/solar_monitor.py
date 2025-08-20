import time
import random
import sqlite3
from datetime import datetime
import requests

# Configuration
CONFIG = {
    "database": "energy_db.sqlite",
    "openweather_api_key": "YOUR_API_KEY",  # Replace with your OpenWeatherMap API key
    "location": {"lat": 19.0760, "lon": 72.8777},  # Mumbai coordinates
    "battery_capacity": 10000,  # 10kWh
    "power_thresholds": {"low": 100, "high": 500}
}

class WeatherService:
    @staticmethod
    def get_current_weather():
        try:
            url = f"https://api.openweathermap.org/data/2.5/weather?lat={CONFIG['location']['lat']}&lon={CONFIG['location']['lon']}&appid={CONFIG['openweather_api_key']}"
            response = requests.get(url, timeout=10)
            data = response.json()
            return {
                "temp": data['main']['temp'] - 273.15,  # Convert to Celsius
                "clouds": data['clouds']['all'],
                "condition": data['weather'][0]['main']
            }
        except Exception as e:
            print(f"Weather API error: {e}")
            return {"temp": 20, "clouds": 0, "condition": "Clear"}  # Default values

class SolarPanel:
    def __init__(self):
        self.energy_output = 0
        self.battery_level = CONFIG['battery_capacity']  # Start with full battery

    def calculate_output(self):
        weather = WeatherService.get_current_weather()
        current_hour = time.localtime().tm_hour

        if 6 <= current_hour < 18:  # Daytime
            cloud_factor = max(0, 1 - (weather['clouds'] / 100))
            temperature_factor = max(0, 1 - (weather['temp'] - 25) * 0.02)  # Decrease output for high temperatures
            self.energy_output = random.randint(CONFIG['power_thresholds']['low'], CONFIG['power_thresholds']['high']) * cloud_factor * temperature_factor
        else:  # Nighttime
            self.energy_output = 0

        self.update_battery()

    def update_battery(self):
        if self.energy_output > 0:
            self.battery_level = min(self.battery_level + self.energy_output / 100, CONFIG['battery_capacity'])
        else:
            self.battery_level = max(self.battery_level - 50, 0)  # Discharge when no output

class DataLogger:
    def __init__(self):
        self.connection = sqlite3.connect(CONFIG['database'])
        self.create_table()

    def create_table(self):
        with self.connection:
            self.connection.execute('''
                CREATE TABLE IF NOT EXISTS energy_data (
                    timestamp TEXT,
                    energy_output REAL,
                    battery_level REAL
                )
            ''')

    def log_data(self, energy_output, battery_level):
        with self.connection:
            self.connection.execute('''
                INSERT INTO energy_data (timestamp, energy_output, battery_level)
                VALUES (?, ?, ?)
            ''', (datetime.now().isoformat(), energy_output, battery_level))

def get_energy_data():
    panel = SolarPanel()
    panel.calculate_output()
    return panel.energy_output, panel.battery_level