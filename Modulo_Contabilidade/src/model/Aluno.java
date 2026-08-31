package model;

//para fazer que o atributo data de nascimento seja feito corretamente
import java.sql.Date;

public class Aluno {
	private int id;
    private String nome;
	private String nome_social; //
    private String cpf;
    private String genero;
    private boolean afrodescendente;
    private boolean escolaridade_publica;
    private Date data_nascimento;
    private String nacionalidade;
    private String responsavel_legal;
    private String grau_parentesco;
    private String habilitacao;
    private String serie;
    private String periodo;
    private String rua;
    private String complemento;
    private String apto;
    private String bloco;
    private String bairro;
    private String cidade;
    private String CEP;
    private String telefone;
    private String email;
    
    

    public Aluno() {
    }

    public Aluno(String nome, String cpf, String genero, boolean afrodescendente, boolean escolaridade_publica, Date data_nascimento, String nacionalidade, String responsavel_legal, String grau_parentesco, String habilitacao, String serie, String periodo, String rua, String bairro, String cidade, String CEP, String telefone, String email ) {
        this.nome = nome;
        this.cpf = cpf;
        this.genero = genero;
        this.afrodescendente = afrodescendente;
        this.escolaridade_publica = escolaridade_publica;
        this.data_nascimento = data_nascimento;
        this.nacionalidade = nacionalidade;
        this.responsavel_legal = responsavel_legal;
        this.grau_parentesco = grau_parentesco;
        this.habilitacao = habilitacao;
        this.serie = serie;
        this.periodo = periodo;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.CEP = CEP;
        this.telefone = telefone;
        this.email = email;
    }

	// Mitsuo 
    public Aluno(String nome, String nome_social, String cpf, String genero, boolean afrodescendente, boolean escolaridade_publica, Date data_nascimento, String nacionalidade, String responsavel_legal, String grau_parentesco, String habilitacao, String serie, String periodo, String rua, String complemento, String apto, String bloco, String bairro, String cidade, String CEP, String telefone, String email) {
        this.nome = nome;
        this.nome_social = nome_social;
        this.cpf = cpf;
        this.genero = genero;
        this.afrodescendente = afrodescendente;
        this.escolaridade_publica = escolaridade_publica;
        this.data_nascimento = data_nascimento;
        this.nacionalidade = nacionalidade;
        this.responsavel_legal = responsavel_legal;
        this.grau_parentesco = grau_parentesco;
        this.habilitacao = habilitacao;
        this.serie = serie;
        this.periodo = periodo;
        this.rua = rua;
        this.complemento = complemento;
        this.apto = apto;
        this.bloco = bloco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.CEP = CEP;
        this.telefone = telefone;
        this.email = email;
    }

	    public Aluno(int id, String nome, String nome_social, String cpf, String genero, boolean afrodescendente, boolean escolaridade_publica, Date data_nascimento, String nacionalidade, String responsavel_legal, String grau_parentesco, String habilitacao, String serie, String periodo, String rua, String complemento, String apto, String bloco, String bairro, String cidade, String CEP, String telefone, String email) {
        this.id = id;
		this.nome = nome;
        this.nome_social = nome_social;
        this.cpf = cpf;
        this.genero = genero;
        this.afrodescendente = afrodescendente;
        this.escolaridade_publica = escolaridade_publica;
        this.data_nascimento = data_nascimento;
        this.nacionalidade = nacionalidade;
        this.responsavel_legal = responsavel_legal;
        this.grau_parentesco = grau_parentesco;
        this.habilitacao = habilitacao;
        this.serie = serie;
        this.periodo = periodo;
        this.rua = rua;
        this.complemento = complemento;
        this.apto = apto;
        this.bloco = bloco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.CEP = CEP;
        this.telefone = telefone;
        this.email = email;
    }

    public Aluno(int id, String nome, String cpf, String genero, boolean afrodescendente, boolean escolaridade_publica, Date data_nascimento, String nacionalidade, String responsavel_legal, String grau_parentesco, String habilitacao, String serie, String periodo, String rua, String bairro, String cidade, String CEP, String telefone, String email ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.genero = genero;
        this.afrodescendente = afrodescendente;
        this.escolaridade_publica = escolaridade_publica;
        this.data_nascimento = data_nascimento;
        this.nacionalidade = nacionalidade;
        this.responsavel_legal = responsavel_legal;
        this.grau_parentesco = grau_parentesco;
        this.habilitacao = habilitacao;
        this.serie = serie;
        this.periodo = periodo;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.CEP = CEP;
        this.telefone = telefone;
        this.email = email;
    }
    
    public int getId() {
        return id;
    }

    public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getApto() {
		return apto;
	}

	public void setApto(String apto) {
		this.apto = apto;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_social() {
		return nome_social;
	}

	public void setNome_social(String nome_social) {
		this.nome_social = nome_social;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public boolean isAfrodescendente() {
		return afrodescendente;
	}

	public void setAfrodescendente(boolean afrodescendente) {
		this.afrodescendente = afrodescendente;
	}

	public boolean isEscolaridade_publica() {
		return escolaridade_publica;
	}

	public void setEscolaridade_publica(boolean escolaridade_publica) {
		this.escolaridade_publica = escolaridade_publica;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getResponsavel_legal() {
		return responsavel_legal;
	}

	public void setResponsavel_legal(String responsavel_legal) {
		this.responsavel_legal = responsavel_legal;
	}

	public String getGrau_parentesco() {
		return grau_parentesco;
	}

	public void setGrau_parentesco(String grau_parentesco) {
		this.grau_parentesco = grau_parentesco;
	}

	public String getHabilitacao() {
		return habilitacao;
	}

	public void setHabilitacao(String habilitacao) {
		this.habilitacao = habilitacao;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
