package org.example;
import java.util.Scanner;
import java.util.jar.JarOutputStream;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        double[] chuva = new double[7];
        double maiorC = 0;
        int diaMaior = 0;
        double somaC = 0;
        double maiorU = 0;
        int escolha = 0;
        int escolha1 = 0;
        double[][] mapa = new double[4][4];

        while (escolha != 4) {
            System.out.println("==== Seja bem-vindo ao AgroJava ====");
            System.out.println("Opções disponíveis: ");
            System.out.println("1 - Cadastrar dados");
            System.out.println("2 - Exibir mapa do campo");
            System.out.println("3 - Relatório de alertas de irrigação");
            System.out.println("4 - Sair");
            System.out.println("Digite a opção escolhida: ");
            escolha = entrada.nextInt();
            System.out.println("");

            switch (escolha) {
                case 1:
                    escolha1 = 0;

                    while (escolha1 != 3) {
                        System.out.println("==== Seja bem-vindo ao cadastro de dados ====");
                        System.out.println("Opções disponíveis: ");
                        System.out.println("1 - Cadastrar índices pluviométricos");
                        System.out.println("2 - Cadastrar umidade dos talhões");
                        System.out.println("3 - Sair");
                        System.out.println("Digite a opção escolhida: ");
                        escolha1 = entrada.nextInt();
                        System.out.println("");

                        switch (escolha1) {
                            case 1:
                                somaC = 0;
                                maiorC = 0;

                                for (int i = 0; i < 7; i++) {
                                    System.out.println("Digite o índice pluviométrico do " + (i + 1) + "º dia: ");
                                    chuva[i] = entrada.nextDouble();
                                    somaC += chuva[i];

                                    if (chuva[i] > maiorC) {
                                        maiorC = chuva[i];
                                        diaMaior = i + 1;
                                    }
                                }

                                System.out.println("Índices registrados com sucesso!");
                                System.out.printf("Média semanal de chuva: %.2f mm%n", (somaC/7));
                                System.out.println("Dia com maior índice de chuva: " + diaMaior + "º dia");
                                System.out.println("");
                                break;

                            case 2:
                                maiorU = 0;

                                for (int f = 0; f < 4; f++) {
                                    for (int h = 0; h < 4; h++) {
                                        mapa[f][h] = 101;
                                        while (mapa[f][h] > 100 || mapa[f][h] < 0){
                                            System.out.println("Digite o índice de umidade da região [" + f + "][" + h + "] (em %): ");
                                            mapa[f][h] = entrada.nextDouble();
                                            if (mapa[f][h] > 100 || mapa[f][h] < 0){
                                                System.out.println("Índice de umidade inválido!");
                                            }
                                        }
                                        if (mapa[f][h] > maiorU) {
                                            maiorU = mapa[f][h];
                                        }
                                    }
                                }

                                System.out.println("Talhões cadastrados com sucesso!");
                                break;

                            case 3:
                                System.out.println("Retornando ao menu principal...");
                                break;

                            default:
                                System.out.println("Opção inválida!");
                        }
                    }
                    break;

                case 2:
                    System.out.println("==== Mapa de umidade do campo ====");

                    for (int i = 0; i < mapa.length; i++) {
                        for (int j = 0; j < mapa[i].length; j++) {
                            System.out.print(mapa[i][j] + "\t");
                        }
                        System.out.println("");
                        System.out.println("O talhão com o maior índice de umidade é: " + maiorU + "%");
                        System.out.println("");
                    }
                    break;

                case 3:
                    System.out.println("==== Relatório de alertas de irrigação ====");

                    for (int i = 0; i < mapa.length; i++) {
                        for (int j = 0; j < mapa[i].length; j++) {
                            if (mapa[i][j] < 30) {
                                System.out.println("Talhão [" + i + "][" + j + "] precisa de irrigação. Umidade: " + mapa[i][j] + "%");
                            }
                        }
                    }
                    break;

                case 4:
                    System.out.println("Fechando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}