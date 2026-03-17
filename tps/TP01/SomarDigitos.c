#include <stdio.h>
#include <string.h>

int main() {
    char string[100];

    if (fgets(string, 100, stdin) == NULL)
    {
        return 0;
    }
    // Remove o caractere de nova linha '\n' que o fgets adiciona
    string[strcspn(string, "\n")] = '\0';

    while (strcmp(string, "FIM") != 0) {
			        
        // Calcula o tamanho da string atual
        int n = strlen(string);

        for (int i = n - 1; i >= 0; i--)//imprime ao contrario
        {
            // Imprime caractere por caractere
            printf("%c", string[i]);
        }
        
        // Pula uma linha após imprimir a palavra invertida
        printf("\n");

       
        if (fgets(string, 100, stdin) == NULL) 
        {
            return 0;
        }
        
        string[strcspn(string, "\n")] = '\0';
    }

    return 0;
}
//dificuldade 6/10, pois o raciocinio foi facil de entender, mas tive que pesquisar um pouco sobre a função "fgets" e "strcspn" para poder fazer o programa funcionar corretamente.
//alem disso, tive que pesquisar sobre a função "strcmp" para poder fazer a comparação entre a string digitada e a palavra "FIM" para poder encerrar o programa.