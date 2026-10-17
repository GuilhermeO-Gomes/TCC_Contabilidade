package model;

public class DREItem {

    public enum GrupoDRE {
        INCREMENTO_LUCRO,
        REDUCAO_LUCRO
    }


    private long id;

    private long dreId;

    private int ordem;

    private long codigoAglutinacaoId;

    private GrupoDRE grupo;

    /*
     * Long porque pode ser null.
     *
     * Um item raiz da DRE não possui
     * item superior.
     */
    private Long itemSuperiorId;

    private boolean ativo;


    public DREItem() {
        this.ativo = true;
    }


    public DREItem(
            long id,
            long dreId,
            int ordem,
            long codigoAglutinacaoId,
            GrupoDRE grupo,
            Long itemSuperiorId,
            boolean ativo) {

        this.id = id;
        this.dreId = dreId;
        this.ordem = ordem;
        this.codigoAglutinacaoId =
            codigoAglutinacaoId;

        this.grupo = grupo;
        this.itemSuperiorId = itemSuperiorId;
        this.ativo = ativo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getDreId() {
        return dreId;
    }

    public void setDreId(long dreId) {
        this.dreId = dreId;
    }


    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }


    public long getCodigoAglutinacaoId() {
        return codigoAglutinacaoId;
    }

    public void setCodigoAglutinacaoId(
            long codigoAglutinacaoId) {

        this.codigoAglutinacaoId =
            codigoAglutinacaoId;
    }


    public GrupoDRE getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDRE grupo) {
        this.grupo = grupo;
    }


    public Long getItemSuperiorId() {
        return itemSuperiorId;
    }

    public void setItemSuperiorId(
            Long itemSuperiorId) {

        this.itemSuperiorId =
            itemSuperiorId;
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}