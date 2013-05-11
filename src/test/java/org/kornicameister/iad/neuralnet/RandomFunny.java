package org.kornicameister.iad.neuralnet;

import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RandomFunny {

    @Test
    public void testShit() throws Exception {
        final int pointsCount = 500;
        final Double start = -3d,
                end = 3d;

        List<Point> points = new LinkedList<>();

        for (int i = 0; i < pointsCount; i++) {
            points.add(new Point(
                    this.getRandomDouble(-1.0, 1.0),
                    this.getRandomDouble(-1.0, 1.0)
            ));
        }

        for (int i = 0; i <= pointsCount; i++) {

            PrintWriter dataWriter = new PrintWriter(
                    new File(
                            String.format("%s/output/%d.txt", "shit", i)
                    )
            );
            for (Point point : points) {
                dataWriter.print(point.getX());
                dataWriter.print(" ");
                dataWriter.print(point.getY());
                dataWriter.println();
            }
            dataWriter.close();
            for (int m = 0; m < 50; m++) {
                Point point = new Point(
                        this.getRandomDouble(start, end),
                        this.getRandomDouble(start, end)
                );
                point.setY(point.getX());
                this.moveToPoint(this.getNearest(points, point), point);
            }
        }
    }

    private void moveToPoint(Point move, Point moveTo) {
        move.x += (1.0 / 3.0) * (moveTo.getX() - move.getX());
        move.y += (1.0 / 3.0) * (moveTo.getY() - move.getY());
    }

    private Point getNearest(List<Point> points, Point point) {
        Double min = this.distance(points.get(0), point);
        Integer position = 0;
        for (int i = 1; i < points.size(); i++) {
            final Double distance = this.distance(points.get(i), point);
            if (min > distance) {
                min = distance;
                position = i;
            }
        }
        return points.get(position);
    }

    private Double distance(final Point p1, final Point p2) {
        return Math.sqrt(
                Math.pow(p1.getX() - p2.getX(), 2.0) +
                        Math.pow(p1.getY() - p2.getY(), 2.0)
        );
    }

    private double getRandomDouble(final Double lower, final Double upper) {
        return doubleBetween(lower, upper);
    }

    public static double doubleBetween(double start, double end) {
        Random random = new Random();

        // We need 64 bits because double have 53 bits precision, so int is too short
        // We have now a value between 0 and Long.MAX_VALUE.
        long value = -1L;
        while (value < 0)
            value = Math.abs(random.nextLong()); // Caution, Long.MIN_VALUE returns negative !


        // Cast to double
        double valueAsDouble = (double) value;

        // Scale so that Long.MAX_VALUE is exactly 1 !
        double diff = (end - start) / (double) Long.MAX_VALUE;


        return start + valueAsDouble * diff;
    }

    class Point {
        Double x;
        Double y;

        Point(final Double x, final Double y) {
            this.x = x;
            this.y = y;
        }

        Double getX() {
            return x;
        }

        void setX(final Double x) {
            this.x = x;
        }

        Double getY() {
            return y;
        }

        void setY(final Double y) {
            this.y = y;
        }
    }
}
