#include <stdio.h>

void selecao(int *v, int tam) {
    int esq = 0;
    int dir = tam - 1;

    while (esq < dir) {
        int posMin = esq;
        int posMax = dir;

        // Busca menor e maior na porção não ordenada [esq..dir]
        for (int i = esq; i <= dir; i++) {
            //se o elemento atual for menor que o elemento na posição do menor, entao atualiza a 
            //posição do menor

            if (v[i] < v[posMin]){ 
                posMin = i;
            }
            if (v[i] > v[posMax]){
                posMax = i; //se o elemento atual for maior que o elemento na posição do maior,
                // entao atualiza a posição do maior
            }
        }

        // Coloca o menor no início
        int tmp = v[esq]; //guarda o valor do elemento na posição do esq, para fazer a troca
        v[esq] = v[posMin]; //coloca o menor elemento encontrado na posição do esq, ou seja, no início da porção não ordenada
        v[posMin] = tmp; //coloca o valor guardado na posição do menor, ou seja, na posição onde estava o menor elemento encontrado

        // Se o maior estava em esq, ele foi movido para posMin na troca acima
        if (posMax == esq) {
            posMax = posMin;
        }

        // Coloca o maior no final
        tmp = v[dir];
        v[dir] = v[posMax];
        v[posMax] = tmp;

        esq++; // ja que ja foi feita atroca possso incrementar o esq, pois o menor elemento ja esta na posicao correta
        dir--; // ja que ja foi feita a troca posso decrementar o dir, pois o maior elemento ja esta na posicao correta
    }
}

int main() {
    int v[] = {29, 10, 14, 37, 13, 5, 26};
    int tam = 7;

    selecao(v, tam);

    for (int i = 0; i < tam; i++)
        printf("%d ", v[i]);

    return 0;
}
/*
(b) Complexidade
O Selection Sort normal faz n iterações no loop externo. Nessa versão modificada faz n/2 iterações porque a cada passagem resolve duas posições ao mesmo tempo.
Mas dentro de cada iteração ainda percorre a porção inteira para achar min e max → continua O(n).
Então: n/2 × O(n) = O(n²) — mesma classe assintótica, mas na prática o número de comparações cai pela metade em relação ao Selection Sort normal.
O professor anotou "comparações" na prova, então a resposta esperada é: a complexidade continua O(n²), porém o número de comparações é reduzido aproximadamente à metade.

*/