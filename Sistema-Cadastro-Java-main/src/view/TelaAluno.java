package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.swing.JComboBox;

import controller.AlunoController;


public class TelaAluno extends JFrame {

    private static final long serialVersionUID = 1L;
	private JLabel lblId;
    private JLabel lblNome;
    private JLabel lblNomeSocial;
    private JLabel lblCPF;
    private JLabel lblGenero;
    private JLabel lblAfrodescendencia;
    private JLabel lblEscolaridade;
    private JLabel lbldtNascimento;
    private JLabel lblNacionalidade;
    private JLabel lblResponsavel;
    private JLabel lblGrauDeParentesco;
    private JLabel lblHabilitacao;
    private JLabel lblSerie;
    private JLabel lblPeriodo;
    
    private JLabel lblRua;
    private JLabel lblComplemento;
    private JLabel lblApto;
    private JLabel lblAptoBloco;
    private JLabel lblBairro;
    private JLabel lblCidade;
    private JLabel lblCep;
    private JLabel lblTel;
    private JLabel lblEmail;


    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtNomeSocial;
    private JTextField txtCPF;
    private JComboBox<String> cbGenero;
    private JComboBox<String> cbAfro;
    private JComboBox<String> cbEscolaridade;
    private JTextField txtdtNascimento;
    private JTextField txtNacionalidade;
    private JTextField txtResponsavel;
    private JTextField txtGrauDeParentesco;
    private JComboBox<String> cbHabilitacao;
    private JComboBox<String> cbSerie;
    private JComboBox<String> cbPeriodo;
    
    private JTextField txtRua;
    private JTextField txtComplemento;
    private JTextField txtApto;
    private JTextField txtBloco;
    private JTextField txtBairro;
    private JTextField txtCidade;
    private JTextField txtCep;
    private JTextField txtTel;
    private JTextField txtEmail;
   
    
    private JButton btnNovo;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    
    private AlunoController controller;

    public TelaAluno() {
    	setTitle("Cadastro de Alunos");
    	setSize(800,700);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        criarComponentes();
        
        controller = new AlunoController(this);
        configurarEventos();
        controller.carregarTabela();
        
    }
    
    private void criarComponentes() {
    	JPanel painelFormulario = new JPanel(new GridLayout(0, 4, 20, 10));
    	painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados Dos Alunos"));
    	
    	lblId = new JLabel("ID:");
        txtId = new JTextField();
        txtId.setEditable(false);

        lblNome = new JLabel("Nome:");
        txtNome = new JTextField();
        
        lblNomeSocial = new JLabel("NomeSocial:");
        txtNomeSocial = new JTextField();
        
        lblCPF = new JLabel("CPF:");
        txtCPF = new JTextField();
        
        lblGenero = new JLabel("Genero:");
        String[] opcoesGenero = {
        	    "Selecione",
        	    "Masculino",
        	    "Feminino",
        	    "Outro",
        	    "Prefiro não informar"
        	};
        cbGenero = new JComboBox<>(opcoesGenero);
        
        lblAfrodescendencia = new JLabel("Afrodescendencia:");
        String[] opcoesAfro = {
        	    "Selecione",
        	    "Sim",
        	    "Não"
    
        	};
        cbAfro = new JComboBox<>(opcoesAfro);
        
        lblEscolaridade = new JLabel("Escolaridade pública:");
        String[] opcoesEscolaridade = {
        	    "Selecione",
        	    "Sim",
        	    "Não"
        	};
        cbEscolaridade = new JComboBox<>(opcoesEscolaridade);
        
        lbldtNascimento = new JLabel("Data de Nascimento:");
        txtdtNascimento = new JTextField();
        
        lblNacionalidade = new JLabel("Nacionalidade:");
        txtNacionalidade = new JTextField();
        
        lblResponsavel = new JLabel("Nome Do Responsavel:");
        txtResponsavel = new JTextField();
        
        lblGrauDeParentesco = new JLabel("Grau De Parentesco:");
        txtGrauDeParentesco = new JTextField();
        
        lblHabilitacao = new JLabel("Habilitação:");
        String[] opcoesHabilitacao = {
        	    "Selecione",
        	    "Desenvolvimento De Sistemas",
        	    "Administração",
        	    "Logística"
        	   
        	};
        cbHabilitacao = new JComboBox<>(opcoesHabilitacao);
        
        lblSerie = new JLabel("Serie:");
        String[] opcoesSerie = {
        	    "Selecione",
        	    "1° Ano",
        	    "2° Ano",
        	    "3° Ano"
        	    
        	};
        cbSerie = new JComboBox<>(opcoesSerie);
        
        lblPeriodo = new JLabel("Periodo:");
        String[] opcoesPeriodo = {
        	    "Selecione",
        	    "Manhã",
        	    "Tarde",
        	    "Noite"
        	};
        cbPeriodo = new JComboBox<>(opcoesPeriodo);
        
        lblRua = new JLabel("Rua:");
        txtRua = new JTextField();
        
        lblComplemento = new JLabel("Complemento:");
        txtComplemento = new JTextField();
        
        lblApto = new JLabel("Apto:");
        txtApto = new JTextField();
        
        lblAptoBloco = new JLabel("Bloco:");
        txtBloco = new JTextField();
        
        lblBairro = new JLabel("Bairro:");
        txtBairro = new JTextField();
        
        lblCidade = new JLabel("Cidade:");
        txtCidade = new JTextField();
        
        lblCep = new JLabel("CEP:");
        txtCep = new JTextField();
        
        lblTel = new JLabel("Telefone: DDD + número");
        txtTel = new JTextField();
        
        lblEmail = new JLabel("E-mail:");
        txtEmail = new JTextField();
        
        painelFormulario.add(lblId);
        painelFormulario.add(txtId);
        painelFormulario.add(lblNome);
        painelFormulario.add(txtNome);
        painelFormulario.add(lblNomeSocial);
        painelFormulario.add(txtNomeSocial);
        painelFormulario.add(lblCPF);
        painelFormulario.add(txtCPF);
        painelFormulario.add(lblGenero);
        painelFormulario.add(cbGenero);
        painelFormulario.add(lblAfrodescendencia);
        painelFormulario.add(cbAfro);
        painelFormulario.add(lblEscolaridade);
        painelFormulario.add(cbEscolaridade);
        painelFormulario.add(lbldtNascimento);
        painelFormulario.add(txtdtNascimento);
        painelFormulario.add(lblNacionalidade);
        painelFormulario.add(txtNacionalidade);
        painelFormulario.add(lblResponsavel);
        painelFormulario.add(txtResponsavel);
        painelFormulario.add(lblGrauDeParentesco);
        painelFormulario.add(txtGrauDeParentesco);
        
        painelFormulario.add(lblHabilitacao);
        painelFormulario.add(cbHabilitacao);
        painelFormulario.add(lblSerie);
        painelFormulario.add(cbSerie);
        painelFormulario.add(lblPeriodo);
        painelFormulario.add(cbPeriodo);
        
        painelFormulario.add(lblRua);
        painelFormulario.add(txtRua);
        painelFormulario.add(lblComplemento);
        painelFormulario.add(txtComplemento);
        painelFormulario.add(lblApto);
        painelFormulario.add(txtApto);
        
        painelFormulario.add(lblAptoBloco);
        painelFormulario.add(txtBloco);
        painelFormulario.add(lblBairro);
        painelFormulario.add(txtBairro);
        painelFormulario.add(lblCidade);
        painelFormulario.add(txtCidade);
        
        painelFormulario.add(lblCep);
        painelFormulario.add(txtCep);
        painelFormulario.add(lblTel);
        painelFormulario.add(txtTel);
        painelFormulario.add(lblEmail);
        painelFormulario.add(txtEmail);
        
        add(painelFormulario, BorderLayout.NORTH);
        
        modeloTabela = new DefaultTableModel(new Object[] { 
        		"ID", "Nome", "Nome Social","CPF","Genero","Afrodescendencia","Escolaridade",
        		"Data de Nascimento","Nacionalidade","Responsavel","Grau de Parentesco", "Habilitação",
        		"Série", "Período", "Rua", "Complemento", "Apto", "Bloco", "Bairro", "Cidade", "Cep",
        		"Telefone", "E-mail"

        }, 0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        

        tabelaAlunos = new JTable(modeloTabela);
        tabelaAlunos.setRowHeight(25);
        
        tabelaAlunos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabelaAlunos.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        add(painelBotoes, BorderLayout.SOUTH);
        
    }
    
    private void configurarEventos() {
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                controller.limpar();
            }
        });

        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                controller.salvar();
            }
        });

        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                controller.excluir();
            }
        });

        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                controller.limpar();
            }
        });

        tabelaAlunos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                controller.preencherFormulario();
            }
        });
    }
    
    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JTextField getTxtNomeSocial() {
        return txtNomeSocial;
    }
    
    public JTextField getTxtCPF() {
        return txtCPF;
    }

    public JComboBox<String> getCbGenero() {
        return cbGenero;
    }

    public JComboBox<String> getCbAfro() {
        return cbAfro;
    }
    
    public JComboBox<String> getCbEscolaridade() {
        return cbEscolaridade;
    }

    public JTextField getTxtdtNascimento() {
        return txtdtNascimento;
    }

    public JTextField getTxtNacionalidade() {
        return txtNacionalidade;
    }
    
    public JTextField getTxtResponsavel() {
        return txtResponsavel;
    }

    public JTextField getTxtGrauDeParentesco() {
        return txtGrauDeParentesco;
    }

    public JComboBox<String> getCbHabilitacao() {
        return cbHabilitacao;
    }
    
    public JComboBox<String> getCbSerie() {
        return cbSerie;
    }

    public JComboBox<String> getCbPeriodo() {
        return cbPeriodo;
    }

    public JTextField getTxtRua() {
        return txtRua;
    }
    
    public JTextField getTxtComplemento() {
        return txtComplemento;
    }

    public JTextField getTxtApto() {
        return txtApto;
    }

    public JTextField getTxtBairro() {
        return txtBairro;
    }
    
    public JTextField getTxtCidade() {
        return txtCidade;
    }

    public JTextField getTxtCEP() {
        return txtCep;
    }

    public JTextField getTxtTel() {
        return txtTel;
    }
    
    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTable getTabelaAlunos() {
        return tabelaAlunos;
    }

    public JTextComponent getTxtBloco() {
       return txtBloco;
    }

    public JTextComponent getTxtCep() {
        return txtCep;
    }
    
}
