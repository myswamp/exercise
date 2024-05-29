package maze;

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