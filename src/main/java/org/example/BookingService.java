package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingService {

    public boolean book(int memberId, int sessionId) throws SQLException {

        try (Connection conn = Db.getConnection()) {

            conn.setAutoCommit(false);

            try {

                int max = findMaxForUpdate(conn, sessionId);
                int booked = countBookings(conn, sessionId);
                if (booked >= max) {
                    conn.rollback();
                    return false;
                }

                insertBooking(conn, memberId, sessionId);
                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();

                throw e;
            }

        }

    }

    private int findMaxForUpdate(Connection conn, int sessionId) throws SQLException {

        String sql = """
                SELECT max_participants FROM sessions
                WHERE id = ? FOR UPDATE
                
                
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("max_participants");
                }
                return 0;
            }
        }


    }

    private int countBookings(Connection conn, int sessionId) throws SQLException {

        String sql = "SELECT COUNT(*) FROM bookings WHERE session_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();

                return rs.getInt(1);
            }
        }


    }

    private void insertBooking(Connection conn, int memberId, int sessionId) throws SQLException {

        String sql = "INSERT INTO bookings (member_id, session_id) VALUES (?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ps.setInt(2, sessionId);
            ps.executeUpdate();
        }


    }


}
