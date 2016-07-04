package com.trycatch.chess.model;

/**
 * Indicates the position of a piece
 */
public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position addPosition(Position position) {
        return new Position(this.getX() + position.getX(), this.getY() + position.getY());
    }

    public Position addReferencePositionX(int x) {
        return new Position(x, this.getY());
    }

    public Position addReferencePositionY(int y) {
        return new Position(this.getX(), y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
