// Construtor da matriz flexível

public mat(int linhas, int colunas) {

this.linhas = linhas;

this.colunas = colunas;



Celula[][] aux = new Celula[linhas][colunas];

for (int i = 0; i < linhas; i++) {

for (int j = 0; j < colunas; j++) {

aux[i][j] = new Celula(i, j, 0);

}

}



for (int i = 0; i < linhas; i++) {

for (int j = 0; j < colunas; j++) {

if (j > 0) aux[i][j].esq = aux[i][j-1];

if (j < colunas-1) aux[i][j].dir = aux[i][j+1];

if (i > 0) aux[i][j].sup = aux[i-1][j];

if (i < linhas-1) aux[i][j].inf = aux[i+1][j];

}

}



this.inicio = aux[0][0];

}

// Método soma

public void soma(mat a) {

if (this.linhas != a.linhas || this.colunas != a.colunas) {

System.out.println("Erro: as matrizes possuem dimensões diferentes!");

return;

}



Celula linha1 = this.inicio;

Celula linha2 = a.inicio;



while (linha1 != null && linha2 != null) {

Celula p1 = linha1;

Celula p2 = linha2;


while (p1 != null && p2 != null) {

p1.valor = p1.valor + p2.valor;

p1 = p1.dir;

p2 = p2.dir;

}


linha1 = linha1.inf;

linha2 = linha2.inf;

}

}