package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = Db.getConnection()) {
            System.out.println("Ansluten till "
                    + conn.getCatalog());
        } catch (SQLException e) {
            System.out.println("Kunde inte ansluta: "
                    + e.getMessage());
        }

        SessionRepository sessionRepo =
                new SessionRepository();
        MemberRepository memberRepo =
                new MemberRepository();
        InstructorRepository instructorRepo =
                new InstructorRepository();

        try {
            for (SessionRepository.Session s
                    : sessionRepo.findAll()) {
                System.out.println(s.name() + " "
                        + s.date() + " " + s.maxParticipants());
            }

            for (MemberRepository.Member m
                    : memberRepo.findAll()) {
                System.out.println(m.id() + " "
                        + m.name() + " " + m.email());
            }

            for (InstructorRepository.InstructorStats i
                    : instructorRepo.findWithSessionCount()) {
                System.out.println(i.name() + " "
                        + i.sessionCount());
            }

            System.out.println("Spinning: "
                    + sessionRepo.findMemberNames("Spinning"));
            System.out.println("Injection: "
                    + sessionRepo.findMemberNames("' OR '1'='1"));

            System.out.println(
                    memberRepo.findByEmail("omar@example.com"));
            System.out.println(
                    memberRepo.findByEmail("x@example.com"));

            System.out.println("Maja Ek: "
                    + sessionRepo.findByInstructor("Maja Ek"));
            System.out.println("Okänd: "
                    + sessionRepo.findByInstructor("Okänd"));
        } catch (SQLException e) {
            System.out.println("Databasfel: " + e.getMessage());
        }
    }
}