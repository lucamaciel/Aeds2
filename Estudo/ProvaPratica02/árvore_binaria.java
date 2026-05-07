/*
 * Estudos de Arvore Binaria de Busca (ABB)
 *
 * Ideia principal:
 * - Todo valor menor que o no atual fica na subarvore esquerda.
 * - Todo valor maior que o no atual fica na subarvore direita.
 * - Para inserir, pesquisar ou percorrer a arvore, usamos recursao.
 *
 * Exemplo:
 * Se a raiz for 10:
 * - 5 entra para a esquerda de 10.
 * - 20 entra para a direita de 10.
 * - 7 entra a esquerda de 10, mas a direita de 5.
 */
public class árvore_binaria {

    /*
     * Classe que representa uma Arvore Binaria de Busca.
     *
     * Ela fica separada da classe principal para deixar claro que a arvore tem
     * seus proprios dados e metodos. A classe principal serve apenas como
     * "arquivo de estudo" para guardar o exercicio.
     */
    static class ArvoreBinaria {
        private No raiz;

        /*
         * A arvore nasce vazia. Por isso a raiz comeca valendo null.
         * Quando o primeiro elemento for inserido, ele vira a raiz.
         */
        ArvoreBinaria() {
            raiz = null;
        }

        // ============================================================
        // Insercao
        // ============================================================

        /*
         * Metodo publico de insercao.
         *
         * Ele recebe apenas o valor, porque quem usa a arvore nao precisa saber
         * por qual no a recursao comeca. Internamente, sempre comecamos pela raiz.
         *
         * Nesta versao, valores repetidos sao inseridos na direita. Alguns
         * professores preferem lancar erro em repetidos; no exercicio toAB mais
         * abaixo eu deixei uma versao que lanca Exception quando encontra repetido.
         */
        void inserir(int x) {
            raiz = inserir(x, raiz);
        }

        /*
         * Metodo recursivo que realmente percorre a arvore para inserir.
         *
         * atual -> no que estou analisando agora.
         * x     -> valor que quero colocar na arvore.
         *
         * Quando atual == null, achei o lugar vazio onde o novo no deve entrar.
         */
        private No inserir(int x, No atual) {
            if (atual == null) {
                return new No(x);
            }

            if (x < atual.valor) {
                atual.esquerda = inserir(x, atual.esquerda);
            } else {
                atual.direita = inserir(x, atual.direita);
            }

            return atual;
        }

        /*
         * Insercao usando a referencia do pai.
         *
         * A logica e a mesma da insercao normal, mas aqui o metodo recursivo
         * tambem recebe o pai do no atual. Isso ajuda em exercicios que pedem
         * para ligar o novo no diretamente no pai.
         *
         * Nesta versao, valor repetido gera erro.
         */
        void inserirPai(int x) throws Exception {
            if (raiz == null) {
                raiz = new No(x);
            } else if (x < raiz.valor) {
                inserirPai(x, raiz.esquerda, raiz);
            } else if (x > raiz.valor) {
                inserirPai(x, raiz.direita, raiz);
            } else {
                throw new Exception("Valor ja existe na arvore");
            }
        }

        private void inserirPai(int x, No atual, No pai) throws Exception {
            if (atual == null) {
                if (x < pai.valor) {
                    pai.esquerda = new No(x);
                } else {
                    pai.direita = new No(x);
                }
            } else if (x < atual.valor) {
                inserirPai(x, atual.esquerda, atual);
            } else if (x > atual.valor) {
                inserirPai(x, atual.direita, atual);
            } else {
                throw new Exception("Valor ja existe na arvore");
            }
        }

        // ============================================================
        // Pesquisa
        // ============================================================

        /*
         * Retorna true se o valor existir na arvore.
         *
         * Mantive tambem o metodo Pesquisar com P maiusculo mais abaixo, porque
         * ele aparecia no seu codigo. Em Java, o padrao e metodo com letra
         * minuscula, entao pesquisar e o nome mais organizado.
         */
        boolean pesquisar(int x) {
            return pesquisar(x, raiz);
        }

        boolean Pesquisar(int x) {
            return pesquisar(x);
        }

        private boolean pesquisar(int x, No atual) {
            if (atual == null) {
                return false;
            } else if (x == atual.valor) {
                return true;
            } else if (x < atual.valor) {
                return pesquisar(x, atual.esquerda);
            } else {
                return pesquisar(x, atual.direita);
            }
        }

        // ============================================================
        // Caminhamentos
        // ============================================================

        /*
         * Caminhamento central: esquerda, raiz, direita.
         * Em uma ABB, esse caminhamento imprime os valores em ordem crescente.
         */
        void caminharCentral() {
            caminharCentral(raiz);
            System.out.println();
        }

        private void caminharCentral(No atual) {
            if (atual != null) {
                caminharCentral(atual.esquerda);
                System.out.print(atual.valor + " ");
                caminharCentral(atual.direita);
            }
        }

        /*
         * Caminhamento pre-ordem: raiz, esquerda, direita.
         * Ele mostra o no antes de visitar os filhos.
         */
        void caminharPre() {
            caminharPre(raiz);
            System.out.println();
        }

        private void caminharPre(No atual) {
            if (atual != null) {
                System.out.print(atual.valor + " ");
                caminharPre(atual.esquerda);
                caminharPre(atual.direita);
            }
        }

        /*
         * Caminhamento pos-ordem: esquerda, direita, raiz.
         * Ele mostra o no depois de visitar os filhos.
         */
        void caminharPos() {
            caminharPos(raiz);
            System.out.println();
        }

        private void caminharPos(No atual) {
            if (atual != null) {
                caminharPos(atual.esquerda);
                caminharPos(atual.direita);
                System.out.print(atual.valor + " ");
            }
        }

        // ============================================================
        // Exercicios com recursao
        // ============================================================

        /*
         * Altura da arvore.
         *
         * Definicao usada aqui:
         * - Arvore vazia tem altura 0.
         * - Arvore com apenas a raiz tem altura 1.
         *
         * O retorno e 1 + a maior altura entre a subarvore esquerda e direita.
         */
        int getAltura() {
            return getAltura(raiz);
        }

        private int getAltura(No atual) {
            if (atual == null) {
                return 0;
            }

            int alturaEsq = getAltura(atual.esquerda);
            int alturaDir = getAltura(atual.direita);
            return 1 + Math.max(alturaEsq, alturaDir);
        }

        /*
         * Insere 100000 valores aleatorios e mostra:
         * - quantidade de elementos inseridos;
         * - log2 dessa quantidade;
         * - altura atual da arvore.
         *
         * Isso ajuda a comparar a altura real da arvore com o comportamento
         * esperado de uma arvore mais equilibrada.
         */
        void inserir100000Aleatorios() {
            for (int j = 0; j < 100000; j++) {
                int x = (int) (Math.random() * 100000);
                inserir(x);

                int numeroElementos = j + 1;
                double logaritmo = Math.log(numeroElementos) / Math.log(2);
                int altura = getAltura();

                System.out.println(
                    "Numero de elementos: " + numeroElementos
                    + ", Logaritmo (base 2): " + logaritmo
                    + ", Altura da arvore: " + altura
                );
            }
        }

        /*
         * Mantive este metodo com o nome antigo para nao quebrar chamadas que
         * voce talvez tenha em outros testes.
         */
        void inserir1000() {
            inserir100000Aleatorios();
        }

        /*
         * Soma todos os valores guardados na arvore.
         *
         * Para cada no:
         * soma = valor do no + soma da esquerda + soma da direita.
         */
        public int soma() {
            return soma(raiz);
        }

        private int soma(No atual) {
            if (atual == null) {
                return 0;
            }

            return atual.valor + soma(atual.esquerda) + soma(atual.direita);
        }

        /*
         * Conta quantos valores pares existem na arvore.
         *
         * Se o valor do no atual for par, conto 1. Depois somo a quantidade
         * de pares da subarvore esquerda e da subarvore direita.
         */
        public int contarPares() {
            return contarPares(raiz);
        }

        private int contarPares(No atual) {
            if (atual == null) {
                return 0;
            }

            int quantidadeDoAtual = (atual.valor % 2 == 0) ? 1 : 0;
            return quantidadeDoAtual + contarPares(atual.esquerda) + contarPares(atual.direita);
        }

        /*
         * Retorna true se existir pelo menos um valor divisivel por 11.
         *
         * O operador || ajuda a parar mais cedo: se o valor atual ja for
         * divisivel por 11, nem preciso procurar nas subarvores.
         */
        public boolean div11() {
            return div11(raiz);
        }

        private static boolean div11(No atual) {
            if (atual == null) {
                return false;
            }

            return atual.valor % 11 == 0 || div11(atual.esquerda) || div11(atual.direita);
        }

        /*
         * Compara duas arvores e retorna true se elas forem iguais.
         *
         * Duas arvores sao iguais quando:
         * - as duas estao vazias; ou
         * - as duas tem no na mesma posicao, com mesmo valor, e suas subarvores
         *   esquerda e direita tambem sao iguais.
         */
        public static boolean saoIguais(ArvoreBinaria a, ArvoreBinaria b) {
            return saoIguais(a.raiz, b.raiz);
        }

        private static boolean saoIguais(No a, No b) {
            if (a == null && b == null) {
                return true;
            }

            if (a == null || b == null) {
                return false;
            }

            return a.valor == b.valor
                && saoIguais(a.esquerda, b.esquerda)
                && saoIguais(a.direita, b.direita);
        }

        // ============================================================
        // TreeSort
        // ============================================================

        /*
         * TreeSort:
         * 1. Insere todos os valores do array em uma ABB.
         * 2. Faz caminhamento central para escrever os valores em ordem crescente.
         *
         * Complexidade:
         * - Caso medio: O(n log n), se a arvore ficar razoavelmente equilibrada.
         * - Pior caso: O(n^2), se a arvore ficar parecida com uma lista.
         * - O caminhamento central sozinho custa O(n).
         */
        public static void treeSort(int[] array) {
            ArvoreBinaria arvore = new ArvoreBinaria();

            for (int valor : array) {
                arvore.inserir(valor);
            }

            int[] posicao = {0};
            preencherEmOrdem(arvore.raiz, array, posicao);
        }

        private static void preencherEmOrdem(No atual, int[] array, int[] posicao) {
            if (atual != null) {
                preencherEmOrdem(atual.esquerda, array, posicao);
                array[posicao[0]] = atual.valor;
                posicao[0]++;
                preencherEmOrdem(atual.direita, array, posicao);
            }
        }

        // ============================================================
        // Exercicio: lista simples + lista dupla -> ABB
        // ============================================================

        /*
         * Recebe:
         * - p1: primeiro no de uma lista simples.
         * - p2: primeiro no de uma lista dupla.
         *
         * Retorna:
         * - a raiz de uma nova ABB criada com os elementos das duas listas.
         *
         * O metodo alterna a insercao:
         * primeiro pega um elemento da lista simples, depois um da lista dupla.
         * Se uma lista acabar antes da outra, os elementos restantes da lista
         * maior continuam sendo inseridos.
         *
         * Observacao sobre celula cabeca:
         * Nas listas da AEDS, normalmente a primeira celula e uma "cabeca",
         * isto e, ela nao guarda um valor real da lista. Por isso comecamos
         * andando uma posicao: p1 = p1.prox e p2 = p2.prox.
         */
        No toAB(Celula p1, CelulaDupla p2) throws Exception {
            No resp = null;

            if (p1 != null) {
                p1 = p1.prox;
            }

            if (p2 != null) {
                p2 = p2.prox;
            }

            while (p1 != null && p2 != null) {
                resp = inserirSemRepetir(resp, p1.elemento);
                resp = inserirSemRepetir(resp, p2.elemento);

                p1 = p1.prox;
                p2 = p2.prox;
            }

            while (p1 != null) {
                resp = inserirSemRepetir(resp, p1.elemento);
                p1 = p1.prox;
            }

            while (p2 != null) {
                resp = inserirSemRepetir(resp, p2.elemento);
                p2 = p2.prox;
            }

            return resp;
        }

        /*
         * Insercao usada pelo toAB.
         *
         * Diferenca para a insercao normal:
         * - aqui valor repetido gera Exception, como no modelo do enunciado.
         */
        private No inserirSemRepetir(No atual, int x) throws Exception {
            if (atual == null) {
                return new No(x);
            } else if (x < atual.valor) {
                atual.esquerda = inserirSemRepetir(atual.esquerda, x);
            } else if (x > atual.valor) {
                atual.direita = inserirSemRepetir(atual.direita, x);
            } else {
                throw new Exception("Erro ao inserir!");
            }

            return atual;
        }

        /*
         * Modelo minimo de celula de lista simples.
         *
         * Campos:
         * - elemento: valor guardado.
         * - prox: endereco da proxima celula.
         */
        static class Celula {
            int elemento;
            Celula prox;

            Celula() {
                this(0);
            }

            Celula(int elemento) {
                this.elemento = elemento;
                this.prox = null;
            }
        }

        /*
         * Modelo minimo de celula de lista dupla.
         *
         * Campos:
         * - elemento: valor guardado.
         * - ant: endereco da celula anterior.
         * - prox: endereco da proxima celula.
         */
        static class CelulaDupla {
            int elemento;
            CelulaDupla ant;
            CelulaDupla prox;

            CelulaDupla() {
                this(0);
            }

            CelulaDupla(int elemento) {
                this.elemento = elemento;
                this.ant = null;
                this.prox = null;
            }
        }

        /*
         * Cada objeto No representa uma caixinha da arvore.
         *
         * Ele guarda:
         * - valor: o numero armazenado.
         * - esquerda: referencia para os menores valores.
         * - direita: referencia para os maiores valores.
         */
        private static class No {
            int valor;
            No esquerda;
            No direita;

            No(int valor) {
                this.valor = valor;
                this.esquerda = null;
                this.direita = null;
            }
        }
    }
}
