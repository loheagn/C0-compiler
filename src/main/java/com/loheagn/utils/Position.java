package com.loheagn.utils;

/**
 * Position
 */
public class Position {

    public int row;
    public int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position(Position position) {
        this.row = position.row;
        this.column = position.column;
    }

    @Override
    public String toString() {
        return row + ", " + column;
    }
}