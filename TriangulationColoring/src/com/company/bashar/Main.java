package com.company.bashar;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static int totalNodes = 22;
    private static char[] colors = {'r', 'b', 'y'}; //an array of available colors
    public static void main(String[] args) {

        int[][] adjacencyMatrix = new int[totalNodes][totalNodes]; //record the graph
        char[] nodeColor = new char[totalNodes]; //record node color
        int totalBoundaryNodes = 11;

        Scanner reader = null;
        String inputFile = "src/input.txt";
        try {
            reader = new Scanner(new File(inputFile));
        } catch(IOException e) {
            e.printStackTrace();
        }

        //Populate the adjacency matrix by the input data
        for(int i = 0; i < totalNodes; i++) {
            for(int j = 0; j < totalNodes; j++) {
                adjacencyMatrix[i][j] = reader.nextInt();
            }
        }

        //populate the color array with boundary node's color from input data
        for(int i= 0; i < totalNodes; i++) {
            nodeColor[i] = reader.next().charAt(0);
        }

        int firstIndexOfTheInsideNode = totalBoundaryNodes;
        if(doColorTheInsideNode(adjacencyMatrix, nodeColor, firstIndexOfTheInsideNode))
            System.out.println("Hurrah! Solution Possible!!");
        else
            System.out.println("Solution not possible");

    }

    /*
    A recursive function to color the inside nodes of the triangulatingPolygon with all possible combination of give number
    of colors.
    Parameter: triangulatingPolygon: the adjacency matrix of the graph, nodeColor: array of colors assigned to nodes,
                node: the node needs to be colored
    Return: Returns true if a solution exists for the given condition else false
     */
    static private boolean doColorTheInsideNode(int[][] triangulatingPolygon, char[] nodeColor, int node) {
        if(node == totalNodes) {
            return checkTwoAndNoMoreCompleteTriangle(triangulatingPolygon, nodeColor);
        }
        for (char c : colors) {
            nodeColor[node] = c;

            if(doColorTheInsideNode(triangulatingPolygon, nodeColor, node + 1)) {
                return true;
            }
        }
        return false;
    }

    /*
    verifies if the solution exists for the assigned combination of colors to all the inside nodes
    Parameters: triangulatingPolygon: the adjacency matrix, nodeColor: colors assigned to nodes
    Returns: returns true if the solution exists else false
     */
    static private boolean checkTwoAndNoMoreCompleteTriangle(int[][] triangulatingPolygon, char[] nodeColor) {

        int numberOfCompleteTriange = 0;

        for(int i=0; i<totalNodes; i++) { //first node of the Triangle
            for(int j = i; j<totalNodes; j++) { //Second node of the Triangle
                if(triangulatingPolygon[i][j] == 1) {
                    for(int k=j; k<totalNodes; k++) { //third node of the Triangle
                        if(triangulatingPolygon[i][k] == 1 && triangulatingPolygon[j][k] == 1) {
                            if(nodeColor[i] != nodeColor[j] && nodeColor[i] != nodeColor[k] && nodeColor[j] != nodeColor[k]) {
                                numberOfCompleteTriange++;
                                if (numberOfCompleteTriange > 2) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return (numberOfCompleteTriange == 2);
    }
}
