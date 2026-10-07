package model;

import java.math.BigDecimal;

public class ResumoContabil {

    private Integer lancamentosNoMes;
    private BigDecimal receitas;
    private BigDecimal despesas;

    public Integer getLancamentosNoMes() {
        return lancamentosNoMes;
    }

    public BigDecimal getReceitas() {
        return receitas;
    }

    public BigDecimal getDespesas() {
        return despesas;
    }

    public BigDecimal getResultado() {
        if (receitas == null || despesas == null) {
            return null;
        }
        return receitas.subtract(despesas);
    }

    public Object[][] getMovimentacoesRecentes() {
        return new Object[0][];
    }
}
