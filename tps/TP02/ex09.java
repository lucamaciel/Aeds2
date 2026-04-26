import java.util.Scanner;
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

public class ex09 {
    static long comparacoes = 0, movimentacoes = 0;

    // compara por data_abertura (ano->mes->dia), desempate: nome
    static int comparar(Restaurante a, Restaurante b) {
        comparacoes++;
        Data da = a.getDataAbertura(), db = b.getDataAbertura();
        if (da.getAno() != db.getAno())
            return da.getAno() - db.getAno();
        if (da.getMes() != db.getMes())
            return da.getMes() - db.getMes();
        if (da.getDia() != db.getDia())
            return da.getDia() - db.getDia();
        return a.getNome().compareTo(b.getNome());
    }

    static void heapify(Restaurante[] v, int n, int i) {
        int maior = i, esq = 2 * i + 1, dir = 2 * i + 2;
        if (esq < n && comparar(v[esq], v[maior]) > 0)
            maior = esq;
        if (dir < n && comparar(v[dir], v[maior]) > 0)
            maior = dir;
        if (maior != i) {
            Restaurante tmp = v[i];
            v[i] = v[maior];
            v[maior] = tmp;
            movimentacoes++;
            heapify(v, n, maior);
        }
    }

    static void heapSort(Restaurante[] v, int n) {
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(v, n, i);
        for (int i = n - 1; i > 0; i--) {
            Restaurante tmp = v[0];
            v[0] = v[i];
            v[i] = tmp;
            movimentacoes++;
            heapify(v, i, 0);
        }
    }

    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Scanner sc = new Scanner(System.in);
        java.util.List<Integer> ids = new java.util.ArrayList<>();
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1)
                break;
            ids.add(id);
        }
        Restaurante[] lista = new Restaurante[ids.size()];
        int n = 0;
        for (int id : ids) {
            Restaurante r = colecao.buscarPorId(id);
            if (r != null)
                lista[n++] = r;
        }
        long t0 = System.nanoTime();
        heapSort(lista, n);
        long t1 = System.nanoTime();
        double tempo = (t1 - t0) / 1e9;
        for (int i = 0; i < n; i++)
            System.out.println(lista[i].formatar());
        try (PrintWriter log = new PrintWriter("888234_heapsort.txt")) {
            log.printf("888234\t%d\t%d\t%.6f%n", comparacoes, movimentacoes, tempo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.close();
    }
}
