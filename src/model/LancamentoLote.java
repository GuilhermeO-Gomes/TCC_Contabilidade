package model;

public class LancamentoLote {
    private int id;
    private String nomeLote; //nome que o Contador vai dar ao lote
    private String descriLote; //específicações do lote
    private String dataAbertura; //data em que se está criando o lote
    private String dataInicial; //a data em que se inicia os lançamentos do lote
    private String dataFinal; //a data em que se finaliza os lançamentos do lote
    private String  debito; //soma de todos os debitos dos lançamentos que estão no lote
    private String credito; //soma de todos os creditos  dos lançamentos que stão no lote
    private String diferenca; // diferença entre debito e credito, em si não é para existir, no documento explica melhor
    private boolean bloqueado; // caso já tenha feito todos os lançamentos, ajustes e apurações nesse lote, pode bloquear o lote para que nenhum outro usuário faça qualquer modificação


    public LancamentoLote() {
        this.bloqueado=true;

    }


    public int getId() {
    return id;
   }

    public void setId(int id) {
    this.id = id;
   }

   public String getDataAbertura() {
    return dataAbertura;
   }

   public void setDataAbertura(String dataAbertura) {
    this.dataAbertura = dataAbertura;
   }

   public String getNomeLote() {
    return nomeLote;
   }

   public void setNomeLote(String nomeLote) {
    this.nomeLote = nomeLote;
   }

   public String getDescriLote() {
    return descriLote;
   }

   public void setDescriLote(String descriLote) {
    this.descriLote = descriLote;
   } 

   public String getDataInicial() {
    return dataInicial;
   }

   public void setDataInicial(String dataInicial) {
    this.dataInicial = dataInicial;
   }

   public String getDataFinal() {
    return dataFinal;
   }

   public void setDataFinal(String dataFinal) {
    this.dataFinal = dataFinal;
   }

   public String getDebito() {
    return debito;
   } 

   public void setDebito(String debito) {
    this.debito = debito;
   }

   public String getCredito() {
    return credito;
   }

   public void setCredito(String credito) {
    this.credito = credito;
   }

   public String getDiferenca() {
    return diferenca;
   }

   public void setDiferenca(String diferenca) {
    this.diferenca = diferenca;
   }

   public boolean isBloqueado() {
    return bloqueado;
   }
    
   public void setBloqueado(boolean bloqueado) {
    this.bloqueado = bloqueado;
  }

  public String toString() {
    return nomeLote;
  }
}



