#include <stdio.h>
//vou fazer com bubble sort pois dessa forma coloco as duas strings em ordem alfabetica e comparo as duas

int meu_strlen(char str[]) {
    int n = 0;
    while (str[n] != '\0') {
        n++;
    }
    return n;
}

int meu_strcmp(char str1[], char str2[]) {
    int i = 0;
    while (str1[i] != '\0' && str2[i] != '\0') {
        if (str1[i] != str2[i]) {
            return str1[i] - str2[i];
        }
        i++;
    }
    return str1[i] - str2[i];
}

void remover_quebra_linha(char str[]) {
    int i = 0;
    while (str[i] != '\0') {
        if (str[i] == '\n') {
            str[i] = '\0';
        }
        i++;
    }
}

void ordenar(char str[]) {
    int n = meu_strlen(str);
    for (int i = 0; i < n - 1; i++) //os contadores servem so para incrementar e fazer as comparações
	{
        for (int j = i + 1; j < n; j++) 
		{
            if (str[i] > str[j])//colocando em ordem alfabetica todas as letras da string
			{
				// so pra eu não esquecer como funciona o bubble sort
				
                char temp = str[i]; // sguarda a maior em uma temporaria
                str[i] = str[j]; // passa a menor para a esqyerda
                str[j] = temp; // passa a maior para adireita
            }
        }
    }
}

int main()
{
	char linha[250];//tive que fazer uma variavel para ler a linha inteira por causa da forma com que
//o verde quer a entrada, ai usso o "sscanf" para tirar o hifem la
    char str1[100];
    char str2[100];
	
    while (fgets(linha, sizeof(linha), stdin)) 
	{    
		// nesse caso como o fgets ta dentro do while não vou precisar por outro ao final dele.

		
		remover_quebra_linha(linha);//consumir os \0 da entrada
        if (meu_strcmp(linha, "FIM") == 0)
		 {
			 return 0;
		 }
		
		sscanf(linha, "%s - %s", str1, str2); 
		
		int n1 = meu_strlen(str1);
	    int n2 = meu_strlen(str2);
		
		/*O Nome: sscanf (String Scan Formatted)
O primeiro s vem de string. Isso diz ao C: "Não espere o usuário digitar nada; olhe para a variável linha que eu já preenchi antes com o fgets*/
		if ( n1 != n2)
		{ 
			printf("NAO\n");
		}else{
			//colocando as duas em ordem alfabetica
			ordenar(str1);
			ordenar(str2);

			if(meu_strcmp(str1, str2) == 0)// quando ha uma diferenca entre as duas strings o strcmp da sempre 
				//diferente de zero IMPORTANTE.
			{ 
				printf("SIM\n");
			}else{
				printf("NAO\n");
			}
		}
		
	}
}

/*erros cometidos:
 - case sensivite
 - aprender a usar esse sscanf
 -fgets pra ler e sscanf para selecionar
 - dificuldade 7.5/10
 - Feito dia 16/03            

        







