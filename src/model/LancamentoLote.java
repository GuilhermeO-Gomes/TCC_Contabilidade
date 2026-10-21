package model;

public class LancamentoLote {
    private int id;
    private String nomeLote; //nome que o Contador vai dar ao lote
    private String descriLote; //específicações do lote
    private String dataAbertura; //data em que se está criando o lote
    private String dataInicial; //a data em que se inicia os lançamentos do lote
    private String dataFinal; //a data em que se finaliza os lançamentos do lote
    private double  debito; //soma de todos os debitos dos lançamentos que estão no lote
    private double credito; //soma de todos os creditos  dos lançamentos que estão no lote
    private double diferenca; // diferença entre debito e credito, em si não é para existir, no documento explica melhor
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

   public double getDebito() {
    return debito;
   } 

   public void setDebito(double debito) {
    this.debito = debito;
   }

   public double getCredito() {
    return credito;
   }

   public void setCredito(double credito) {
    this.credito = credito;
   }

   public double getDiferenca() {
    return diferenca;
   }

   public void setDiferenca(double diferenca) {
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



