package fractals;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Inspired by: https://www.youtube.com/watch?v=kbKtFN71Lfs.

public class ChaosGame extends JPanel {
    private Map<Integer, Point> corePoints;
    private ArrayList<Point> otherPoints;
    private JFrame frame;
    private boolean doneWithPoints = false;

    public ChaosGame (){
        init();
    }

    private void init(){
        SetWindowOptions();
        SetInitialPoints();
    }

    private void SetWindowOptions(){
        frame = new JFrame("Simple fractals");
        frame.setSize(1000, 1000);
        frame.setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void SetInitialPoints(){
        corePoints = new HashMap<>();
        otherPoints = new ArrayList<>(); // Fill after setup.
        int size = 20; // higher number is larger fractals.
        int lowY = frame.getHeight() - (frame.getHeight() / size);
        int highY = frame.getHeight() / size;
        int leftX = frame.getWidth() / size;
        int middleX = frame.getWidth() / 2;
        int rightX = frame.getWidth() - (frame.getWidth() / size);

        var topLeftPoint = new Point(leftX, highY);
        corePoints.put(1, topLeftPoint);
        corePoints.put(2, topLeftPoint);

        var topMiddlePoint = new Point(middleX, lowY);
        corePoints.put(3, topMiddlePoint);
        corePoints.put(4, topMiddlePoint);

        var topRightPoint = new Point(rightX, highY);
        corePoints.put(5, topRightPoint);
        corePoints.put(6, topRightPoint);

        // The point to start from.
        var startingPointX = (topLeftPoint.GetX() + topMiddlePoint.GetX()) / 2;
        var startingPointY = (topLeftPoint.GetY() + topMiddlePoint.GetY()) / 2;
        var startingPoint = new Point(startingPointX, startingPointY);
        otherPoints.add(startingPoint);
    }

    private void DrawPoints(Graphics graphics){
        int pointSize = 1;

        for (Point corePoint: corePoints.values()) {
            graphics.fillRect(corePoint.GetX(), corePoint.GetY(), pointSize, pointSize);
        }

        for (Point otherPoint: otherPoints) {
            graphics.fillRect(otherPoint.GetX(), otherPoint.GetY(), pointSize, pointSize);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(doneWithPoints){
            super.paintComponent(g);
            DrawPoints(g);
        }
    }

    public void StartGame(){
        Random r = new Random();
        int sidesOfDice = 6;
        int maxIteration = 10000000;

        for(int i = 0; i < maxIteration; i++){
            int diceRoll = r.nextInt(sidesOfDice) + 1;
            Point pointToMoveTowards = corePoints.get(diceRoll);
            var previousPoint = otherPoints.get(otherPoints.size() - 1);
            int newPointPosX =  (previousPoint.GetX() + pointToMoveTowards.GetX() ) / 2;
            int newPointPosY =  (previousPoint.GetY() + pointToMoveTowards.GetY() ) / 2;
            Point newPoint = new Point(newPointPosX, newPointPosY);
            otherPoints.add(newPoint);
        }

        doneWithPoints = true;
        frame.repaint();
        System.out.println("Done!");
    }
}
