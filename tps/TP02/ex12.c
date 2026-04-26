#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int my_len(const char *s) {
    int i = 0;
    while (s && s[i]) i++;
    return i;
}

void my_zero(void *ptr, int n) {
    char *p = (char *)ptr;
    for (int i = 0; i < n; i++) p[i] = 0;
}

void str_dup(char **dst, const char *src) {
    int len = my_len(src);
    *dst = (char *)malloc(len + 1);
    for (int i = 0; i <= len; i++) (*dst)[i] = src[i];
}

void prox_campo(const char *src, int *pos, char delim, char *dst, int dst_sz) {
    int i = 0;
    while (src[*pos] != '\0' && src[*pos] != delim) {
        if (i < dst_sz - 1) dst[i++] = src[(*pos)];
        (*pos)++;
    }
    dst[i] = '\0';
    if (src[*pos] == delim) (*pos)++;
}

typedef struct { int ano, mes, dia; } Data;
typedef struct { int hora, minuto; } Hora;

typedef struct {
    int id, capacidade, n_tipos_cozinha, faixa_preco, aberto;
    char *nome, *cidade, **tipos_cozinha;
    double avaliacao;
    Hora horario_abertura, horario_fechamento;
    Data data_abertura;
} Restaurante;

Restaurante* mapa_id[20000];

Restaurante *parse_restaurante(char *s) {
    Restaurante *r = (Restaurante *)malloc(sizeof(Restaurante));
    my_zero(r, sizeof(Restaurante));
    int pos = 0;
    char tmp[512];

    prox_campo(s, &pos, ',', tmp, 512); r->id = atoi(tmp);
    prox_campo(s, &pos, ',', tmp, 512); str_dup(&r->nome, tmp);
    prox_campo(s, &pos, ',', tmp, 512); str_dup(&r->cidade, tmp);
    prox_campo(s, &pos, ',', tmp, 512); r->capacidade = atoi(tmp);
    prox_campo(s, &pos, ',', tmp, 512); r->avaliacao = atof(tmp);

    prox_campo(s, &pos, ',', tmp, 512);
    r->tipos_cozinha = (char **)malloc(10 * sizeof(char *));
    int tpos = 0, n = 0;
    while (tmp[tpos] != '\0') {
        char tbuf[128];
        int tp = 0;
        while(tmp[tpos] != ';' && tmp[tpos] != '\0') tbuf[tp++] = tmp[tpos++];
        tbuf[tp] = '\0';
        str_dup(&r->tipos_cozinha[n++], tbuf);
        if(tmp[tpos] == ';') tpos++;
    }
    r->n_tipos_cozinha = n;

    prox_campo(s, &pos, ',', tmp, 512); r->faixa_preco = my_len(tmp);
    prox_campo(s, &pos, ',', tmp, 512);
    sscanf(tmp, "%d:%d-%d:%d", &r->horario_abertura.hora, &r->horario_abertura.minuto, 
                               &r->horario_fechamento.hora, &r->horario_fechamento.minuto);
    prox_campo(s, &pos, ',', tmp, 512);
    sscanf(tmp, "%d-%d-%d", &r->data_abertura.ano, &r->data_abertura.mes, &r->data_abertura.dia);
    prox_campo(s, &pos, ',', tmp, 512);
    r->aberto = (tmp[0] == 't');

    return r;
}

int main() {
    FILE *f = fopen("/tmp/restaurantes.csv", "r");
    if (!f) return 1;

    char linha[1024];
    fgets(linha, sizeof(linha), f);
    while (fgets(linha, sizeof(linha), f)) {
        int len = my_len(linha);
        while (len > 0 && (linha[len-1] == '\n' || linha[len-1] == '\r')) linha[--len] = '\0';
        if (len == 0) continue;
        Restaurante *r = parse_restaurante(linha);
        mapa_id[r->id] = r;
    }
    fclose(f);

    Restaurante **pilha = malloc(2000 * sizeof(Restaurante *));
    int topo = -1;

    char entrada[100];
    while (scanf("%s", entrada) && strcmp(entrada, "FIM") != 0) {
        int id = atoi(entrada);
        if (mapa_id[id]) pilha[++topo] = mapa_id[id];
    }

    int n;
    scanf("%d", &n);
    while (n--) {
        char cmd[10];
        scanf("%s", cmd);
        if (cmd[0] == 'I') {
            int id;
            scanf("%d", &id);
            if (mapa_id[id]) pilha[++topo] = mapa_id[id];
        } else if (cmd[0] == 'R') {
            if (topo > -1) {
                printf("(R)%s\n", pilha[topo--]->nome);
            }
        }
    }

    for (int i = topo; i >= 0; i--) {
        Restaurante *r = pilha[i];
        printf("[%d ## %s ## %s ## %d ## %.1f ## [", r->id, r->nome, r->cidade, r->capacidade, r->avaliacao);
        for(int j=0; j<r->n_tipos_cozinha; j++) {
            printf("%s%s", r->tipos_cozinha[j], j == r->n_tipos_cozinha - 1 ? "" : ", ");
        }
        printf("] ## ");
        for(int j=0; j<r->faixa_preco; j++) printf("$");
        printf(" ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]\n", 
            r->horario_abertura.hora, r->horario_abertura.minuto,
            r->horario_fechamento.hora, r->horario_fechamento.minuto,
            r->data_abertura.dia, r->data_abertura.mes, r->data_abertura.ano,
            r->aberto ? "true" : "false");
    }

    return 0;
}