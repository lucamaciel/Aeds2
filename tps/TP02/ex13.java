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
    private Hora abertura, fechamento;
    private Data data;
    private boolean aberto;

    private Restaurante() {
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Data getData() {
        return data;
    }

    public static Restaurante parse(String s) {
        String[] c = s.split(",");
        Restaurante r = new Restaurante();

        r.id = Integer.parseInt(c[0].trim());
        r.nome = c[1].trim();
        r.cidade = c[2].trim();
        r.capacidade = Integer.parseInt(c[3].trim());
        r.avaliacao = Double.parseDouble(c[4].trim());
        r.tipoCozinha = c[5].trim().split(";");
        r.faixaPreco = c[6].trim().length();

        String[] h = c[7].trim().split("-");
        r.abertura = Hora.parseHora(h[0]);
        r.fechamento = Hora.parseHora(h[1]);

        r.data = Data.parseData(c[8].trim());
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
                abertura.formatar(), fechamento.formatar(),
                data.formatar(), aberto);
    }
}

class Colecao {
    private Restaurante[] arr;
    private int n;

    public Colecao() {
        arr = new Restaurante[100];
        n = 0;
    }

    public void ler(String path) {
        try {
            Scanner sc = new Scanner(new File(path));
            sc.nextLine();

            while (sc.hasNextLine()) {
                String l = sc.nextLine().trim();
                if (l.isEmpty())
                    continue;

                if (n == arr.length) {
                    Restaurante[] novo = new Restaurante[arr.length * 2];
                    for (int i = 0; i < n; i++)
                        novo[i] = arr[i];
                    arr = novo;
                }

                arr[n++] = Restaurante.parse(l);
            }

            sc.close();
        } catch (Exception e) {
            System.err.println("Erro CSV");
        }
    }

    public static Colecao carregar() {
        Colecao c = new Colecao();
        c.ler("/tmp/restaurantes.csv");
        return c;
    }

    public Restaurante buscar(int id) {
        for (int i = 0; i < n; i++)
            if (arr[i].getId() == id)
                return arr[i];
        return null;
    }
}

class Fila {
    private Restaurante[] f;
    private int ini, fim, qtd;

    public Fila() {
        f = new Restaurante[5];
        ini = fim = qtd = 0;
    }

    public Restaurante inserir(Restaurante r) {
        Restaurante rem = null;

        if (qtd == 5)
            rem = remover();

        f[fim] = r;
        fim = (fim + 1) % 5;
        qtd++;

        return rem;
    }

    public Restaurante remover() {
        if (qtd == 0)
            return null;

        Restaurante r = f[ini];
        ini = (ini + 1) % 5;
        qtd--;

        return r;
    }

    public int media() {
        int soma = 0;
        int i = ini;

        for (int j = 0; j < qtd; j++) {
            soma += f[i].getData().getAno();
            i = (i + 1) % 5;
        }

        return (int) Math.round((double) soma / qtd);
    }

    public Restaurante get(int i) {
        return f[(ini + i) % 5];
    }

    public int tamanho() {
        return qtd;
    }
}

public class ex13 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Colecao base = Colecao.carregar();
        Fila fila = new Fila();

        // inicial
        while (true) {
            int id = sc.nextInt();
            if (id == -1)
                break;

            Restaurante r = base.buscar(id);

            if (r != null) {
                Restaurante rem = fila.inserir(r);

                if (rem != null)
                    System.out.println("(R)" + rem.getNome());

                System.out.println("(I)" + fila.media());
            }
        }

        sc.nextLine();

        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            String[] cmd = sc.nextLine().split("\\s+");

            if (cmd[0].equals("I")) {
                Restaurante r = base.buscar(Integer.parseInt(cmd[1]));

                if (r != null) {
                    Restaurante rem = fila.inserir(r);

                    if (rem != null)
                        System.out.println("(R)" + rem.getNome());

                    System.out.println("(I)" + fila.media());
                }

            } else {
                Restaurante r = fila.remover();

                if (r != null)
                    System.out.println("(R)" + r.getNome());
            }
        }

        // final
        for (int i = 0; i < fila.tamanho(); i++) {
            System.out.println(fila.get(i).formatar());
        }

        sc.close();
    }
}