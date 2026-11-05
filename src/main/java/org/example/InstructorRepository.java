package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InstructorRepository {

    public record InstructorStats(
            String name, int sessionCount) {}

    public List<InstructorStats> findWithSessionCount()
            throws SQLException {
        String sql = """
        SELECT i.name, COUNT(s.id) AS session_count
        FROM instructors AS i
        LEFT JOIN sessions AS s ON s.instructor_id = i.id
        GROUP BY i.id, i.name
        ORDER BY i.name
        """;
        List<InstructorStats> result = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new InstructorStats(
                        rs.getString("name"),
                        rs.getInt("session_count")));
            }
        }
        return result;
    }
}