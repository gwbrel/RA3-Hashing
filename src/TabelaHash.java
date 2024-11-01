public class TabelaHash {
    private No[] tabela;
    private int tamanho;
    private int numeroColisoes;
    private int tipoHash; // 1 = resto da divisão, 2 = multiplicação, 3 = dobramento

    public TabelaHash(int tamanho, int tipoHash) {
        this.tamanho = tamanho;
        this.tabela = new No[tamanho];
        this.numeroColisoes = 0;
        this.tipoHash = tipoHash;
    }

    private int hash(String chave) {
        int codigo = Integer.parseInt(chave);

        switch (tipoHash) {
            case 1: // Resto da divisão
                return codigo % tamanho;
            case 2: // Multiplicação
                double A = 0.6180339887; // Constante sugerida
                return (int) (tamanho * (codigo * A % 1));
            case 3: // Dobramento
                int part1 = codigo / 1000;
                int part2 = codigo % 1000;
                return (part1 + part2) % tamanho;
            default:
                throw new IllegalArgumentException("Tipo de hash invalido");
        }
    }

    public void inserir(Registro registro) {
        int indice = hash(registro.getCodigo());

        if (tabela[indice] != null) {
            numeroColisoes++;
        }

        No novoNo = new No(registro);
        novoNo.proximo = tabela[indice];
        tabela[indice] = novoNo;
    }

    public Registro buscar(String codigo) {
        int indice = hash(codigo);
        No atual = tabela[indice];

        while (atual != null) {
            if (atual.registro.getCodigo().equals(codigo)) {
                return atual.registro;
            }
            atual = atual.proximo;
        }

        return null;
    }

    public int getNumeroColisoes() {
        return numeroColisoes;
    }
}
