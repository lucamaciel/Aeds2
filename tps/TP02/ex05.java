import java.util.Scanner;
import java.io.*;

class Data {
    private int dia, mes, ano;

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
                id, nome, cidade, capacidade, avaliacao, tipos, fp,
                horarioAbertura.formatar(), horarioFechamento.formatar(), dataAbertura.formatar(), aberto);
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

public class ex05 {
    static long comparacoes = 0;

    // pesquisa sequencial por nome — retorna true se encontrado
    static boolean pesquisaSequencial(Restaurante[] v, int n, String nome) {
        for (int i = 0; i < n; i++) {
            comparacoes++;
            if (v[i].getNome().compareTo(nome) == 0)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Scanner sc = new Scanner(System.in);

        // --- Parte 1: ids -> preenche array base de pesquisa ---
        java.util.List<Integer> ids = new java.util.ArrayList<>();
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1)
                break;
            ids.add(id);
        }
        sc.nextLine(); // consome o \n apos o -1

        Restaurante[] base = new Restaurante[ids.size()];
        int n = 0;
        for (int id : ids) {
            Restaurante r = colecao.buscarPorId(id);
            if (r != null)
                base[n++] = r;
        }

        // --- Parte 2: nomes a pesquisar, termina em FIM ---
        StringBuilder saida = new StringBuilder();
        long t0 = System.nanoTime();
        while (sc.hasNextLine()) {
            String linha = sc.nextLine().trim();
            if (linha.equals("FIM"))
                break;
            boolean encontrado = pesquisaSequencial(base, n, linha);
            saida.append(encontrado ? "SIM" : "NAO").append("\n");
        }
        long t1 = System.nanoTime();
        double tempo = (t1 - t0) / 1e9;

        System.out.print(saida);

        // log
        try (PrintWriter log = new PrintWriter("888234_sequencial.txt")) {
            log.printf("888234\t%d\t%.6f%n", comparacoes, tempo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}