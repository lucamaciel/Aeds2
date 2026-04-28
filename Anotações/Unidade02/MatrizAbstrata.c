#include <stdio.h>

class MatrizAbstrata {
    private Celula inicio;
    private int linhas, colunas;
    public MatrizAbstrata (){
        Matriz(3,3);
    }
    Celula getInicio(){
        return this.inicio;
    
    public MatrizAbstrata(int l, int c) {
        this.linha = l; //significa que o atributo linha recebe o valor de linha
        this.coluna = c;//significa que o atributo coluna recebe o valor de coluna

        //alocar a matriz com this.linha linhas e ths.coluna colunas
    }
    public Matriz soma(Matriz a ,Matriz b ){
        Matriz resp = null; //inicializa a resposta como nula
        if ( a.linha == b.linha && a.coluna == b.coluna){
            resp = new Matriz(a.linha, a.coluna);
                            }
            }
        }
        return resp;
    }
}