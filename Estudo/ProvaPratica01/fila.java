package Estudo.ProvaPratica01;
/*
inserir — antes de jogar o paciente no final, você anda para trás no array empurrando para a 
direita todo mundo que tem prioridade menor que o novo. Quando parar, coloca o novo naquela posição.
 Isso garante que quem tem mais prioridade fica sempre na frente, e entre iguais o mais antigo continua na frente (FIFO).


 remover — sempre remove o índice [0] (que é sempre o de maior prioridade), 
 desloca todo mundo um espaço para a esquerda, e retorna quem saiu.




*/

public class fila {
    class Paciente {
        //aqui criei a classe paciente, com os atributos nome e prioridade, para representar cada paciente na fila de prioridade
        public String nome;
        public int prioridade;
    }

    class FilaPrioridade {
        //aqui criei a classe fila de prioridade, com um array de pacientes e um contador de elementos,
        //  para representar a fila de prioridade
        private Paciente[] itens; //array de pacientes, para armazenar os pacientes na fila de prioridade
        private int n; //contador de elementos, para controlar quantos pacientes tem na fila de prioridade

        // Construtor: cria a fila com capacidade máxima "tam"
        public FilaPrioridade(int tam) {
            //aqui no construtor, eu crio o array de pacientes com o tamanho definido, e inicializo o contador de elementos com 0
            itens = new Paciente[tam];
            n = 0; //inicializa o contador de elementos com 0, pois a fila de prioridade começa vazia
        }

        // Inserção mantendo ordem de prioridade (maior na frente)
        // Empate: quem chegou primeiro fica na frente (FIFO)
        public void inserir(Paciente p) {
            if (n == itens.length) {
                System.out.println("Erro: overflow");
                return; //se a fila de prioridade estiver cheia, imprime uma mensagem de erro e retorna, para evitar overflow
            }

            int i = n - 1; //começa a comparação a partir do último elemento da fila de prioridade,
            //  para encontrar a posição correta do novo paciente
            //aqui estou comparando o paciente atual com os pacientes já inseridos,
            //  para encontrar a posição correta de inserção

            while (i >= 0 && itens[i].prioridade < p.prioridade) {
                //PARTE MAIS IMPORTANTE: aqui acontece a comparação, enquanto o i for maior ou igual a 0 e o paciente na posição i 
                // tiver prioridade menor que o paciente atual, entao tem que fazer a troca, ou seja, empurrar o paciente para a direita, 
                // para abrir espaço para o novo paciente
                itens[i + 1] = itens[i];
                i--;
            }
            itens[i + 1] = p; //aqui estou inserindo o novo paciente na posição correta, que é a posição i+1,
            //  pois o i foi decrementado no while
            n++; //incrementa o contador de elementos, pois foi inserido um novo paciente na fila de prioridade 
        }

        // Remoção: sempre remove o índice [0] (maior prioridade)
        public Paciente remover() {
            if (n == 0) {
                System.out.println("Erro: underflow");
                return null;
            }

            Paciente removido = itens[0];

            for (int i = 0; i < n - 1; i++) {
                //anda a fila para a esquerda, ou seja, empurra os pacientes para a esquerda, para preencher o espaço deixado 
                // pelo paciente removido
                itens[i] = itens[i + 1];
            }
            itens[n - 1] = null; //limpa a última posição da fila, para evitar lixo de memóriab
            n--;

            return removido;
        }
    }
    
}
