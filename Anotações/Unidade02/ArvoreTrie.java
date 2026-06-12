import java.util.Scanner;

class Trie{
   
    //agora pelo que eu acgho devo fazer um metodo de inserção
    //que apos a riaiz principal verifica se ja tem a letra a er inserida
    //se não tiver creio que ela deve ser inserida, queria aprender fazer a classe primeiro
    //depois disso tem que fazer um metodo de busca, para verificar se a palavra existe ou não 
    //então bora começar a fazer a classe, depois disso a gente faz os metodos de inserção e busca, e depois a gente testa o codigo para ver se ta funcionando ou não
    
    Trie raiz = new No();
    //como eu coloco os 26 filhos dessse no, acho que tem que ser um array de 26 posições, cada posição representando uma letra do alfabeto, 
    // e cada posição do array vai ser um objeto do tipo No, e cada objeto do tipo No vai ter um array de 26 posições, e assim por diante, até chegar na
    //  folha da arvore, onde vai ser marcado que aquela palavra existe ou não
    //fazer com tabela hash depois, agora vai array msm
    int tamanho = 26;
    Trie[] filhos = new Trie[tamanho]; //criei o array de filhos, agora tenho que criar o metodo de inserção, para inserir as palavras na arvore, e
    //  depois o metodo de busca, para verificar se a palavra existe ou não na arvore
    //construtor da arvoee trie, que vai inicializar o array de filhos com null, para indicar que não tem nenhum filho ainda
    public Trie(){
        for(int i = 0; i<tamanho; i++){
            filhos[i] = null; //inicializando o array de filhos com null, para indicar que não tem nenhum filho ainda
        }
    }

    void inserir(String Palavra){
        //primeira coia a se fazer eh destrinchar a palavra em letras, para isso tem que usar o metodo charAt() da classe String, 
        // para pegar cada letra da palavra e verificar se ela ja existe na arvore ou não
        //depois disso verifico no array se a primeira letra da palavra existe ou não, se não existir eu crio um novo objeto do tipo No
        //  e coloco na posição do array correspondente a letra, e depois disso eu vou para o proximo nivel da arvore, e assim por diante, até chegar na folha da 
        // arvore, onde eu marco que aquela palavra existe ou não
        for(int i = 0; i<palavra.length(); i++){
          char letra = palavra.charAt(i);
          int pos = letra - 'a'; //isso vai me dar a posição da letra no array, por exemplo, se a letra for 'a', pos vai ser 0, se for 'b', pos vai ser 1, e assim por diante
    
} 
}

public class ArvoreTrie {
    public static void main(String[] args) {
        Trie arvore = new Trie();
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite uma palavra para inserir na arvore trie: ");
        String palavra = sc.nextLine();
        arvore.inserir(palavra);
        System.out.println("Palavra inserida na arvore trie: " + palavra);
    
}
}
