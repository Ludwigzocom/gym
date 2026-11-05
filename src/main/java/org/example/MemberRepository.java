package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    public record Member(int id,
                         String name, String email) {
    }

    public List<Member> findAll() throws SQLException {
        String sql = """
                SELECT id, name, email
                FROM members
                ORDER BY name
                """;
        List<Member> members = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")));

            }
        }
        return members;
    }

    public Member findByEmail(String email)
            throws SQLException {
        String sql =
                "SELECT id, name, email FROM members WHERE email = ?";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"));
                }
                return null;
            }
        }
    }


    public int create(String name, String email) throws SQLException {

        String sql = "INSERT INTO members (name, email) VALUES (?,?)";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return keys.getInt("id");
            }

        }


    }

    public int updateEmail(int id, String newEmail) throws SQLException {

        String sql = "UPDATE members SET email = ? WHERE id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newEmail);
            ps.setInt(2, id);
            return ps.executeUpdate();


        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM members WHERE id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }

    }


}