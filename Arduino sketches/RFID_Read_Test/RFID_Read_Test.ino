#include <SoftwareSerial.h>
SoftwareSerial RFID(2, 3); // RX and TX

int i;
int v;


void setup(){
  RFID.begin(9600); // start serial to RFID reader
  Serial.begin(9600); // start serial to PC

}
void loop(){
  if (RFID.available() > 0){
    i = RFID.read();
    Serial.print(i, DEC);
    v = 1;
  }
  //else{
  //  if(v){
  //    Serial.print("\n");
  //    v = 0;
  //  }
  //}
  
}
