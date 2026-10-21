package model;

public class Depreciacao {
    
    private int id;
    private double taxa;
    private String tempo;
    private String dataDepreciacao;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;

    }

    public String getDataDepreciacao() {
        return dataDepreciacao;
    }

    public void setDataDepreciacao(String dataDepreciacao) {
        this.dataDepreciacao = dataDepreciacao;
    }
    
}


