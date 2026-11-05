package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        MemberRepository memberRepo = new MemberRepository();


     try{

         memberRepo.create("Anna Igen", "anna@example.com");



     }catch(SQLException e){
         if("23505".equals(e.getSQLState())){
             System.out.println("E-posten finns redan");
         }else{
             System.out.println("Databasfel: " + e.getMessage());
         }
     }



    }
}