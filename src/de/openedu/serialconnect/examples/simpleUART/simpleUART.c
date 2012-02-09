#include <avr/io.h>
#include <util/delay.h>
#include <math.h>
#include "uart.h"

// Wechselt den Zustand an PORTD Pin6
void blink()
{
	if(PIND&(1<<PD6))
		PORTD &= ~(1<<PD6);
	else
		PORTD |= 1<<PD6;
}

int main(void)
{
	usart_init(9600);	// Initialisieren der seriellen Schnittstelle

	DDRD = 1<<PD6;		// Bit6 PortD als Ausgang festlegen

	double x=0, y;		// Variablen 

	double pi_ =  M_PI / 25.0;

	while(1)
	{
	//	usart_putf(x,6,4);		// Ausgabe von x
	//	usart_puts(",");		// Ausgabe des Kommas
	//	usart_putf(sin(x),6,4);	// Berechnen und ausgeben von Sinus x
	//	usart_puts("\n");		// Zeilenumbruch senden
		usart_puts("Hallo Welt! \n");
		blink();				// Signal wechseln	

		_delay_ms(1000);		// Wartezeit bis zum naechsten Senden

		x+=pi_;					// Counter fuer x
	}
	
	return 0;

}
