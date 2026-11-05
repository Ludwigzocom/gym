package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        BookingService bookingService = new BookingService();


        try {
            // Billy books Styrka
            boolean ok = bookingService.book(4, 3);

            System.out.println(ok ? "Bokad" : "Passet är fullt");


            //Billy books Spinning

            ok = bookingService.book(4, 1);

            System.out.println(ok ? "Bokad" : "Passet är fullt");


        } catch (SQLException e) {
            System.out.println("Databasfel: " + e.getMessage());
        }


    }
}