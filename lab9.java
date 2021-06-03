import java.util.Scanner;

// find the number of live cells after running GOL
// simulation 'states' times


class lab9 {

    static final int size = 20;
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        // inputs
        int[][] matrix = new int[size][size];
        int states = Integer.parseInt(reader.nextLine().trim());
        for (int i = 0; i < size; ++i) {
            String line = reader.nextLine();
            for (int j = 0; j < size; ++j) {
                matrix[i][j] = line.charAt(j) - 48;
            }
        }
        // Game of Life simulation
        int[][] finalState = simulation(matrix, states);
        // final output
        int n = liveCells(finalState);
        System.out.println(n); 
    }
    // simulate GOL 'n' times
    static int[][] simulation(int[][] matrix, int n) {
        int dead = 0, live = 1;
        int[][] current = matrix, next;
        while (n-- != 0) {
            next = new int[size][size];
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    int cell = current[i][j];
                    int npeers = peers(current, i, j);
                    if (cell == dead) {
                        if (npeers == 3)
                            next[i][j] = live;
                    } else {
                        next[i][j] = (npeers == 2 || npeers == 3)? live:dead;
                    }
                }
            }
            current = next;
        }
        return current;
    }

    static int peers(int[][] matrix, int x, int y) {
        int n = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if ((x > 0 && x < size-1) && (y > 0 && y < size-1)) {
                    n += matrix[x + i][y + j];
                } else { // borders
                    if ((x + i) > -1 && (x + i) < size) {
                        if ((y + j) > -1 && (y + j) < size) {
                            n += matrix[x + i][y + j];
                        }
                    }
                }
            }
        }
        return n - matrix[x][y];
    }

    static int liveCells(int[][] matrix) {
        int n = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                n += matrix[i][j];
        return n;
    }
}




/*
    INITIAL LOGIC

    if (cell == dead) {
        if (npeers == 3) {
            next[i][j] = live;
        }
    } else {
        if (npeers == 2 || npeers == 3) {
            next[i][j] = live;
        } else {
            next[i][j] = dead;
        }
    }


    SECOND LOGIC

    if (cell == live) {
        if (npeers < 2) {
            next[i][j] = dead;
        } else if (npeers > 3) {
            next[i][j] = dead;
        } else { // if 2 or 3 live neighbors
            next[i][j] = live;
        }
    } else {
        if (npeers == 3) {
            next[i][j] = live;
        }
    }

    static int peers(int[][] matrix, int x, int y) {
        int n = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (!(i == 0 && j == 0) && matrix[x+i][y+j] == 1)
                    n++;
        return n;
    }


    static int neighbors(int[][] grid, int x, int y) {
        int n = 0, i = -1, j = -1, il = 1, jl = 1;

        for (; i <= il; i++) {
            for (; j <= jl; j++) {
                if (x+i > -1 && x+i < 20) {
                    if (x+j > -1 && x+j < 20) {
                        n = n + grid[x + i][y + j];
                    }
                } 
            }
        }
        return n - grid[x][y];
    }
    
        // if (x == 0 && y == 0) {
        //     i = 0; j = 0;
        //     il = 1; jl = 1;
        // } else if (x == 19 && y == 0) {
        //     i = -1; j = 0;
        //     il = 0; jl = 1;
        // } else if (x == 0 && y == 19) {
        //     i = 0; j = -1;
        //     il = 1; jl = 0;
        // } else if (x == 19 && y == 19) {
        //     i = -1; j = -1;
        //     il = 0; jl = 0;
        // }
*/