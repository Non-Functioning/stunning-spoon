package cs3500.animator.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Animations;
import cs3500.animator.model.SimpleAnimationModel;

public abstract class AbstractVisualView extends AbstractView {
  private List<Float> red;
  private List<Float> green;
  private List<Float> blue;
  private List<AnimatedShape.ShapeType> shapeType;
  private List<Integer> xPosition;
  private List<Integer> yPosition;
  private List<List<Double>> size;
  private Long animationPeriod;
  protected Timer timer;
  protected int currTick;
  protected boolean isPaused;

  protected JFrame frame;
  protected JPanel mainPanel;
  protected DrawingPane drawingPanel;
  protected JScrollPane drawingScollPane;

  /**
   * Constructor for an abstract view.
   *
   * @param animationModel The model to transfer to a view.
   * @param tempo          Speed of the animation, as ticks per second.
   */
  public AbstractVisualView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);
    timer = new Timer();
    animationPeriod = (long) (timeline.size()) * 100;
    isLooped = false;
    isPaused = true;
    currTick = 0;

    frame = new JFrame("Simple Animation!");
    //frame.setSize(500, 500);
    frame.setLocation(200, 25);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(true);
    frame.setPreferredSize(new Dimension(1000, 600));
    frame.setLayout(new BorderLayout());
    //frame.setVisible(true);

    drawingPanel = new DrawingPane();
    drawingScollPane = new JScrollPane(drawingPanel);
    drawingScollPane.setPreferredSize(new Dimension(1000, 600));

    mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(drawingScollPane, BorderLayout.CENTER);
    frame.add(mainPanel, BorderLayout.CENTER);
    //frame.add(mainPanel, BorderLayout.CENTER);

    //frame.pack();
  }

  /**
   * This method is used by both the Visual and Interactive views. It plays an
   * animation starting from the given tick. This method schedules all the tasks required
   * each tick to draw an animation. The method creates a task based on the timeline and
   * the animations within the timeline.
   * @param startTime   starting tick
   */
  @Override
  public void startAnimation(int startTime) {
    currTick = startTime;
    TimerTask task;

    for (int i = 0; i < timeline.size(); i++) {
      final int FINALI = currTick;
      List<Animations> animationTime = timeline.get(FINALI);

      task = new TimerTask() {
        @Override
        public void run() {
          initializeParams();
          addShapeParamsAtTimeT(animationTime, FINALI);
          drawingPanel.repaint();
          currTick = (currTick + 1) % timeline.size();
        }
      };
      scheduleTimerTasks(task, i);
      currTick = (currTick + 1) % timeline.size();
      if ((!isLooped) & (currTick == 0) & (startTime != 0)) {
        break;
      }
    }
  }

  protected void initializeParams() {
    red = new ArrayList<>();
    green = new ArrayList<>();
    blue = new ArrayList<>();
    shapeType = new ArrayList<>();
    xPosition = new ArrayList<>();
    yPosition = new ArrayList<>();
    size = new ArrayList<>();
  }

  /**
   * This method creates a shape in the window using the Graphics class.
   *
   * @param g graphics
   */
  private void drawShape(Graphics g, int index) {
    g.setColor(new Color(red.get(index), green.get(index), blue.get(index)));
    switch (shapeType.get(index)) {
      case RECTANGLE:
        g.fillRect(xPosition.get(index), yPosition.get(index), size.get(index).get(0).intValue(),
                size.get(index).get(1).intValue());
        break;
      case OVAL:
        g.fillOval(xPosition.get(index), yPosition.get(index),
                (size.get(index).get(0).intValue() * 2), (size.get(index).get(1).intValue() * 2));
        break;
      case SQUARE:
        g.fillRect(xPosition.get(index), yPosition.get(index), size.get(index).get(0).intValue(),
                size.get(index).get(0).intValue());
        break;
      case CIRCLE:
        g.fillOval(xPosition.get(index), yPosition.get(index),
                (size.get(index).get(0).intValue() * 2), (size.get(index).get(0).intValue() * 2));
        break;
      default:
        throw new IllegalArgumentException("Invalid shape type");
    }
  }

  /**
   * This assigns values that will be drawn corresponding to a given animation at a given time.
   *
   * @param animation animation to draw
   * @param time      time of draw
   */
  private void singleAnimationChange(Animations animation, int time) {
    switch (animation.getType()) {
      case MOVE:
        xPosition.add(calcTweening(animation.getPosition1().getX(),
                animation.getPosition2().getX(), animation.getTime1(), animation.getTime2(),
                time).intValue());
        yPosition.add(calcTweening(animation.getPosition1().getY(),
                animation.getPosition2().getY(), animation.getTime1(), animation.getTime2(),
                time).intValue());
        break;
      case CHANGESIZE:
        java.util.List<Double> newSize = new ArrayList<>();
        for (int i = 0; i < animation.getSizeParams1().size(); i++) {
          newSize.add(calcTweening(animation.getSizeParams1().get(i),
                  animation.getSizeParams2().get(i), animation.getTime1(), animation.getTime2(),
                  time));
        }
        size.add(newSize);
        break;
      case CHANGECOLOR:
        red.add(calcTweening(animation.getColor1().getRed(), animation.getColor2().getRed(),
                animation.getTime1(), animation.getTime2(), time).floatValue());
        green.add(calcTweening(animation.getColor1().getGreen(),
                animation.getColor2().getGreen(), animation.getTime1(), animation.getTime2(),
                time).floatValue());
        blue.add(calcTweening(animation.getColor1().getBlue(),
                animation.getColor2().getBlue(), animation.getTime1(), animation.getTime2(),
                time).floatValue());
        break;
      case APPEAR:
        break;
      case DISAPPEAR:
        break;
      case STILL:
        addStillShape(animation);
        break;
      default:
        throw new IllegalArgumentException("Invalid animation type");
    }
  }

  /**
   * This method assigns values that will be drawn that correspond to when a shape first appears
   * or when a shape is staying still at the given time.
   *
   * @param animation animation to draw
   */
  private void addStillShape(Animations animation) {
    red.add(animation.getColor1().getRed().floatValue());
    green.add(animation.getColor1().getGreen().floatValue());
    blue.add(animation.getColor1().getBlue().floatValue());
    xPosition.add(animation.getPosition1().getX().intValue());
    yPosition.add(animation.getPosition1().getY().intValue());
    size.add(animation.getSizeParams1());
  }

  /**
   * This method calculates the tweening value of an animation at a given time.
   * This method is used for move, change color, and change size animations.
   *
   * @param initVal   initial value
   * @param finalVal  final value
   * @param initTick  time of initial value
   * @param finalTick time of final value
   * @param tick  current time
   * @return value at current time
   */
  private Double calcTweening(Double initVal, Double finalVal, Integer initTick,
                              Integer finalTick, Integer tick) {
    Integer t1 = (finalTick - tick);
    Integer t2 = (tick - initTick);
    Integer t3 = (finalTick - initTick);
    Double v1 = t1.doubleValue() / t3.doubleValue();
    Double v2 = t2.doubleValue() / t3.doubleValue();
    return (initVal * v1) + (finalVal * v2);
  }

  protected void addShapeParamsAtTimeT(List<Animations> animationTime, int timelineIndex) {
    for (int j = 0; j < timeline.get(timelineIndex).size(); j++) {
      String nextShape = "";

      shapeType.add(animationTime.get(j).getChangedShape().getShapeType());
      singleAnimationChange(animationTime.get(j), timelineIndex);

      if ((j + 1) < animationTime.size()) {
        nextShape = animationTime.get(j + 1).getChangedShape().getShapeName();
      }

      while (nextShape.equals(animationTime.get(j).getChangedShape().getShapeName())) {
        singleAnimationChange(animationTime.get(j + 1), timelineIndex);
        j++;
        if ((j + 1) < animationTime.size()) {
          nextShape = animationTime.get(j + 1).getChangedShape().getShapeName();
        } else {
          nextShape = "";
        }
      }
      addRemainingShapeParams(animationTime.get(j));
    }
  }

  protected void addShapeParamsAtTimeT(List<Animations> animationTime, int timelineIndex,
                                       List<List<Animations>> subsetTimeline) {
    for (int j = 0; j < subsetTimeline.get(timelineIndex).size(); j++) {
      String nextShape = "";

      shapeType.add(animationTime.get(j).getChangedShape().getShapeType());
      singleAnimationChange(animationTime.get(j), timelineIndex);

      if ((j + 1) < animationTime.size()) {
        nextShape = animationTime.get(j + 1).getChangedShape().getShapeName();
      }

      while (nextShape.equals(animationTime.get(j).getChangedShape().getShapeName())) {
        singleAnimationChange(animationTime.get(j + 1), timelineIndex);
        j++;
        if ((j + 1) < animationTime.size()) {
          nextShape = animationTime.get(j + 1).getChangedShape().getShapeName();
        } else {
          nextShape = "";
        }
      }
      addRemainingShapeParams(animationTime.get(j));
    }
  }

  private void addRemainingShapeParams(Animations animation) {
    int shapeCount = shapeType.size();
    if (red.size() < shapeCount) {
      red.add(animation.getColor1().getRed().floatValue());
    }
    if (green.size() < shapeCount) {
      green.add(animation.getColor1().getGreen().floatValue());
    }
    if (blue.size() < shapeCount) {
      blue.add(animation.getColor1().getBlue().floatValue());
    }
    if (xPosition.size() < shapeCount) {
      xPosition.add(animation.getPosition1().getX().intValue());
    }
    if (yPosition.size() < shapeCount) {
      yPosition.add(animation.getPosition1().getY().intValue());
    }
    if (size.size() < shapeCount) {
      size.add(animation.getSizeParams1());
    }
  }

  protected void scheduleTimerTasks(TimerTask task, int time) {
    if (isLooped) {
      timer.scheduleAtFixedRate(task, (long) ((time / tempo) * 1000), animationPeriod);
    }
    else {
      timer.schedule(task, (long) ((time / tempo) * 1000));
    }
  }

  protected class DrawingPane extends JPanel {
    private DrawingPane() {
      setLayout(new BorderLayout());
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(1000, 1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (shapeType != null) {
        for (int i = 0; i < shapeType.size(); i++) {
          drawShape(g, i);
        }
      }
    }
  }
}
