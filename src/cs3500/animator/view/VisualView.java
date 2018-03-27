package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JScrollBar;

import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Animations;
import cs3500.animator.model.SimpleAnimationModel;

/**
 * This class represents a visual view. It creates a window and
 * draws an animation in the window based on a given model.
 */
public class VisualView extends AbstractView {
  private List<Float> red;
  private List<Float> green;
  private List<Float> blue;
  private List<AnimatedShape.ShapeType> shapeType;
  private List<Integer> xPosition;
  private List<Integer> yPosition;
  private List<List<Double>> size;
  private Long taskTime;
  private Long animationPeriod;

  /**
   * Constructor for a visual view.
   *
   * @param animationModel Model to represent in a visual view.
   * @param tempo          Tempo, representing the desired ticks per second.
   */
  public VisualView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);

    setSize(500, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    setMinimumSize(new Dimension(500, 500));
    getContentPane().setLayout(new BorderLayout());

    JScrollBar horizonScroll = new JScrollBar(JScrollBar.HORIZONTAL);
    getContentPane().add(horizonScroll, BorderLayout.SOUTH);
    JScrollBar verticalScroll = new JScrollBar(JScrollBar.VERTICAL);
    getContentPane().add(verticalScroll, BorderLayout.EAST);
    animationPeriod = (long) (timeline.size()) * 100;
    start();

    pack();
    setVisible(true);
  }

  /**
   * Drawing a shape in the view.
   *
   * @param g Graphics class to utilise in drawing.
   */
  public void paint(Graphics g) {
    super.paint(g);
    for (int i = 0; i < red.size(); i++) {
      addShape(g, i);
    }
  }

  /**
   * This method schedules all the tasks required each tick to draw an animation.
   * The method creates a task based on the timeline and the animations within the
   * timeline.
   */
  private void start() {
    TimerTask task;

    for (int i = 0; i < timeline.size(); i++) {
      final int FINALI = i;
      List<Animations> animationTime = timeline.get(i);
      task = new TimerTask() {
        @Override
        public void run() {
          initializeParams();
          for (int j = 0; j < timeline.get(FINALI).size(); j++) {
            final int FINALJ = j;
            Animations.AnimateTypes type1 = animationTime.get(j).getType();
            if ((j + 1) < animationTime.size()) {
              if (animationTime.get(j).getChangedShape().equals(animationTime.get(j + 1)
                      .getChangedShape())) {
                Animations.AnimateTypes type2 = animationTime.get(j + 1).getType();

                if ((j + 2) < animationTime.size()) {
                  if (animationTime.get(j).getChangedShape().equals(animationTime.get(j + 2)
                          .getChangedShape())) {
                    Animations.AnimateTypes type3 = animationTime.get(j + 2).getType();

                    if ((j + 3) < animationTime.size()) {
                      if (animationTime.get(j).getChangedShape().equals(animationTime.get(j + 3)
                              .getChangedShape())) {
                        if (type1.equals(Animations.AnimateTypes.APPEAR)
                                | type1.equals(Animations.AnimateTypes.DISAPPEAR)) {
                          threeAnimationsAtTimeT(animationTime.get(FINALJ + 1),
                                  animationTime.get(FINALJ + 2),
                                  animationTime.get(FINALJ + 3), FINALI, FINALJ);
                        } else if (type2.equals(Animations.AnimateTypes.APPEAR)
                                | type2.equals(Animations.AnimateTypes.DISAPPEAR)) {
                          threeAnimationsAtTimeT(animationTime.get(FINALJ),
                                  animationTime.get(FINALJ + 2),
                                  animationTime.get(FINALJ + 3), FINALI, FINALJ);
                        } else if (type3.equals(Animations.AnimateTypes.APPEAR)
                                | type3.equals(Animations.AnimateTypes.DISAPPEAR)) {
                          threeAnimationsAtTimeT(animationTime.get(FINALJ),
                                  animationTime.get(FINALJ + 1),
                                  animationTime.get(FINALJ + 3), FINALI, FINALJ);
                        } else {
                          threeAnimationsAtTimeT(animationTime.get(FINALJ),
                                  animationTime.get(FINALJ + 1),
                                  animationTime.get(FINALJ + 2), FINALI, FINALJ);
                        }
                        j += 3;
                        continue;
                      }
                    }
                    if (type1.equals(Animations.AnimateTypes.APPEAR)
                            | type1.equals(Animations.AnimateTypes.DISAPPEAR)) {
                      twoAnimationsAtTimeT(animationTime.get(FINALJ + 1),
                              animationTime.get(FINALJ + 2), FINALI, FINALJ);
                    } else if (type2.equals(Animations.AnimateTypes.APPEAR)
                            | type2.equals(Animations.AnimateTypes.DISAPPEAR)) {
                      twoAnimationsAtTimeT(animationTime.get(FINALJ),
                              animationTime.get(FINALJ + 2), FINALI, FINALJ);
                    } else if (type3.equals(Animations.AnimateTypes.APPEAR)
                            | type3.equals(Animations.AnimateTypes.DISAPPEAR)) {
                      twoAnimationsAtTimeT(animationTime.get(FINALJ),
                              animationTime.get(FINALJ + 1), FINALI, FINALJ);
                    } else {
                      threeAnimationsAtTimeT(animationTime.get(FINALJ),
                              animationTime.get(FINALJ + 1),
                              animationTime.get(FINALJ + 2), FINALI, FINALJ);
                    }
                    j += 2;
                    continue;
                  }
                }
                if (type1.equals(Animations.AnimateTypes.APPEAR)
                        | type1.equals(Animations.AnimateTypes.DISAPPEAR)) {
                  animationAtTimeT(animationTime.get(FINALJ + 1), FINALI, FINALJ);
                } else if (type2.equals(Animations.AnimateTypes.APPEAR)
                        | type2.equals(Animations.AnimateTypes.DISAPPEAR)) {
                  animationAtTimeT(animationTime.get(FINALJ), FINALI, FINALJ);
                } else {
                  twoAnimationsAtTimeT(animationTime.get(FINALJ),
                          animationTime.get(FINALJ + 1), FINALI, FINALJ);
                }
                j += 1;
                continue;
              }
            }
            animationAtTimeT(animationTime.get(FINALJ), FINALI, FINALJ);
          }
          repaint();
        }
      };
      Timer timer = new Timer();
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
   * This method draws the given animation at time in the window.
   *
   * @param animation animation to draw
   * @param time      time of draw
   */
  private void animationAtTimeT(Animations animation, int time, int index) {
    switch (animation.getType()) {
      case MOVE:
        addMovingShape(animation, time, index);
        break;
      case CHANGESIZE:
        addScalingShape(animation, time, index);
        break;
      case CHANGECOLOR:
        addColorChangingShape(animation, time, index);
        break;
      case APPEAR:
      case DISAPPEAR:
      case STILL:
        addAppearOrStillShape(animation, time, index);
        break;
      default:
        throw new IllegalArgumentException("Invalid animation type");
    }
  }

  /**
   * This method draws 2 animations that occur on the same shape at the same time.
   *
   * @param animation1 animation1 to draw
   * @param animation2 animation2 to draw
   * @param time       time of draw
   */
  private void twoAnimationsAtTimeT(Animations animation1, Animations animation2,
                                    int time, int index) {
    shapeType.add(index, animation1.getChangedShape().getShapeType());
    Animations.AnimateTypes type1 = animation1.getType();
    Animations.AnimateTypes type2 = animation2.getType();
    singleAnimationChange(animation1, time, index);
    singleAnimationChange(animation2, time, index);

    switch (type1) {
      case MOVE:
        switch (type2) {
          case CHANGESIZE:
            red.add(index, animation1.getColor1().getRed().floatValue());
            green.add(index, animation1.getColor1().getGreen().floatValue());
            blue.add(index, animation1.getColor1().getBlue().floatValue());
            break;
          case CHANGECOLOR:
            size.add(index, animation1.getSizeParams1());
            break;
          default:
            addAppearOrStillShape(animation1, time, index);
            break;
        }
        break;
      case CHANGESIZE:
        switch (type2) {
          case MOVE:
            red.add(index, animation1.getColor1().getRed().floatValue());
            green.add(index, animation1.getColor1().getGreen().floatValue());
            blue.add(index, animation1.getColor1().getBlue().floatValue());
            break;
          case CHANGECOLOR:
            xPosition.add(index, animation1.getPosition1().getX().intValue());
            yPosition.add(index, animation1.getPosition1().getY().intValue());
            break;
          default:
            addAppearOrStillShape(animation1, time, index);
            break;
        }
        break;
      case CHANGECOLOR:
        switch (type2) {
          case MOVE:
            size.add(index, animation1.getSizeParams1());
            break;
          case CHANGESIZE:
            xPosition.add(index, animation1.getPosition1().getX().intValue());
            yPosition.add(index, animation1.getPosition1().getY().intValue());
            break;
          default:
            addAppearOrStillShape(animation1, time, index);
            break;
        }
        break;
      default:
        addAppearOrStillShape(animation1, time, index);
        break;
    }
  }

  /**
   * This method draws 3 animations that occur on the same shape at the same time.
   *
   * @param animation1 animation1 to draw
   * @param animation2 animation2 to draw
   * @param animation3 animation3 to draw
   * @param time       time of draw
   */
  private void threeAnimationsAtTimeT(Animations animation1, Animations animation2,
                                      Animations animation3, int time, int index) {
    shapeType.add(index, animation1.getChangedShape().getShapeType());
    singleAnimationChange(animation1, time, index);
    singleAnimationChange(animation2, time, index);
    singleAnimationChange(animation3, time, index);
  }

  /**
   * This assigns values that will be drawn corresponding to a given animation at a given time.
   *
   * @param animation animation to draw
   * @param time      time of draw
   */
  private void singleAnimationChange(Animations animation, int time, int index) {
    switch (animation.getType()) {
      case MOVE:
        xPosition.add(index, calcTweening(animation.getPosition1().getX(),
                animation.getPosition2().getX(), animation.getTime1(), animation.getTime2(),
                time).intValue());
        yPosition.add(index, calcTweening(animation.getPosition1().getY(),
                animation.getPosition2().getY(), animation.getTime1(), animation.getTime2(),
                time).intValue());
        break;
      case CHANGESIZE:
        List<Double> newSize = new ArrayList<>();
        for (int i = 0; i < animation.getSizeParams1().size(); i++) {
          newSize.add(calcTweening(animation.getSizeParams1().get(i),
                  animation.getSizeParams2().get(i), animation.getTime1(), animation.getTime2(),
                  time));
        }
        size.add(index, newSize);
        break;
      case CHANGECOLOR:
        red.add(index, calcTweening(animation.getColor1().getRed(), animation.getColor2().getRed(),
                animation.getTime1(), animation.getTime2(), time).floatValue());
        green.add(index, calcTweening(animation.getColor1().getGreen(),
                animation.getColor2().getGreen(), animation.getTime1(), animation.getTime2(),
                time).floatValue());
        blue.add(index, calcTweening(animation.getColor1().getBlue(),
                animation.getColor2().getBlue(), animation.getTime1(), animation.getTime2(),
                time).floatValue());
        break;
      case APPEAR:
      case DISAPPEAR:
      case STILL:
        addAppearOrStillShape(animation, time, index);
        break;
      default:
        throw new IllegalArgumentException("Invalid animation type");
    }
  }

  /**
   * This method creates a shape in the window using the Graphics class.
   *
   * @param g graphics
   */
  private void addShape(Graphics g, int index) {
    if (taskTime != null) {
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
  }

  /**
   * This method assigns values that will be drawn that correspond to a given move animation
   * at a given time.
   *
   * @param animation animation to draw
   * @param time      time of draw
   */
  private void addMovingShape(Animations animation, int time, int index) {
    red.add(index, animation.getColor1().getRed().floatValue());
    green.add(index, animation.getColor1().getGreen().floatValue());
    blue.add(index, animation.getColor1().getBlue().floatValue());
    shapeType.add(index, animation.getChangedShape().getShapeType());
    xPosition.add(index, calcTweening(animation.getPosition1().getX(),
            animation.getPosition2().getX(), animation.getTime1(), animation.getTime2(),
            time).intValue());
    yPosition.add(index, calcTweening(animation.getPosition1().getY(),
            animation.getPosition2().getY(), animation.getTime1(), animation.getTime2(),
            time).intValue());
    size.add(index, animation.getSizeParams1());
    taskTime = ((long) time);
  }

  /**
   * This method assigns values that will be drawn that correspond to a given change size animation
   * at a given time.
   *
   * @param animation animation to draw
   * @param time      time of draw
   */
  private void addScalingShape(Animations animation, int time, int index) {
    List<Double> newSize = new ArrayList<>();
    for (int i = 0; i < animation.getSizeParams1().size(); i++) {
      newSize.add(calcTweening(animation.getSizeParams1().get(i), animation.getSizeParams2().get(i),
              animation.getTime1(), animation.getTime2(), time));
    }

    red.add(index, animation.getColor1().getRed().floatValue());
    green.add(index, animation.getColor1().getGreen().floatValue());
    blue.add(index, animation.getColor1().getBlue().floatValue());
    shapeType.add(index, animation.getChangedShape().getShapeType());
    xPosition.add(index, animation.getPosition1().getX().intValue());
    yPosition.add(index, animation.getPosition1().getY().intValue());
    size.add(index, newSize);
    taskTime = ((long) time);
  }

  /**
   * This method assigns values that will be drawn that correspond to a given change color
   * animation at a given time.
   *
   * @param animation animation to draw
   * @param time      time of draw
   */
  private void addColorChangingShape(Animations animation, int time, int index) {
    red.add(index, calcTweening(animation.getColor1().getRed(), animation.getColor2().getRed(),
            animation.getTime1(), animation.getTime2(), time).floatValue());
    green.add(index, calcTweening(animation.getColor1().getGreen(),
            animation.getColor2().getGreen(), animation.getTime1(), animation.getTime2(),
            time).floatValue());
    blue.add(index, calcTweening(animation.getColor1().getBlue(), animation.getColor2().getBlue(),
            animation.getTime1(), animation.getTime2(), time).floatValue());
    shapeType.add(index, animation.getChangedShape().getShapeType());
    xPosition.add(index, animation.getPosition1().getX().intValue());
    yPosition.add(index, animation.getPosition1().getY().intValue());
    size.add(index, animation.getSizeParams1());
    taskTime = ((long) time);
  }

  /**
   * This method assigns values that will be drawn that correspond to when a shape first appears
   * or when a shape is staying still at the given time.
   *
   * @param animation animation to draw
   * @param time      time of draw
   */
  private void addAppearOrStillShape(Animations animation, int time, int index) {
    red.add(index, animation.getColor1().getRed().floatValue());
    green.add(index, animation.getColor1().getGreen().floatValue());
    blue.add(index, animation.getColor1().getBlue().floatValue());
    shapeType.add(index, animation.getChangedShape().getShapeType());
    xPosition.add(index, animation.getPosition1().getX().intValue());
    yPosition.add(index, animation.getPosition1().getY().intValue());
    size.add(index, animation.getSizeParams1());
    taskTime = ((long) time);
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
}
