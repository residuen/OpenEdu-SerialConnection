#include <avr/io.h>
#include <util/delay.h>
#include "uart.h"

void blink()
{
		if(PIND&(1<<PD6))
			PORTD &= ~(1<<PD6);
		else
			PORTD |= 1<<PD6;
}

int main(void)
{
	usart_init(9600);

	DDRD = 1<<PD6;

	while(1)
	{
		usart_puts("Hallo Welt!\n");
		blink();
		_delay_ms(2500);
	}
	
	return 0;

}
