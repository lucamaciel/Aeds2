/*

3/04/2026
Estudo para prova prática 01 do dia 06/04/2026

Matéria : Somatórios e noção de complexidade de algoritmos.

    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            código a ser executado, a complexidade é O(n^2), pois 
            o número de operações cresce proporcionalmente ao quadrado do tamanho da entrada n.
            (tem dois loops aninhados, cada um iterando n vezes)
        }
    }


QUESTÂO 01 ( prova antiga)

int x = 0;
for(int i = 15; i < n; i++) { // Loop Externo, que eh realizado n-15 vezes, ou seja O(n)
    for (int j = 0; j < 3; j++)  //eh realizado sempre 3 vezes, ou seja O(1) (O(1) porque 
                                                        é uma constante, não depende de n)

        x += a * 2;          // (1) Multiplicação dentro de loop fixo (3 vezes, ou seja O(1))

    for(int k = i; k < n; k++) { // Loop Interno Dependente, que é realizado n-i vezes(que nesse caso
                                    eh n-15), ou seja O(n) (O(n) porque o número de iterações 
                                    depende de n)

        x += b * 5;          // (2) Multiplicação
        x += c * 4;          // (3) Multiplicação
    }
    x += x * 10;             // (4) Multiplicação fora dos loops internos

}
    a) A função de complexidade usando a notação de somatorio
    para o numero de multiplicações realizadas por esse código é dada por:


    b) A formula fechada da função de complexidade e a ordem 
    de comlexidade usando a notaçãpo de O-grande.

RESPOSTA + RACIOCINIO:

a) 
   -O loop J roda sempre 3 vezes, logo total de multiplicações realizadas por ele é 3 (O(1)).
   - o loop K=i vai ate o k == n, ou seja, 2 X (n-1) multiplicações, ou seja O(n).
   - A ultima multiplicação ocorre somente 1 vez, logo O(n).

---Somando as multiplicações temos:

    3 (do loop J) + 2(n-i) (do loop K) + 1 (da multiplicação fora dos loops internos)
     = 3 + 2n - 30 + 1 ==>  2n - 26
    logo a notação de somatório para o número de multiplicações é dada por:

    F(n)= ∑(i=15 ate n-1) [3 + 2(n-i) + 1]    = ∑(i=15 ate n-1) [2n - 26]   RESPOSTA A)



b) passos 01 - Para encontrar a fórmula fechada, podemos simplificar a expressão dentro do somatório:
    Para isso podemos reescrever a expressão que antes era 3 + 2(n-i) + 1 
    como 2n - 26, pois o termo -2i se cancela com o termo +2i, resultando em:
    F(n) = ∑(i=15 ate n-1) [2n - 26]
         = (n - 15) * (2n - 26)   // multiplicando o número de termos pelo valor de cada termo
         = 2n^2 - 56n + 390       // expandindo a expressão
    Passo 02 - Agora, para determinar a ordem de complexidade usando a notação O-grande,
     focamos no termo de maior grau na fórmula fechada, que é 2n^2. Portanto, a ordem de complexidade é O(n^2).
    
     Passos resumidos:
     1. Simplificar a expressão dentro do somatório.
        2. Multiplicar o número de termos pelo valor de cada termo para obter a fórmula fechada.
        3. Identificar o termo de maior grau na fórmula fechada para determinar a ordem de complexidade.
        RESPOSTA B) F(n) = 2n^2 - 56n + 390, O(n^2)


*/
#include <stdio.h>