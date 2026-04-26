#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// --- Funções Auxiliares de String ---
int my_len(const char *s) {
    int i = 0;
    while (s && s[i] != '\0') i++;
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
    // Removido o pulo de espaços para manter a fidelidade ao CSV
    while (src[*pos] != '\0' && src[*pos] != delim) {
        if (i < dst_sz - 1) dst[i++] = src[(*pos)];
        (*pos)++;
    }
    dst[i] = '\0';
    if (src[*pos] == delim) (*pos)++;
}

// --- Tipos e Structs ---
typedef struct { int ano, mes, dia; } Data;
typedef struct { int hora, minuto; } Hora;

#define MAX_TIPOS 10
#define MAX_STR 256

typedef struct {
    int id;
    char *nome, *cidade;
    int capacidade;
    double avaliacao;
    int n_tipos_cozinha;
    char **tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura, horario_fechamento;
    Data data_abertura;
    int aberto;
} Restaurante;

// --- Parsing ---
Restaurante *parse_restaurante(char *s) {
    Restaurante *r = (Restaurante *)malloc(sizeof(Restaurante));
    my_zero(r, sizeof(Restaurante));
    int pos = 0;
    char tmp[MAX_STR * MAX_TIPOS];

    prox_campo(s, &pos, ',', tmp, sizeof(tmp)); sscanf(tmp, "%d", &r->id);
    prox_campo(s, &pos, ',', tmp, MAX_STR); str_dup(&r->nome, tmp);
    prox_campo(s, &pos, ',', tmp, MAX_STR); str_dup(&r->cidade, tmp);
    prox_campo(s, &pos, ',', tmp, sizeof(tmp)); sscanf(tmp, "%d", &r->capacidade);
    prox_campo(s, &pos, ',', tmp, sizeof(tmp)); sscanf(tmp, "%lf", &r->avaliacao);

    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    int cnt = 1, ci = 0;
    while (tmp[ci] != '\0') { if (tmp[ci] == ';') cnt++; ci++; }
    r->tipos_cozinha = (char **)malloc(cnt * sizeof(char *));
    int tpos = 0;
    while (tmp[tpos] != '\0' && r->n_tipos_cozinha < MAX_TIPOS) {
        char tbuf[MAX_STR];
        prox_campo(tmp, &tpos, ';', tbuf, MAX_STR);
        str_dup(&r->tipos_cozinha[r->n_tipos_cozinha++], tbuf);
    }

    prox_campo(s, &pos, ',', tmp, sizeof(tmp)); r->faixa_preco = my_len(tmp);
    prox_campo(s, &pos, ',', tmp, sizeof(tmp));
    char hab[6], hfe[6]; int hi = 0, hj = 0;
    while (tmp[hi] != '-' && tmp[hi] != '\0') hab[hj++] = tmp[hi++];
    hab[hj] = '\0'; if (tmp[hi] == '-') hi++; hj = 0;
    while (tmp[hi] != '\0') hfe[hj++] = tmp[hi++];
    hfe[hj] = '\0';
    sscanf(hab, "%d:%d", &r->horario_abertura.hora, &r->horario_abertura.minuto);
    sscanf(hfe, "%d:%d", &r->horario_fechamento.hora, &r->horario_fechamento.minuto);

    prox_campo(s, &pos, ',', tmp, sizeof(tmp)); sscanf(tmp, "%d-%d-%d", &r->data_abertura.ano, &r->data_abertura.mes, &r->data_abertura.dia);
    prox_campo(s, &pos, ',', tmp, sizeof(tmp)); r->aberto = (strcmp(tmp, "true") == 0);

    return r;
}

void formatar_restaurante(Restaurante *r, char *buffer) {
    char tipos[MAX_STR * MAX_TIPOS] = "[";
    for (int i = 0; i < r->n_tipos_cozinha; i++) {
        if (i > 0) my_cat(tipos, ", ");
        my_cat(tipos, r->tipos_cozinha[i]);
    }
    my_cat(tipos, "]");

    char fp[8] = "";
    for (int i = 0; i < r->faixa_preco; i++) my_cat(fp, "$");

    sprintf(buffer, "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]",
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, tipos, fp,
            r->horario_abertura.hora, r->horario_abertura.minuto,
            r->horario_fechamento.hora, r->horario_fechamento.minuto,
            r->data_abertura.dia, r->data_abertura.mes, r->data_abertura.ano,
            r->aberto ? "true" : "false");
}

// --- Coleção e Pilha ---
typedef struct { int tamanho; Restaurante **restaurantes; } Colecao;
typedef struct { int topo, capacidade; Restaurante **dados; } Pilha;

Colecao *ler_csv(const char *path) {
    FILE *f = fopen(path, "r");
    if (!f) return NULL;
    Colecao *col = malloc(sizeof(Colecao));
    int cap = 100;
    col->restaurantes = malloc(cap * sizeof(Restaurante *));
    col->tamanho = 0;
    char linha[1024];
    fgets(linha, sizeof(linha), f);
    while (fgets(linha, sizeof(linha), f)) {
        int len = my_len(linha);
        while (len > 0 && (linha[len-1] == '\n' || linha[len-1] == '\r')) linha[--len] = '\0';
        if (len == 0) continue;
        if (col->tamanho == cap) col->restaurantes = realloc(col->restaurantes, (cap *= 2) * sizeof(Restaurante *));
        col->restaurantes[col->tamanho++] = parse_restaurante(linha);
    }
    fclose(f);
    return col;
}

void pilha_criar(Pilha *p) {
    p->capacidade = 100;
    p->dados = malloc(p->capacidade * sizeof(Restaurante *));
    p->topo = -1;
}

void pilha_inserir(Pilha *p, Restaurante *r) {
    if (p->topo == p->capacidade - 1) p->dados = realloc(p->dados, (p->capacidade *= 2) * sizeof(Restaurante *));
    p->dados[++(p->topo)] = r;
}

Restaurante *pilha_remover(Pilha *p) {
    return (p->topo == -1) ? NULL : p->dados[(p->topo)--];
}

// --- Main ---
int main() {
    Colecao *col = ler_csv("/tmp/restaurantes.csv");
    if (!col) return 1;

    Pilha p; pilha_criar(&p);
    char entrada[MAX_STR];
    
    // 1. IDs iniciais até "FIM"
    while (scanf("%s", entrada) && strcmp(entrada, "FIM") != 0) {
        int id = atoi(entrada);
        for (int i = 0; i < col->tamanho; i++) {
            if (col->restaurantes[i]->id == id) {
                pilha_inserir(&p, col->restaurantes[i]);
                break;
            }
        }
    }

    // 2. Comandos dinâmicos
    int n; scanf("%d", &n);
    while (n--) {
        char cmd[10]; scanf("%s", cmd);
        if (cmd[0] == 'I') {
            int id; scanf("%d", &id);
            for (int i = 0; i < col->tamanho; i++) {
                if (col->restaurantes[i]->id == id) {
                    pilha_inserir(&p, col->restaurantes[i]);
                    break;
                }
            }
        } else if (cmd[0] == 'R') {
            Restaurante *r = pilha_remover(&p);
            if (r) printf("(R)%s\n", r->nome); // ERRO CORRIGIDO: Removido espaço
        }
    }

    // 3. Exibição final: Topo ao fundo (sem índice)
    for (int i = p.topo; i >= 0; i--) {
        char buf[1024];
        formatar_restaurante(p.dados[i], buf);
        printf("%s\n", buf); // ERRO CORRIGIDO: Removido [i]
    }

    return 0;
}