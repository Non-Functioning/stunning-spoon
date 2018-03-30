package cs3500.animator.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

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
  protected Long animationPeriod;

  /**
   * Constructor for an abstract view.
   *
   * @param animationModel The model to transfer to a view.
   * @param tempo          Speed of the animation, as ticks per second.
   */
  public AbstractVisualView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);
  }

  /**
   * Draws a shape in the view.
   *
   * @param g Graphics class to utilise in drawing.
   */
  public void paint(Graphics g) {
    super.paint(g);
    for (int i = 0; i < shapeType.size(); i++) {
      drawShape(g, i);
    }
  }

  /**
   * This method schedules all the tasks required each tick to draw an animation.
   * The method creates a task based on the timeline and the animations within the
   * timeline.
   */
  protected void startVisual() {
    TimerTask task;

    for (int i = 0; i < timeline.size(); i++) {
      final int FINALI = i;
      java.util.List<Animations> animationTime = timeline.get(i);
      task = new TimerTask() {
        @Override
        public void run() {
          initializeParams();
          addShapeParamsAtTimeT(animationTime, FINALI);
          repaint();
        }
      };

      java.util.Timer timer = new java.util.Timer();
      timer.scheduleAtFixedRate(task, (long) ((FINALI / tempo) * 1000), animationPeriod);
    }
  }

  private void initializeParams() {
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
        g.fillOval(xPosition.get(index), yPosition.get(index), size.get(index).get(0).intValue(),
                size.get(index).get(1).intValue());
        break;
      case SQUARE:
        g.fillRect(xPosition.get(index), yPosition.get(index), size.get(index).get(0).intValue(),
                size.get(index).get(0).intValue());
        break;
      case CIRCLE:
        g.fillOval(xPosition.get(index), yPosition.get(index), size.get(index).get(0).intValue(),
                size.get(index).get(0).intValue());
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
   * @param currTick  current time
   * @return value at current time
   */
  private Double calcTweening(Double initVal, Double finalVal, Integer initTick,
                              Integer finalTick, Integer currTick) {
    Integer t1 = (finalTick - currTick);
    Integer t2 = (currTick - initTick);
    Integer t3 = (finalTick - initTick);
    Double v1 = t1.doubleValue() / t3.doubleValue();
    Double v2 = t2.doubleValue() / t3.doubleValue();
    return (initVal * v1) + (finalVal * v2);
  }

  private void addShapeParamsAtTimeT(List<Animations> animationTime, int timelineIndex) {
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
}