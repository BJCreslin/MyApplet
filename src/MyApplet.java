import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collector;

/**
 * <applet code="MyApplet" width="600" height="450"></applet>
 **/


public class MyApplet extends JApplet {
    /*Размер Апплета*/
    private static final int xDimension = 600;
    private static final int yDimension = 450;
    public static final Dimension DIMENSION = new Dimension(xDimension, yDimension);

    /*Начальные позиции текста*/
    private static final int startXPosition = 10;
    private static final int startYPosition = 10;
    /*Шаг движения по X и Y*/
    private static final int stepY = 10;
    private static final int stepX = 10;

    /*Задержка в милисекундах. Меньше число- быстрее бежит*/
    private static final int DelayInms = 333;
    /* Текст, который будет бежать*/
    private String textToOutput = "Бегущий текст";
    private Timer tmr;
    private int xPos = startXPosition;
    private int yPos = startYPosition;

    @Override
    public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    setMaximumSize(DIMENSION);
                    setSize(DIMENSION);
                    tmr = new Timer(DelayInms, tmrs -> {
                        /* Проверка на достижения края апплета*/
                        if (((yPos + stepY) < DIMENSION.height) &
                                ((xPos + stepX) < DIMENSION.width)) {
                            /* Если на следующем шаге увеличения координат текст за край апплета не выйдет,
                            то увеличиваем координаты на шаг*/
                            xPos += stepX;
                            yPos += stepY;
                        } else {
                            /* Иначе останавливаем таймер*/
                            tmr.stop();
                            Random randomBoolean = new Random();
                                    /* делаем из входящего String поток*/
                            textToOutput = Arrays.stream(textToOutput.split("")).

                                    map(x -> randomBoolean.nextBoolean() ? x.toLowerCase() : x.toUpperCase()).
                                    collect(Collector.of(
                                            StringBuilder::new, (b, s) -> b.append(s),
                                            (b1, b2) -> b1.append(b2),
                                            StringBuilder::toString
                                    ));

                        }
                        repaint();
                    });
                    getContentPane().add(new JPanel() {
                        @Override
                        public void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.drawString(textToOutput, xPos, yPos);
                        }
                    });
                }
            });
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Override
    public void start() {
        tmr.start();
    }

    @Override
    public void stop() {
        tmr.stop();
    }
}