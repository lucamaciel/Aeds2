import java.util.Scanner;

public Class Ciframento//nao tem () LEMBRETE
{
    public static void main(string args[]){
        
     Scanner sc = new Scanner(System.in);//criação da entrada

        String s0 = sc.nextLine();//Para ler a String inteira (a linha toda incluindo espaços), você usa o método .nextLine()

        while (!s0.equals("FIM")) //confere se o usuário digitou "FIM", se ele digitar acaba o programa
        {
            String resposta = ""; // Variável para acumular as letras cifradas
            for( int i = 0 ; i < s0.length() ; i ++)
                {
            
                char letraOriginal = s0.charAt(i);//pega a posição a letra da posição  i para depois eu cifrar ela.
                char termoCifrado[] = (char) (LetraOriginal + 3); // Soma 3 e faz 
                resposta = resposta + termoCifrado;
                }
            System.out.println(resposta); // Imprime a palavra cifrada (ex: def)
            s0 = sc.nextLine(); // Lê a próxima palavra
        }
    sc.close();
}
}
    //dificuldade 8.5/10 mais por ser java e eu não ter muita pratica com a linguagem, mas o raciocinio foi facil de entender, o que me ajudou a fazer o programa foi a logica de pegar a letra da posição i e somar 3 para depois imprimir a palavra cifrada.