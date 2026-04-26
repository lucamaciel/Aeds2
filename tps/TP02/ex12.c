#include <stdio.h>   // printf, scanf, fopen, fgets, fclose, fprintf, sscanf, sprintf
#include <stdlib.h>  // malloc, realloc, free
#include <string.h>  // strcmp (unica funcao de string.h permitida pelo enunciado)

// ============================================================
// Funcoes auxiliares de string implementadas manualmente
// (o enunciado permite apenas strcmp de string.h)
// ============================================================

int my_len(const char *s) {
    int i = 0;
    while (s[i] != '\0') i++;
    return i;
}

void my_cat(char *dst, const char *src) {
    int i = 0;
    while (dst[i] != '\0') i++;
    int j = 0;
    while (src[j] != '\0') dst[i++] = src[j++];
    dst[i] = '\0';
}

void my_zero(void *ptr, int n) {
    char *p = (char *)ptr;
    for (int i = 0; i < n; i++) p[i] = 0;
}

void str_dup(char **dst, const char *src) {
    *dst = (char *)malloc(my_len(src) + 1);
    int k = 0;
    while (src[k] != '\0') { (*dst)[k] = src[k]; k++; }
    (*dst)[k] = '\0';
}

void prox_campo(const char *src, int *pos, char delim, char *dst, int dst_sz) {
    int i = 0;
    while (src[*pos] == ' ') (*pos)++;
    while (src[*pos] != '\0' && src[*pos] != delim) {
        if (i < dst_sz - 1) dst[i++] = src[(*pos)];
        (*pos)++;
    }
    dst[i] = '\0';
    if (src[*pos] == delim) (*pos)++;
}

// ============================================================
// Tipo Data
// ============================================================
typedef struct { int ano; int mes; int dia; } Data;

Data parse_data(char *s) {
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

void formatar_data(Data *data, char *buffer) {
    sprintf(buffer, "%02d/%02d/%04d", data->dia, data->mes, data->ano);
}

// ============================================================
// Tipo Hora
// ============================================================
typedef struct { int hora; int minuto; } Hora;

Hora parse_hora(char *s) {
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

void formatar_hora(Hora *hora, char *buffer) {
    sprintf(buffer, "%02d:%02d", hora->hora, hora->minuto);
}

// ============================================================
// Tipo Restaurante
// ============================================================
#define MAX_TIPOS 10
#define MAX_STR   256

typedef struct {
    int     id;
    char   *nome;
    char   *cidade;
    int     capacidade;
    double  avaliacao;
    int     n_tipos_cozinha;
    char  **tipos_cozinha;
    int     faixa_preco;
    Hora    horario_abertura;
    Hora    horario_fechamento;
    Data    data_abertura;
    int     aberto;
} Restaurante;

Restaurante *parse_restaurante(char *s) {
    Restaurante *r = (Restaurante *)malloc(sizeof(Restaurante));
    my_zero(r, sizeof(Restaurante));
    int pos = 0;
    char tmp[MAX_STR * MAX_TIPOS];

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    sscanf(tmp, "%d", &r->id);

    prox_campo(s, &pos, ',', tmp, MAX_STR);
    str_dup(&r->nome, tmp);

    prox_campo(s, &pos, ',', tmp, MAX_STR);
    str_dup(&r->cidade, tmp);

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    sscanf(tmp, "%d", &r->capacidade);

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    sscanf(tmp, "%lf", &r->avaliacao);

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    int cnt = 1, ci = 0;
    while (tmp[ci] != '\0') { if (tmp[ci] == ';') cnt++; ci++; }
    r->tipos_cozinha = (char **)malloc(cnt * sizeof(char *));
    int tpos = 0;
    while (tmp[tpos] != '\0' && r->n_tipos_cozinha < MAX_TIPOS) {
        char tbuf[MAX_STR];
        prox_campo(tmp, &tpos, ';', tbuf, MAX_STR);
        str_dup(&r->tipos_cozinha[r->n_tipos_cozinha], tbuf);
        r->n_tipos_cozinha++;
    }

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    r->faixa_preco = my_len(tmp);

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    char hab[6], hfe[6];
    int hi = 0, hj = 0;
    while (tmp[hi] != '-' && tmp[hi] != '\0') {
        if (hj < 5) hab[hj++] = tmp[hi];
        hi++;
    }
    hab[hj] = '\0';
    if (tmp[hi] == '-') { hi++; }
    hj = 0;
    while (tmp[hi] != '\0') {
        if (hj < 5) hfe[hj++] = tmp[hi];
        hi++;
    }
    hfe[hj] = '\0';
    r->horario_abertura   = parse_hora(hab);
    r->horario_fechamento = parse_hora(hfe);

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    r->data_abertura = parse_data(tmp);

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    r->aberto = (strcmp(tmp, "true") == 0) ? 1 : 0;

    return r;
}

void formatar_restaurante(Restaurante *r, char *buffer) {
    char tipos[MAX_STR * MAX_TIPOS];
    tipos[0] = '['; tipos[1] = '\0';
    for (int i = 0; i < r->n_tipos_cozinha; i++) {
        if (i > 0) my_cat(tipos, ",");
        my_cat(tipos, r->tipos_cozinha[i]);
    }
    my_cat(tipos, "]");

    char fp[8]; fp[0] = '\0';
    for (int i = 0; i < r->faixa_preco; i++) my_cat(fp, "$");

    char ba[6], bf[6], bd[11];
    formatar_hora(&r->horario_abertura,   ba);
    formatar_hora(&r->horario_fechamento, bf);
    formatar_data(&r->data_abertura,      bd);

    sprintf(buffer,
        "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
        r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
        tipos, fp, ba, bf, bd,
        r->aberto ? "true" : "false");
}

void free_restaurante(Restaurante *r) {
    if (!r) return;
    free(r->nome);
    free(r->cidade);
    for (int i = 0; i < r->n_tipos_cozinha; i++) free(r->tipos_cozinha[i]);
    free(r->tipos_cozinha);
    free(r);
}

// ============================================================
// Colecao_Restaurantes
// ============================================================
#define CAP_INICIAL 100

typedef struct {
    int          tamanho;
    Restaurante **restaurantes;
} Colecao_Restaurantes;

void ler_csv_colecao(Colecao_Restaurantes *colecao, char *path) {
    FILE *f = fopen(path, "r");
    if (!f) { fprintf(stderr, "Arquivo nao encontrado: %s\n", path); return; }
    int cap = CAP_INICIAL;
    colecao->restaurantes = (Restaurante **)malloc(cap * sizeof(Restaurante *));
    colecao->tamanho = 0;
    char linha[1024];
    int cabecalho = 1;
    while (fgets(linha, sizeof(linha), f)) {
        int len = my_len(linha);
        while (len > 0 && (linha[len-1] == '\n' || linha[len-1] == '\r'))
            linha[--len] = '\0';
        if (cabecalho) { cabecalho = 0; continue; }
        if (len == 0) continue;
        if (colecao->tamanho == cap) {
            cap *= 2;
            colecao->restaurantes = (Restaurante **)realloc(
                colecao->restaurantes, cap * sizeof(Restaurante *));
        }
        colecao->restaurantes[colecao->tamanho++] = parse_restaurante(linha);
    }
    fclose(f);
}

Colecao_Restaurantes *ler_csv() {
    Colecao_Restaurantes *col =
        (Colecao_Restaurantes *)malloc(sizeof(Colecao_Restaurantes));
    ler_csv_colecao(col, "/tmp/restaurantes.csv");
    return col;
}

void free_colecao(Colecao_Restaurantes *col) {
    for (int i = 0; i < col->tamanho; i++) free_restaurante(col->restaurantes[i]);
    free(col->restaurantes);
    free(col);
}

Restaurante *buscar_por_id(Colecao_Restaurantes *col, int id) {
    for (int i = 0; i < col->tamanho; i++)
        if (col->restaurantes[i]->id == id) return col->restaurantes[i];
    return NULL;
}

// ============================================================
// Pilha com alocacao sequencial de Restaurante*
// baseada na pilha de inteiros vista em aula
// ============================================================
#define CAP_PILHA 1000

typedef struct {
    int          topo;
    int          capacidade;
    Restaurante **dados;
} Pilha;

void pilha_criar(Pilha *p) {
    p->dados      = (Restaurante **)malloc(CAP_PILHA * sizeof(Restaurante *));
    p->topo       = -1;
    p->capacidade = CAP_PILHA;
}

int pilha_vazia(Pilha *p) {
    return p->topo == -1;
}

void pilha_inserir(Pilha *p, Restaurante *r) {
    if (p->topo == p->capacidade - 1) {
        p->capacidade *= 2;
        p->dados = (Restaurante **)realloc(
            p->dados, p->capacidade * sizeof(Restaurante *));
    }
    p->dados[++(p->topo)] = r;
}

Restaurante *pilha_remover(Pilha *p) {
    if (pilha_vazia(p)) return NULL;
    return p->dados[(p->topo)--];
}

void pilha_destruir(Pilha *p) {
    free(p->dados);
}

int main() {
    Colecao_Restaurantes *colecao = ler_csv();
    Pilha pilha;
    pilha_criar(&pilha);

    // primeira parte: ids ate -1 — empilha cada restaurante encontrado
    int id;
    while (scanf("%d", &id) == 1) {
        if (id == -1) break;
        Restaurante *r = buscar_por_id(colecao, id);
        if (r) pilha_inserir(&pilha, r);
    }

    // segunda parte: n comandos I (empilhar) e R (desempilhar)
    int n;
    scanf("%d", &n);
    char cmd[4];
    for (int i = 0; i < n; i++) {
        scanf("%s", cmd);
        if (strcmp(cmd, "I") == 0) {
            scanf("%d", &id);
            Restaurante *r = buscar_por_id(colecao, id);
            if (r) pilha_inserir(&pilha, r);
        } else if (strcmp(cmd, "R") == 0) {
            Restaurante *r = pilha_remover(&pilha);
            if (r) printf("(R)%s\n", r->nome);
        }
    }

    // exibe do topo ao fundo
    for (int i = pilha.topo; i >= 0; i--) {
        char buf[1024];
        formatar_restaurante(pilha.dados[i], buf);
        printf("%s\n", buf);
    }

    pilha_destruir(&pilha);
    free_colecao(colecao);
    return 0;
}