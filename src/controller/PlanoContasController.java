package controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.PlanoContasDAO;
import model.PlanoContas;
import view.TelaPlanoDeContas;

public class PlanoContasController {

    private TelaPlanoDeContas tela;
    private PlanoContasDAO planoContasDAO;

    public PlanoContasController(TelaPlanoDeContas tela) {
        this.tela = tela;
        this.planoContasDAO = new PlanoContasDAO();
    }

    public void AtualizarTela() {
        carregarTabela();

        JOptionPane.showMessageDialog(
                tela,
                "Tela atualizada com sucesso!"
        );
    }

    public void AdicionarConta() {
        JOptionPane.showMessageDialog(
                tela,
                "Funcionalidade de adicionar conta ainda será implementada."
        );
    }

    public void CancelarConta() {

        int linha = tela.getTabelaPlanoDeContas().getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Selecione uma conta na tabela.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String conta = tela.getTabelaPlanoDeContas()
                .getValueAt(linha, 0)
                .toString();

        int confirmacao = JOptionPane.showConfirmDialog(
                tela,
                "Deseja realmente excluir a conta " + conta + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            planoContasDAO.excluir(conta);

            JOptionPane.showMessageDialog(
                    tela,
                    "Conta excluída com sucesso!"
            );

            carregarTabela();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    tela,
                    "Erro ao excluir conta: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void preencherTabela() {

        int linha = tela.getTabelaPlanoDeContas().getSelectedRow();

        if (linha != -1) {

            String conta = tela.getTabelaPlanoDeContas()
                    .getValueAt(linha, 0)
                    .toString();

            System.out.println("Conta selecionada: " + conta);
        }
    }

    public void carregarTabela() {

        DefaultTableModel modelo =
                (DefaultTableModel)
                tela.getTabelaPlanoDeContas().getModel();

        modelo.setRowCount(0);

        try {

            List<PlanoContas> contas = planoContasDAO.listar();

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