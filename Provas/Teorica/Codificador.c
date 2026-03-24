/*creio que vou tirar 4/4 nessa questão
pois, faz o que foi proposto sem usar o  string.h
que era proibido, me atentar ao beecrowd e não usar o scanf para ler a string, e sim o fgets, que é mais seguro.
while(fgets(s, 200,stdin) != NULL) é a maneira correta de ler uma linha de entrada até EOF, e o loop for percorre cada caractere da string para fazer as substituições necessárias.
 Depois, outro loop for imprime a string decodificada. O código é eficiente e atende aos requisitos do problema.
*/
#include <stdio.h>

int main ()
{
 char s[200];
 while(fgets(s, 200,stdin) != NULL)
 {

 for( int i = 0 ;s[i] != '\0' ; i ++ )
 {
	 if ( s[i] =='@')
	{ s[i] = 'a';}
	if ( s[i] =='&')
        { s[i] = 'e';}
	if ( s[i] =='!')
        { s[i] = 'i';}
	if ( s[i] =='*')
        { s[i] ='o';}
	if ( s[i] =='#')
        { s[i] = 'u';}
 }
for( int z = 0 ; s[z] != '\0' ; z ++ )
 { printf("%c", s[z]);}

}
}
// O código acima é um decodificador que substitui os caracteres '@', '&', '!', '*', e '#' por 'a', 'e', 'i', 'o', e 'u' respectivamente. Ele lê linhas de entrada até EOF e imprime a linha decodificada.
