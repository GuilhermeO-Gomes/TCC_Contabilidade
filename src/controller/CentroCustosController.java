package controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import dao.CentroCustosDAO;
import model.PlanoContas;
import view.TelaCentroCustos;


public class CentroCustosController {
	private TelaCentroCustos tela;
    private CentroCustosDAO centroCustosDAO;
    
    public CentroCustosController(TelaCentroCustos tela) {
        this.tela = tela;
        this.centroCustosDAO = new CentroCustosDAO();
    }
	
	public void carregarTabela() {
        DefaultTableModel modelo =(DefaultTableModel) tela.getTabelaCentroCustos().getModel();
        modelo.setRowCount(0);

        try {
            List<PlanoContas> contas = centroCustosDAO.listar();
            for (PlanoContas plano : contas) {
                modelo.addRow(new Object[] {
                        plano.getConta(),       // Conta
                        plano.getDescricao(),   // Descrição
                        plano.getReduzida(),    // Reduzida
                        "",                    // Superior
                        "",                    // Nível
                        plano.getSaldo(),       // Saldo
                        plano.getTipo(),        // Tipo
                        plano.getCc(),          // C.C.
                        "",                    // Natureza
                        plano.getSituacao(),    // Situação
                        "",                    // Cadastro
                        ""                     // Movimentação
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Erro ao carregar tabela: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
