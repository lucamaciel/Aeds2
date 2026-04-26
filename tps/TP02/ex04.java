import java.util.Scanner;
import java.io.*;

// ============================================================
// Tipo Data
// ============================================================
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

// ============================================================
// Tipo Hora
// ============================================================
class Hora {
    private int hora, minuto;

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

    public static Hora parseHora(String s) {
        String[] p = s.split(":");
        return new Hora(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }

    public String formatar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}

// ============================================================
// Tipo Restaurante
// ============================================================
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
    private static final int CAP = 100;

    public ColecaoRestaurantes() {
        restaurantes = new Restaurante[CAP];
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
                    Restaurante[] novo = new Restaurante[restaurantes.length * 2];
                    for (int i = 0; i < tamanho; i++)
                        novo[i] = restaurantes[i];
                    restaurantes = novo;
                }
                restaurantes[tamanho++] = Restaurante.parseRestaurante(l);
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
// Main — Insertion Sort por cidade
// ============================================================
public class ex04 {
    static long comparacoes = 0;
    static long movimentacoes = 0;

    static void insertionSort(Restaurante[] v, int n) {
        for (int i = 1; i < n; i++) {
            Restaurante chave = v[i];
            int j = i - 1;
            while (j >= 0) {
                comparacoes++;
                if (v[j].getCidade().compareTo(chave.getCidade()) > 0) {
                    v[j + 1] = v[j];
                    movimentacoes++;
                    j--;
                } else
                    break;
            }
            v[j + 1] = chave;
        }
    }

    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Scanner sc = new Scanner(System.in);

        // leitura de ids
        java.util.List<Integer> ids = new java.util.ArrayList<>();
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1)
                break;
            ids.add(id);
        }

        // monta array dos restaurantes pedidos
        Restaurante[] lista = new Restaurante[ids.size()];
        int n = 0;
        for (int id : ids) {
            Restaurante r = colecao.buscarPorId(id);
            if (r != null)
                lista[n++] = r;
        }

        long t0 = System.nanoTime();
        insertionSort(lista, n);
        long t1 = System.nanoTime();
        double tempo = (t1 - t0) / 1e9;

        for (int i = 0; i < n; i++)
            System.out.println(lista[i].formatar());

        // log
        try (PrintWriter log = new PrintWriter("888234_insercao.txt")) {
            log.printf("888234\t%d\t%d\t%.6f%n", comparacoes, movimentacoes, tempo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}