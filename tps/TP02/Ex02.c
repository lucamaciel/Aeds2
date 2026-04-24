#include <stdio.h>
#include <stdlib.h>
#include <string.h> // apenas strcmp

// ============================================================
// Utilitários manuais
// ============================================================

// concatena src no fim de dst (dst deve ter espaço)
static void myCat(char *dst, const char *src) {
    int i = 0;
    while (dst[i] != '\0') i++;
    int j = 0;
    while (src[j] != '\0') dst[i++] = src[j++];
    dst[i] = '\0';
}

// comprimento de string
static int myLen(const char *s) {
    int i = 0;
    while (s[i] != '\0') i++;
    return i;
}

// zera n bytes de ptr
static void myZero(void *ptr, int n) {
    char *p = (char *)ptr;
    for (int i = 0; i < n; i++) p[i] = 0;
}

// ============================================================
// Tipo Data
// ============================================================
typedef struct {
    int dia;
    int mes;
    int ano;
} Data;

// recebe "YYYY-MM-DD", preenche struct Data
Data parseData(const char *s) {
    Data d;
    // s = "YYYY-MM-DD"
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

// retorna "DD/MM/YYYY" em buf (tam >= 11)
void formatarData(const Data *d, char *buf) {
    sprintf(buf, "%02d/%02d/%04d", d->dia, d->mes, d->ano);
}

// ============================================================
// Tipo Hora
// ============================================================
typedef struct {
    int hora;
    int minuto;
} Hora;

// recebe "HH:mm", preenche struct Hora
Hora parseHora(const char *s) {
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

// retorna "HH:mm" em buf (tam >= 6)
void formatarHora(const Hora *h, char *buf) {
    sprintf(buf, "%02d:%02d", h->hora, h->minuto);
}

// ============================================================
// Tipo Restaurante
// ============================================================
#define MAX_TIPOS_COZINHA 10
#define MAX_STR 128

typedef struct {
    int id;
    char nome[MAX_STR];
    char cidade[MAX_STR];
    int capacidade;
    double avaliacao;
    char tipoCozinha[MAX_TIPOS_COZINHA][MAX_STR];
    int numTipos;
    int faixaPreco; // conta os '$': $=1, $$=2, etc.
    Hora horarioAbertura;
    Hora horarioFechamento;
    Data dataAbertura;
    int aberto; // 0=false, 1=true
} Restaurante;

// Copia campo delimitado por 'delim' de src[*pos] para dst; avança *pos
static void proxCampo(const char *src, int *pos, char delim, char *dst, int dstSz) {
    int i = 0;
    // trim espaço inicial
    while (src[*pos] == ' ') (*pos)++;
    while (src[*pos] != '\0' && src[*pos] != delim) {
        if (i < dstSz - 1) dst[i++] = src[(*pos)];
        (*pos)++;
    }
    dst[i] = '\0';
    if (src[*pos] == delim) (*pos)++; // pula o delimitador
}

// CSV:
// id,nome,cidade,capacidade,avaliacao,tipos_cozinha,faixa_preco,horario,data_abertura,aberto
// c[0] c[1] c[2] c[3] c[4] c[5] c[6] c[7] c[8] c[9]
Restaurante parseRestaurante(const char *linha) {
    Restaurante r;
    myZero(&r, sizeof(r));

    int pos = 0;
    char tmp[MAX_STR * MAX_TIPOS_COZINHA];

    // c[0] id
    proxCampo(linha, &pos, ',', tmp, sizeof(tmp));
    sscanf(tmp, "%d", &r.id);

    // c[1] nome
    proxCampo(linha, &pos, ',', r.nome, MAX_STR);

    // c[2] cidade
    proxCampo(linha, &pos, ',', r.cidade, MAX_STR);

    // c[3] capacidade
    proxCampo(linha, &pos, ',', tmp, sizeof(tmp));
    sscanf(tmp, "%d", &r.capacidade);

    // c[4] avaliacao
    proxCampo(linha, &pos, ',', tmp, sizeof(tmp));
    sscanf(tmp, "%lf", &r.avaliacao);

    // c[5] tipos_cozinha (separados por ';')
    proxCampo(linha, &pos, ',', tmp, sizeof(tmp));
    r.numTipos = 0;
    int tpos = 0;
    while (tmp[tpos] != '\0' && r.numTipos < MAX_TIPOS_COZINHA) {
        proxCampo(tmp, &tpos, ';', r.tipoCozinha[r.numTipos], MAX_STR);
        r.numTipos++;
    }

    // c[6] faixa_preco (conta '$')
    proxCampo(linha, &pos, ',', tmp, sizeof(tmp));
    r.faixaPreco = myLen(tmp); // conta os '$'

    // c[7] horario "HH:mm-HH:mm"
    proxCampo(linha, &pos, ',', tmp, sizeof(tmp));
    // acha o '-' separador de horários manualmente
    char horAb[6], horFech[6];
    int hi = 0, hj = 0;
    while (tmp[hi] != '-' && tmp[hi] != '\0') {
        if (hj < 5) horAb[hj++] = tmp[hi];
        hi++;
    }
    horAb[hj] = '\0';
    if (tmp[hi] == '-') hi++;
    hj = 0;
    while (tmp[hi] != '\0') {
        if (hj < 5) horFech[hj++] = tmp[hi];
        hi++;
    }
    horFech[hj] = '\0';
    r.horarioAbertura   = parseHora(horAb);
    r.horarioFechamento = parseHora(horFech);

    // c[8] data_abertura
    proxCampo(linha, &pos, ',', tmp, sizeof(tmp));
    r.dataAbertura = parseData(tmp);

    // c[9] aberto
    proxCampo(linha, &pos, ',', tmp, sizeof(tmp));
    r.aberto = (strcmp(tmp, "true") == 0) ? 1 : 0;

    return r;
}

void formatarRestaurante(const Restaurante *r, char *out, int outSz) {
    // tipos de cozinha: [tipo1,tipo2,...]
    char tipos[MAX_STR * MAX_TIPOS_COZINHA];
    tipos[0] = '['; tipos[1] = '\0';
    for (int i = 0; i < r->numTipos; i++) {
        if (i > 0) myCat(tipos, ",");
        myCat(tipos, r->tipoCozinha[i]);
    }
    myCat(tipos, "]");

    // faixa de preço: $$$...
    char fp[MAX_TIPOS_COZINHA + 1];
    fp[0] = '\0';
    for (int i = 0; i < r->faixaPreco; i++) myCat(fp, "$");

    char bufAb[6], bufFech[6], bufData[11];
    formatarHora(&r->horarioAbertura,   bufAb);
    formatarHora(&r->horarioFechamento, bufFech);
    formatarData(&r->dataAbertura,      bufData);

    sprintf(out,
        "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
        r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
        tipos, fp,
        bufAb, bufFech,
        bufData,
        r->aberto ? "true" : "false");
}

// ============================================================
// Coleção de Restaurantes
// ============================================================
#define CAPACIDADE_INICIAL 100

typedef struct {
    int tamanho;
    int capacidade;
    Restaurante *restaurantes;
} ColecaoRestaurantes;

void initColecao(ColecaoRestaurantes *col) {
    col->restaurantes = (Restaurante *)malloc(CAPACIDADE_INICIAL * sizeof(Restaurante));
    col->tamanho    = 0;
    col->capacidade = CAPACIDADE_INICIAL;
}

void freeColecao(ColecaoRestaurantes *col) {
    free(col->restaurantes);
    col->restaurantes = NULL;
    col->tamanho = col->capacidade = 0;
}

void lerCsv(ColecaoRestaurantes *col, const char *path) {
    FILE *f = fopen(path, "r");
    if (!f) {
        fprintf(stderr, "Arquivo nao encontrado: %s\n", path);
        return;
    }

    char linha[1024];
    int cabecalho = 1;
    while (fgets(linha, sizeof(linha), f)) {
        // remove '\n' e '\r' do fim com loop manual
        int len = myLen(linha);
        while (len > 0 && (linha[len-1] == '\n' || linha[len-1] == '\r'))
            linha[--len] = '\0';

        if (cabecalho) { cabecalho = 0; continue; }
        if (len == 0)  continue;

        if (col->tamanho == col->capacidade) {
            col->capacidade *= 2;
            col->restaurantes = (Restaurante *)realloc(col->restaurantes,
                                        col->capacidade * sizeof(Restaurante));
        }
        col->restaurantes[col->tamanho++] = parseRestaurante(linha);
    }
    fclose(f);
}

ColecaoRestaurantes lerCsvPadrao() {
    ColecaoRestaurantes col;
    initColecao(&col);
    lerCsv(&col, "/tmp/restaurantes.csv");
    return col;
}

Restaurante *buscarPorId(ColecaoRestaurantes *col, int id) {
    for (int i = 0; i < col->tamanho; i++) {
        if (col->restaurantes[i].id == id)
            return &col->restaurantes[i];
    }
    return NULL;
}

// ============================================================
// Main
// ============================================================
int main() {
    ColecaoRestaurantes colecao = lerCsvPadrao();

    int id;
    while (scanf("%d", &id) == 1) {
        if (id == -1) break;
        Restaurante *r = buscarPorId(&colecao, id);
        if (r != NULL) {
            char out[1024];
            formatarRestaurante(r, out, sizeof(out));
            printf("%s\n", out);
        }
    }

    freeColecao(&colecao);
    return 0;
}