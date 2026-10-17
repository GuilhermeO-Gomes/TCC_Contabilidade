package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public final class Jdbc {
  private Jdbc() {}

  public interface Leitor<T> {
    T ler(ResultSet rs) throws SQLException;
  }

  private static void vincular(PreparedStatement ps, Object... valores) throws SQLException {
    for (int i = 0; i < valores.length; i++) {
      Object valor = valores[i];
      if (valor instanceof LocalDate) valor = java.sql.Date.valueOf((LocalDate) valor);
      if (valor instanceof LocalDateTime) valor = Timestamp.valueOf((LocalDateTime) valor);
      if (valor instanceof Enum<?>) valor = ((Enum<?>) valor).name();
      ps.setObject(i + 1, valor);
    }
  }

  public static <T> List<T> listar(Connection c, String sql, Leitor<T> leitor, Object... parametros)
      throws SQLException {
    try (PreparedStatement ps = c.prepareStatement(sql)) {
      vincular(ps, parametros);
      try (ResultSet rs = ps.executeQuery()) {
        List<T> lista = new ArrayList<>();
        while (rs.next()) lista.add(leitor.ler(rs));
        return lista;
      }
    }
  }

  public static <T> T unico(Connection c, String sql, Leitor<T> leitor, Object... parametros)
      throws SQLException {
    List<T> lista = listar(c, sql, leitor, parametros);
    if (lista.size() > 1) throw new SQLException("Resultado não é único");
    return lista.isEmpty() ? null : lista.get(0);
  }

  public static int executar(Connection c, String sql, Object... parametros) throws SQLException {
    try (PreparedStatement ps = c.prepareStatement(sql)) {
      vincular(ps, parametros);
      return ps.executeUpdate();
    }
  }

  public static long inserir(Connection c, String sql, Object... parametros) throws SQLException {
    try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      vincular(ps, parametros);
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (!rs.next()) throw new SQLException("ID não retornado");
        return rs.getLong(1);
      }
    }
  }

  public static Long idOpcional(ResultSet rs, String nome) throws SQLException {
    long id = rs.getLong(nome);
    return rs.wasNull() ? null : id;
  }
}
