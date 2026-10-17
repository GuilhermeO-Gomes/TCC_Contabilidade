package view;

import java.awt.*;
import java.awt.event.*;
import java.util.function.Supplier;

import javax.swing.*;

import model.DadosContabeis;

import view.TelaAuditoriaDigital;
import view.TelaDashboard;
import view.TelaLivroDiario;
import view.TelaGeracaoECF;

public class TelaPrincipalContabilidade extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JTabbedPane abas = new JTabbedPane();

   
    private final DadosContabeis dados = new DadosContabeis();

    public TelaPrincipalContabilidade() {

        super("ERP - Módulo Contabilidade");

        montar();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setMinimumSize(
            new Dimension(920, 650)
        );

        setSize(
            1100,
            760
        );

        setLocationRelativeTo(null);

        addWindowListener(
            new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    sair();
                }
            }
        );
    }


    

    private void montar() {

        setLayout(
            new BorderLayout()
        );

        setJMenuBar(
            criarMenu()
        );


        

        JPanel inicio =
            new JPanel(
                new GridBagLayout()
            );

        JLabel texto =
            new JLabel(
                "Selecione uma funcionalidade no menu acima."
            );

        texto.setFont(
            texto
                .getFont()
                .deriveFont(
                    Font.BOLD,
                    18f
                )
        );

        inicio.add(texto);

        abas.addTab(
            "Início",
            inicio
        );


        add(
            abas,
            BorderLayout.CENTER
        );


        

        JPanel rodape =
            new JPanel(
                new BorderLayout()
            );

        rodape.setBorder(
            BorderFactory.createEmptyBorder(
                4,
                8,
                4,
                8
            )
        );

        rodape.add(
            new JLabel(
                "Sistema ERP - Módulo Contabilidade"
            ),
            BorderLayout.EAST
        );

        add(
            rodape,
            BorderLayout.SOUTH
        );
    }


    

    private JMenuBar criarMenu() {

        JMenuBar barra =
            new JMenuBar();


        

        JMenu cadastro =
            new JMenu("Cadastro");

        JMenu registro =
            new JMenu("Registro");

        JMenu controle =
            new JMenu("Controle");

        JMenu patrimonio =
            new JMenu("Patrimônio");

        JMenu rotinaFechamento =
            new JMenu("Rotina de Fechamento");

        JMenu livro =
            new JMenu("Livros");

        JMenu demonstracao =
            new JMenu("Demonstração");

        JMenu visao =
            new JMenu("Visão");

        JMenu obrigacaoFiscal =
            new JMenu("Obrigação Fiscal");

        JMenu sistema =
            new JMenu("Sistema");


       

        cadastro.add(
            item(
                "Plano de Contas",
                () -> abrirPainel(
                    "Plano de Contas",
                    TelaPlanoDeContas::new
                )
            )
        );


        cadastro.add(
            item(
                "Centro de Custo",
                () -> abrirPainel(
                    "Centro de Custo",
                    TelaCentroCustos::new
                )
            )
        );


        cadastro.add(
            item(
                "Histórico Padrão",
                () -> abrirPainel(
                    "Histórico Padrão",
                    TelaHistoricoPadrao::new
                )
            )
        );


        cadastro.add(
            item(
                "Exercícios Sociais",
                () -> abrirPainel(
                    "Exercícios Sociais",
                    TelaExerciciosSociais::new
                )
            )
        );


        cadastro.add(
            item(
                "Regras de Contabilização",
                () -> abrirPainel(
                    "Regras de Contabilização",
                    TelaRegrasContabilizacao::new
                )
            )
        );


        cadastro.add(
            item(
                "Aglutinação",
                () -> abrirPainel(
                    "Aglutinação",
                    TelaAglutinacao::new
                )
            )
        );


        

        registro.add(
            item(
                "Lançamentos Contábeis",
                () -> abrirPainel(
                    "Lançamentos Contábeis",
                    TelaLancamentos::new
                )
            )
        );


        registro.add(
            item(
                "Integração Contábil",
                () -> abrirPainel(
                    "Integração Contábil",
                    TelaIntegracao::new
                )
            )
        );


        registro.add(
            item(
                "Ajuste de Exercício",
                () -> abrirPainel(
                    "Ajuste de Exercício",
                    TelaAjusteExercicio::new
                )
            )
        );


        

        controle.add(
            item(
                "Gerenciamento de Lotes",
                () -> abrirPainel(
                    "Gerenciamento de Lotes",
                    TelaGerenciamentoLote::new
                )
            )
        );


      

        patrimonio.add(
            item(
                "Depreciação",
                () -> abrirPainel(
                    "Depreciação",
                    TelaDepreciacao::new
                )
            )
        );


        patrimonio.add(
            item(
                "Reavaliação",
                this::abrirReavaliacao
            )
        );


       

        rotinaFechamento.add(
            item(
                "Conciliação Contábil",
                () -> abrirPainel(
                    "Conciliação Contábil",
                    TelaConciliacaoContabil::new
                )
            )
        );


        rotinaFechamento.add(
            item(
                "Zeramento de Contas",
                () -> abrirPainel(
                    "Zeramento de Contas",
                    TelaZeramentoContas::new
                )
            )
        );


        rotinaFechamento.add(
            item(
                "Consolidação de Balanços",
                () -> abrirPainel(
                    "Consolidação de Balanços",
                    TelaConsolidacaoBalanco::new
                )
            )
        );


        

        livro.add(
            item(
                "Livro Diário",
                () -> abrirPainel(
                    "Livro Diário",
                    () -> new TelaLivroDiario(
                        dados,
                        this::atualizarTelasDependentes
                    )
                )
            )
        );



        livro.add(
            item(
                "Livro Razão",
                this::abrirLivroRazao
            )
        );


      

        demonstracao.add(
            item(
                "Balancete de Verificação",
                () -> abrirPainel(
                    "Balancete de Verificação",
                    TelaBalancete::new
                )
            )
        );


        demonstracao.add(
            item(
                "Balanço Patrimonial",
                () -> abrirPainel(
                    "Balanço Patrimonial",
                    TelaBalancoPatrimonial::new
                )
            )
        );


        demonstracao.add(
            item(
                "DRE",
                () -> abrirPainel(
                    "DRE",
                    TelaDRE::new
                )
            )
        );


        demonstracao.add(
            item(
                "DFC",
                () -> abrirPainel(
                    "DFC",
                    TelaDFC::new
                )
            )
        );


     

        visao.add(
            item(
                "Dashboard",
                () -> abrirPainel(
                    "Dashboard",
                    () -> new TelaDashboard(dados)
                )
            )
        );



        obrigacaoFiscal.add(
            item(
                "ECD",
                () -> abrirPainel(
                    "ECD",
                    TelaEcd::new
                )
            )
        );


        obrigacaoFiscal.add(
            item(
                "ECF",
                () -> abrirPainel(
                    "ECF",
                    () -> new TelaGeracaoECF(dados)
                )
            )
        );


        obrigacaoFiscal.addSeparator();


        obrigacaoFiscal.add(
            item(
                "Auditoria Digital",
                () -> abrirPainel(
                    "Auditoria Digital",
                    () -> new TelaAuditoriaDigital(
                        dados,
                        this::atualizarTelasDependentes
                    )
                )
            )
        );


      

        JMenuItem sobre =
            new JMenuItem("Sobre");

        JMenuItem sair =
            new JMenuItem("Sair");


        sobre.addActionListener(
            e -> mostrarSobre()
        );


        sair.addActionListener(
            e -> sair()
        );


        sistema.add(sobre);

        sistema.addSeparator();

        sistema.add(sair);


      

        barra.add(cadastro);

        barra.add(registro);

        barra.add(controle);

        barra.add(patrimonio);

        barra.add(rotinaFechamento);

        barra.add(livro);

        barra.add(demonstracao);

        barra.add(visao);

        barra.add(obrigacaoFiscal);

        barra.add(sistema);


        return barra;
    }




    private JMenuItem item(
        String titulo,
        Runnable acao
    ) {

        JMenuItem item =
            new JMenuItem(titulo);

        item.addActionListener(
            e -> acao.run()
        );

        return item;
    }
    
    

    private void abrirPainel(
        String titulo,
        Supplier<JPanel> fornecedor
    ) {


        int indice =
            abas.indexOfTab(titulo);

        if (indice >= 0) {

            abas.setSelectedIndex(indice);

            return;
        }


        try {

            JPanel painel =
                fornecedor.get();

            abrirAba(
                titulo,
                painel
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Não foi possível abrir a funcionalidade:\n"
                    + titulo
                    + "\n\n"
                    + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public void abrirAba(
        String titulo,
        JPanel painel
    ) {

        int indice =
            abas.indexOfTab(titulo);

        if (indice >= 0) {

            abas.setSelectedIndex(indice);

            return;
        }


        abas.addTab(
            titulo,
            painel
        );


        abas.setTabComponentAt(
            abas.indexOfComponent(painel),
            cabecalhoFechavel(
                titulo,
                painel
            )
        );


        abas.setSelectedComponent(
            painel
        );
    }


    private JPanel cabecalhoFechavel(
        String titulo,
        final Component painel
    ) {

        JPanel cabecalho =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT,
                    3,
                    0
                )
            );

        cabecalho.setOpaque(false);


        JLabel label =
            new JLabel(titulo);

        cabecalho.add(label);


        JButton fechar =
            new JButton("x");

        fechar.setMargin(
            new Insets(
                0,
                4,
                0,
                4
            )
        );

        fechar.setFocusable(false);

        fechar.setToolTipText(
            "Fechar aba"
        );


        fechar.addActionListener(
            e -> abas.remove(painel)
        );


        cabecalho.add(fechar);


        return cabecalho;
    }


    private void abrirLivroRazao() {

        TelaLivroRazao dialogo =
            new TelaLivroRazao(this);

        dialogo.setVisible(true);
    }


    private void abrirReavaliacao() {

        String valor =
            JOptionPane.showInputDialog(
                this,
                "Informe o ID do bem patrimonial:",
                "Reavaliação de Bem",
                JOptionPane.QUESTION_MESSAGE
            );



        if (valor == null) {
            return;
        }


        valor =
            valor.trim();


        if (valor.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Informe o ID do bem.",
                "Reavaliação",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        try {

            int idBem =
                Integer.parseInt(valor);


            if (idBem <= 0) {

                throw new NumberFormatException();
            }


            String titulo =
                "Reavaliação - Bem " + idBem;


            final int idSelecionado =
                idBem;


            abrirPainel(
                titulo,
                () -> new TelaReavaliacao(
                    idSelecionado
                )
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "O ID do bem deve ser um número inteiro positivo.",
                "Valor inválido",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }



    private void atualizarTelasDependentes() {

        for (
            int i = 0;
            i < abas.getTabCount();
            i++
        ) {

            Component componente =
                abas.getComponentAt(i);



            if (
                componente
                    instanceof TelaDashboard
            ) {

                TelaDashboard tela =
                    (TelaDashboard) componente;

                tela.atualizar_tela();
            }


            else if (
                componente
                    instanceof TelaLivroDiario
            ) {

                TelaLivroDiario tela =
                    (TelaLivroDiario) componente;

                tela.atualizar_tabela();
            }



            else if (
                componente
                    instanceof TelaGeracaoECF
            ) {

                TelaGeracaoECF tela =
                    (TelaGeracaoECF) componente;

                tela.atualizar_dados();
            }


  

            else if (
                componente
                    instanceof TelaAuditoriaDigital
            ) {

                TelaAuditoriaDigital tela =
                    (TelaAuditoriaDigital) componente;

                tela.atualizar_tabela();
            }
        }
    }



    private void mostrarSobre() {

        JOptionPane.showMessageDialog(
            this,

            "Sistema didático de ERP\n"
                + "Módulo de Contabilidade\n\n"
                + "Eduardo Andrade\n"
                + "Eric Aragão\n"
                + "Guilherme Oliveira\n"
                + "Gustavo Andrade\n"
                + "Gustavo Mitsuo\n"
                + "Gustavo Lameiras\n"
                + "Mariana Oliveira\n"
                + "Nicollas Domingues\n\n"
                + "Java 8+ + Swing + JDBC + MySQL",

            "Sobre",

            JOptionPane.INFORMATION_MESSAGE
        );
    }



    private void sair() {

        int resposta =
            JOptionPane.showConfirmDialog(
                this,
                "Deseja encerrar o sistema?",
                "Sair",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );


        if (
            resposta
                == JOptionPane.YES_OPTION
        ) {

            dispose();

            System.exit(0);
        }
    }
}