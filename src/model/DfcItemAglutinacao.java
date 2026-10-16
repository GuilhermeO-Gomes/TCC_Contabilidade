package model;

public class DfcItemAglutinacao {

    private long id;
    private long dfcItemId;
    private long codigoAglutinacaoId;

    public DfcItemAglutinacao() {
    }

    public DfcItemAglutinacao(
            long id,
            long dfcItemId,
            long codigoAglutinacaoId) {

        this.id = id;
        this.dfcItemId = dfcItemId;
        this.codigoAglutinacaoId = codigoAglutinacaoId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDfcItemId() {
        return dfcItemId;
    }

    public void setDfcItemId(long dfcItemId) {
        this.dfcItemId = dfcItemId;
    }

    public long getCodigoAglutinacaoId() {
        return codigoAglutinacaoId;
    }

    public void setCodigoAglutinacaoId(long codigoAglutinacaoId) {
        this.codigoAglutinacaoId = codigoAglutinacaoId;
    }
}
