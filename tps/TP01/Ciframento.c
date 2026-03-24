#include <stdio.h>

void cifrarRecursivo(char *str, int i);
int isFim(char *str);
void rql(char *s);

int main()
{
char entrada[1000];

if (fgets(entrada, sizeof(entrada), stdin) != NULL)
{
rql(entrada);
}

while (!isFim(entrada))
{
cifrarRecursivo(entrada, 0);
printf("%s\n", entrada);

if (fgets(entrada, sizeof(entrada), stdin) != NULL)
{
rql(entrada);
}
}

return 0;
}

void cifrarRecursivo(char *str, int i)
{
if (str[i] != '\0')
{
// Aplica o deslocamento de 3 na tabela ASCII
str[i] = (char)(str[i] + 3);

// Chamada recursiva para o próximo caractere
cifrarRecursivo(str, i + 1);
}
}

int isFim(char *str)
{
return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0');
}

void rql(char *s)
{
for (int i = 0; s[i] != '\0'; i++)
{
if (s[i] == '\n')
{
s[i] = '\0';
}
}
}