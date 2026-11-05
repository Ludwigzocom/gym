package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository {

    public record Session(int id,
                          String name, LocalDate date,
                          int maxParticipants) {}

    public List<Session> findAll() throws SQLException {
        String sql = """
        SELECT id, name, session_date, max_participants
        FROM sessions
        ORDER BY session_date
        """;
        List<Session> sessions = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sessions.add(new Session(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("session_date").toLocalDate(),
                        rs.getInt("max_participants")));
            }
        }
        return sessions;
    }

    public List<String> findMemberNames(String sessionName)
            throws SQLException {
        String sql = """
        SELECT m.name
        FROM bookings AS b
        JOIN members AS m ON m.id = b.member_id
        JOIN sessions AS s ON s.id = b.session_id
        WHERE s.name = ?
        ORDER BY m.name
        """;
        List<String> names = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sessionName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    names.add(rs.getString("name"));
                }
            }
        }
        return names;
    }

    public List<String> findByInstructor(String instructorName)
            throws SQLException {
        String sql = """
        SELECT s.name, s.session_date
        FROM sessions AS s
        JOIN instructors AS i ON i.id = s.instructor_id
        WHERE i.name = ?
        ORDER BY s.session_date
        """;
        List<String> sessions = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, instructorName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sessions.add(rs.getString("name") + " "
                            + rs.getDate("session_date"));
                }
            }
        }
        return sessions;
    }
}