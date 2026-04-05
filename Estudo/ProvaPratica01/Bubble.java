
package Estudo.ProvaPratica01;

import java.util.Scanner;

public class Bubble {
    public static void main(String[] args) {
        //Usei esse "try" para fechar o scanner apos usar.
        try (Scanner sc = new Scanner(System.in)) {
            int tam=0;
            System.out.println("Digite o t5amanho do vetor: ");
            tam = sc.nextInt(); //Entrada de dados no tam do vetor
            int[] vet = new int[tam]; // em java tenho que criar assim o vetor, tem que ser do tipo e do tamanho definido.

            for (int i = 0; i < tam; i++) {
                System.out.print("Digite um valor para a posição " + i + " do vetor: ");
                vet[i] = sc.nextInt(); //Entrada de dados para cada posição do vetor, for basico
            }
            
            //agora aqui vem o bubble sort, tem que ser feito com um for dentro do outro, 
            // o primeiro for é para controlar as passagens, o segundo for é para comparar os 
            // elementos e fazer as trocas.

            boolean trocou = true;//variavel para controlar se houve troca, se nao houve troca,
            //  o vetor ja esta ordenado e pode parar.
            for (int i = 0; i < tam - 1 && trocou == true; i++) {
                //condição de parada, se nao houve troca, o vetor ja esta ordenado e pode parar,porisso o "&& trocou == true"
                trocou = false;
                for (int j = 0; j < tam - 1 - i; j++) {
                    if (vet[j] > vet[j + 1]) {
                        int temp = vet[j + 1]; //guarda o valor do proximo elemento, para fazer a troca
                        vet[j + 1] = vet[j]; //pssa o valor do elemento atual para a proxima posição
                        vet[j] = temp; //passa o valor guardado para a posição atual, fazendo a troca
                        trocou = true; //se houve troca, entao o vetor ainda nao esta ordenado, entao continua o processo
                    }
                }
            }
            for (int i = 0; i < tam; i++) {
                System.out.println(vet[i]); //imprime o vetor ordenado, for basico
            }
        }
    }
}