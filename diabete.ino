//#include <SoftwareSerial.h>
#include <MySignals.h>
#include "Wire.h"
#include "SPI.h"


//String patientId = 1;
String AP ="Hahaha";             //"wifisba"; "equipeBensliman(SOC)"; "DLink";               // CHANGE ME
String PASS =  "tjbl6947";                    //"12345678a";  "labri-sba"; "esisba2019";// CHANGE ME
String HOST = "192.168.121.133";
String PORT = "9005";
String year = "year";
String month = "month";
String day = "day";
String hour = "hour";
String minutes = "minutes";
String deviceKey = "BLI3SC1";
String glucose = "glucose";

int countTrueCommand;
int countTimeCommand; 
boolean found = false; 
float valSensor = 1;

boolean b=true;

 
  
void setup() {
  Serial.println(deviceKey);
   Serial.begin(115200);
 
 MySignals.begin();
 
 
 
  bitSet(MySignals.expanderState, EXP_ESP8266_POWER);
  MySignals.expanderWrite(MySignals.expanderState);

  MySignals.initSensorUART();


MySignals.enableSensorUART(GLUCOMETER);

  delay(1000);

 MySignals.getGlucose();
 

  
  MySignals.enableSensorUART(WIFI_ESP8266);
  delay(1000);
  connectionWifi(AP,PASS);


 



 

  
}


void loop()
{


   Serial.begin(115200);
 

if(b) {
  

//  MySignals.disableMuxUART();

  for (uint8_t i = 0; i < MySignals.glucoseLength; i++)
  {
    
    
    Serial.print(F("measure number:"));
    Serial.println(i + 1);
    Serial.print(F("year:"));
    Serial.println(MySignals.glucometerData[i].year);
    Serial.print(F("month:"));
    Serial.println(MySignals.glucometerData[i].month);
    Serial.print(F("day:"));
    Serial.println(MySignals.glucometerData[i].day);
    Serial.print(F("hour:"));
    Serial.println(MySignals.glucometerData[i].hour);
    Serial.print(F("minutes:"));
    Serial.println(MySignals.glucometerData[i].minutes);
    Serial.print(F("glucose:"));
    Serial.println(MySignals.glucometerData[i].glucose);
    Serial.print(F("meridian:"));
    Serial.println(MySignals.glucometerData[i].meridian);
    Serial.println();

    /*String(patientId),*/
    sendDataToMs("BLI3SC1",
                String(MySignals.glucometerData[i].year), 
                String(MySignals.glucometerData[i].month), 
               String(MySignals.glucometerData[i].day), 
               String(MySignals.glucometerData[i].hour),  
               String(MySignals.glucometerData[i].minutes), 
              String(MySignals.glucometerData[i].glucose));          
  }
 MySignals.enableMuxUART();
 b=false;
 Serial.println(b);
}
}


void sendDataToMs(String deviceKey, String year, String month, String day, String hour, String minutes, String glucose)
{
      /*String patientId,*/
      String getData = "GET /iot/patient/add?deviceKey="+deviceKey+"&year=20"+year+"&month="+month+"&day="+day+"&hour="+hour+"&minutes="+minutes+"&glucose="+glucose;
      //String getData = "GET /api/add?year=20"+year+"&month="+month+"&day="+day+"&hour="+hour+"&minutes="+minutes+"&glucose="+glucose;
      //String getData = "GET /api/add2?glucose="+glucose;
      sendCommand("AT+CIPMUX=1",10,"OK");
      sendCommand("AT+CIPSTART=0,\"TCP\",\""+ HOST +"\","+ PORT,10,"OK");
      sendATcommand("AT+CIPSEND=0,"+String(getData.length()+4),10000,"OK");
      Serial.println(getData);delay(1500);countTrueCommand++;
      sendCommand("AT+CIPCLOSE=0",10,"OK");
      
     
       delay(1000);
}




void connectionWifi(String AP,String PASS){

  sendCommand("AT",10,"OK");
  sendCommand("AT+CWMODE=1",10,"OK");
  sendCommand("AT+CWJAP=\""+ AP +"\",\""+ PASS +"\"",20,"OK");
  sendATcommand("AT+CIFSR",10000,"OK");
  
  }

void sendCommand(String command, int maxTime,char readReplay[]) {
  Serial.print(countTrueCommand);
  Serial.print(". at command => ");
  Serial.print(command);
  Serial.print(" ");
  while(countTimeCommand < (maxTime*1))
  {
    Serial.println(command);//at+cipsend
    if(Serial.find(readReplay))//ok
    {
       
      found = true;
      break;
    }
  
    countTimeCommand++;
  }
  
  if(found == true)
  {
    Serial.println("OYI");
    countTrueCommand++;
    countTimeCommand = 0;
  }
  
  if(found == false)
  {
    Serial.println("Fail");
    countTrueCommand = 0;
    countTimeCommand = 0;
  }
  
  found = false;
 }


 int8_t sendATcommand(String ATcommand,unsigned int timeout,char* expected_answer1)
{

  uint8_t x = 0,  answer = 0;
  char response[500];
  unsigned long previous;

  memset(response, '\0', sizeof(response));    // Initialize the string

  delay(100);

  while ( Serial.available() > 0) Serial.read();   // Clean the input buffer

  delay(1000);
  Serial.println(ATcommand);    // Send the AT command

  x = 0;
  previous = millis();

  // this loop waits for the answer
  do
  {

    if (Serial.available() != 0)
    {
      response[x] = Serial.read();
      x++;
      // check if the desired answer is in the response of the module
      if (strstr(response, expected_answer1) != NULL)
      {
        answer = 1;
        MySignals.println(response);

      }
    }
    // Waits for the asnwer with time out
  }
  while ((answer == 0) && ((millis() - previous) < timeout));

  return answer;
}
