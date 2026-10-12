package view;

import java.awt.*;
import javax.swing.*;

public class TelaEcd extends JPanel {

    private final JTextField txtPeriodoInicial = new JTextField(10),
            txtPeriodoFinal = new JTextField(10),
            txtNumeroOrdemAL = new JTextField(10),
            txtDataArquivamento = new JTextField(10),
            txtDataConversao = new JTextField(10),
            txtNumeroOrdemIA = new JTextField(10),
            txtCodigoHash = new JTextField(10),
            txtDestino = new JTextField(30),

            txtNomeEmpresarial = new JTextField(30),
            txtCnpj = new JTextField(10),
            txtCpf = new JTextField(10),
            txtInscricaoEstadual = new JTextField(10),
            txtInscricaoMunicipal = new JTextField(10),
            txtInscricaoSuframa = new JTextField(10),
            txtEndereco = new JTextField(30),
            txtNumero = new JTextField(10),
            txtComplemento = new JTextField(20),
            txtBairro = new JTextField(30),
            txtMunicipio = new JTextField(20),
            txtCep = new JTextField(10),
            txtTelefoneE = new JTextField(10),
            txtFax = new JTextField(10),

            txtNomeS = new JTextField(30),
            txtCpfS = new JTextField(30),
            txtQualificacao = new JTextField(30),
            
            txtCrc = new JTextField(30),
            txtNumeroSequencial = new JTextField(30),
            txtValidadeCrc = new JTextField(30),
            txtEmailCrc = new JTextField(30),
            txtTelefoneCrc = new JTextField(30);

    private final JButton btnProcessar = new JButton("Processar");

    private final JComboBox<String> cbxFormaEscrituracao = new JComboBox<>(
            new String[]{
                    "G = Livro diário (Completo sem escrituração auxiliar)","R = Livro Diário com escrituração Resumida (com escrituração auxiliar)",
                    "A = Livro Diário Auxiliar ao Diário com Escrituração Resumida",
                    "B = Livro Balancetes Diários e Balanços",
                    "Z = Razão Auxiliar"
            }),
    		cbxUfE = new JComboBox<>(new String[]{
    				"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    		}),
    		cbxUfCrc = new JComboBox<>(new String[]{
    				"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    		}),
    		cbxResponsavel= new JComboBox<>(new String[]{"Sim", "Não"});

    private final JComboBox<String> cbxCodigoQualificacao = new JComboBox<>(
            new String[]{
            		"001 - Signatário da ECD",
            	    "201 - Signatário",
            	    "202 - Diretor",
            	    "203 - Diretor",
            	    "204 - Conselheiro",
            	    "205 - Administrador",
            	    "206 - Sócio",
            	    "207 - Representante Legal",
            	    "208 - Procurador",
            	    "209 - Outro representante legal",
            	    "226 - Gestor Judicial",
            	    "305 - Interventor",
            	    "309 - Procurador",
            	    "312 - Inventariante",
            	    "313 - Liquidante",
            	    "315 - Interventor",
            	    "401 - Titular - Pessoa Física - EIRELI",
            	    "801 - Empresário",
            	    "900 - Contador/Contabilista",
            	    "999 - Outros"
            	});

    private final JTabbedPane abas = new JTabbedPane();

    public TelaEcd() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
    }

    private void montar() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 4, 3, 4);
        g.anchor = GridBagConstraints.WEST;

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        JScrollPane painel = new JScrollPane(conteudo);

        JPanel periodoGer = new JPanel(new GridBagLayout());
        periodoGer.setBorder(
                BorderFactory.createTitledBorder("Período de Geração:")
        );
        periodoGer.add(txtPeriodoInicial);
        periodoGer.add(new JLabel(" até "));
        periodoGer.add(txtPeriodoFinal);

        JPanel FormaEC = new JPanel(new GridBagLayout());
        FormaEC.setBorder(
                BorderFactory.createTitledBorder("Forma de Escrituração Contábil:")
        );
        adicionar(FormaEC, g, 0, "Forma de Escrituração Contábil:", cbxFormaEscrituracao);

        JPanel dadosAL = new JPanel(new GridBagLayout());
        dadosAL.setBorder(
                BorderFactory.createTitledBorder("Dados para a Abertura do Livro:")
        );
        adicionar(dadosAL, g, 0, "Número da Ordem:", txtNumeroOrdemAL);
        adicionar(dadosAL, g, 1, "Data de Arquivamento:", txtDataArquivamento);
        adicionar(dadosAL, g, 2, "Data de conversão:", txtDataConversao);

        JPanel instrumentoA = new JPanel(new GridBagLayout());
        instrumentoA.setBorder(
                BorderFactory.createTitledBorder("Instrumento Associado:")
        );
        adicionar(instrumentoA, g, 0, "Número de Ordem:", txtNumeroOrdemIA);
        adicionar(instrumentoA, g, 1, "Código Hash:", txtCodigoHash);

        JPanel arquivo = new JPanel(new GridBagLayout());
        arquivo.setBorder(
                BorderFactory.createTitledBorder("Arquivo:")
        );
        adicionar(arquivo, g, 0, "Destino:", txtDestino);

        JPanel dadosE = new JPanel(new GridBagLayout());
        dadosE.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        adicionar(dadosE, g, 0, "Nome Empresarial:", txtNomeEmpresarial);
        adicionar(dadosE, g, 1, "CNPJ:", txtCnpj);
        adicionar(dadosE, g, 2, "CPF:", txtCpf);
        adicionar(dadosE, g, 3, "Inscrição Estadual:", txtInscricaoEstadual);
        adicionar(dadosE, g, 4, "Inscrição Municipal:", txtInscricaoMunicipal);
        adicionar(dadosE, g, 5, "Inscrição SUFRAMA:", txtInscricaoSuframa);
        adicionar(dadosE, g, 6, "Endereço:", txtEndereco);
        adicionar(dadosE, g, 7, "Número:", txtNumero);
        adicionar(dadosE, g, 8, "Complemento:", txtComplemento);
        adicionar(dadosE, g, 9, "Bairro:", txtBairro);
        adicionar(dadosE, g, 10, "Município:", txtMunicipio);
        adicionar(dadosE, g, 11, "CEP:", txtCep);
        adicionar(dadosE, g, 12, "UF:", cbxUfE);
        adicionar(dadosE, g, 13, "Telefone:", txtTelefoneE);
        adicionar(dadosE, g, 14, "Fax:", txtFax);
        
        JPanel dadosS = new JPanel(new GridBagLayout());
        dadosS.setBorder(
                BorderFactory.createTitledBorder("Dados do Signatário")
        );

        GridBagConstraints gSignatario = new GridBagConstraints();
        gSignatario.insets = new Insets(3, 4, 3, 4);
        gSignatario.anchor = GridBagConstraints.WEST;

        adicionar(dadosS, gSignatario, 0, "Nome:", txtNomeS);
        adicionar(dadosS, gSignatario, 1, "CPF/CNPJ:", txtCpfS);
        adicionar(dadosS, gSignatario, 2, "Codigo de Qualificação:", cbxCodigoQualificacao);
        adicionar(dadosS, gSignatario, 3, "Qualificação:", txtQualificacao);
        
        
        JPanel dadosP = new JPanel(new GridBagLayout());
        dadosP.setBorder(
                BorderFactory.createTitledBorder("Dados do Profissional")
        );
        GridBagConstraints gDP = new GridBagConstraints();
        gDP.insets = new Insets(3, 4, 3, 4);
        gDP.anchor = GridBagConstraints.WEST;
        
        adicionar(dadosP, gDP, 0, "CRC:", txtCrc);
        adicionar(dadosP, gDP, 1, "UF do CRC:", cbxUfCrc);
        adicionar(dadosP, gDP, 2, "Número Sequencial:", txtNumeroSequencial);
        adicionar(dadosP, gDP, 3, "Validade CRC:", txtValidadeCrc);
        adicionar(dadosP, gDP, 4, "E-mail:", txtEmailCrc);
        adicionar(dadosP, gDP, 5, "Telefone:", txtTelefoneCrc);
        
        

        JPanel abaSignatario = new JPanel(new BorderLayout());
        abaSignatario.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JPanel conteudoSignatario = new JPanel();
        conteudoSignatario.setLayout(
                new BoxLayout(conteudoSignatario, BoxLayout.Y_AXIS)
        );
        dadosS.setAlignmentX(Component.CENTER_ALIGNMENT);
        dadosP.setAlignmentX(Component.CENTER_ALIGNMENT);
        conteudoSignatario.add(dadosS);
        conteudoSignatario.add(dadosP);
        adicionar(conteudoSignatario, gSignatario, 0, "Responsável pela Assinatura da ECD:", cbxResponsavel);

        JPanel botaoSignatario = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoSignatario.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoSignatario.setVisible(false);
        conteudoSignatario.add(botaoSignatario);
        abaSignatario.add(conteudoSignatario, BorderLayout.NORTH);

        JPanel butao = new JPanel(new GridBagLayout());
        butao.add(btnProcessar);

        JPanel topo = new JPanel();
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        JPanel topo2 = new JPanel(new GridLayout(1, 2, 8, 0));
        JPanel topo3 = new JPanel(new BorderLayout());

        topo.add(periodoGer);
        topo.add(FormaEC);
        topo2.add(dadosAL);
        topo2.add(instrumentoA);
        topo3.add(arquivo);

        conteudo.add(topo);
        conteudo.add(Box.createVerticalStrut(5));
        conteudo.add(topo2);
        conteudo.add(Box.createVerticalStrut(5));
        conteudo.add(topo3);
        conteudo.add(Box.createVerticalStrut(5));
        conteudo.add(abas);
        conteudo.add(butao);

        abas.addTab("Dados da Entidade", dadosE);
        abas.addTab("Signatário da Escrituração", abaSignatario);

        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 1) {
                butao.remove(btnProcessar);
                butao.setVisible(false);
                botaoSignatario.add(btnProcessar);
                botaoSignatario.setVisible(true);
            } else {
                botaoSignatario.remove(btnProcessar);
                botaoSignatario.setVisible(false);
                butao.add(btnProcessar);
                butao.setVisible(true);
            }

            conteudo.revalidate();
            conteudo.repaint();
        });

        add(painel, BorderLayout.CENTER);
    }

    private void adicionar(
            JPanel p,
            GridBagConstraints g,
            int y,
            String rotulo,
            JComponent componente
    ) {
        g.gridx = 0;
        g.gridy = y;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        p.add(new JLabel(rotulo), g);

        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        p.add(componente, g);
    }
}