/* Quelle: http://tschallener.net/AVR/usart_h.html */

#include <avr/io.h>
#include <stdlib.h>

//=======================================================================
//Serielle Schnittstelle mit 8 Bit, no parity, 1 Stoppbit  initialisieren
//=======================================================================
//
void usart_init(uint16_t baud) {

	const uint32_t fosz=8000000L;	//Taktfrequenz 8MHz
//	const uint32_t fosz=´16000000L;	//Taktfrequenz 16MHz

	uint16_t baud_wert;

	//Baudrate einstellen
	baud_wert=fosz/(baud*16L)-1;
	UBRRH=(uint8_t)(baud_wert>>8);
	UBRRL=(uint8_t)baud_wert;     

	//8N1 Daten
	UCSRC|=(1<<URSEL)|(3<<UCSZ0); 
	//Empfangen und Senden
	UCSRB=(1<<RXEN)|(1<<TXEN);
}


//==============================================================
//Empfangsfunktionen
//==============================================================
uint8_t usart_byte_avail(void) {
	//Prüft, ob Byte empfangen wurde
	if(UCSRA&(1<<RXC))
		return 1;
	else
		return 0;
}
//---------------------------------------------------------------
uint8_t usart_getc(void) {

	//Wartet, bis ein Byte empfangen wurde!
	//Liest dann dieses Byte.
	while(!(UCSRA&(1<<RXC)));
		return UDR;
}
//--------------------------------------------------------------
void usart_gets(char *s) {

	//Wartet auf die Zeichen einer Zeichenkette!
	//Liest dann die einzelnen Zeichen der  Zeichenkette
	//einschließlich Endmarke 0 in die Stringvariable s
	char c;

	do{
		c = usart_getc();
		*s=c;
		s++;
	} while(c!=0);
}

//
//Hinweise zum Empfang Zahlen (int, long, foat und double)
//--------------------------------------------------------
// Zahlen werden vor dem Senden in Zeichenketten umgewandelt.
// Sie müssen deshalb mit der obigen Funktion usart_gets_intr als 
// Zeichenketten empfangen werden.
//
// Die Rückumwandung dieser Zeichenkette in Zahlen kann dann mit
// den Funktionen aus <stdlib.h> erfolgen:
//
//    int    atoi(const char* s);    => Umwandlung in int
//    long   atol(const char* s);    => Umwandlung in long
//    double atof(const char* s);    => Umwandlung in double oder float
//
//==============================================================
//Sendefunktionen
//==============================================================
void usart_putc(uint8_t byte) {

	//Ein Byte senden
	while(!(UCSRA&(1<<UDRE)));//warten auf Datenregister empty
	UDR=byte;
}
//--------------------------------------------------------------
void usart_puts(char *s) {

	//Einen String mit Endmarke 0 senden
    while (*s!=0) {
        usart_putc(*s);
		s++;
    }
    usart_putc(0); //Endmarke 0 übertragen!
}
//---------------------------------------------------------------
void usart_puti(int zahl, int sges) {

	//Senden der Integerzahl zahl formatiert  mit sges Stellen 
	//als Zeichenkette
	char buffer[16];
	uint8_t l=0,n;
	char *z=buffer;
	itoa(zahl,buffer,10);//Integer to String (ASCII)

	while(*z!=0) //Stellenbedarf l für zahl
	{
		l++;
		z++;
	}

	for(n=l;n<sges;n++)
		 usart_putc(' '); //Leerstellen senden

	usart_puts(buffer);//Zahl senden
}
//--------------------------------------------------------------
void usart_putf(float zahl, int sges, int snach) {

	//Senden einer Fließkommazahl mit sges Gesamtstellen 
	//als Zeichenkette
	//Hiervon sind snach Nachkommastellen.
	//Die Nachkollastellen werden gerundet. 
	char buffer[16];
	dtostrf(zahl,sges,snach,buffer);//Double to String formatiert
	usart_puts(buffer);
}
//--------------------------------------------------------------
void usart_putui(unsigned int zahl, int sges) {

	//Senden der Integerzahl zahl formatiert  mit sges Stellen
	//als Zeichenkette  
	char buffer[16];
	uint8_t l=0,n;
	char *z=buffer;
	utoa(zahl,buffer,10);//Unsigned to String (ASCII)

	while(*z!=0) //Bufferlänge l
	{
		l++; 
		z++;
	}

	for(n=l;n<sges;n++)
		usart_putc(' '); 

	usart_puts(buffer);
}
//--------------------------------------------------------------
void usart_putui_0(unsigned int zahl, int sges) {

	//Senden der Integerzahl zahl formatiert  mit sges Stellen
	//als zeichekette
	//Leerstellen werden mit 0 aufgefüllt (TSC)
	char buffer[16];
	uint8_t l=0,n;
	char *z=buffer;

	utoa(zahl,buffer,10);//Unsigned to String (ASCII)

	while(*z!=0)	//Bufferlänge l
	{
		l++;
		z++;
	}
	
	for(n=l;n<sges;n++)
		usart_putc('0');

	usart_puts(buffer);
}
//--------------------------------------------------------------
void usart_putui_hex(unsigned int zahl, int sges) {

	//Senden der nur-positiven Integerzahl zahl formatiert  mit sges Stellen
	// das Ausgabeformat ist hex (TSC)
	char buffer[17];
	uint8_t l=0,n;
	char *z=buffer;
	utoa(zahl,buffer,16);//Unsigned to String (ASCII)

	while(*z!=0) //Bufferlänge l
	{
		l++;
		z++;
	}

	for(n=l;n<sges;n++)
		 usart_putc(' '); 
		
	usart_puts(buffer);
}
//--------------------------------------------------------------
void usart_putui_bin(unsigned int zahl, int sges) {

	//Senden der Integerzahl zahl formatiert  mit sges Stellen
	// Das Ausgabeformat ist binär. Leerstellen werden mit 0 aufgefüllt.
	char buffer[17];
	uint8_t l=0,n;
	char *z=buffer;
	utoa(zahl,buffer,2);//Unsigned to String (ASCII)

	while(*z!=0) //Bufferlänge l
	{
		l++;
		z++;
	}
	
	for(n=l;n<sges;n++)
		usart_putc('0');
		
	usart_puts(buffer);
}
//--------------------------------------------------------------

void usart_putb(uint8_t a) {

	//Senden einer 8-Bit-Zahl binär(TSC)
	usart_putui_bin(a,8);
}
/*
---------------------------------------------------------------
Hinweise:
Die Übertragung der Daten erfolgt grundsätzlich in ASCII-Form.

Beispiel Senden einer Integer-Zahl: -1230
         Die Zahl wird eine Zeichenkette "-1230" umgewandelt und die einzelnen
		 Zeichen dann mit abschließender Endmarke gesendet.
		 
		 Empfang einer Integer-Zahl:
		 Es wird eine Zeichenkette bis einschließlich der abschließenden 
		 Endmarke 0 eingelesen. Danach wird die empfangene Zeichenkette in 
		 eine Zahl umgewandelt.

-----------------------------------------------------------------
*/
