package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

enum MazeCell {
    EMPTY(" "),
    BLOCKED("X"),
    START("S"),
    DESTINATION("D"),
    PATH("*");

    private final String code;

    MazeCell(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

record MazeLocation(int row, int column) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeLocation that = (MazeLocation) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}

public class Maze {
    private final MazeLocation start, destination;
    private MazeCell[][] grid;

    public Maze(int rows, int columns, MazeLocation start, MazeLocation destination, double sparseness) {
        this.start = start;
        this.destination = destination;
        this.grid = new MazeCell[rows][columns];

        for (int row = 0; row < rows; row++) {
            Arrays.fill(grid[row], MazeCell.EMPTY);
            for (int column = 0; column < columns; column++) {
                if (Math.random() < sparseness) {
                    grid[row][column] = MazeCell.BLOCKED;
                }
            }
        }
        markEnds();
    }

    public List<MazeLocation> successors(MazeLocation location) {
        List<MazeLocation> successors = new ArrayList<>();
        if (location.row() + 1 < grid.length && grid[location.row() + 1][location.column()] != MazeCell.BLOCKED) {
            successors.add(new MazeLocation(location.row() + 1, location.column()));
        }
        if (location.row() - 1 > -1 && grid[location.row() - 1][location.column()] != MazeCell.BLOCKED) {
            successors.add(new MazeLocation(location.row() - 1, location.column()));
        }
        if (location.column() + 1 < grid[0].length && grid[location.row()][location.column() + 1] != MazeCell.BLOCKED) {
            successors.add(new MazeLocation(location.row(), location.column() + 1));
        }
        if (location.column() - 1 > -1 && grid[location.row()][location.column() - 1] != MazeCell.BLOCKED) {
            successors.add(new MazeLocation(location.row(), location.column() - 1));
        }
        return successors;
    }

    public void mark(List<MazeLocation> path) {
        for (MazeLocation ml : path) {
            grid[ml.row()][ml.column()] = MazeCell.PATH;
        }
        markEnds();
    }

    public void clear(List<MazeLocation> path) {
        for (MazeLocation ml : path) {
            grid[ml.row()][ml.column()] = MazeCell.EMPTY;
        }
        markEnds();
    }

    private void markEnds() {
        grid[start.row()][start.column()] = MazeCell.START;
        grid[destination.row()][destination.column()] = MazeCell.DESTINATION;
    }


    public double manhattanDistance(MazeLocation ml) {
        return Math.abs(ml.column() - destination.column()) + Math.abs(ml.row() - destination.row());
    }

    public double euclideanDistance(MazeLocation ml) {
        int xdist = ml.column() - destination.column();
        int ydist = ml.row() - destination.row();
        return Math.sqrt((xdist * xdist) + (ydist * ydist));
    }

    public boolean isDestination(MazeLocation ml) {
        return ml.equals(destination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MazeCell[] row : grid) {
            for (MazeCell cell : row) {
                sb.append(cell);
            }

            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        Maze maze = new Maze(10, 10, new MazeLocation(0, 0), new MazeLocation(9, 9), 0.2);
        System.out.println(maze);

        SearchAlgorithm.Node<MazeLocation> solution1 = SearchAlgorithm.dfs(maze.start, maze::isDestination, maze::successors);
        if (solution1 == null) {
            System.out.println("No solution found using depth-first search!");
        } else {
            List<MazeLocation> path1 = SearchAlgorithm.Node.nodeToPath(solution1);
            System.out.println(path1.size());
            maze.mark(path1);
            System.out.println(maze);
            maze.clear(path1);
        }

        SearchAlgorithm.Node<MazeLocation> solution2 = SearchAlgorithm.bfs(maze.start, maze::isDestination, maze::successors);
        if (solution2 == null) {
            System.out.println("No solution found using breadth-first search!");
        } else {
            List<MazeLocation> path2 = SearchAlgorithm.Node.nodeToPath(solution2);
            System.out.println(path2.size());
            maze.mark(path2);
            System.out.println(maze);
            maze.clear(path2);
        }

        SearchAlgorithm.Node<MazeLocation> solution3 = SearchAlgorithm.astar(maze.start, maze::isDestination, maze::successors, maze::manhattanDistance);
        if (solution3 == null) {
            System.out.println("No solution found using A* manhattan search!");
        } else {
            List<MazeLocation> path3 = SearchAlgorithm.Node.nodeToPath(solution3);
            System.out.println(path3.size());
            maze.mark(path3);
            System.out.println(maze);
            maze.clear(path3);
        }

        SearchAlgorithm.Node<MazeLocation> solution4 = SearchAlgorithm.astar(maze.start, maze::isDestination, maze::successors, maze::euclideanDistance);
        if (solution4 == null) {
            System.out.println("No solution found using A* euclidean search!");
        } else {
            List<MazeLocation> path4 = SearchAlgorithm.Node.nodeToPath(solution4);
            System.out.println(path4.size());
            maze.mark(path4);
            System.out.println(maze);
            maze.clear(path4);
        }
    }


}