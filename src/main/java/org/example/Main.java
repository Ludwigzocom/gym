package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SessionRepository sessionRepo = new SessionRepository();
        MemberRepository memberRepo = new MemberRepository();
        BookingService bookingService = new BookingService();

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("1. Lista pass");
            System.out.println("2. Visa bokade på ett pass");
            System.out.println("3. Boka ett pass");
            System.out.println("4. Lägg till medlem");
            System.out.println("0. Avsluta program");
            System.out.print("Val: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        for (SessionRepository.Session s : sessionRepo.findAll()) {
                            System.out.println(s.id() + ". " + s.name() + " " + s.date());
                        }
                    }
                    case "2" -> {
                        System.out.print("Pass: ");
                        String sessionName = scanner.nextLine();

                        System.out.println(sessionRepo.findMemberNames(sessionName));

                    }
                    case "3" -> {
                        System.out.print("Medlemsnummber: ");
                        int memberId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Passnummer: ");
                        int sessionId = Integer.parseInt(scanner.nextLine());
                        boolean ok = bookingService.book(memberId, sessionId);
                        System.out.println(ok ? "Bokad" : "Passet är fullt");
                    }
                    case "4" -> {
                        System.out.print("Namn: ");
                        String name = scanner.nextLine();
                        System.out.print("E-post: ");
                        String email = scanner.nextLine();
                        int id = memberRepo.create(name, email);

                        System.out.println("Ny medlem med id: " + id);
                    }
                    case "0" -> running = false;
                    default -> System.out.println("Okänt menyval");
                }


            } catch (SQLException e) {
                System.out.println("Databasfel: " + e.getMessage());
            }


        }
        System.out.println("Stänger av program...");

    }
}