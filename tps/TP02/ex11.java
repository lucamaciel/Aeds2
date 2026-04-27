
// Bloco base de tipos — igual ao exercicio 1 (Java), copiado em cada exercicio
// imports permitidos pelo enunciado: Scanner, String.format, compareTo
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// ============================================================
// Tipo Data
// ============================================================
class Data {
    private int dia;
    private int mes;
    private int ano;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    // recebe "YYYY-MM-DD", retorna objeto Data
    public static Data parseData(String s) {
        String[] p = s.split("-");
        return new Data(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0]));
    }

    // retorna "DD/MM/YYYY"
    public String formatar() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

// ============================================================
// Tipo Hora
// ============================================================
class Hora {
    private int hora;
    private int minuto;

    private Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    // recebe "HH:mm", retorna objeto Hora
    public static Hora parseHora(String s) {
        String[] p = s.split(":");
        return new Hora(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }

    // retorna "HH:mm"
    public String formatar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}

// ============================================================
// Tipo Restaurante
// ============================================================
class Restaurante {
    private int id;
    private String nome;
    private String cidade;
    private int capacidade;
    private double avaliacao;
    private String[] tipoCozinha;
    private int faixaPreco;
    private Hora horarioAbertura;
    private Hora horarioFechamento;
    private Data dataAbertura;
    private boolean aberto;

    private Restaurante() {
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public String[] getTiposCozinha() {
        return tipoCozinha;
    }

    public int getFaixaPreco() {
        return faixaPreco;
    }

    public Hora getHorarioAbertura() {
        return horarioAbertura;
    }

    public Hora getHorarioFechamento() {
        return horarioFechamento;
    }

    public Data getDataAbertura() {
        return dataAbertura;
    }

    public boolean getAberto() {
        return aberto;
    }

    // recebe linha CSV, retorna objeto Restaurante
    public static Restaurante parseRestaurante(String s) {
        String[] c = s.split(",");
        Restaurante r = new Restaurante();
        r.id = Integer.parseInt(c[0].trim());
        r.nome = c[1].trim();
        r.cidade = c[2].trim();
        r.capacidade = Integer.parseInt(c[3].trim());
        r.avaliacao = Double.parseDouble(c[4].trim());
        r.tipoCozinha = c[5].trim().split(";");
        r.faixaPreco = c[6].trim().length();
        String[] hor = c[7].trim().split("-");
        r.horarioAbertura = Hora.parseHora(hor[0]);
        r.horarioFechamento = Hora.parseHora(hor[1]);
        r.dataAbertura = Data.parseData(c[8].trim());
        r.aberto = Boolean.parseBoolean(c[9].trim());
        return r;
    }

    // retorna string no formato do enunciado
    public String formatar() {
        StringBuilder tipos = new StringBuilder("[");
        for (int i = 0; i < tipoCozinha.length; i++) {
            if (i > 0)
                tipos.append(",");
            tipos.append(tipoCozinha[i]);
        }
        tipos.append("]");

        String fp = "";
        for (int i = 0; i < faixaPreco; i++)
            fp += "$";

        return String.format("[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %b]",
                id, nome, cidade, capacidade, avaliacao,
                tipos, fp,
                horarioAbertura.formatar(), horarioFechamento.formatar(),
                dataAbertura.formatar(), aberto);
    }
}

// ============================================================
// ColecaoRestaurantes
// ============================================================
class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    public ColecaoRestaurantes() {
        restaurantes = new Restaurante[100];
        tamanho = 0;
    }

    public int getTamanho() {
        return tamanho;
    }

    public Restaurante[] getRestaurantes() {
        return restaurantes;
    }

    public void lerCsv(String path) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(path));
            boolean cabecalho = true;
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }
                if (linha.isEmpty())
                    continue;
                // expande o array se necessario
                if (tamanho == restaurantes.length) {
                    Restaurante[] novo = new Restaurante[restaurantes.length * 2];
                    for (int i = 0; i < tamanho; i++)
                        novo[i] = restaurantes[i];
                    restaurantes = novo;
                }
                restaurantes[tamanho++] = Restaurante.parseRestaurante(linha);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo nao encontrado: " + path);
        } finally {
            if (sc != null)
                sc.close();
        }
    }

    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes c = new ColecaoRestaurantes();
        c.lerCsv("/tmp/restaurantes.csv");
        return c;
    }

    public Restaurante buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++)
            if (restaurantes[i].getId() == id)
                return restaurantes[i];
        return null;
    }
}

// ============================================================
// Lista com Alocacao Sequencial de Restaurantes
// baseada na lista de inteiros vista em aula, adaptada para Restaurante
// ============================================================
class Lista {
    private int tamanho;
    private int capacidade;
    private Restaurante[] elementos;

    public Lista(int capacidade) {
        this.capacidade = capacidade;
        this.elementos = new Restaurante[capacidade];
        this.tamanho = 0;
    }

    public int getTamanho() {
        return tamanho;
    }

    // expande o array interno se necessario
    private void crescer() {
        if (tamanho < capacidade)
            return;
        capacidade *= 2;
        Restaurante[] novo = new Restaurante[capacidade];
        for (int i = 0; i < tamanho; i++)
            novo[i] = elementos[i];
        elementos = novo;
    }

    // insere na primeira posicao, remaneja os demais
    public void inserirInicio(Restaurante r) {
        crescer();
        for (int i = tamanho; i > 0; i--)
            elementos[i] = elementos[i - 1];
        elementos[0] = r;
        tamanho++;
    }

    // insere na posicao informada (posicao < n), remaneja os demais
    public void inserir(Restaurante r, int posicao) {
        crescer();
        for (int i = tamanho; i > posicao; i--)
            elementos[i] = elementos[i - 1];
        elementos[posicao] = r;
        tamanho++;
    }

    // insere na ultima posicao
    public void inserirFim(Restaurante r) {
        crescer();
        elementos[tamanho++] = r;
    }

    // remove e retorna o primeiro, remaneja os demais
    public Restaurante removerInicio() {
        if (tamanho == 0)
            return null;
        Restaurante r = elementos[0];
        for (int i = 0; i < tamanho - 1; i++)
            elementos[i] = elementos[i + 1];
        elementos[--tamanho] = null;
        return r;
    }

    // remove e retorna o elemento na posicao informada, remaneja os demais
    public Restaurante remover(int posicao) {
        if (posicao < 0 || posicao >= tamanho)
            return null;
        Restaurante r = elementos[posicao];
        for (int i = posicao; i < tamanho - 1; i++)
            elementos[i] = elementos[i + 1];
        elementos[--tamanho] = null;
        return r;
    }

    // remove e retorna o ultimo
    public Restaurante removerFim() {
        if (tamanho == 0)
            return null;
        Restaurante r = elementos[--tamanho];
        elementos[tamanho] = null;
        return r;
    }

    public Restaurante get(int i) {
        return elementos[i];
    }
}

// ============================================================
// Main — Exercicio 11: Lista com Alocacao Sequencial
// ============================================================
public class ex11 {
    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Scanner sc = new Scanner(System.in);

        // --- Parte 1: ids ate -1 — insere ao fim da lista ---
        Lista lista = new Lista(100);
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1)
                break;
            Restaurante r = colecao.buscarPorId(id);
            if (r != null)
                lista.inserirFim(r);
        }
        if (sc.hasNextLine())
            sc.nextLine(); // consome \n apos -1

        // --- Parte 2: n comandos de insercao/remocao ---
        int n = Integer.parseInt(sc.nextLine().trim());
        for (int c = 0; c < n; c++) {
            String linha = sc.nextLine().trim();
            String[] partes = linha.split(" ");
            String cmd = partes[0];

            if (cmd.compareTo("II") == 0) {
                // inserir no inicio
                int id = Integer.parseInt(partes[1]);
                Restaurante r = colecao.buscarPorId(id);
                if (r != null)
                    lista.inserirInicio(r);

            } else if (cmd.compareTo("I*") == 0) {
                // inserir na posicao informada
                int pos = Integer.parseInt(partes[1]);
                int id = Integer.parseInt(partes[2]);
                Restaurante r = colecao.buscarPorId(id);
                if (r != null)
                    lista.inserir(r, pos);

            } else if (cmd.compareTo("IF") == 0) {
                // inserir no fim
                int id = Integer.parseInt(partes[1]);
                Restaurante r = colecao.buscarPorId(id);
                if (r != null)
                    lista.inserirFim(r);

            } else if (cmd.compareTo("RI") == 0) {
                // remover do inicio
                Restaurante r = lista.removerInicio();
                if (r != null)
                    System.out.println("(R)" + r.getNome());

            } else if (cmd.compareTo("R*") == 0) {
                // remover da posicao informada
                int pos = Integer.parseInt(partes[1]);
                Restaurante r = lista.remover(pos);
                if (r != null)
                    System.out.println("(R)" + r.getNome());

            } else if (cmd.compareTo("RF") == 0) {
                // remover do fim
                Restaurante r = lista.removerFim();
                if (r != null)
                    System.out.println("(R)" + r.getNome());
            }
        }

        // exibe todos os registros da lista do primeiro ao ultimo
        for (int i = 0; i < lista.getTamanho(); i++)
            System.out.println(lista.get(i).formatar());

        sc.close();
    }
}