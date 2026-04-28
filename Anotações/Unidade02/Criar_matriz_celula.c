#include <stdio.h>


class Celula {
    private int valor;
    private Celula prox;
    public Celula(int v){
        this.valor = v;
        this.prox = null;
    }
    public int getValor(){
        return this.valor;
    }
    public void setValor(int v){
        this.valor = v;
    }
    public Celula getProx(){
        return this.prox;
    }
    public void setProx(Celula p){
        this.prox = p;
    }
public void criarMatriz(){
    Celula i = inicio; // passo 1, (criar um ponteiro i que aponta para o início da matriz)
    i.dir = new Celula(0); // passo 2, (criar a célula à direita de i e atribuir o valor 0 a ela)
    i.dir.esq = i; // passo 3, (fazer com que a célula à direita de i aponte para i, ou seja, estabelecer a ligação entre as células)
    i.dir.esq = i; // passo 4, (fazer com que a célula à direita de i aponte para i, ou seja, estabelecer a ligação entre as células)
    i=i.dir; // passo 5, (fazer com que i aponte para a célula à direita, ou seja, avançar para a próxima célula na linha)
    //repetir os passos 2,3,4,5 para criar a linha 1 (linha 0 foi criada no passo ao 5), 

    i=inicio; // passo 6
    i.bai = new Celula(0); // passo 7
    i.inf.sup = i; // passo 8
    i=i.inf; // passo 9
    //repetir os passos 2,3,4,5,6,7,8,9 para criar a coluna 1 (coluna 0 foi criada no passo ao 9)

    i.dir.sup= i.sup.dir; // passo 10
    i.sup.dir.inf = i.dir; // passo 11


    //inserir demais elementos da matriz seguindo a mesma lógica dos passos anteriores
}

public void criar_matriz_com_for(){
    Celula i = inicio;
    


}
}