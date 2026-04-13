#include <stdio.h>

int meu_strlen(char string[]) {
    int tamanho = 0;
    while (string[tamanho] != '\0') {
        tamanho++;
    }
    return tamanho;
}

int isFim(char string[]) {
    return (string[0] == 'F' && string[1] == 'I' && string[2] == 'M' && string[3] == '\0');
}

void remover_quebra_linha(char string[]) {
    int i = 0;
    while (string[i] != '\0') {
        if (string[i] == '\n') {
            string[i] = '\0';
        }
        i++;
    }
}

int main() {
    char string[100];

    if (fgets(string, 100, stdin) == NULL)
    {
        return 0;
    }
    // Remove o caractere de nova linha '\n' que o fgets adiciona
    remover_quebra_linha(string);

    while (!isFim(string)) {
			        
        // Calcula o tamanho da string atual
        int n = meu_strlen(string);

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
        
        remover_quebra_linha(string);
    }

    return 0;
}
//dificuldade 6/10, pois o raciocinio foi facil de entender, mas tive que pesquisar um pouco sobre a função "fgets" e "strcspn" para poder fazer o programa funcionar corretamente.
//alem disso, tive que pesquisar sobre a função "strcmp" para poder fazer a comparação entre a string digitada e a palavra "FIM" para poder encerrar o programa.
