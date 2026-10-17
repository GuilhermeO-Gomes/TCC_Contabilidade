package model;

public class BalancoPatrimonialItem {

    public enum GrupoBalanco {
        ATIVO,
        PASSIVO,
        PATRIMONIO_LIQUIDO
    }


    private long id;

    private long balancoPatrimonialId;

    private int ordem;

    private long codigoAglutinacaoId;

    private GrupoBalanco grupo;

    private Long itemSuperiorId;

    private boolean ativo;


    public BalancoPatrimonialItem() {
        this.ativo = true;
    }


    public BalancoPatrimonialItem(
            long id,
            long balancoPatrimonialId,
            int ordem,
            long codigoAglutinacaoId,
            GrupoBalanco grupo,
            Long itemSuperiorId,
            boolean ativo) {

        this.id = id;
        this.balancoPatrimonialId =
            balancoPatrimonialId;

        this.ordem = ordem;

        this.codigoAglutinacaoId =
            codigoAglutinacaoId;

        this.grupo = grupo;

        this.itemSuperiorId =
            itemSuperiorId;

        this.ativo = ativo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getBalancoPatrimonialId() {
        return balancoPatrimonialId;
    }

    public void setBalancoPatrimonialId(
            long balancoPatrimonialId) {

        this.balancoPatrimonialId =
            balancoPatrimonialId;
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


    public GrupoBalanco getGrupo() {
        return grupo;
    }

    public void setGrupo(
            GrupoBalanco grupo) {

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