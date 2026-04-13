import java.util.Scanner;

public class Ciframento//nao tem () LEMBRETE
{
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String args[]){
        
     Scanner sc = new Scanner(System.in);//criação da entrada

        String s0 = sc.nextLine();//Para ler a String inteira (a linha toda incluindo espaços), você usa o método .nextLine()

        while (!isFim(s0)) //confere se o usuário digitou "FIM", se ele digitar acaba o programa
        {
            String resposta = ""; // Variável para acumular as letras cifradas
            for( int i = 0 ; i < s0.length() ; i ++)
                {
            
                char letraOriginal = s0.charAt(i);//pega a posição a letra da posição  i para depois eu cifrar ela.
                char termoCifrado = (char) (letraOriginal + 3); // Soma 3 e faz 
                resposta = resposta + termoCifrado;
                }
            System.out.println(resposta); // Imprime a palavra cifrada (ex: def)
            s0 = sc.nextLine(); // Lê a próxima palavra
        }
    sc.close();
}
}
//dificuldade 8/10, pois o raciocinio foi facil de entender, mas tive que pesquisar um pouco sobre a função "nextLine" para poder ler a linha inteira incluindo espaços e a função "charAt" para poder pegar a letra da posição i da String.
//também tive que pesquisar sobre a função "length" para poder pegar o tamanho da String e a função "equals" para poder comparar a String digitada com a palavra "FIM" para poder encerrar o programa.
// muitas vezes tive que pesquisar sobre a função "char" para poder converter a letra da posição i da String para um char e a função "String" para poder criar uma nova String com a letra cifrada.
//não sabia que em java, a letra 'z' + 3 não iria dar 'c', então tive que pesquisar sobre a função "char" para poder fazer a conversão correta da letra cifrada.
