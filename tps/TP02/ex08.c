#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

static int my_len(const char *s){int i=0;while(s[i])i++;return i;}
static void my_cat(char *d,const char *s){int i=my_len(d),j=0;while(s[j])d[i++]=s[j++];d[i]='\0';}
static void my_zero(void *p,int n){char *c=(char*)p;for(int i=0;i<n;i++)c[i]=0;}
static void prox_campo(const char *src,int *pos,char delim,char *dst,int sz){
    int i=0;while(src[*pos]==' ')(*pos)++;
    while(src[*pos]&&src[*pos]!=delim){if(i<sz-1)dst[i++]=src[(*pos)];(*pos)++;}
    dst[i]='\0';if(src[*pos]==delim)(*pos)++;
}

typedef struct{int ano,mes,dia;}Data;
Data parse_data(char *s){Data d;sscanf(s,"%d-%d-%d",&d.ano,&d.mes,&d.dia);return d;}
void formatar_data(Data *d,char *b){sprintf(b,"%02d/%02d/%04d",d->dia,d->mes,d->ano);}
typedef struct{int hora,minuto;}Hora;
Hora parse_hora(char *s){Hora h;sscanf(s,"%d:%d",&h.hora,&h.minuto);return h;}
void formatar_hora(Hora *h,char *b){sprintf(b,"%02d:%02d",h->hora,h->minuto);}

#define MAX_TIPOS 10
#define MAX_STR   256

typedef struct{
    int id;char nome[MAX_STR],cidade[MAX_STR];int capacidade;double avaliacao;
    int n_tipos_cozinha;char tipos_cozinha[MAX_TIPOS][MAX_STR];
    int faixa_preco;Hora horario_abertura,horario_fechamento;Data data_abertura;int aberto;
}Restaurante;

Restaurante *parse_restaurante(char *s){
    Restaurante *r=(Restaurante*)malloc(sizeof(Restaurante));my_zero(r,sizeof(Restaurante));
    int pos=0;char tmp[MAX_STR*MAX_TIPOS];
    prox_campo(s,&pos,',',tmp,sizeof(tmp));sscanf(tmp,"%d",&r->id);
    prox_campo(s,&pos,',',r->nome,MAX_STR);prox_campo(s,&pos,',',r->cidade,MAX_STR);
    prox_campo(s,&pos,',',tmp,sizeof(tmp));sscanf(tmp,"%d",&r->capacidade);
    prox_campo(s,&pos,',',tmp,sizeof(tmp));sscanf(tmp,"%lf",&r->avaliacao);
    prox_campo(s,&pos,',',tmp,sizeof(tmp));
    r->n_tipos_cozinha=0;int tpos=0;
    while(tmp[tpos]&&r->n_tipos_cozinha<MAX_TIPOS)
        prox_campo(tmp,&tpos,';',r->tipos_cozinha[r->n_tipos_cozinha++],MAX_STR);
    prox_campo(s,&pos,',',tmp,sizeof(tmp));r->faixa_preco=my_len(tmp);
    prox_campo(s,&pos,',',tmp,sizeof(tmp));
    char ab[6],fech[6];int hi=0,hj=0;
    while(tmp[hi]!='-'&&tmp[hi]){if(hj<5)ab[hj++]=tmp[hi];hi++;}ab[hj]='\0';
    if(tmp[hi]=='-'){hi++;}hj=0;
    while(tmp[hi]){if(hj<5)fech[hj++]=tmp[hi];hi++;}fech[hj]='\0';
    r->horario_abertura=parse_hora(ab);r->horario_fechamento=parse_hora(fech);
    prox_campo(s,&pos,',',tmp,sizeof(tmp));r->data_abertura=parse_data(tmp);
    prox_campo(s,&pos,',',tmp,sizeof(tmp));r->aberto=(strcmp(tmp,"true")==0)?1:0;
    return r;
}

void formatar_restaurante(Restaurante *r,char *buffer){
    char tipos[MAX_STR*MAX_TIPOS];tipos[0]='[';tipos[1]='\0';
    for(int i=0;i<r->n_tipos_cozinha;i++){if(i>0)my_cat(tipos,",");my_cat(tipos,r->tipos_cozinha[i]);}
    my_cat(tipos,"]");
    char fp[8];fp[0]='\0';for(int i=0;i<r->faixa_preco;i++)my_cat(fp,"$");
    char ba[6],bf[6],bd[11];
    formatar_hora(&r->horario_abertura,ba);formatar_hora(&r->horario_fechamento,bf);
    formatar_data(&r->data_abertura,bd);
    sprintf(buffer,"[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
        r->id,r->nome,r->cidade,r->capacidade,r->avaliacao,tipos,fp,ba,bf,bd,r->aberto?"true":"false");
}

typedef struct{int tamanho;Restaurante **restaurantes;}Colecao_Restaurantes;
void ler_csv_colecao(Colecao_Restaurantes *col,char *path){
    FILE *f=fopen(path,"r");if(!f){fprintf(stderr,"Nao encontrado: %s\n",path);return;}
    int cap=100;col->restaurantes=(Restaurante**)malloc(cap*sizeof(Restaurante*));col->tamanho=0;
    char linha[1024];int cab=1;
    while(fgets(linha,sizeof(linha),f)){
        int len=my_len(linha);
        while(len>0&&(linha[len-1]=='\n'||linha[len-1]=='\r'))linha[--len]='\0';
        if(cab){cab=0;continue;}if(len==0)continue;
        if(col->tamanho==cap){cap*=2;col->restaurantes=(Restaurante**)realloc(col->restaurantes,cap*sizeof(Restaurante*));}
        col->restaurantes[col->tamanho++]=parse_restaurante(linha);
    }
    fclose(f);
}
Colecao_Restaurantes *ler_csv(){
    Colecao_Restaurantes *col=(Colecao_Restaurantes*)malloc(sizeof(Colecao_Restaurantes));
    ler_csv_colecao(col,"/tmp/restaurantes.csv");return col;
}

// ============================================================
// Quicksort por avaliacao (crescente), desempate: nome
// ============================================================
static long long comparacoes=0, movimentacoes=0;

static int comparar(Restaurante *a, Restaurante *b){
    comparacoes++;
    if(a->avaliacao < b->avaliacao) return -1;
    if(a->avaliacao > b->avaliacao) return  1;
    return strcmp(a->nome, b->nome);
}

static void trocar(Restaurante **v,int i,int j){
    Restaurante *t=v[i];v[i]=v[j];v[j]=t;movimentacoes++;
}

void quicksort(Restaurante **v,int esq,int dir){
    if(esq>=dir) return;
    // pivo: mediana de tres
    int meio=(esq+dir)/2;
    if(comparar(v[esq],v[meio])>0) trocar(v,esq,meio);
    if(comparar(v[esq],v[dir])>0)  trocar(v,esq,dir);
    if(comparar(v[meio],v[dir])>0) trocar(v,meio,dir);
    // pivo esta em meio; coloca no dir-1
    Restaurante *pivo=v[meio]; trocar(v,meio,dir-1);
    int i=esq, j=dir-1;
    if(dir-esq<2){ // particao de 2 ou 3 elementos, ordena direto
        // ja ordenado acima; apenas verifica extremo
        if(comparar(v[esq],v[dir])>0) trocar(v,esq,dir);
        return;
    }
    // particao
    while(1){
        while(comparar(v[++i],pivo)<0);
        while(comparar(v[--j],pivo)>0);
        if(i>=j) break;
        trocar(v,i,j);
    }
    trocar(v,i,dir-1); // restaura pivo
    quicksort(v,esq,i-1);
    quicksort(v,i+1,dir);
}

int main(){
    Colecao_Restaurantes *colecao=ler_csv();
    int ids[10000],n_ids=0,id;
    while(scanf("%d",&id)==1){if(id==-1)break;ids[n_ids++]=id;}
    Restaurante **lista=(Restaurante**)malloc(n_ids*sizeof(Restaurante*));
    int n=0;
    for(int i=0;i<n_ids;i++)
        for(int j=0;j<colecao->tamanho;j++)
            if(colecao->restaurantes[j]->id==ids[i]){lista[n++]=colecao->restaurantes[j];break;}

    struct timespec t0,t1;
    clock_gettime(CLOCK_MONOTONIC,&t0);
    if(n>1) quicksort(lista,0,n-1);
    clock_gettime(CLOCK_MONOTONIC,&t1);
    double tempo=(t1.tv_sec-t0.tv_sec)+(t1.tv_nsec-t0.tv_nsec)/1e9;

    for(int i=0;i<n;i++){char buf[2048];formatar_restaurante(lista[i],buf);printf("%s\n",buf);}

    FILE *log=fopen("888234_quicksort.txt","w");
    if(log){fprintf(log,"888234\t%lld\t%lld\t%.6f\n",comparacoes,movimentacoes,tempo);fclose(log);}

    free(lista);
    for(int i=0;i<colecao->tamanho;i++)free(colecao->restaurantes[i]);
    free(colecao->restaurantes);free(colecao);
    return 0;
}
