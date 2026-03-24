/*
Errei demais nesse exercício, não consegui entender o que era pedido, e acabei 
fazendo um código que não resolve o problema. O código acima é um programa em Java 
que tenta resolver um problema relacionado a estacionamento de veículos. Ele lê o número de veículos
(n) e a capacidade do estacionamento (k), e depois lê os horários de entrada e saída dos veículos. 
O programa verifica se é possível acomodar todos os veículos no estacionamento sem ultrapassar a 
capacidade, e imprime "Sim" ou "Nao" dependendo do resultado. No entanto, o código tem alguns erros 
lógicos e de implementação que precisam ser corrigidos para funcionar corretamente.

entre eles podemos destacar:
1. O loop que verifica os horários de entrada e saída dos veículos está acessando índices fora do
 limite do array, o que pode causar um erro de ArrayIndexOutOfBoundsException.
2. A lógica para determinar se o estacionamento pode acomodar os veículos está incorreta, pois 
não leva em consideração o número de veículos presentes no estacionamento em cada momento do tempo.
3. O programa não tem um mecanismo para encerrar o loop de entrada de dados, o que pode levar a um 
loop infinito se os valores de n e k não forem atualizados corretamente.
*/
package Provas.Teorica;

import java.util.Scanner;

public class Carros {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n = sc.nextInt();// n usarao o estaconamento
        int k = sc.nextInt();// consegue comportar k veiculos

        int[] horaentrada = new int[n]; // O tamanho do array deve ser suficiente para 
        // armazenar os horários de entrada e saída dos veículos, dependendo do valor de n.
        int[] horasaida = new int[n];
        boolean teste = true;

        while (n != 0 && k != 0)//fiz um loop infinito, pois n e k não são atualizados dentro do 
        // loop, o que pode levar a um comportamento indesejado.
         {
            for (int i = 0; i < n; i++) {
                horaentrada[i] = sc.nextInt(); //certo
                horasaida[i] = sc.nextInt();//certo
            }
            /*
            aqui deveria ser implementada uma lógica para contar o número de veículos presentes no 
            estacionamento em cada momento do tempo, e verificar se esse número ultrapassa a capacidade k.
            if (n > k) {
                teste = false; // Se o número de veículos for maior que a capacidade, o teste é falso.
            }
            em seguida deveria ser implementada uma lógica para verificar os horários de entrada e saída 
            dos veículos,
            e contar quantos veículos estão presentes no estacionamento em cada momento do tempo, para 
            determinar se o estacionamento pode acomodar todos os veículos sem ultrapassar a capacidade.
            Então ficaria: 
            */

            int count = 0; // Contador de veículos presentes no estacionamento
            for (int z = 0; z < n; z++) {
                if (horaentrada[z] < horasaida[z]) {
                    count++; // Incrementa o contador quando um veículo entra
                } else {
                    count--; // Decrementa o contador quando um veículo sai
                }
                if (count > k) {
                    teste = false; // Se o contador ultrapassar a capacidade, o teste é falso.
                    break; // Sai do loop se a capacidade for ultrapassada
            //falta implementar a lógica para verificar os horários de entrada e saída dos veículos, 
            // e contar quantos veículos estão presentes no estacionamento em cada momento do tempo,
            //  para determinar se o estacionamento pode acomodar todos os veículos sem ultrapassar a capacidade.
            // então ficaria algo como:(falta implementar a questão de pilha ou fila para contar os veículos presentes no estacionamento em cada momento do tempo)   
        }
            
            if (teste == false) {
                System.out.println("Nao");
            } else {
                System.out.println("Sim");
            }
            sc.nextLine(); // Limpa o buffer para a próxima leitura de n e k
                    n = sc.nextInt(); // Atualiza n para a próxima iteração
                    k = sc.nextInt(); // Atualiza k para a próxima iteração
                }
            }
        }
    }
    /*previsão de entradas e saídas:
    - Entrada: 3 2
      1 10
      2 5
      6 9
        Saída: Sim

     */ 


