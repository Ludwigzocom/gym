package org.example;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) {

        try (Connection conn = Db.getConnection()) {
            System.out.println("Ansluten till " + conn.getCatalog());

        } catch (SQLException e) {
            System.out.println("Kunde inte ansluta" + e.getMessage());

        }

        SessionRepository sessionRepo = new SessionRepository();

        try {
            for (SessionRepository.Session s : sessionRepo.findAll()) {
                System.out.println(s.name() + " " + s.date() + " " + s.maxParticipants());
            }

        } catch (SQLException e) {
            System.out.println("Databasefel: " + e.getMessage());
        }

       try {
           System.out.println("Spinning: " + sessionRepo.findMembersNames("Spinning"));
           System.out.println("Spinning: " + sessionRepo.findMembersNames("'OR '1'= 1"));
       }catch(SQLException e){
           e.getMessage();
       }

    }

}
