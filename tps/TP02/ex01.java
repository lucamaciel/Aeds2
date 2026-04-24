import java.util.Scanner;

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
        // s = "YYYY-MM-DD"
        // partes[0]=YYYY partes[1]=MM partes[2]=DD
        String[] partes = s.split("-");
        return new Data(
                Integer.parseInt(partes[2]), // dia
                Integer.parseInt(partes[1]), // mes
                Integer.parseInt(partes[0]) // ano
        );
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
        return new Hora(
                Integer.parseInt(p[0]),
                Integer.parseInt(p[1]));
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

    // CSV:
    // id,nome,cidade,capacidade,avaliacao,tipos_cozinha,faixa_preco,horario,data_abertura,aberto
    // c[0] c[1] c[2] c[3] c[4] c[5] c[6] c[7] c[8] c[9]
    public static Restaurante parseRestaurante(String s) {
        String[] c = s.split(",");

        Restaurante r = new Restaurante();
        r.id = Integer.parseInt(c[0].trim());
        r.nome = c[1].trim();
        r.cidade = c[2].trim();
        r.capacidade = Integer.parseInt(c[3].trim());
        r.avaliacao = Double.parseDouble(c[4].trim());
        r.tipoCozinha = c[5].trim().split(";");
        r.faixaPreco = c[6].trim().length(); // conta os '$': $=1, $$=2, etc.

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
// Coleção de Restaurantes
// ============================================================
class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    private static final int CAPACIDADE_INICIAL = 100;

    public ColecaoRestaurantes() {
        restaurantes = new Restaurante[CAPACIDADE_INICIAL];
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
            sc = new Scanner(new java.io.File(path));
            boolean cabecalho = true;
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }
                if (linha.isEmpty())
                    continue;

                if (tamanho == restaurantes.length) {
                    Restaurante[] novo = new Restaurante[restaurantes.length * 2];
                    for (int i = 0; i < tamanho; i++)
                        novo[i] = restaurantes[i];
                    restaurantes = novo;
                }
                restaurantes[tamanho++] = Restaurante.parseRestaurante(linha);
            }
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Arquivo nao encontrado: " + path);
        } finally {
            if (sc != null)
                sc.close();
        }
    }

    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes col = new ColecaoRestaurantes();
        col.lerCsv("/tmp/restaurantes.csv");
        return col;
    }

    public Restaurante buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (restaurantes[i].getId() == id)
                return restaurantes[i];
        }
        return null;
    }
}

public class ex01 {
    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1)
                break;
            Restaurante r = colecao.buscarPorId(id);
            if (r != null)
                System.out.println(r.formatar());
        }
        sc.close();
    }
}