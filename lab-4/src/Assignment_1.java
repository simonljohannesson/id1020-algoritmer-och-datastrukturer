/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4, assignment 1.
 *  Usage:          Run and follow the instructions.
 *
 *  Dependencies:   SymbolGraphUndirected
 *  (own classes)   SymbolGraphSearchDF
 *
 *  Based on:       None.
 */
import java.util.Scanner;

public class Assignment_1 {
    public static void main(String[] args){
        String fileName = "database.txt";
        String delimiter = " ";
        SymbolGraphUndirected sg = new SymbolGraphUndirected(fileName, delimiter);
        System.out.println(sg);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Between which vertices do you want a path? "
                            + "(Format: 'X to Y')");
        while(scanner.hasNextLine()){
                String vertices[] = scanner.nextLine().split("\\sto\\s");
                if(vertices.length != 2){
                    System.out.println("Input format incorrect. Should be: 'X to Y'");
                    continue;
                }
                String sourceVertex = vertices[0];
                String toVertex = vertices[1];
            try{
                SymbolGraphSearchDF sgSearchDF = new SymbolGraphSearchDF(sg, sourceVertex);
                System.out.print("A path from " + sourceVertex + " to " + toVertex);
                if(sgSearchDF.hasPath(toVertex)){
                    System.out.println(" is: ");
                    for(String v : sgSearchDF.pathTo(toVertex)){
                        System.out.print(v + " ");
                    }
                    System.out.println();
                }else{
                    System.out.println(" does not exist.");
                }
            }catch (IllegalArgumentException e){
                System.out.println("Source: '" + sourceVertex + "'");
                System.out.println("To: '" + toVertex + "'");
                System.out.println("Invalid vertex used. Try again.");
                continue;
            }
            System.out.println("Between which vertices do you want a path? "
                    + "(Format: 'X to Y')");
        }
    }
}
