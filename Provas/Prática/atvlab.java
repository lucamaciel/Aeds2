import java.util.Scanner;

class No {

    // false = branco | true = preto
    public int elemento;
    public boolean cor;
    public No esq;
    public No dir;

    public No() {
        this(-1);
    }

    public No(int elemento) {
        this(elemento, false, null, null);
    }

    public No(int elemento, boolean cor) {
        this(elemento, cor, null, null);
    }

    public No(int elemento, boolean cor, No esq, No dir) {
        this.elemento = elemento;
        this.cor = cor;
        this.esq = esq;
        this.dir = dir;
    }

    public boolean isNoTipo4() {
        return (esq != null && dir != null && esq.cor && dir.cor);
    }
}

public class atvlab {

    private No raiz;

    public atvlab() {
        raiz = null;
    }

    public boolean pesquisar(int elemento) {
        return pesquisar(elemento, raiz);
    }

    private boolean pesquisar(int elemento, No i) {

        if (i == null) {
            return false;
        } else if (elemento == i.elemento) {
            return true;
        } else if (elemento < i.elemento) {
            return pesquisar(elemento, i.esq);
        } else {
            return pesquisar(elemento, i.dir);
        }
    }

    public void caminharCentral() {
        caminharCentral(raiz);
        System.out.println();
    }

    private void caminharCentral(No i) {

        if (i != null) {
            caminharCentral(i.esq);
            System.out.print(i.elemento + "(cor=" + (i.cor ? 1 : 0) + ") ");
            caminharCentral(i.dir);
        }
    }

    private void fragmentar(No i) {

        i.cor = true;

        if (i.esq != null) {
            i.esq.cor = false;
        }

        if (i.dir != null) {
            i.dir.cor = false;
        }

        if (i == raiz) {
            i.cor = false;
        }
    }

    public void inserir(int elemento) throws Exception {

        if (raiz == null) {

            raiz = new No(elemento);

        } else if (raiz.esq == null && raiz.dir == null) {

            if (elemento < raiz.elemento) {
                raiz.esq = new No(elemento);
            } else {
                raiz.dir = new No(elemento);
            }

        } else if (raiz.esq == null) {

            if (elemento < raiz.elemento) {

                raiz.esq = new No(elemento);

            } else if (elemento < raiz.dir.elemento) {

                raiz.esq = new No(raiz.elemento);
                raiz.elemento = elemento;

            } else {

                raiz.esq = new No(raiz.elemento);
                raiz.elemento = raiz.dir.elemento;
                raiz.dir.elemento = elemento;
            }

            raiz.esq.cor = false;
            raiz.dir.cor = false;

        } else if (raiz.dir == null) {

            if (elemento > raiz.elemento) {

                raiz.dir = new No(elemento);

            } else if (elemento > raiz.esq.elemento) {

                raiz.dir = new No(raiz.elemento);
                raiz.elemento = elemento;

            } else {

                raiz.dir = new No(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = elemento;
            }

            raiz.esq.cor = false;
            raiz.dir.cor = false;

        } else {

            inserir(elemento, null, null, null, raiz);
        }

        raiz.cor = false;
    }

    private void inserir(int elemento, No bisavo, No avo, No pai, No i) throws Exception {

        if (i == null) {

            if (elemento < pai.elemento) {
                i = pai.esq = new No(elemento, true);
            } else {
                i = pai.dir = new No(elemento, true);
            }

            if (pai.cor) {
                balancear(bisavo, avo, pai, i);
            }

        } else {

            if (i.isNoTipo4()) {

                fragmentar(i);

                if (pai != null && pai.cor) {
                    balancear(bisavo, avo, pai, i);
                }
            }

            if (elemento < i.elemento) {

                inserir(elemento, avo, pai, i, i.esq);

            } else if (elemento > i.elemento) {

                inserir(elemento, avo, pai, i, i.dir);

            } else {

                throw new Exception("Elemento repetido.");
            }
        }
    }

    private void balancear(No bisavo, No avo, No pai, No i) {

        if (!pai.cor) {
            return;
        }

        if (pai.elemento > avo.elemento) {

            if (i.elemento > pai.elemento) {
                avo = rotacaoEsq(avo);
            } else {
                avo = rotacaoDirEsq(avo);
            }

        } else {

            if (i.elemento < pai.elemento) {
                avo = rotacaoDir(avo);
            } else {
                avo = rotacaoEsqDir(avo);
            }
        }

        if (bisavo == null) {
            raiz = avo;
        } else if (avo.elemento < bisavo.elemento) {
            bisavo.esq = avo;
        } else {
            bisavo.dir = avo;
        }

        avo.cor = false;

        if (avo.esq != null) {
            avo.esq.cor = true;
        }

        if (avo.dir != null) {
            avo.dir.cor = true;
        }
    }

    private No rotacaoDir(No no) {

        No filhoEsq = no.esq;
        No temp = filhoEsq.dir;

        filhoEsq.dir = no;
        no.esq = temp;

        return filhoEsq;
    }

    private No rotacaoEsq(No no) {

        No filhoDir = no.dir;
        No temp = filhoDir.esq;

        filhoDir.esq = no;
        no.dir = temp;

        return filhoDir;
    }

    private No rotacaoDirEsq(No no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }

    private No rotacaoEsqDir(No no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }

    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);
            atvlab arvore = new atvlab();

            System.out.print("Quantidade de numeros: ");
            int quantidade = sc.nextInt();

            for (int i = 0; i < quantidade; i++) {
                System.out.print("Digite o numero " + (i + 1) + ": ");
                int valor = sc.nextInt();
                arvore.inserir(valor);
            }

            System.out.println("Arvore em ordem:");
            arvore.caminharCentral();

            sc.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
