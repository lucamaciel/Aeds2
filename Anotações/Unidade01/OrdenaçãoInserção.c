/*
Algoritmo de ordenação por inserção
*/

void insercao(int v[], int n){
    for( int i = 1 ; i < n ; i++){
        int temp = v[i];// Armazenar o elemento a ser inserido
        int j = i - 1; // Percorrer o vetor da direita para a esquerda, comparando o elemento a ser 
        // inserido com os elementos já ordenados
        while( j >= 0 && v[j] > temp){
            // Se o elemento a ser inserido for menor do que o elemento atual,
            // deslocar o elemento atual para a direita
            v[j + 1] = v[j]; // Deslocar o elemento atual para a direita
            j--;// Continuar comparando o elemento a ser inserido com os elementos já ordenados
        }
        v[j + 1] = temp;// Inserir o elemento a ser inserido na posição correta
    }
}

/*
Ordenaemos somente os k menores elementos do vetor, ou seja, os k primeiros elementos do vetor ordenado. 
Para isso, basta modificar o laço externo para percorrer somente os k primeiros elementos do vetor, ou seja,
 de 1 a k-1. O código ficaria assim:
*/
void insercaoKMenores(int v[], int n, int k){
    // Ordenar somente os k primeiros elementos do vetor
    //para isso, basta modificar o laço externo para percorrer somente os k primeiros elementos do vetor, 
    //ou seja, de 1 a k-1
    
    for( int i = 1 ; i < k ; i++){
        // Armazenar o elemento a ser inserido
        int temp = v[i];
        // Percorrer o vetor da direita para a esquerda, comparando o elemento a ser 
        // inserido com os elementos já ordenados
        int j = i - 1;
        while( j >= 0 && v[j] > temp){
            // Se o elemento a ser inserido for menor do que o elemento atual,
            // deslocar o elemento atual para a direita
            v[j + 1] = v[j]; // Deslocar o elemento atual para a direita
            j--;// Continuar comparando o elemento a ser inserido com os elementos já ordenados
        }
        v[j + 1] = temp;// Inserir o elemento a ser inserido na posição correta
    }
}