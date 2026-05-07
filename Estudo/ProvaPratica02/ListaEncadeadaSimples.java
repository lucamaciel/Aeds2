public class ListaEncadeadaSimples {
class Celula {
    public int elemento;//variavel para armazenar o elemento da celula
    public Celula prox; //variavel para armazenar o endereço da proxima celula
    public Celula(int x) {
        //construtor da celula, recebe um inteiro e inicializa o elemento e o prox
        this.elemento = x; //inicializa o elemento com o valor recebido
        this.prox = null; //inicializa o prox com null, indicando que a celula não aponta para nenhuma outra celula
    }
}
class Lista{
    Celula inicio; //variavel para armazenar o endereço da primeira celula da lista

    Lista(){
        this.inicio = null; //inicializa o inicio com null, indicando que a lista está vazia

    }
    void inserir(int x ){
        if(inicio == null){
            inicio = new Celula(x); //se a lista estiver vazia, cria uma nova celula e atribui ao inicio
        } else {
            Celula atual = inicio; //variavel para percorrer a lista, inicia no inicio
            while (atual.prox != null) {
                atual = atual.prox;//percorre a lista até encontrar a ultima celula
                // , ou seja, aquela que tem o prox igual a null
            }
            atual.prox = new Celula(x); 
//quando encontra a ultima celula, cria uma nova celula e atribui ao prox da ultima celula
        }
    }
 void acharMeio() {
    /*
    -----EXERCICIO 01) Faça um método que retorne o elemento do meio da lista encadeada simples. Se a lista tiver um número par de elementos, retorne o segundo elemento do meio. Por exemplo, se a lista tiver os elementos [1, 2, 3, 4], o método deve retornar 3, pois é o segundo elemento do meio (2 e 3 são os elementos do meio). Se a lista tiver os elementos [1, 2, 3], o método deve retornar 2, pois é o elemento do meio. Se a lista tiver os elementos [1, 2, 3, 4, 5], o método deve retornar 3, pois é o elemento do meio.
    */
        if (inicio == null) {
            System.out.println("A lista está vazia");
            return;
        }
        Celula atual = inicio; //variavel para percorrer a lista, inicia no inicio
        int count = 0; //variavel para contar o numero de celulas da lista
        while (atual != null) {
            count++; //incrementa o contador para cada celula encontrada
            atual = atual.prox; //percorre a lista até o final
        }
        int meio = count / 2; //calcula a posição do meio da lista
        atual = inicio; //reinicia a variavel para percorrer a lista novamente
        for (int i = 0; i < meio; i++) {
            atual = atual.prox; //percorre a lista até chegar na posição do meio
        }
        System.out.println("O elemento do meio é: " + atual.elemento); //imprime o elemento do meio encontrado
    }
}
}
