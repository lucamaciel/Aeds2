import java.util.Scanner;
class ArvoreAlvinegra234{
    public int []chaves; //array de chaves, onde cada posição representa uma chave da arvore
    public ArvoreAlvinegra234(){
        chaves = new int[3]; //inicializando o array de chaves com 3 posições, pois a arvore alvinegra 2-3-4 tem no máximo 3 chaves por nó
        //serve pra inicializar o array de chaves com 0, para indicar que não tem nenhuma chave ainda
        //isso eh bom para acumular os valores das chaves, para depois fazer as comparações e as inserções na arvore
        //as vezes subindo os valores das chaves para os nós superiores, e as vezes descendo os valores das chaves para os nós inferiores, dependendo do caso
        for(int i = 0; i<3; i++){
            chaves[i] = 0; //inicializando o array de chaves com 0, para indicar que não tem nenhuma chave ainda
        }
    }
    public boolean estaCheio(){
        //esse metodo vai verificar se o nó está cheio, ou seja, se tem 3 chaves, o que indica que o nó está cheio e precisa ser dividido
        //isso é importante para a inserção na arvore alvinegra 2-3-4, pois quando um nó está cheio, ele precisa ser dividido em dois nós, e a chave do meio sobe
        //  para o nó pai, e as chaves menores ficam em um nó filho, e as chaves maiores ficam em outro nó filho
        //isso é importante para manter a propriedade da arvore alvinegra 2-3-4, que é uma arvore balanceada, onde todos os nós folhas estão no mesmo nível
        // , e os nós internos têm entre 2 e 4 filhos
        if(chaves[0] != 0 && chaves[1] != 0 && chaves[2] != 0){
            return true; //se as 3 posições do array de chaves estiverem preenchidas, o nó está cheio
        }
        return false; //se não estiver cheio, retorna false
    }
    

    private No raiz;
    private boolean cor; //cor do no, vermelho ou preto

}

public class ArvoreBinaria {
    public static void main(String[] args) {
    }
}