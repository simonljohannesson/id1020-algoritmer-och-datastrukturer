/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Run and follow instruction.
 *
 *  Dependencies:   EdgeWeightedDigraph
 *  (own classes)   DijkstraSearch
 *                  DirectedEdge
 *
 *  Test:           To run test, start program with an argument 'test'.
 *                  The database used for testing has been manually created
 *                  and checked the output should be the following:
 *
 *                  Testing path: 0 13 6
 *                  Output:
 *                  No such path.

 *                  Testing path: 0 13 15
 *                  Output:
 *                  0 1: 2
 *                  1 2: 1
 *                  2 3: 1
 *                  3 4: 2
 *                  4 5: 3
 *                  5 14: 1
 *                  14 13: 4
 *                  13 12: 1
 *                  12 15: 2
 *
 *  Based on:       None.
 */
import java.io.FileNotFoundException;
        import java.util.Scanner;

public class Assignment_higher_grade_simple {

    public static void printPath(Iterable<DirectedEdge> iter){
        for(DirectedEdge e : iter){
            System.out.println(e);
        }
    }
    public static void main(String[] args){
        if(args.length < 1){
            try {
                String fileName = "NYC.txt";
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
                        // check input
                        if(vertices.length > 1){
                            int source = Integer.parseInt(vertices[0]);
                            int via = Integer.parseInt(vertices[1]);
                            DijkstraSearch search = new DijkstraSearch(g, source);
                            // check if path exists
                            if(search.hasPathTo(via)){
                                // check if next search is needed
                                Iterable<DirectedEdge> partOfPath = search.pathTo(via);
                                if(vertices.length == 3){
                                    // do another search
                                    int destination = Integer.parseInt(vertices[2]);
                                    DijkstraSearch searchPtTwo = new DijkstraSearch(g, via);
                                    if(searchPtTwo.hasPathTo(destination)){
                                        LinkedList<DirectedEdge> fullPath = new LinkedList<>();
                                        for(DirectedEdge e : partOfPath){
                                            fullPath.append(e);
                                        }
                                        for(DirectedEdge e : searchPtTwo.pathTo(destination)){
                                            fullPath.append(e);
                                        }
                                        printPath(fullPath);
                                    }else{
                                        System.out.println("No such path.");
                                    }
                                }else{
                                    printPath(partOfPath);
                                }
                            }else{
                                System.out.println("No such path.");
                            }
                        }else{
                            System.out.println("Input format incorrect. Should be: 'X Y' or 'X Y Z'");
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
        // tests
        }else{
            if(args[0].equals("test")){
                try{
                    String fileName = "test-higher.txt";
                    String delimiter = " ";
                    EdgeWeightedDigraph g = new EdgeWeightedDigraph(fileName, delimiter);

                    // test 1
                    DijkstraSearch search = new DijkstraSearch(g, 0);
                    System.out.println("Testing path: 0 13 6\nOutput:");
                    if(search.hasPathTo(13)){
                        search = new DijkstraSearch(g, 13);
                        if(search.hasPathTo(6)) System.out.println("Found path!!");
                        else System.out.println("No such path.");
                    }else System.out.println("Test failed, should have found a path here.");

                    // test 2
                    search = new DijkstraSearch(g, 0);
                    System.out.println("\nTesting path: 0 13 15\nOutput:");
                    if(search.hasPathTo(13)){
                        printPath(search.pathTo(13));
                        search = new DijkstraSearch(g, 13);
                        if(search.hasPathTo(15)) printPath(search.pathTo(15));
                        else System.out.println("No such path.");
                    }else System.out.println("Test failed, should have found a path here.");


                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Incorrect argument passed. Terminating.");
            }
        }

    }
}