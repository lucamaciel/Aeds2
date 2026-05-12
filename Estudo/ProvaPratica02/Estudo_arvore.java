public class Estudo_arvore {
//criação de uma classe para representar um nó da árvore
class No{
    int valor; //variavel para armazenar o valor do nó
    No esquerda; //variavel para armazenar o endereço do filho esquerdo
    No direita; //variavel para armazenar o endereço do filho direito

    No(int valor){
        this.valor = valor; //inicializa o valor do nó com o valor recebido
        this.esquerda = null; //inicializa o filho esquerdo com null, indicando que não há filho esquerdo
        this.direita = null; //inicializa o filho direito com null, indicando que não há filho direito
    }
    public void printemordem(No atual){
        if(atual != null){ //verifica se o nó atual é diferente de null, ou seja, se a árvore não está vazia
            printemordem(atual.esquerda); //chama recursivamente o método para o filho esquerdo
            System.out.print(atual.valor + " "); //imprime o valor do nó atual
            printemordem(atual.direita); //chama recursivamente o método para o filho direito
        }
    }
    public No inserir(No atual, int x){
        if(atual == null ){
            return new No(x);
        }else if (x > atual.valor){
            return inserir(atual.esquerda,x);
        }else if(x < atual.valor){
            return inserir(atual.direita,x);
        }
        return atual;
    }

//imprimir todos os caminhos de uma arvore binária com -> entre os nos

public void Imprimir_caminhos(No atual)

}
    
}
