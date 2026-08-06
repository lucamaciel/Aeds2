package Estudo.ProvaPratica03;
//balanceamos uma arvore para facilitar inserção, pesquisa e remoção
//as arvores desbalanceadas para a direta devem ser rotacionadas para a esquerda, e as arvores 
// desbalanceadas para a esquerda devem ser rotacionadas para a direita
class No {
    private No raiz;

    public No esq;
    public No dir;
    public int elemento;

    private No Balancementoesq(No no){
        No noDir = no.dir;
        No nodiresq = noDir.esq;

        noDir.esq = no;
        no.dir = nodiresq;

        return noDir;
    }//rotaciona para a esquerda, ou seja, o nó da direita sobe para a posição do nó desbalanceado,
    //  e o nó desbalanceado desce para a posição do nó da direita, e o nó da direita da esquerda do nó da
    //  direita sobe para a posição do nó desbalanceado
     
    private No BalancementoDireita(No no){
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }
}//rotaciona para a direita, ou seja, o nó da esquerda sobe para a posição do nó desbalanceado,
//  e o nó desbalanceado desce para a posição do nó da esquerda, e 
//  o nó da esquerda da direita sobe para a posição do nó desbalanceado

private No BalanceamentoDuploesqdir(No no){
    no.esq = Balancementoesq(no.esq);
    return BalancementoDireita(no);
}
private No BalanceamentoDuplodireitaesq(No no){
    no.dir = BalancementoDireita(no.dir);
    return Balancementoesq(no);
}

/*ATIVIDADE 01) nO CÓDIGO DA ARVORE binária,
faça um metodo que leia 3 numeros inteiros e os insira na arvore.
se a arvore estiver com 3 niveis efetue uma das 4 balanceamentos feitos
*/

private int ContarNivelEsq(No no){
    int nivel = 0;
    while(no != null){
        nivel++;
        no = no.esq; //conta o nível da árvore seguindo o caminho mais à esquerda, pois é o caminho mais longo
        //  em uma árvore binária, mas e se o caminho mais longo for para a direita? nesse caso, o método não
        //  contaria o nível correto, pois ele só conta o caminho mais à esquerda, então seria necessário contar o
        //  nível seguindo o caminho mais à direita também, e retornar o maior dos dois níveis
    }
    return nivel;
}
private int ContarNivelDir(No no){
    int nivel = 0;
    while(no != null){
        nivel++;
        no = no.dir; //conta o nível da árvore seguindo o caminho mais à direita, pois é o caminho mais longo
        //  em uma árvore binária, mas e se o caminho mais longo for para a esquerda? nesse caso, o método não
        //  contaria o nível correto, pois ele só conta o caminho mais à direita, então seria necessário contar o
        //  nível seguindo o caminho mais à esquerda também, e retornar o maior dos dois níveis
    }
    return nivel;
}
//assim ficaria um método para contar o nível da árvore, contando o caminho mais à esquerda e o caminho mais à direita, e retornando o maior dos dois níveis
private int ContarNivel(No no){
    int nivelEsq = ContarNivelEsq(no);
    int nivelDir = ContarNivelDir(no);
    return Math.max(nivelEsq, nivelDir); //retorna o maior dos dois níveis
}

//voltando a atv 01

    void inserir(int elemento) {
        raiz = inserirRecursivo(raiz, elemento);
    }

    private No inserirrecursivo(No no, int elemento) {
        if (no == null){
            return new No(elemento);
        }
        if (elemento < no.elemento) {
            no.esq = inserirrecursivo(no.esq, elemento);
        } else if (elemento > no.elemento) {
            no.dir = inserirrecursivo(no.dir, elemento);
        }
        return no;
    }

    private void ex01 (no no){
        if(ContarNivel(no) >= 3){
            if(ContarNivelEsq(no) > ContarNivelDir(no)){
                if(ContarNivelEsq(no.esq) >= ContarNivelEsq(no.dir)){
                    //se o nível da subárvore esquerda do nó desbalanceado for maior ou igual ao nível da 
                    // subárvore direita do nó desbalanceado, então é um caso de desbalanceamento para a direita, 
                    // e o método de balanceamento a ser utilizado é o BalancementoDireita
                    no = BalancementoDireita(no);
                } else {
                    no = BalanceamentoDuploesqdir(no);
                }
            } else {
                if(ContarNivelDir(no.dir) >= ContarNivelDir(no.esq)){
                    no = Balancementoesq(no);
                } else {
                    no = BalanceamentoDuplodireitaesq(no);
                }
            }
        }
    }


public class Aprendendo_Balancemento {
    
}
