import java.util.Scanner;
import java.io.*;

class Data {
    private int dia, mes, ano;

    public Data(int d, int m, int a) {
        dia = d;
        mes = m;
        ano = a;
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

    public Data getDataAbertura() {
        return dataAbertura;
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
                id, nome, cidade, capacidade, avaliacao, t, fp,
                horarioAbertura.formatar(), horarioFechamento.formatar(),
                dataAbertura.formatar(), aberto);
    }
}

class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    public ColecaoRestaurantes() {
        restaurantes = new Restaurante[100];
        tamanho = 0;
    }

    public void lerCsv(String path) {
        try {
            Scanner sc = new Scanner(new File(path));
            sc.nextLine(); // pula cabeçalho

            while (sc.hasNextLine()) {
                String l = sc.nextLine().trim();
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
            sc.close();
        } catch (Exception e) {
            System.err.println("Erro ao ler CSV");
        }
    }

    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes c = new ColecaoRestaurantes();
        c.lerCsv("/tmp/restaurantes.csv");
        return c;
    }

    public Restaurante buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (restaurantes[i].getId() == id)
                return restaurantes[i];
        }
        return null;
    }
}

// Fila Circular tamanho 5
class FilaCircular {
    private static final int TAM = 5;
    private Restaurante[] array;
    private int inicio, fim, qtd;

    public FilaCircular() {
        array = new Restaurante[TAM];
        inicio = fim = qtd = 0;
    }

    public Restaurante enfileirar(Restaurante r) {
        Restaurante removido = null;

        if (qtd == TAM) {
            removido = desenfileirar();
        }

        array[fim] = r;
        fim = (fim + 1) % TAM;
        qtd++;

        return removido;
    }

    public Restaurante desenfileirar() {
        if (qtd == 0)
            return null;

        Restaurante r = array[inicio];
        inicio = (inicio + 1) % TAM;
        qtd--;

        return r;
    }

    public int mediaAno() {
        if (qtd == 0)
            return 0;

        int soma = 0;
        int i = inicio;

        for (int j = 0; j < qtd; j++) {
            soma += array[i].getDataAbertura().getAno();
            i = (i + 1) % TAM;
        }

        return (int) Math.round((double) soma / qtd);
    }

    public Restaurante get(int i) {
        return array[(inicio + i) % TAM];
    }

    public int tamanho() {
        return qtd;
    }
}

public class ex13 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ColecaoRestaurantes base = ColecaoRestaurantes.lerCsv();
        FilaCircular fila = new FilaCircular();

        // Entrada inicial
        while (true) {
            int id = sc.nextInt();
            if (id == -1)
                break;

            Restaurante r = base.buscarPorId(id);
            if (r != null) {
                Restaurante removido = fila.enfileirar(r);

                if (removido != null)
                    System.out.println("(R) " + removido.getNome());

                System.out.println("(I) " + fila.mediaAno());
            }
        }

        sc.nextLine();

        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            String[] cmd = sc.nextLine().split("\\s+");

            if (cmd[0].equals("I")) {
                Restaurante r = base.buscarPorId(Integer.parseInt(cmd[1]));

                if (r != null) {
                    Restaurante removido = fila.enfileirar(r);

                    if (removido != null)
                        System.out.println("(R) " + removido.getNome());

                    System.out.println("(I) " + fila.mediaAno());
                }

            } else {
                Restaurante r = fila.desenfileirar();
                if (r != null)
                    System.out.println("(R) " + r.getNome());
            }
        }

        // saída final
        for (int i = 0; i < fila.tamanho(); i++) {
            System.out.println(fila.get(i).formatar());
        }

        sc.close();
    }
}