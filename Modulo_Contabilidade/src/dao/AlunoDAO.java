package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import util.Conexao;

public class AlunoDAO {
	
	public void salvar(Aluno aluno) {
		Connection conn = null;
		PreparedStatement comando = null;
		
		// SQL Corrigido: Removidos campos inexistentes (local_nascimento, pais_origem, filiacoes)
		String sql = "INSERT INTO aluno (nome, nome_social, cpf, genero, afrodescendente, escolaridade_publica, data_nascimento, nacionalidade, responsavel_legal, grau_parentesco, habilitacao, serie, periodo, rua, complemento, apto, bloco, bairro, cidade, CEP, telefone, email) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			conn = Conexao.conectar();
			comando = conn.prepareStatement(sql);
			
			comando.setString(1, aluno.getNome());
			
			if (aluno.getNome_social() != null) {
				comando.setString(2, aluno.getNome_social());
			} else {
				comando.setNull(2, java.sql.Types.VARCHAR);
			}
			
			comando.setString(3, aluno.getCpf());
			comando.setString(4, aluno.getGenero());
			comando.setBoolean(5, aluno.isAfrodescendente());
			comando.setBoolean(6, aluno.isEscolaridade_publica());
			comando.setDate(7, aluno.getData_nascimento());
			comando.setString(8, aluno.getNacionalidade()); // Antes era o número 9 desalinhado
			comando.setString(9, aluno.getResponsavel_legal());
			comando.setString(10, aluno.getGrau_parentesco());
			comando.setString(11, aluno.getHabilitacao());
			comando.setString(12, aluno.getSerie());
			comando.setString(13, aluno.getPeriodo());
			comando.setString(14, aluno.getRua());
			
			if (aluno.getComplemento() != null) {
				comando.setString(15, aluno.getComplemento());
			} else {
				comando.setNull(15, java.sql.Types.VARCHAR);
			}
			
			if (aluno.getApto() != null) {
				comando.setString(16, aluno.getApto());
			} else {
				comando.setNull(16, java.sql.Types.VARCHAR);
			}
			
			if (aluno.getBloco() != null) {
				comando.setString(17, aluno.getBloco());
			} else {
				comando.setNull(17, java.sql.Types.VARCHAR);
			}
			
			comando.setString(18, aluno.getBairro());
			comando.setString(19, aluno.getCidade());
			comando.setString(20, aluno.getCEP());
			comando.setString(21, aluno.getTelefone()); // Corrigido número repetido
			comando.setString(22, aluno.getEmail());
			
			comando.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar no Banco de Dados: " + e.getMessage());
		} finally {
			try {
				if (comando != null) comando.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void atualizar(Aluno aluno) {
		Connection conn = null;
		PreparedStatement comando = null;
		
		String sql = "UPDATE aluno SET nome = ?, nome_social = ?, cpf = ?, genero = ?, afrodescendente = ?, escolaridade_publica = ?, data_nascimento = ?, nacionalidade = ?, responsavel_legal = ?, grau_parentesco = ?, habilitacao = ?, serie = ?, periodo = ?, rua = ?, complemento = ?, apto = ?, bloco = ?, bairro = ?, cidade = ?, CEP = ?, telefone = ?, email = ? WHERE id = ?";

		try {
			conn = Conexao.conectar();
			comando = conn.prepareStatement(sql);
			
			comando.setString(1, aluno.getNome());
			//Essa estrutura é porque o campo nome_social é opcional o preenchimento. Caso esteja preenchido irá pro BD, se n, irá como NULL
			if (aluno.getNome_social() != null) {
				comando.setString(2, aluno.getNome_social());
			} else {
				comando.setNull(2, java.sql.Types.VARCHAR);
			}
			
			comando.setString(3, aluno.getCpf());
			comando.setString(4, aluno.getGenero());
			comando.setBoolean(5, aluno.isAfrodescendente());
			comando.setBoolean(6, aluno.isEscolaridade_publica());
			comando.setDate(7, aluno.getData_nascimento());
			comando.setString(8, aluno.getNacionalidade());
			comando.setString(9, aluno.getResponsavel_legal());
			comando.setString(10, aluno.getGrau_parentesco());
			comando.setString(11, aluno.getHabilitacao());
			comando.setString(12, aluno.getSerie());
			comando.setString(13, aluno.getPeriodo());
			comando.setString(14, aluno.getRua());
			
			if (aluno.getComplemento() != null) {
				comando.setString(15, aluno.getComplemento());
			} else {
				comando.setNull(15, java.sql.Types.VARCHAR);
			}
			
			if (aluno.getApto() != null) {
				comando.setString(16, aluno.getApto());
			} else {
				comando.setNull(16, java.sql.Types.VARCHAR);
			}
			
			if (aluno.getBloco() != null) {
				comando.setString(17, aluno.getBloco());
			} else {
				comando.setNull(17, java.sql.Types.VARCHAR);
			}
			
			comando.setString(18, aluno.getBairro());
			comando.setString(19, aluno.getCidade());
			comando.setString(20, aluno.getCEP());
			comando.setString(21, aluno.getTelefone());
			comando.setString(22, aluno.getEmail());
			comando.setInt(23, aluno.getId()); 
			
			comando.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao atualizar dados no Banco de Dados: " + e.getMessage());
		} finally {
			try {
				if (comando != null) comando.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void excluir(int id) {
		Connection conn = null;
		PreparedStatement comando = null;
		String sql = "DELETE FROM aluno WHERE id = ?";
		
		try {
			conn = Conexao.conectar();
			comando = conn.prepareStatement(sql);
			comando.setInt(1, id);
			comando.executeUpdate();
		} catch (Exception e) {
            throw new RuntimeException("Erro ao excluir aluno: " + e.getMessage());
        } finally {
            try {
                if (comando != null) comando.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	public List<Aluno> listar() {
		List<Aluno> lista = new ArrayList<Aluno>();
		Connection conn = null;
		PreparedStatement comando = null;
		//o rs serve para mostrar o resultado, o SELECT
		ResultSet rs = null;
		
		String sql = "SELECT * FROM aluno ORDER BY id DESC";
		
		try {
			conn = Conexao.conectar();
			comando = conn.prepareStatement(sql);
			rs = comando.executeQuery();
			
			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setNome_social(rs.getString("nome_social"));
				aluno.setCpf(rs.getString("CPF"));
				aluno.setGenero(rs.getString("genero"));
				aluno.setAfrodescendente(rs.getBoolean("afrodescendente"));
				aluno.setEscolaridade_publica(rs.getBoolean("escolaridade_publica"));
				aluno.setData_nascimento(rs.getDate("data_nascimento"));
				aluno.setNacionalidade(rs.getString("nacionalidade"));
				aluno.setResponsavel_legal(rs.getString("responsavel_legal"));
				aluno.setGrau_parentesco(rs.getString("grau_parentesco"));
				aluno.setHabilitacao(rs.getString("habilitacao"));
				aluno.setSerie(rs.getString("serie"));
				aluno.setPeriodo(rs.getString("periodo"));
				aluno.setRua(rs.getString("rua"));
				aluno.setComplemento(rs.getString("complemento"));
				aluno.setApto(rs.getString("apto"));
				aluno.setBloco(rs.getString("bloco"));
				aluno.setBairro(rs.getString("bairro"));
				aluno.setCidade(rs.getString("cidade"));
				aluno.setCEP(rs.getString("CEP"));
				aluno.setTelefone(rs.getString("telefone"));
				aluno.setEmail(rs.getString("email"));
				lista.add(aluno);
			}
		} catch (Exception e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (comando != null) comando.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return lista;
	}
}
