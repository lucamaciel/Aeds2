public import java.util.Scanner;
import java.io.*;

class Data {
    private int dia, mes, ano;

    public Data(int d, int m, int a) {
        dia = d;
        mes = m;
        ano = a;
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

    public static Data parseData(String s) {
        String[] p = s.split("-");
        return new Data(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0]));
    }

    public String formatar() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

class Hora {
    private int hora, minuto;

    private Hora(int h, int m) {
        hora = h;
        minuto = m;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public static Hora parseHora(String s) {
        String[] p = s.split(":");
        return new Hora(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }

    public String formatar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}

class Restaurante {
    private int id;
    private String nome, cidade;
    private int capacidade;
    private double avaliacao;
    private String[] tipoCozinha;
    private int faixaPreco;
    private Hora horarioAbertura, horarioFechamento;
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

    public String formatar() {
        StringBuilder t = new StringBuilder("[");
        for (int i = 0; i < tipoCozinha.length; i++) {
            if (i > 0)
                t.append(",");
            t.append(tipoCozinha[i]);
        }
        t.append("]");
        String fp = "";
        for (int i = 0; i < faixaPreco; i++)
            fp += "$";
        return String.format("[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %b]",
                id, nome, cidade, capacidade, avaliacao, t, fp, horarioAbertura.formatar(),
                horarioFechamento.formatar(), dataAbertura.formatar(), aberto);
    }
}

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
            boolean cab = true;
            while (sc.hasNextLine()) {
                String l = sc.nextLine().trim();
                if (cab) {
                    cab = false;
                    continue;
                }
                if (l.isEmpty())
                    continue;
                if (tamanho == restaurantes.length) {
                    Restaurante[] n = new Restaurante[restaurantes.length * 2];
                    for (int i = 0; i < tamanho; i++)
                        n[i] = restaurantes[i];
                    restaurantes = n;
                }
                restaurantes[tamanho++] = Restaurante.parseRestaurante(l);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nao encontrado: " + path);
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
// Fila Circular de tamanho 5
// ============================================================
class FilaCircular {
    private static final int TAMANHO = 5;
    private Restaurante[] elementos;
    private int inicio, fim, quantidade;

    public FilaCircular() {
        elementos = new Restaurante[TAMANHO];
        inicio = 0;
        fim = 0;
        quantidade = 0;
    }

    public boolean cheia() {
        return quantidade == TAMANHO;
    }

    public boolean vazia() {
        return quantidade == 0;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // desenfileira e retorna o removido
    public Restaurante desenfileirar() {
        if (vazia())
            return null;
        Restaurante r = elementos[inicio];
        elementos[inicio] = null;
        inicio = (inicio + 1) % TAMANHO;
        quantidade--;
        return r;
    }

    // enfileira; se cheia, remove antes (conforme enunciado)
    public Restaurante enfileirar(Restaurante r) {
        Restaurante removido = null;
        if (cheia())
            removido = desenfileirar();
        elementos[fim] = r;
        fim = (fim + 1) % TAMANHO;
        quantidade++;
        return removido; // pode ser null se nao havia remocao
    }

    // media arredondada do ano de abertura dos elementos na fila
    public int mediaAnoAbertura() {
        if (quantidade == 0)
            return 0;
        long soma = 0;
        int idx = inicio;
        for (int i = 0; i < quantidade; i++) {
            soma += elementos[idx].getDataAbertura().getAno();
            idx = (idx + 1) % TAMANHO;
        }
        return (int) Math.round((double) soma / quantidade);
    }

    // itera do primeiro ao ultimo (inicio -> fim)
    public Restaurante get(int i) {
        return elementos[(inicio + i) % TAMANHO];
    }
}

public class Main {
    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Scanner sc = new Scanner(System.in);

        FilaCircular fila = new FilaCircular();

        // --- Parte 1: ids -> insere na fila ---
        while (sc.hasNextInt()) {
            int id = sc.nextInt(); if (id == -1) break;
            Restaurante r = colecao.buscarPorId(id);
            if (r != null) {
                Restaurante removido = fila.enfileirar(r);
                if (removido != null) System.out.println("(R) " + removido.getNome());
                System.out.println("(I) " + fila.mediaAnoAbertura());
            }
        }
        if (sc.hasNextLine()) sc.nextLine();

        // --- Parte 2: n comandos ---
        int n = Integer.parseInt(sc.nextLine().trim());
        for (int c = 0; c < n; c++) {
            String linha = sc.nextLine().trim();
            String[] partes = linha.split(" ");
            String cmd = partes[0];

            if (cmd.equals("I")) {
                int id = Integer.parseInt(partes[1]);
                Restaurante r = colecao.buscarPorId(id);
                if (r != null) {
                    Restaurante removido = fila.enfileirar(r);
                    if (removido != null) System.out.println("(R) " + removido.getNome());
                    System.out.println("(I) " + fila.mediaAnoAbertura());
                }
            } else if (cmd.equals("R")) {
                Restaurante r = fila.desenfileirar();
                if (r != null) System.out.println("(R) " + r.getNome());
            }
        }

        // mostra fila do primeiro ao ultimo
        for (int i = 0; i < fila.getQuantidade(); i++)
            System.out.println(fila.get(i).formatar());

        sc.close();
    }
} {
    
}
