#include "FirebaseESP8266.h"
#include <ESP8266WiFi.h>
#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include "MAX30100_PulseOximeter.h"
#include <OneWire.h>
#include <DallasTemperature.h>

#define WIFI_SSID "Nemo 4G"
#define WIFI_PASSWORD "unity@1234"
#define FIREBASE_HOST "texhibition-2021-default-rtdb.firebaseio.com/"
#define FIREBASE_AUTH "CelOPgTLNZMdh823etZrx6fSsWrOuGHcZIuPizf3"

FirebaseData firebaseData;
FirebaseJson json;
void printResult(FirebaseData &data);


#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 64 // OLED display height, in pixels
#define OLED_RESET     -1 // Reset pin # (or -1 if sharing Arduino reset pin)
#define SCREEN_ADDRESS 0x3C ///< See datasheet for Address; 0x3D for 128x64, 0x3C for 128x32
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);
#define NUMFLAKES     10 
#define LOGO_HEIGHT   16
#define LOGO_WIDTH    16
#define REPORTING_PERIOD_MS 1000

#define ONE_WIRE_BUS D3

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
DeviceAddress insideThermometer;


PulseOximeter pox;
 
uint32_t tsLastReport = 0;
uint32_t lastFirebaseTime = 0;

int heartRate = 0;
int spo2 = 0;
float temp = 91.50;

 
void onBeatDetected()
{
}
void setup() 
{
  Serial.begin(115200);

  if(!display.begin(SSD1306_SWITCHCAPVCC, SCREEN_ADDRESS))
  {
    Serial.println(F("SSD1306 allocation failed"));
    for(;;); 
  }

      
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }

  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();
  


    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
    Firebase.reconnectWiFi(true); 
    firebaseData.setBSSLBufferSize(1024, 1024);
    firebaseData.setResponseSize(1024);
    Firebase.setReadTimeout(firebaseData, 1000 * 60);
    Firebase.setwriteSizeLimit(firebaseData, "tiny");

  
  if (!pox.begin()) 
  {
    display.clearDisplay();
    display.setTextSize(1);
    display.setTextColor(1);
    display.setCursor(0, 0);
    display.println("Failed");
    display.display();
    for(;;);
  }
  else 
  {
     pox.setOnBeatDetectedCallback(onBeatDetected);
  }
  
   

    sensors.begin();
    if (sensors.isParasitePowerMode()) Serial.println("ON");
    else Serial.println("OFF");
    if (!sensors.getAddress(insideThermometer, 0)) Serial.println("Unable to find address for Device 0");
    Serial.print("Device 0 Address: ");
    printAddress(insideThermometer);
    Serial.println();
    sensors.setResolution(insideThermometer, 9);

    

}

void loop() 
{ 
        check();
        if(millis() - lastFirebaseTime > 15000)
        {
           lastFirebaseTime =  millis();    
            
           if (Firebase.setInt(firebaseData, "/Hospitals/P9ptLvItc8hNYXiuny02FNkzCs13/Beds/102/HeartRate", pox.getHeartRate()))
            {
              printResult(firebaseData);
            }
            else
            {
            }  

         if (Firebase.setInt(firebaseData, "/Hospitals/P9ptLvItc8hNYXiuny02FNkzCs13/Beds/102/OxygenSaturation", spo2))
            {
              printResult(firebaseData);
            }
            else
            {
            }   

         if (Firebase.setInt(firebaseData, "/Hospitals/P9ptLvItc8hNYXiuny02FNkzCs13/Beds/102/BodyTemperature", temp))
            {
              printResult(firebaseData);
              ESP.restart();
            }
            else
            {
            }           
       }  
}


void check()
{
  pox.update();
  if (millis() - tsLastReport > REPORTING_PERIOD_MS) 
   {   
      heartRate = pox.getHeartRate();
      spo2 = pox.getSpO2();
      display.clearDisplay();
      display.setTextSize(1);
      display.setTextColor(1);
      display.setCursor(0,2);
      display.print("BPM:  ");
      display.print(heartRate);
         
      display.setTextSize(1);
      display.setTextColor(1);
      display.setCursor(0, 20);
      display.print("Spo2: ");
      display.print(spo2);
      display.print("%");
      display.display();

        sensors.requestTemperatures(); 
        float tempC = sensors.getTempC(insideThermometer);
        if(tempC == DEVICE_DISCONNECTED_C) 
        {
          return;
        }
  
      temp = DallasTemperature::toFahrenheit(tempC);
      display.setTextSize(1);
      display.setTextColor(1);
      display.setCursor(0, 40);
      display.print("Temperature: ");
      display.print(temp);
      display.print(" F");
      display.display(); 

     //  Publishing data to the firebase database..........................
     tsLastReport = millis();       
  }
}


















void printAddress(DeviceAddress deviceAddress)
{
  for (uint8_t i = 0; i < 8; i++)
  {
    if (deviceAddress[i] < 16) Serial.print("0");
    Serial.print(deviceAddress[i], HEX);
  }
}

void printResult(FirebaseData &data)
{

    if (data.dataType() == "int")
        Serial.println(data.intData());
    else if (data.dataType() == "float")
        Serial.println(data.floatData(), 5);
    else if (data.dataType() == "double")
        printf("%.9lf\n", data.doubleData());
    else if (data.dataType() == "boolean")
        Serial.println(data.boolData() == 1 ? "true" : "false");
    else if (data.dataType() == "string")
        Serial.println(data.stringData());
    else if (data.dataType() == "json")
    {
        Serial.println();
        FirebaseJson &json = data.jsonObject();
        //Print all object data
        Serial.println("Pretty printed JSON data:");
        String jsonStr;
        json.toString(jsonStr,true);
        Serial.println(jsonStr);
        Serial.println();
        Serial.println("Iterate JSON data:");
        Serial.println();
        size_t len = json.iteratorBegin();
        String key, value = "";
        int type = 0;
        for (size_t i = 0; i < len; i++)
        {
            json.iteratorGet(i, type, key, value);
            Serial.print(i);
            Serial.print(", ");
            Serial.print("Type: ");
            Serial.print(type == FirebaseJson::JSON_OBJECT ? "object" : "array");
            if (type == FirebaseJson::JSON_OBJECT)
            {
                Serial.print(", Key: ");
                Serial.print(key);
            }
            Serial.print(", Value: ");
            Serial.println(value);
        }
        json.iteratorEnd();
    }
    else if (data.dataType() == "array")
    {
        Serial.println();
        //get array data from FirebaseData using FirebaseJsonArray object
        FirebaseJsonArray &arr = data.jsonArray();
        //Print all array values
        Serial.println("Pretty printed Array:");
        String arrStr;
        arr.toString(arrStr,true);
        Serial.println(arrStr);
        Serial.println();
        Serial.println("Iterate array values:");
        Serial.println();
        for (size_t i = 0; i < arr.size(); i++)
        {
            Serial.print(i);
            Serial.print(", Value: ");

            FirebaseJsonData &jsonData = data.jsonData();
            //Get the result data from FirebaseJsonArray object
            arr.get(jsonData, i);
            if (jsonData.typeNum == FirebaseJson::JSON_BOOL)
                Serial.println(jsonData.boolValue ? "true" : "false");
            else if (jsonData.typeNum == FirebaseJson::JSON_INT)
                Serial.println(jsonData.intValue);
            else if (jsonData.typeNum == FirebaseJson::JSON_DOUBLE)
                printf("%.9lf\n", jsonData.doubleValue);
            else if (jsonData.typeNum == FirebaseJson::JSON_STRING ||
                     jsonData.typeNum == FirebaseJson::JSON_NULL ||
                     jsonData.typeNum == FirebaseJson::JSON_OBJECT ||
                     jsonData.typeNum == FirebaseJson::JSON_ARRAY)
                Serial.println(jsonData.stringValue);
        }
    }
}
