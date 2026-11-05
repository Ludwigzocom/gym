package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        MemberRepository memberRepo = new MemberRepository();


        try{
            int id = memberRepo.create("Sara Lind", "sara@example.com");
            System.out.println("Ny medlem med id: " +id);

            int updated = memberRepo.updateEmail(id, "sara.lind@example.com");
            System.out.println(updated + " rad ändrad");

            int deleted = memberRepo.delete(id);
        } catch(SQLException e){
            System.out.println("Databasfel: "+ e.getMessage());
        }






    }
}