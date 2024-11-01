import java.util.Random;

public class GeradorDados {
    public static void main(String[] args) {
        Random random = new Random();

        // Tamanho dos veteroes do hash
        int[] tamanhosTabela = {100, 1000, 10000};

        // funcao para escolher as variacoes de funcoes do hashing
        int[] tiposHash = {1, 2, 3}; // 1 = resto da divisão 2 = multiplicacao 3 = dobramento

        // Funcao selecionar o tamanho do cojunto de dados, utilizando math.pow para calcular a potencia (calculando 10^6 que seria um milhao como um valor double)
        // ai a funcao vai retornar um valor double que teremos que converter para int para poder amarzenar no vetor, já que ele nao armazana double
        // basicamente multiplicamos os inteiros.
        int[] tamanhosConjunto = {(int) Math.pow(10, 6), 5 * (int) Math.pow(10, 6), 20 * (int) Math.pow(10, 6)};
        // gerando assim um vetor de int[] tamanho = {1000000, 5000000, 20000000};

        // funcao para percorer cada valor do vetor, que contem o tamanho dos conjuntos de dados, sendo assim a variavel tamanhoconjunto vai assumir cada valor durante
        // a interacao, e e m cada interacao printa uma mensagem informando quantos regristos foram inseridos no vetor escolhido.
        for (int tamanhoConjunto : tamanhosConjunto) {
            System.out.println("\n\nInserindo " + tamanhoConjunto + " registros...");

            for (int tamanhoTabela : tamanhosTabela) {
                // este laco vai percorrer o vetor da tabela, que contem os tamanhos diferentes citados la em cima (100, 1000, 10000) que vai ser usado para criar
                // uma itstencia nova na tabelahash com os valores das pocicoes
                for (int tipoHash : tiposHash) {
                    // laco pra percorrer o tipo do hash escolhido
                    TabelaHash tabela = new TabelaHash(tamanhoTabela, tipoHash);
                    // aqui vamos criar uma nova intencia da tabelahash usando o tamanho atual da tabela e o tipo da funcao hash escolhida
                    long tempoInicioInsercao = System.nanoTime();
                    // sys nano time é apenas pra capturar o tempo inicial das insercoes em NS

                    for (int j = 0; j < tamanhoConjunto; j++) {
                        // funcao pra inserir os dados na tabelahash
                        String codigo = String.format("%09d", random.nextInt(1000000000));
                        // formata os numeros pra limitar em 9 digitos
                        tabela.inserir(new Registro(codigo));
                        // cria um novo objeto chamado registro com o codigo gerado e insere ele na tabelahash
                    }

                    long tempoFinalInsercao = System.nanoTime();
                    long duracaoInsercao = tempoFinalInsercao - tempoInicioInsercao;

                    System.out.println("\nTabela de tamanho " + tamanhoTabela + " usando tipo de hash " + tipoHash);
                    System.out.println("Tempo de inserção: " + duracaoInsercao + " ns.");
                    System.out.println("Número de colisões: " + tabela.getNumeroColisoes());

                    // Realiza 5 buscas aleatórias
                    for (int k = 0; k < 5; k++) {
                        String codigoBusca = String.format("%09d", random.nextInt(1000000000));
                        long tempoInicioBusca = System.nanoTime();
                        Registro resultadoBusca = tabela.buscar(codigoBusca);
                        long tempoFinalBusca = System.nanoTime();

                        long duracaoBusca = tempoFinalBusca - tempoInicioBusca;

                        if (resultadoBusca != null) {
                            System.out.println("Registro com código " + resultadoBusca.getCodigo() + " encontrado em " + duracaoBusca + " ns.");
                        } else {
                            System.out.println("Registro com código " + codigoBusca + " não encontrado em " + duracaoBusca + " ns.");
                        }
                    }
                }
            }
        }
    }
}
