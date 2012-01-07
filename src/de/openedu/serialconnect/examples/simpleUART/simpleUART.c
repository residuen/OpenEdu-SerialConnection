#include <avr/io.h>
#include <util/delay.h>
#include <math.h>
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

	float x=0, y;

	while(1)
	{
		// usart_puts("HALLO WELT!\n");
		//usart_puts("x=");
		usart_putf(x,2,1);
		usart_puts(",");
		usart_putf(sin(x),4,3);
		usart_puts("\n");
		blink();
		_delay_ms(250);

		x+=0.1;
	}
	
	return 0;

}
