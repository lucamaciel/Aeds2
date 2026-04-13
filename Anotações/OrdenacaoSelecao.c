/*Algoritmo de ordenação por seleção
*/

void ordenaSelecao(int v[], int n){
    for( int i =0 ; i < n -1 ; i++){
        int menor = i;
        for( int j = i + 1 ; j < n ; j++){
            if( v[j] < v[menor]){
                menor = j;
            }
    }
    swap(menor, i);
}
int swap(int a, int b){
    int temp = v[a];
    v[a] = v[b];
    v[b] = temp;
}
/*
Porém agora vamos ordenar somente os k menores elementos do vetor, ou seja, 
os k primeiros elementos do vetor ordenado. Para isso, basta modificar o laço externo 
para percorrer somente os k primeiros elementos do vetor, ou seja, de 0 a k-1. O código ficaria assim:
*/
void ordenaKMenores(int v[], int n, int k){
    // Ordenar somente os k primeiros elementos do vetor
    //para isso, basta modificar o laço externo para percorrer somente os k primeiros elementos do vetor, 
    //ou seja, de 0 a k-1
    
    for( int i =0 ; i < k ; i++){
        // Encontrar o menor elemento do vetor a partir da posição i
        int menor = i;
        for( int j = i + 1 ; j < n ; j++){
            if( v[j] < v[menor]){
                menor = j;
            }
    }
    swap(menor, i);
}
}
