package controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.AlunoDAO;
import model.Aluno;
import view.TelaAluno;

public class AlunoController {

    private TelaAluno tela;
    private AlunoDAO alunoDAO;

    public AlunoController(TelaAluno tela) {
        this.tela = tela;
        this.alunoDAO = new AlunoDAO();
    }

    public void salvar() {
        String nome = tela.getTxtNome().getText().trim();
        String nomeSocial = tela.getTxtNomeSocial().getText().trim();
        String cpf = tela.getTxtCPF().getText().trim();
        String genero = tela.getCbGenero().getSelectedItem().toString().trim();
        String afro = tela.getCbAfro().getSelectedItem().toString().trim();
        String escolaridade = tela.getCbEscolaridade().getSelectedItem().toString().trim();
        String dtNascimento = tela.getTxtdtNascimento().getText().trim();
        String nacionalidade = tela.getTxtNacionalidade().getText().trim();
        String nmResponsavel = tela.getTxtResponsavel().getText().trim();
        String grauParentesco = tela.getTxtGrauDeParentesco().getText().trim();
        String habilitacao = tela.getCbHabilitacao().getSelectedItem().toString().trim();
        String serie = tela.getCbSerie().getSelectedItem().toString().trim();
        String periodo = tela.getCbPeriodo().getSelectedItem().toString().trim();

        String rua = tela.getTxtRua().getText().trim();
        String complemento = tela.getTxtComplemento().getText().trim();
        String apto = tela.getTxtApto().getText().trim();
        String bloco = tela.getTxtBloco().getText().trim();
        String bairro = tela.getTxtBairro().getText().trim();
        String cidade = tela.getTxtCidade().getText().trim();
        String cep = tela.getTxtCep().getText().trim();
        String tel = tela.getTxtTel().getText().trim();
        String email = tela.getTxtEmail().getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || genero.equalsIgnoreCase("Selecione")
                || afro.equalsIgnoreCase("Selecione") || escolaridade.equalsIgnoreCase("Selecione") ||
                dtNascimento.isEmpty() || nacionalidade.isEmpty()
                || nmResponsavel.isEmpty() || grauParentesco.isEmpty() || habilitacao.equalsIgnoreCase("Selecione") ||
                serie.equalsIgnoreCase("Selecione") || periodo.equalsIgnoreCase("Selecione") ||
                rua.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || cep.isEmpty() || tel.isEmpty()
                || email.isEmpty()) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Preencha os campos.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String idTexto = tela.getTxtId().getText().trim();

            java.text.SimpleDateFormat formatador = new java.text.SimpleDateFormat("dd/MM/yyyy");
            // Define que a análise da data deve ser rígida
            formatador.setLenient(false);

            // Converte a String da tela para uma data utilitária do Java
            java.util.Date dataUtil = formatador.parse(dtNascimento);
            // Converte a data utilitária para o formato java.sql.Date exigido pelo seu
            java.sql.Date dataFormatada = new java.sql.Date(dataUtil.getTime());

            // Converte o texto do ComboBox para true ou false
            boolean afroBoolean = afro.equalsIgnoreCase("Sim");
            boolean escolaridadeBoolean = escolaridade.equalsIgnoreCase("Pública")
                    || escolaridade.equalsIgnoreCase("Sim");

            if (idTexto.isEmpty()) {
                Aluno aluno = new Aluno(nome, nomeSocial, cpf, genero, afroBoolean, escolaridadeBoolean, dataFormatada,
                        nacionalidade, nmResponsavel,
                        grauParentesco, habilitacao, serie, periodo, rua, complemento, apto, bloco, bairro, cidade, cep,
                        tel, email);
                alunoDAO.salvar(aluno);
                JOptionPane.showMessageDialog(tela, "Aluno salvo com sucesso.");
            } else {
                Aluno aluno = new Aluno(Integer.parseInt(idTexto), nome, nomeSocial, cpf, genero, afroBoolean,
                        escolaridadeBoolean, dataFormatada, nacionalidade, nmResponsavel,
                        grauParentesco, habilitacao, serie, periodo, rua, complemento, apto, bloco, bairro, cidade, cep,
                        tel, email);
                alunoDAO.atualizar(aluno);
                JOptionPane.showMessageDialog(tela, "Aluno atualizado com sucesso.");
            }

            limpar();
            carregarTabela();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Formato de data inválido! Use o padrão DD/MM/AAAA.",
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Erro ao salvar: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void excluir() {
        int linha = tela.getTabelaAlunos().getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Selecione um aluno na tabela para excluir.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                tela,
                "Deseja realmente excluir o aluno selecionado?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(tela.getTxtId().getText());
            alunoDAO.excluir(id);
            JOptionPane.showMessageDialog(tela, "Aluno excluído com sucesso.");
            limpar();
            carregarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Erro ao excluir: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void carregarTabela() {
        DefaultTableModel modelo = (DefaultTableModel) tela.getTabelaAlunos().getModel();
        modelo.setRowCount(0);

        try {
            List<Aluno> alunos = alunoDAO.listar();

            java.text.SimpleDateFormat formatadorBr = new java.text.SimpleDateFormat("dd/MM/yyyy");

            int i;
            for (i = 0; i < alunos.size(); i++) {
                Aluno a = alunos.get(i);

                String txtAfro = a.isAfrodescendente() ? "Sim" : "Não";
                String txtEscolaridade = a.isEscolaridade_publica() ? "Sim" : "Não";

                String dataFormatadaBr = "";
                if (a.getData_nascimento() != null) {
                    // Transforma a data do banco em texto "dd/MM/yyyy"
                    dataFormatadaBr = formatadorBr.format(a.getData_nascimento());
                }

                modelo.addRow(new Object[] {
                        a.getId(),
                        a.getNome(),
                        a.getNome_social(),
                        a.getCpf(),
                        a.getGenero(),
                        txtAfro,
                        txtEscolaridade,
                        dataFormatadaBr,
                        a.getNacionalidade(),
                        a.getResponsavel_legal(),
                        a.getGrau_parentesco(),
                        a.getHabilitacao(),
                        a.getSerie(),
                        a.getPeriodo(),
                        a.getRua(),
                        a.getComplemento(),
                        a.getApto(),
                        a.getBloco(),
                        a.getBairro(),
                        a.getCidade(),
                        a.getCEP(),
                        a.getTelefone(),
                        a.getEmail()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Erro ao carregar tabela: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpar() {
        tela.getTxtId().setText("");
        tela.getTxtNome().setText("");
        tela.getTxtNomeSocial().setText("");
        tela.getTxtCPF().setText("");
        tela.getCbGenero().setSelectedIndex(0);
        tela.getCbAfro().setSelectedIndex(0);
        tela.getCbEscolaridade().setSelectedIndex(0);
        tela.getTxtdtNascimento().setText("");
        tela.getTxtNacionalidade().setText("");
        tela.getTxtResponsavel().setText("");
        tela.getTxtGrauDeParentesco().setText("");
        tela.getCbHabilitacao().setSelectedIndex(0);
        tela.getCbSerie().setSelectedIndex(0);
        tela.getCbPeriodo().setSelectedIndex(0);

        tela.getTxtRua().setText("");
        tela.getTxtComplemento().setText("");
        tela.getTxtApto().setText("");
        tela.getTxtBloco().setText("");
        tela.getTxtBairro().setText("");
        tela.getTxtCidade().setText("");
        tela.getTxtCep().setText("");
        tela.getTxtTel().setText("");
        tela.getTxtEmail().setText("");
        tela.getTxtEmail().setText("");
        tela.getTxtNome().requestFocus();
        tela.getTabelaAlunos().clearSelection();
    }

    public void preencherFormulario() {
        int linha = tela.getTabelaAlunos().getSelectedRow();

        if (linha != -1) {
            tela.getTxtId().setText(tela.getTabelaAlunos().getValueAt(linha, 0).toString());
            tela.getTxtNome().setText(tela.getTabelaAlunos().getValueAt(linha, 1).toString());
            
            Object nomeSocialObj = tela.getTabelaAlunos().getValueAt(linha, 2);
            tela.getTxtNomeSocial().setText(nomeSocialObj != null ? nomeSocialObj.toString() : "");
            
            tela.getTxtCPF().setText(tela.getTabelaAlunos().getValueAt(linha, 3).toString());
            tela.getCbGenero().setSelectedItem(tela.getTabelaAlunos().getValueAt(linha, 4).toString());
            tela.getCbAfro().setSelectedItem(tela.getTabelaAlunos().getValueAt(linha, 5).toString());
            tela.getCbEscolaridade().setSelectedItem(tela.getTabelaAlunos().getValueAt(linha, 6).toString());
            tela.getTxtdtNascimento().setText(tela.getTabelaAlunos().getValueAt(linha, 7).toString());
            tela.getTxtNacionalidade().setText(tela.getTabelaAlunos().getValueAt(linha, 8).toString());
            tela.getTxtResponsavel().setText(tela.getTabelaAlunos().getValueAt(linha, 9).toString());
            tela.getTxtGrauDeParentesco().setText(tela.getTabelaAlunos().getValueAt(linha, 10).toString());
            tela.getCbHabilitacao().setSelectedItem(tela.getTabelaAlunos().getValueAt(linha, 11).toString());
            tela.getCbSerie().setSelectedItem(tela.getTabelaAlunos().getValueAt(linha, 12).toString());
            tela.getCbPeriodo().setSelectedItem(tela.getTabelaAlunos().getValueAt(linha, 13).toString());
            
            tela.getTxtRua().setText(tela.getTabelaAlunos().getValueAt(linha, 14).toString());
            
            Object compObj = tela.getTabelaAlunos().getValueAt(linha, 15);
            tela.getTxtComplemento().setText(compObj != null ? compObj.toString() : "");
            
            Object aptoObj = tela.getTabelaAlunos().getValueAt(linha, 16);
            tela.getTxtApto().setText(aptoObj != null ? aptoObj.toString() : "");
            
            Object blocoObj = tela.getTabelaAlunos().getValueAt(linha, 17);
            tela.getTxtBloco().setText(blocoObj != null ? blocoObj.toString() : "");
            
            tela.getTxtBairro().setText(tela.getTabelaAlunos().getValueAt(linha, 18).toString());
            tela.getTxtCidade().setText(tela.getTabelaAlunos().getValueAt(linha, 19).toString());
            tela.getTxtCep().setText(tela.getTabelaAlunos().getValueAt(linha, 20).toString());
            tela.getTxtTel().setText(tela.getTabelaAlunos().getValueAt(linha, 21).toString());
            tela.getTxtEmail().setText(tela.getTabelaAlunos().getValueAt(linha, 22).toString());
        }
    }

}
