package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TelaPrincipalContabilidade extends JFrame {

  private final JTabbedPane abas = new JTabbedPane();

  public TelaPrincipalContabilidade() {
    super("ERP - Módulo Contabilidade");
    montar();
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setMinimumSize(new Dimension(920, 650));
    setSize(1100, 760);
    setLocationRelativeTo(null);
    addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          sair();
        }
      }
    );
  }

  private void montar() {
    setJMenuBar(criarMenu());
    JPanel inicio = new JPanel(new GridBagLayout());
    JLabel texto = new JLabel("Selecione uma funcionalidade no menu acima.");
    texto.setFont(texto.getFont().deriveFont(Font.BOLD, 18f));
    inicio.add(texto);
    abas.addTab("Inicio", inicio);
    add(abas, BorderLayout.CENTER);
    JPanel rodape = new JPanel(new BorderLayout());
    rodape.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
    rodape.add(
      new JLabel("Sistema ERP - Java Desktop"),
      BorderLayout.EAST
    );
    add(rodape, BorderLayout.SOUTH);
  }

  private JMenuBar criarMenu() {
    JMenuBar barra = new JMenuBar();
    
    JMenu cadastro = new JMenu("Cadastro"),
      registro = new JMenu("Registro"),
      controle = new JMenu("Controle"),
      rotinaFechamento = new JMenu("Rotina de Fechamento"),
      livro = new JMenu("Livros"),
      demonstracao = new JMenu("Demonstração"),
      visao = new JMenu("Visão"),
      obrigacaoFiscal = new JMenu("Obrigação Fiscal"),
      sistema = new JMenu("Sistema"),
      menuProvisorio = new JMenu("Provisório");
    
    JMenuItem planoContas = item("Plano de Contas", 1),
      centroCustos = item("Centro de Custo", 2),
      historicoPadrao = item("Histórico Padrão", 3),
      exerciciosSociais = item("Exercícios Sociais", 4),
      regrasContabilizacao = item("Regras de Contabilização", 5),
      aglutacao = item("Aglutação", 6),
      lancamentos = item("Lançamentos Contábeis", 7),
      lancamentosLote = item("Lançamentos em Lote", 8),
      integracao = item("Integração", 9),
      ajusteExercicio = item("Ajuste de Exercício", 10),
      lotes = item("Lotes", 11),
      conciliacaoContabil = item("Conciliação Contábil", 12),
      zeramentoContas= item("Zeramento de Contas", 13),
      reavaliacaoDepreciacao = item("Reavaliação e Depreciação", 14),
      consolidacaoBalancos = item("Consolidação de Balanços", 15),
      livroDiario = item("Livro Diário", 16),
      livroRazao = item("Livro Razão", 17),
      balanceteVerificacao = item("Balancete de Verificação", 18),
      bp = item("Balanço Patrimonial", 19),
      dre = item("DRE", 20),
      dfc = item("DFC", 21),
      dashboard = item("Dashboard", 22),
      ecd = item("ECD", 23),
      ecf = item("ECF", 24),
      auditoriaDigital = item("Auditoria Digital", 25),
      adicionarConta = item("Adicionar Conta - Plano de Contas", 26),
      gerenciamentoLote = item("Gerenciamento Lote - Lancamento em Lote", 26);
    
    cadastro.add(planoContas);
    cadastro.add(centroCustos);
    cadastro.add(historicoPadrao);
    cadastro.add(exerciciosSociais);
    cadastro.add(regrasContabilizacao);
    cadastro.add(aglutacao);
    
    registro.add(lancamentos);
    registro.add(lancamentosLote);
    registro.add(integracao);
    registro.add(ajusteExercicio);
    
    controle.add(lotes);
    
    rotinaFechamento.add(conciliacaoContabil);
    rotinaFechamento.add(zeramentoContas);
    rotinaFechamento.add(reavaliacaoDepreciacao);
    rotinaFechamento.add(consolidacaoBalancos);
    
    livro.add(livroDiario);
    livro.add(livroRazao);
    
    demonstracao.add(balanceteVerificacao);
    demonstracao.add(bp);
    demonstracao.add(dre);
    demonstracao.add(dfc);
    
    visao.add(dashboard);
    
    obrigacaoFiscal.add(ecd);
    obrigacaoFiscal.add(ecf);
    obrigacaoFiscal.add(auditoriaDigital);
    

    JMenuItem sobre = new JMenuItem("Sobre"),
      sair = new JMenuItem("Sair");
    sobre.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(
            TelaPrincipalContabilidade.this,
            "Sistema didatico de ERP\nEduardo Andrade\nEric Aragão\nGuilherme Oliveira\nGustavo Andrade\nGustavo Mitsuo\nGustavo Lameiras\nMariana Oliveira\nNicollas Domingues\nJava SE 6 + Swing + JDBC + MySQL",
            "Sobre",
            JOptionPane.INFORMATION_MESSAGE
          );
        }
      }
    );
    sair.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          sair();
        }
      }
    );
    sistema.add(sobre);
    sistema.addSeparator();
    sistema.add(sair);
    
    menuProvisorio.add(adicionarConta);
    menuProvisorio.add(gerenciamentoLote);
    
    barra.add(cadastro);
    barra.add(registro);
    barra.add(controle);
    barra.add(rotinaFechamento);
    barra.add(livro);
    barra.add(demonstracao);
    barra.add(visao);
    barra.add(obrigacaoFiscal);
    barra.add(sistema);
    barra.add(menuProvisorio);
    return barra;
  }

  private JMenuItem item(String titulo, final int modulo) {
    JMenuItem item = new JMenuItem(titulo);
    item.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          abrirModulo(modulo);
        }
      }
    );
    return item;
  }

  private void abrirModulo(int modulo) {
    String titulo;
    JPanel painel;
    if (modulo == 1) {
      titulo = "Plano de Contas";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 2) {
      titulo = "Centro de Custo";
      if (selecionar(titulo)) return;
      painel = new TelaCentroCustos();
    } else if (modulo == 3) {
      titulo = "Histórico Padrão";
      if (selecionar(titulo)) return;
      painel = new TelaHistoricoPadrao();
    } else if (modulo == 4) {
      titulo = "Exercícios Sociais";
      if (selecionar(titulo)) return;
      painel = new TelaExerciciosSociais();
    } else if (modulo == 5) {
      titulo = "Regras de Contabilização";
      if (selecionar(titulo)) return;
      painel = new TelaRegrasContabilizacao();
    } else if (modulo == 6) {
      titulo = "Aglutação";
      if (selecionar(titulo)) return;
      painel = new TelaAglutamento();
    }else if (modulo == 7) {
      titulo = "Lançamentos Contábeis";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 8) {
      titulo = "Lançamentos em Lote";
      if (selecionar(titulo)) return;
      painel = new TelaLancamentoLote();
    } else if (modulo == 9) {
      titulo = "Integração";
      if (selecionar(titulo)) return;
      painel = new TelaIntegracao();
    } else if (modulo == 10) {
      titulo = "Ajuste de Exercício";
      if (selecionar(titulo)) return;
      painel = new TelaAjusteExercicio();
    } else if (modulo == 11) {
      titulo = "Lotes";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 12) {
      titulo = "Conciliação Contábil";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 13) {
      titulo = "Zeramento de Contas";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 14) {
      titulo = "Reavaliação e Depreciação";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 15) {
      titulo = "Consolidação de Balanços";
      if (selecionar(titulo)) return;
      painel = new TelaConsolidacaoBalanco();
    } else if (modulo == 16) {
      titulo = "Livro Diário";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 17) {
      titulo = "Livro Razão";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 18) {
      titulo = "Balancete de Verificação";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 19) {
      titulo = "Balanço Patrimonial";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 20) {
      titulo = "DRE";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 21) {
      titulo = "DFC";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 22) {
      titulo = "Dashboard";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 23) {
      titulo = "ECD";
      if (selecionar(titulo)) return;
      painel = new TelaEcd();
    } else if (modulo == 24) {
      titulo = "ECF";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 25) {
      titulo = "Auditoria Digital";
      if (selecionar(titulo)) return;
      painel = new TelaPlanoDeContas();
    } else if (modulo == 26) {
      titulo = "Adicionar Conta - Plano de Contas";
      if (selecionar(titulo)) return;
      painel = new TelaAdicionarConta();
    } else {
      titulo = "Gerenciamento Lote - Lancamento em Lote";
      if (selecionar(titulo)) return;
      painel = new TelaGerenciamentoLote();
    }
    
    abrirAba(titulo, painel);
  }

  public void abrirAba(String titulo, JPanel painel) {
    int i = abas.indexOfTab(titulo);
    if (i >= 0) {
      abas.setSelectedIndex(i);
      return;
    }
    abas.addTab(titulo, painel);
    abas.setTabComponentAt(
      abas.indexOfComponent(painel),
      cabecalhoFechavel(titulo, painel)
    );
    abas.setSelectedComponent(painel);
  }

  private boolean selecionar(String titulo) {
    int i = abas.indexOfTab(titulo);
    if (i >= 0) {
      abas.setSelectedIndex(i);
      return true;
    }
    return false;
  }

  private JPanel cabecalhoFechavel(String titulo, final Component painel) {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
    p.setOpaque(false);
    p.add(new JLabel(titulo));
    JButton fechar = new JButton("x");
    fechar.setMargin(new Insets(0, 4, 0, 4));
    fechar.setToolTipText("Fechar aba");
    fechar.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          abas.remove(painel);
        }
      }
    );
    p.add(fechar);
    return p;
  }

  private void sair() {
    if (
      JOptionPane.showConfirmDialog(
        this,
        "Deseja encerrar o sistema?",
        "Sair",
        JOptionPane.YES_NO_OPTION
      ) == JOptionPane.YES_OPTION
    ) {
      dispose();
      System.exit(0);
    }
  }
}
