/*Inserção em matriz abstrata
- A inserção sempre ocorre na primeira posição disponível, ou seja, a
posição mais à esquerda da última linha preenchida.
- A inserção é feita de forma sequencial, preenchendo as posições da matriz da esquerda para a
direita e de cima para baixo.

-Se o termo a ser inserido for menor do que o valor do nó atual, ele deve ser inserido na subárvore
 esquerda.
 -Se o termo a ser inserido for maior do que o valor do nó atual, ele deve ser inserido na subárvore
    direita.
- se o termo a ser inserido for igual ao valor do nó atual, ele pode ser inserido em qualquer subárvore,
    desde que seja mantida a propriedade da árvore binária de busca.
- A inserção em uma árvore binária de busca é feita de forma recursiva, comparando o valor a ser inserido
    com o valor do nó atual e decidindo se deve ser inserido na subárvore esquerda ou direita, até encontrar
    a posição correta para a inserção.
*/
public class árvore_binaria {


    class ArvoreBinaria {
        /* Criação da classe No para representar cada nó da árvore

        Pergunta: "eu não deveria criar a classe No fora da classe ArvoreBinaria? Porque ela é
        uma classe interna? Qual a vantagem disso?"
        Resposta: A classe No é criada como uma classe interna dentro da classe ArvoreBinaria 
        porque ela é uma estrutura de dados que é usada exclusivamente para representar os nós
        da árvore binária. Ao criar a classe No como uma classe interna, ela tem acesso direto 
        aos membros da classe ArvoreBinaria, o que facilita a implementação dos métodos de 
        inserção e outras operações na árvore. Além disso, isso ajuda a encapsular a lógica 
        relacionada aos nós dentro da própria classe ArvoreBinaria, tornando o código mais 
        organizado e fácil de entender.
        */
    //as funções de inserção de elementos na árvore binária de busca são implementadas dentro da 
    // classe ArvoreBinaria, pois se forem colocadas dentro da classe No, elas não teriam acesso 
    // direto aos membros da classe ArvoreBinaria, o que dificultaria a implementação dos métodos de
    //  inserção e outras operações na árvore.
    ArvoreBinaria() {
        raiz = null;
    } // necessario para criar a árvore

    void inserirPai(int x) throws Exception {
        if (raiz == null){raiz = new No(x);} 
        // se a raiz for nula, o novo nó se torna a raiz da árvore
        else if(x<raiz.elemento){inserirPai(x, raiz.esquerda, raiz);} 
        // se o valor a ser inserido for menor do que o valor da raiz, chama o método recursivo para inserir na subárvore esquerda
        else if(x>raiz.elemento){inserirPai(x, raiz.direita, raiz);} 
        // se o valor a ser inserido for maior do que o valor da raiz, chama o método recursivo para inserir na subárvore direita
        else{throw new Exception("Valor já existe na árvore");}
         // se o valor a ser inserido for igual ao valor da raiz, lança uma exceção indicando que o 
         // valor já existe na árvore
    }
    boolean Pesquisar(int x){    
        //função para pesquisar que recebe o valor a ser pesquisado como parametro
        return Pesquisar(x, raiz); // chama o método recursivo para pesquisar na árvore
    }

Class No {
        int valor; // valor armazenado no nó
        No esquerda; // referência para o nó da subárvore esquerda
        No direita; // referência para o nó da subárvore direita
        No(int valor) { // construtor para criar um novo nó com o valor fornecido
            this.valor = valor;
            this.esquerda = null; // inicializa a referência para a subárvore esquerda como nula
            this.direita = null; // inicializa a referência para a subárvore direita como nula
        }
        void inserir(int x) {
            // vamos inserir os elementos 3,5,1,8,2,4,7,6
            raiz = Inserir(x, raiz); // chama o método recursivo para inserir o elemento na árvore
            // metodo que recebe o valor a ser inserido e o nó atual da árvore
        }

        No Inserir(int x, No atual) {
            if (atual == null) { // se o nó atual for nulo, significa que encontramos a posição correta para
                                 // inserir o novo nó
                No novo = new No(x); // cria um novo nó com o valor a ser inserido
                return novo; // retorna o novo nó para ser inserido na árvore
            }
            if (x < atual.valor) { // se o valor a ser inserido for menor do que o valor do nó atual, devemos
                                   // inserir na subárvore esquerda
                atual.esquerda = Inserir(x, atual.esquerda); // chama o método recursivo para inserir na subárvore
                                                             // esquerda
            } else { // se o valor a ser inserido for maior ou igual ao valor do nó atual, devemos
                     // inserir na subárvore direita
                atual.direita = Inserir(x, atual.direita); // chama o método recursivo para inserir na subárvore direita
            }
            return atual; // retorna o nó atual para manter a estrutura da árvore
        }
        
        void inserirPai(int x, No atual, No pai) throws Exception {
            if (atual == null) { 
                if(x<pai.valor){pai.esquerda = new No(x);}
                // se o nó atual for nulo, significa que encontramos a posição correta para inserir o novo nó
                else if(x>pai.valor){pai.direita = new No(x);}
                // se o valor a ser inserido for menor do que o valor do nó pai, o novo nó é inserido como filho esquerdo do pai
                }
            else if (x<pai.valor){inserirPai(x, atual.esquerda, atual);}
            // se o valor a ser inserido for menor do que o valor do nó pai, chama o método 
            // recursivo para inserir na subárvore esquerda
            else if(x>pai.valor){inserirPai(x, atual.direita, atual);}
            else{throw new Exception("Valor já existe na árvore");}
            // se o valor a ser inserido for igual ao valor do nó pai, lança uma
    }
        
        boolean pesquisar ( int x, No i){
            boolean resp;

            if (atual == null){
                resp = false;
        }else if (x == i.elemento){
            resp = true;
        }else if(x < i.elemento){
            pesquisar(x, i.esq);
        }else if ( x > i.elemento){
            pesquisar(x,i.dir);
        }
        return resp;
    }
        
        void caminharCentral(No i){
            if(i != null){
                caminharCentral(i.esq);
                System.out.print(i.elemento + "");
                caminharCenral(i.dir);
            }
        }
        
        void caminharPos(No i){
            if( i != null){
                caminharPos(i.esq);
                caminharPos(i.dir);
                System.out.print(i.elemento + "");

            }
        }
        
        void caminharPre(No i) {
            if (i != null) {
                System.out.print(i.elemento + "");
                caminharPos(i.esq);
                caminharPos(i.dir);
            }
        }
    //faça um metodo que retorne a altura da arvore

    public int Altura(int i, int altura){
        if ( i == null){
            altura --;
        }else{
            int AlturaEsq = getAltura(i.esq,altura+1);
            int AlturaDir = getAltura(i.dir,altura+1);
            altura = (alturaEsq > alturaDir) ? alturaEsq : alturaDir; 
            //o operador ? é um operador ternário que funciona como um if-else, ele verifica 
            // se a altura da subárvore esquerda é maior do que a altura da subárvore direita,
            //  se for verdade ele retorna a altura da subárvore esquerda, caso contrário ele 
            // retorna a altura da subárvore direita

        }
        return altura;
    }
 
    // Insira 100000 elementos de forma aleatória. Para cada inserção, mostre na
 // tela o número de elementos da árvore, o logaritmo (base 2) desse número e a
 // altura da árvore 
    void inserir1000(No i){
        for (int j = 0; j < 100000; j++){
            int x = (int) (Math.random() * 100000); // gera um número aleatório entre 0 e 100000
            inserir(x); // insere o número aleatório na árvore
            int numeroElementos = j + 1; 
            // número de elementos na árvore é igual ao número de iterações + 1
            double logaritmo = Math.log(numeroElementos) / Math.log(2); 
            // calcula o logaritmo base 2 do número de elementos
            int altura = Altura(i, 0); 
            // calcula a altura da árvore
            System.out.println("Número de elementos: " + numeroElementos + ", Logaritmo (base 2): " + logaritmo + ", Altura da árvore: " + altura);
        }
    }
    
}
}