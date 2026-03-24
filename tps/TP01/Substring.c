#include <stdio.h>

// função que limpa o vetor de letras pra cada novo teste
void zerar_balde(int *balde) {
    for (int i = 0; i < 256; i++) balde[i] = 0;
}

// essa função vê o tamanho sem repetir começando de uma posição fixa
int testar_daqui(char *s, int *balde) {
    // se acabou a string ou a letra ja apareceu no balde, para tudo
    if (*s == '\0' || *s == '\n' || balde[(unsigned char)*s] == 1) {
        return 0;
    }

    // marca que viu a letra e vai pra proxima
    balde[(unsigned char)*s] = 1;
    return 1 + testar_daqui(s + 1, balde);
}

// a recursão principal que vai tentando começar de cada letra da string
int resolver_recursivo(char *s) {
    if (*s == '\0' || *s == '\n') {
        return 0;
    }

    int balde[256];
    zerar_balde(balde);

    // vê qual o tamanho máximo começando exatamente dessa letra aqui
    int atual = testar_daqui(s, balde);

    // chama a função pra próxima letra pra ver se lá na frente tem um pedaço maior
    int proximo = resolver_recursivo(s + 1);

    // se o que eu achei agora for maior que o que vem depois, retorno o agora
    if (atual > proximo) return atual;
    return proximo;
}

int isFim(char *s) {
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main() {
    char entrada[1000];

    // fgets é vida, mas o \n é triste
    while (fgets(entrada, 1000, stdin)) {
        if (isFim(entrada)) break;

        // manda pra função e reza pro Verde aceitar
        printf("%d\n", resolver_recursivo(entrada));
    }

    return 0;
}