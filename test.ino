#include <SoftwareSerial.h> //시리얼 통신 라이브러리 호출
SoftwareSerial Bluetooth(12,13);

const int SOUND_SENSOR_PIN = A0;
int a; 


void setup()
{
  Bluetooth.begin(9600);
}

void loop()
{
  a = analogRead(SOUND_SENSOR_PIN);
  if(a > 0 && a < 57)
  {
      Bluetooth.print("thingspeak:key=ZM1V5CYDQV2UPL33&field1=");
      Bluetooth.print(a);
      Bluetooth.print("[*]");
      delay(2000);
   }
   else if (a > 56 && a < 65)
   {
      Bluetooth.print("thingspeak:key=ZM1V5CYDQV2UPL33&field1=");
      Bluetooth.print(a);
      Bluetooth.print("[*]");
      Bluetooth.print("Be careful");
      delay(2000);
   }
   else
   {
      Bluetooth.print("thingspeak:key=ZM1V5CYDQV2UPL33&field1=");
      Bluetooth.print(a);
      Bluetooth.print("[*]");
      Bluetooth.print("Warning");
      delay(2000);
   }
}
