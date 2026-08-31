package dao;

import model.PlanoContas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanoContasDAO {

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/contabilidade",
                "root",
                ""
        );
    }

    public void inserir(PlanoContas plano) {
        String sql = "INSERT INTO plano_contas (conta, descricao, reduzida, saldo, tipo, cc, situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, plano.getConta());
            stmt.setString(2, plano.getDescricao());
            stmt.setInt(3, plano.getReduzida());
            stmt.setString(4, plano.getSaldo());
            stmt.setString(5, plano.getTipo());
            stmt.setString(6, plano.getCc());
            stmt.setString(7, plano.getSituacao());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlanoContas> listar() {
        List<PlanoContas> lista = new ArrayList<>();
        String sql = "SELECT * FROM plano_contas ORDER BY conta";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PlanoContas plano = new PlanoContas(
                        rs.getString("conta"),
                        rs.getString("descricao"),
                        rs.getInt("reduzida"),
                        rs.getString("saldo"),
                        rs.getString("tipo"),
                        rs.getString("cc"),
                        rs.getString("situacao")
                );

                lista.add(plano);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public void atualizar(PlanoContas plano) {
        String sql = "UPDATE plano_contas SET descricao = ?, reduzida = ?, saldo = ?, tipo = ?, cc = ?, situacao = ? WHERE conta = ?";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, plano.getDescricao());
            stmt.setInt(2, plano.getReduzida());
            stmt.setString(3, plano.getSaldo());
            stmt.setString(4, plano.getTipo());
            stmt.setString(5, plano.getCc());
            stmt.setString(6, plano.getSituacao());
            stmt.setString(7, plano.getConta());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluir(String conta) {
        String sql = "DELETE FROM plano_contas WHERE conta = ?";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, conta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
