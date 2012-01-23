/*
* Header-Datei fuer uart.c
*/

void usart_init(uint16_t baud);
uint8_t usart_byte_avail(void);
uint8_t usart_getc(void);
void usart_gets(char *s);
void usart_putc(uint8_t byte);
void usart_puts(char *s);
void usart_puti(int zahl, int sges);
void usart_putf(float zahl, int sges, int snach);
void usart_putui(unsigned int zahl, int sges);
void usart_putui_0(unsigned int zahl, int sges);
void usart_putui_hex(unsigned int zahl, int sges);
void usart_putui_bin(unsigned int zahl, int sges);
void usart_putb(uint8_t a);
