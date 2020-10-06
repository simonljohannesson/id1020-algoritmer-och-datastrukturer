/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Run and follow instruction.
 *
 *  Dependencies:   EdgeWeightedDigraph
 *  (own classes)   DijkstraSearchBF
 *                  DirectedEdge
 *
 *  Based on:       None.
 */
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assignment_higher_grade {
    public static void main(String[] args){
        try {
            String fileName = "test-higher.txt";
            String delimiter = " ";
            EdgeWeightedDigraph g = new EdgeWeightedDigraph(fileName, delimiter);
            System.out.println(g);

            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("Between which vertices do you want a path? "
                    + "(Format: 'X Y' or 'X Y Z'");
            while (scanner.hasNextLine()){
                try{
                    String[] vertices = scanner.nextLine().split(" ");
                    if(vertices.length == 2 || vertices.length == 3){
                        int source = Integer.parseInt(vertices[0]);
                        int via = Integer.parseInt(vertices[1]);
                        // use copy of graph since it is destroyed in search
                        EdgeWeightedDigraph gCopy = g.copy();
                        DijkstraSearchBF search = new DijkstraSearchBF(gCopy, source, via);
                        if(vertices.length == 2){
                            if(search.hasPath()){
                                System.out.println("Path from " + source + " to " + via + " is:");
                                for(DirectedEdge e : search.pathTo()){
                                    System.out.println(e);
                                }
                            }else{
                                System.out.println("No path from " + source + " to " + via + " found");
                            }
                        }
                        else {
                            int destination = Integer.parseInt(vertices[2]);
                            if(search.hasPathTo(destination)){
                                System.out.println("Path from " + source + " to " + via + " to " + destination + " is:");
                                for(DirectedEdge e : search.pathTo(destination)){
                                    System.out.println(e);
                                }
                            }else{
                                System.out.println("No path from " + source + " to " + via + " found");
                            }
                        }
                    }else{
                        System.out.println("Input format incorrect. Should be: 'X Y' or 'X Y Z'");
                        continue;
                    }
                }catch (NumberFormatException e){
                    System.out.println("Input vertices invalid. Try again.");
                }
                System.out.println("Between which vertices do you want a path? "
                        + "(Format: 'X Y' or 'X Y Z'");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
