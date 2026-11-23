package org.example;

import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;

public class Main {
    Scanner entrada = new Scanner(System.in);
    double[] chuva = new double[7];
    double maior = -1;
    double menor = 1000000000;
    double maiorC = 0;
    int totalSemanas = 0;
    int diaMaior = 0;
    double somaC = 0;
    double maiorU = -1;
    double menorU = 101;
    int escolha = 0;
    int escolha1 = 0;
    double[][] mapa = new double[4][4];
    double domingo, segunda, terca, quarta, quinta, sexta, sabado;
    File arquivo = new File("relatorio.csv");
    boolean arquivoVazio = !arquivo.exists() || arquivo.length() == 0;
    LocalDate hoje = LocalDate.now();
    String sMaior = "";
    String sMenor = "";
    double totalGeral = 0;
    int talhaoA;
    int talhaoB;
    double umidade;
    String criticidade;
    int totalAlertas = 0;
    double totalUmidade;
    int irrigacao;
    double talhaoM, talhaoM1, talhaoP, talhaoP1;
    double mUmidade;
    int talhoesAdequados;
    double precisaIrrigar;

    void main(String[] args) throws IOException {
        while (escolha != 8) {
            System.out.println("==== Seja bem-vindo ao AgroJava ====");
            System.out.println("Opções disponíveis: ");
            System.out.println("1 - Cadastrar dados");
            System.out.println("2 - Exibir mapa do campo");
            System.out.println("3 - Relatório de alertas de irrigação");
            System.out.println("4 - Exibir relatório");
            System.out.println("5 - Dashbord climático");
            System.out.println("6 - Verificar a criticidade dos talhões");
            System.out.println("7 - Relatório da fazenda");
            System.out.println("8 - Sair");
            System.out.println("Digite a opção escolhida: ");
            escolha = entrada.nextInt();
            System.out.println("");

            switch (escolha) {
                case 1:
                    cadastroDados();
                    break;

                case 2:
                    mapaUmidade();
                    break;

                case 3:
                    relatoriosIrrigacao();
                    break;

                case 4:
                    exibirRelatorio();
                    break;

                case 5:
                    dash();
                    break;

                case 6:
                    relatorioCriticidade();
                    break;

                case 7:
                    relatorioFazenda();
                    break;

                case 8:
                    System.out.println("Fechando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void cadastroDados() throws IOException {
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
                    cadastroPluviometrico();
                    break;

                case 2:
                    cadastroUmidade();
                    break;

                case 3:
                    System.out.println("Retornando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void mapaUmidade() {
        System.out.println("==== Mapa de umidade do campo ====");

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                System.out.print(mapa[i][j] + "\t");
            }
            System.out.println("");
        }

        System.out.println("O talhão com o maior índice de umidade é: " + maiorU + "%");
        System.out.println("");
    }

    public void relatoriosIrrigacao() throws IOException {
        System.out.println("==== Relatório de alertas de irrigação ====");

        irrigacao = 0;
        totalUmidade = 0;

        try {
            BufferedReader leitor = new BufferedReader(new FileReader("relatorio1.csv"));
            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");

                if (dados.length < 3 || dados[0].trim().equals("Talhao")) {
                    continue;
                }

                String talhao = dados[0];
                double umidade = Double.parseDouble(dados[1]);
                String criticidade = dados[2];

                if (criticidade.equals("Crítico") || criticidade.equals("Atenção")) {
                    System.out.println("Talhão " + talhao + " precisa de irrigação. Umidade: " + umidade + "%");
                    irrigacao++;
                }
                totalUmidade += umidade;
            }

            leitor.close();

            System.out.println("Total de alertas: " + irrigacao);

        } catch (IOException e) {
            System.out.println("Nenhum cadastro de umidade encontrado.");
        }
    }

    public void relatorioCsv() {
        try {
            FileWriter adicionar = new FileWriter("relatorio.csv", true);
            BufferedWriter escritor = new BufferedWriter(adicionar);

            if (arquivoVazio) {
                escritor.write("Data,Domingo,Segunda,Terca,Quarta,Quinta,Sexta,Sabado,Media,Total");
                escritor.newLine();
                arquivoVazio = false;
            }

            escritor.write(hoje + "," + domingo + "," + segunda + "," + terca + "," + quarta + "," + quinta + "," + sexta + "," + sabado + "," + (somaC / 7) + "," + somaC);

            escritor.newLine();
            escritor.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cadastroPluviometrico() throws IOException {
        somaC = 0;
        maiorC = 0;

        System.out.println("Digite a data da semana (AAAA-MM-DD): ");
        hoje = LocalDate.parse(entrada.next());

        for (int i = 0; i < 7; i++) {
            System.out.println("Digite o índice pluviométrico do " + (i + 1) + "º dia: ");
            chuva[i] = entrada.nextDouble();
            somaC += chuva[i];

            if (chuva[i] > maiorC) {
                maiorC = chuva[i];
                diaMaior = i + 1;
            }
        }

        domingo = chuva[0];
        segunda = chuva[1];
        terca = chuva[2];
        quarta = chuva[3];
        quinta = chuva[4];
        sexta = chuva[5];
        sabado = chuva[6];

        relatorioCsv();

        System.out.println("Índices registrados com sucesso!");
        System.out.printf("Média semanal de chuva: %.2f mm%n", (somaC / 7));
        System.out.println("Total semanal de chuva: " + somaC + " mm");
        System.out.println("Dia com maior índice de chuva: " + diaMaior + "º dia");
        System.out.println("");
    }

    public void cadastroUmidade() throws IOException {
        for (int f = 0; f < 4; f++) {
            for (int h = 0; h < 4; h++) {
                mapa[f][h] = 101;

                while (mapa[f][h] > 100 || mapa[f][h] < 0) {
                    System.out.println("Digite o índice de umidade da região [" + f + "][" + h + "] (em %): ");
                    mapa[f][h] = entrada.nextDouble();

                    if (mapa[f][h] > 100 || mapa[f][h] < 0) {
                        System.out.println("Índice de umidade inválido!");
                    }
                }

                if (mapa[f][h] > maiorU) {
                    talhaoM = f;
                    talhaoM1 = h;
                    maiorU = mapa[f][h];
                }

                if (mapa[f][h] < menorU) {
                    talhaoP = f;
                    talhaoP1 = h;
                    menorU = mapa[f][h];
                }

                talhaoA = f;
                talhaoB = h;
                umidade = mapa[f][h];

                if (mapa[f][h] < 20) {
                    criticidade = "Crítico";
                } else if (mapa[f][h] <= 30) {
                    criticidade = "Atenção";
                } else {
                    criticidade = "Adequado";
                }

                csvTalhoes();
            }
        }

        System.out.println("Talhões cadastrados com sucesso!");
    }

    public void exibirRelatorio() {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("relatorio.csv"));
            String linha;

            totalGeral = 0;
            totalSemanas = 0;
            maior = -1;
            menor = 1000000000;
            sMaior = "";
            sMenor = "";

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");

                if (dados.length < 10 || dados[0].trim().equals("Data")) {
                    continue;
                }

                String semana = dados[0];
                String media = dados[8];
                String total = dados[9];

                totalGeral += Double.parseDouble(dados[9]);
                double totalComparar = Double.parseDouble(dados[9]);
                totalSemanas++;

                if (totalComparar > maior) {
                    maior = totalComparar;
                    sMaior = dados[0];
                }

                if (totalComparar < menor) {
                    menor = totalComparar;
                    sMenor = dados[0];
                }

                System.out.println("Semana: " + semana);
                System.out.println("Média: " + media);
                System.out.println("Total: " + total);
                System.out.println("========================================");
            }

            leitor.close();

            if (totalSemanas > 0) {
                System.out.println("Semana mais chuvosa: " + sMaior);
                System.out.println("Total: " + maior + " mm");
                System.out.println("Semana mais seca: " + sMenor);
                System.out.println("Total: " + menor + " mm");
            } else {
                System.out.println("Nenhuma semana cadastrada.");
            }

        } catch (IOException e) {
            System.out.println("Nenhum relatório de chuva encontrado.");
        }
    }

    public void dash() {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("relatorio.csv"));
            String linha;

            totalGeral = 0;
            totalSemanas = 0;
            maior = -1;
            menor = 1000000000;
            sMaior = "";
            sMenor = "";

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");

                if (dados.length < 10 || dados[0].trim().equals("Data")) {
                    continue;
                }

                double totalComparar = Double.parseDouble(dados[9]);

                totalGeral += totalComparar;
                totalSemanas++;

                if (totalComparar > maior) {
                    maior = totalComparar;
                    sMaior = dados[0];
                }

                if (totalComparar < menor) {
                    menor = totalComparar;
                    sMenor = dados[0];
                }
            }

            leitor.close();

            System.out.println("===== Dashboard =====");

            if (totalSemanas > 0) {
                double mGeral = totalGeral / totalSemanas;

                System.out.println("Semanas cadastradas: " + totalSemanas);
                System.out.println("Volume acumulado: " + totalGeral + " mm");
                System.out.println("Semana mais chuvosa: " + sMaior);
                System.out.println("Maior chuva: " + maior + " mm");
                System.out.println("Semana mais seca: " + sMenor);
                System.out.println("Menor chuva: " + menor + " mm");
                System.out.println("Média geral: " + mGeral + " mm");
            } else {
                System.out.println("Nenhuma semana cadastrada.");
            }

        } catch (IOException e) {
            System.out.println("Nenhum relatório de chuva encontrado.");
        }
    }

    public void csvTalhoes() throws IOException {
        File arquivoTalhoes = new File("relatorio1.csv");
        boolean vazio = !arquivoTalhoes.exists() || arquivoTalhoes.length() == 0;

        BufferedWriter escritor = new BufferedWriter(
                new FileWriter("relatorio1.csv", true));

        try {
            if (vazio) {
                escritor.write("Talhao,Umidade,Status");
                escritor.newLine();
            }

            String talhao = "";

            if (talhaoA == 0) {
                talhao = "A";
            } else if (talhaoA == 1) {
                talhao = "B";
            } else if (talhaoA == 2) {
                talhao = "C";
            } else {
                talhao = "D";
            }

            talhao = talhao + (talhaoB + 1);

            escritor.write(talhao + "," + umidade + "," + criticidade);
            escritor.newLine();
            escritor.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void relatorioCriticidade() throws IOException {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("relatorio1.csv"));
            String linha;

            totalAlertas = 0;

            System.out.println("===== Alertas =====");

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");

                if (dados.length < 3 || dados[0].trim().equals("Talhao")) {
                    continue;
                }

                String talhao = dados[0];
                String critico = dados[2];

                if (critico.equals("Crítico") || critico.equals("Atenção")) {
                    System.out.println(talhao + " - " + critico);
                    totalAlertas++;
                }
            }

            leitor.close();

            System.out.println("Total de alertas: " + totalAlertas);

        } catch (IOException e) {
            System.out.println("Nenhum cadastro de talhões encontrado.");
        }
    }

    public void relatorioFazenda() {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("relatorio1.csv"));
            String linha;

            totalUmidade = 0;
            totalAlertas = 0;
            talhoesAdequados = 0;
            irrigacao = 0;
            maiorU = -1;
            menorU = 101;

            String talhaoMaior = "";
            String talhaoMenor = "";

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");

                if (dados.length < 3 || dados[0].trim().equals("Talhao")) {
                    continue;
                }

                String talhao = dados[0];
                double umidade = Double.parseDouble(dados[1]);
                String criticidade = dados[2];

                totalUmidade += umidade;

                if (criticidade.equals("Crítico")) {
                    totalAlertas++;
                    irrigacao++;
                } else if (criticidade.equals("Atenção")) {
                    irrigacao++;
                } else {
                    talhoesAdequados++;
                }

                if (umidade > maiorU) {
                    maiorU = umidade;
                    talhaoMaior = talhao;
                }

                if (umidade < menorU) {
                    menorU = umidade;
                    talhaoMenor = talhao;
                }
            }

            leitor.close();

            mUmidade = totalUmidade / 16;
            precisaIrrigar = ((double) irrigacao / 16) * 100;

            System.out.println("===== Relatório da fazenda =====");
            System.out.println("Média geral: " + mUmidade + "%");
            System.out.println("Talhões críticos: " + totalAlertas);
            System.out.println("Talhões adequados: " + talhoesAdequados);
            System.out.println("Necessidade irrigação: " + precisaIrrigar + "%");
            System.out.println("Talhão mais seco: " + talhaoMenor + " - " + menorU + "%");
            System.out.println("Talhão mais úmido: " + talhaoMaior + " - " + maiorU + "%");

            relatorioFazendaCSV();

        } catch (IOException e) {
            System.out.println("Nenhum cadastro de talhões encontrado.");
        }
    }

    public void relatorioFazendaCSV() throws IOException {
        BufferedWriter escritor = new BufferedWriter(
                new FileWriter("relatorio_fazenda.txt"));

        try {
            escritor.write("===== Relatório da fazenda =====");
            escritor.newLine();
            escritor.write("Média geral: " + mUmidade + "%");
            escritor.newLine();
            escritor.write("Talhões críticos: " + totalAlertas);
            escritor.newLine();
            escritor.write("Talhões adequados: " + talhoesAdequados);
            escritor.newLine();
            escritor.write("Necessidade irrigação: " + precisaIrrigar + "%");
            escritor.newLine();
            escritor.write("Talhão mais seco: " + talhaoP + talhaoP1 + " - " + menorU);
            escritor.newLine();
            escritor.write("Talhão mais úmido: " + talhaoM + talhaoM1 + " - " + maiorU);
            escritor.newLine();
            escritor.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}