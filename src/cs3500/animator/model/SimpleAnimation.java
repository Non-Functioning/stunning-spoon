package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.util.TweenModelBuilder;

/**
 * This is the class that represents the model of the SimpleAnimation.
 * An instance of this model represents an animation through shapes, their
 * characteristics, and changes to their characteristics over time.
 */
public class SimpleAnimation implements SimpleAnimationModel {
  private List<AnimatedShape> shapes;
  private List<Animations> animations;
  private List<List<Animations>> timeline;

  /**
   * The constructor for the SimpleAnimation that initializes the Lists
   * that hold all the shapes and animations in the model.
   */
  public SimpleAnimation() {
    shapes = new ArrayList<>();
    animations = new ArrayList<>();
    timeline = new ArrayList<>();
  }

  /**
   * Creates a new AnimatedShape object holding the shape's parameters.
   * Then adds the shape to the List of shapes and adds its appearance and
   * disappearance into the timeline based on the time they occur.
   * @param name    shape name
   * @param type    shape type
   * @param color1  shape color
   * @param initial shape's initial position
   * @param params  shape's size
   * @param time1   time of appearance
   * @param time2   time of disappearance
   */
  @Override
  public void createShape(String name, AnimatedShape.ShapeType type, RGB color1,
                          Position2D initial, List<Double> params, Integer time1, Integer time2) {
    AnimatedShape shape1 = new AnimatedShape(name, type, color1, initial, params, time1, time2);
    int shapeCount = shapes.size();
    if (shapes.isEmpty()) {
      shapes.add(shape1);
    }
    else {
      for (int i = 0; i < shapeCount; i++) {
        if (shape1.getAppearTime() <= shapes.get(i).getAppearTime()) {
          shapes.add(i, shape1);
          break;
        }
        else if (i == (shapeCount - 1)) {
          shapes.add(shape1);
        }
      }
    }
    if (timeline.size() < time2) {
      for (int i = timeline.size(); i <= time2; i++) {
        timeline.add(i, new ArrayList<>());
      }
    }
    timeline.get(time1).add(new ShapeAppears(shape1, time1));
    timeline.get(time2).add(new ShapeDisappears(shape1, time2));
    for (int i = (time1 + 1); i < time2; i++) {
      timeline.get(i).add(new StillShape(shape1, i));
    }
  }

  @Override
  public void copyShape(AnimatedShape shape) {
    createShape(shape.getShapeName(), shape.getShapeType(), shape.getInitialColor(),
            shape.getInitialPosition(), shape.getInitialSize(), shape.getAppearTime(),
            shape.getDisappearTime());
  }

  @Override
  public void copyAnimation(Animations animate) {
    if (getShapeByName(animate.getChangedShape().getShapeName()) != null) {
      addNewAnimInTimeOrder(animate);
      addNewAnimToTimeline(animate);
      updateBeginAnimaPositions();
      updateBeginAnimaColors();
      updateBeginAnimaSizes();
    }
    else {
      throw new IllegalArgumentException("Animation's shape does not exist in animation.");
    }
  }

  /**
   * Gets a shape from the shape List based on the index.
   * @param   shapeIndex  shape's index
   * @return              specified shape
   */
  @Override
  public AnimatedShape getShape(int shapeIndex) {
    AnimatedShape animatedShape = shapes.get(shapeIndex);
    return animatedShape;
  }

  @Override
  public AnimatedShape getShapeByName(String name) {
    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).getShapeName().equals(name)) {
        return shapes.get(i);
      }
    }
    return null;
  }

  @Override
  public List<AnimatedShape> getShapes() {
    return this.shapes;
  }

  @Override
  public List<Animations> getAnimations() {
    return this.animations;
  }

  @Override
  public List<List<Animations>> getTimeline() {
    return this.timeline;
  }

  /**
   * Creates a new move Animation based on the parameters. Tests if its a valid
   * move and then adds it to the List of Animations and the timeline.
   * @param shape         shape to be moved
   * @param newPosition   shape's new position
   * @param time1         beginning time of move
   * @param time2         end time of move
   */
  @Override
  public void moveShape(AnimatedShape shape, Position2D newPosition, Integer time1,
                        Integer time2) {
    MoveShape move = new MoveShape(shape, calcCurrPosition(shape, time1), newPosition,
            time1, time2);
    if (!isValidAnimation(move)) {
      throw new IllegalArgumentException("Invalid move");
    }

    addNewAnimInTimeOrder(move);
    addNewAnimToTimeline(move);
    updateBeginAnimaPositions();
    updateBeginAnimaColors();
    updateBeginAnimaSizes();
  }

  /**
   * Creates a new changeColor Animation based on the parameters. Tests if its a valid
   * changeColor and then adds it to the List of Animations and the timeline.
   * @param shape     shape to be changed
   * @param newColor  shape's new color
   * @param time1     beginning time of color change
   * @param time2     end time of color change
   */
  @Override
  public void changeShapeColor(AnimatedShape shape, RGB newColor, Integer time1,
                               Integer time2) {
    ChangeShapeColor colorChange = new ChangeShapeColor(shape, calcCurrColor(shape, time1),
            newColor, time1, time2);
    if (!isValidAnimation(colorChange)) {
      throw new IllegalArgumentException("Invalid color change");
    }

    addNewAnimInTimeOrder(colorChange);
    addNewAnimToTimeline(colorChange);
    updateBeginAnimaPositions();
    updateBeginAnimaColors();
    updateBeginAnimaSizes();
  }

  /**
   * Creates a new sizeChange Animation based on the parameters. Tests if its a valid
   * sizeChange and then adds it to the List of Animations and the timeline.
   * @param shape           shape to be changed
   * @param newSizeParams   shape's new size
   * @param time1           beginning time of size change
   * @param time2           end time of size change
   */
  @Override
  public void changeShapeSize(AnimatedShape shape, List<Double> newSizeParams, Integer time1,
                              Integer time2) {
    ChangeShapeSize sizeChange = new ChangeShapeSize(shape, calcCurrSize(shape, time1),
            newSizeParams, time1, time2);
    if (!isValidAnimation(sizeChange)) {
      throw new IllegalArgumentException("Invalid size change");
    }

    addNewAnimInTimeOrder(sizeChange);
    addNewAnimToTimeline(sizeChange);
    updateBeginAnimaPositions();
    updateBeginAnimaColors();
    updateBeginAnimaSizes();
  }

  /**
   * Returns the String representation of the entire animation including
   * all shapes and Animations.
   * @return    animation as a String
   */
  @Override
  public String printAnimation() {
    StringBuilder shapesString = new StringBuilder("Shapes:\n");
    StringBuilder animationString = new StringBuilder();

    for (int i = 0; i < shapes.size(); i++) {
      shapesString.append(shapes.get(i).toString());
      shapesString.append("\n\n");
    }

    for (int i = 0; i < animations.size(); i++) {
      animationString.append(animations.get(i).toString());
      if (i != (animations.size() - 1)) {
        animationString.append("\n");
      }
    }
    return shapesString.append(animationString).toString();
  }

  /**
   * Returns the String representation of a shape based on the shape's index.
   * @param shapeIndex  shape's index
   * @return            shape
   */
  @Override
  public String getShapeStatus(int shapeIndex) {
    return shapes.get(shapeIndex).toString();
  }

  /**
   * Removes an Animation from the timeline and animation List that is
   * occuring to a specified shape at a specified time.
   * The time does not have to be the beginning or the end of the Animation,
   * it can be at any point between the two.
   * @param shape   shape being animated
   * @param type    animation type
   * @param time    time of animation
   */
  @Override
  public void removeAnimation(AnimatedShape shape, Animations.AnimateTypes type, Integer time) {
    Integer time1 = 0;
    Integer time2 = 0;
    for (int i = 0; i < timeline.get(time).size(); i++) {
      if (timeline.get(time).get(i).getChangedShape().equals(shape)
              && timeline.get(time).get(i).getType().equals(type)) {
        time1 = timeline.get(time).get(i).getTime1();
        time2 = timeline.get(time).get(i).getTime2();
        break;
      }
      if (i == (timeline.get(time).size() - 1)) {
        throw new IllegalArgumentException("Invalid animation to remove");
      }
    }
    for (int j = time1; j <= time2; j++) {
      for (int k = 0; k < timeline.get(j).size(); k++) {
        if (timeline.get(j).get(k).getChangedShape().equals(shape)
                && timeline.get(j).get(k).getType().equals(type)) {
          timeline.get(j).remove(k);
        }
      }
    }

    for (int i = 0; i < animations.size(); i++) {
      if (animations.get(i).getChangedShape().equals(shape)
              && animations.get(i).getType().equals(type)
              && (animations.get(i).getTime1().equals(time1))
              && (animations.get(i).getTime2().equals(time2))) {
        animations.remove(i);
        break;
      }
      if (i == (animations.size() - 1)) {
        throw new IllegalArgumentException("Invalid animation to remove");
      }
    }
  }

  /**
   * Removes a shape at the specified shapeIndex and all related Animations
   * from the animations and timeline Lists.
   * @param shapeIndex  shape index to be removed
   */
  @Override
  public void removeShape(int shapeIndex) {
    AnimatedShape tempShape = shapes.get(shapeIndex);
    shapes.remove(shapeIndex);
    for (int i = 0; i < timeline.size(); i++) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if (timeline.get(i).get(j).getChangedShape().equals(tempShape)) {
          timeline.get(i).remove(j);
        }
      }
    }
    for (int i = 0; i < animations.size(); i++) {
      if (animations.get(i).getChangedShape().equals(tempShape)) {
        animations.remove(i);
      }
    }
  }

  @Override
  public void removeShapeByName(String name) {
    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).getShapeName().equals(name)) {
        removeShape(i);
      }
    }
  }

  /**
   * This method calculates a shape's position at a given time. If a shape is
   * moving at the given time, then this method will return the new position the
   * shape is moving to.
   * @param shape   shape
   * @param time    time of position
   * @return        shape's Position2D at time
   */
  @Override
  public Position2D calcCurrPosition(AnimatedShape shape, int time) {
    for (int i = time; i > -1; i--) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getChangedShape() == shape)
                & (timeline.get(i).get(j).getType() == Animations.AnimateTypes.MOVE)) {
          return timeline.get(i).get(j).getPosition2();
        }
      }
    }
    return shape.getInitialPosition();
  }

  /**
   * This method calculates a shape's color at a given time. If a shape is
   * changing color at the given time, then this method will return the new
   * color the shape is changing to.
   * @param shape   shape
   * @param time    time of color
   * @return        shape's RGB at time
   */
  @Override
  public RGB calcCurrColor(AnimatedShape shape, int time) {
    for (int i = time; i > -1; i--) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getChangedShape() == shape)
                & (timeline.get(i).get(j).getType() == Animations.AnimateTypes.CHANGECOLOR)) {
          return timeline.get(i).get(j).getColor2();
        }
      }
    }
    return shape.getInitialColor();
  }

  /**
   * This method calculates a shape's size at a given time. If a shape is
   * changing size at the given time, then this method will return the new
   * size the shape is changing to.
   * @param shape   shape
   * @param time    time of size
   * @return        shape's size at time
   */
  @Override
  public List<Double> calcCurrSize(AnimatedShape shape, int time) {
    for (int i = time; i > -1; i--) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getChangedShape() == shape)
                & (timeline.get(i).get(j).getType() == Animations.AnimateTypes.CHANGESIZE)) {
          return timeline.get(i).get(j).getSizeParams2();
        }
      }
    }
    return shape.getInitialSize();
  }

  /**
   * Checks if the given Animation is valid and that it does not overlap
   * with another Animation of the same type occurring at the same time on
   * the same shape.
   * @param animate   Animation to validate
   * @return          true, if Animation is valid
   */
  private boolean isValidAnimation(Animations animate) {
    for (int i = animate.getTime1(); i <= animate.getTime2(); i++) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getType() == animate.getType())
                && (animate.getChangedShape().equals(timeline.get(i).get(j).getChangedShape()))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Adds an Animation to the animations List in the order of beginning time.
   * @param obj   Animation to add
   */
  private void addNewAnimInTimeOrder(Animations obj) {
    if (animations.isEmpty()) {
      animations.add(obj);
    }
    else {
      for (int i = 0; i < animations.size(); i++) {
        if (obj.getTime1() <= animations.get(i).getTime1()) {
          animations.add(i, obj);
          break;
        }
        else if (i == (animations.size() - 1)) {
          animations.add(obj);
          break;
        }
      }
    }
  }

  /**
   * Adds an Animation to the timeline List at all time instances
   * that the Animation occurs during.
   * @param obj   Animation to add
   */
  private void addNewAnimToTimeline(Animations obj) {
    for (int i = obj.getTime1(); i <= obj.getTime2(); i++) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if (timeline.get(i).get(j).getChangedShape() == obj.getChangedShape()
                & timeline.get(i).get(j).getType() == Animations.AnimateTypes.STILL) {
          timeline.get(i).remove(j);
        }
      }
      if (timeline.get(i).isEmpty()) {
        timeline.get(i).add(obj);
      }
      else {
        for (int j = 0; j < timeline.get(i).size(); j++) {
          if (obj.getChangedShape().getShapeName().compareTo(timeline.get(i).get(j).
                  getChangedShape().getShapeName()) <= 0) {
            timeline.get(i).add(j, obj);
            break;
          }
          else if (j == (timeline.get(i).size()) - 1) {
            timeline.get(i).add(obj);
            break;
          }
        }
      }
    }
  }

  /**
   * This method updates all positions of animations within the timeline and
   * animations Lists. If a move animation is added, this method will update
   * the animations after the added one to have the correct starting position.
   */
  private void updateBeginAnimaPositions() {
    Position2D currPosition;
    Position2D nextPosition;
    AnimatedShape currShape;
    for (int i = 0; i < shapes.size(); i++) {
      currShape = shapes.get(i);
      currPosition = currShape.getInitialPosition();
      nextPosition = currPosition;
      for (int j = 0; j < timeline.size(); j++) {
        for (int k = 0; k < timeline.get(j).size(); k++) {
          if (timeline.get(j).get(k).getChangedShape().equals(currShape)) {
            timeline.get(j).get(k).setPosition1(currPosition);
            if (timeline.get(j).get(k).getType() == Animations.AnimateTypes.MOVE) {
              if (timeline.get(j).get(k).getTime2() == j) {
                nextPosition = timeline.get(j).get(k).getPosition2();
              }
            }
          }
        }
        currPosition = nextPosition;
      }
      currPosition = currShape.getInitialPosition();
      for (int j = 0; j < animations.size(); j++) {
        if (animations.get(j).getChangedShape().equals(currShape)) {
          animations.get(j).setPosition1(currPosition);
          if (animations.get(j).getType() == Animations.AnimateTypes.MOVE) {
            currPosition = animations.get(j).getPosition2();
          }
        }
      }
    }
  }

  /**
   * This method updates all colors of animations within the timeline and
   * animations Lists. If a change color animation is added, this method will
   * update the animations after the added one to have the correct starting
   * color.
   */
  private void updateBeginAnimaColors() {
    RGB currColor;
    RGB nextColor;
    AnimatedShape currShape;
    for (int i = 0; i < shapes.size(); i++) {
      currShape = shapes.get(i);
      currColor = currShape.getInitialColor();
      nextColor = currColor;
      for (int j = 0; j < timeline.size(); j++) {
        for (int k = 0; k < timeline.get(j).size(); k++) {
          if (timeline.get(j).get(k).getChangedShape().equals(currShape)) {
            timeline.get(j).get(k).setColor1(currColor);
            if (timeline.get(j).get(k).getType() == Animations.AnimateTypes.CHANGECOLOR) {
              if (timeline.get(j).get(k).getTime2() == j) {
                nextColor = timeline.get(j).get(k).getColor2();
              }
            }
          }
        }
        currColor = nextColor;
      }
      currColor = currShape.getInitialColor();
      for (int j = 0; j < animations.size(); j++) {
        if (animations.get(j).getChangedShape().equals(currShape)) {
          animations.get(j).setColor1(currColor);
          if (animations.get(j).getType() == Animations.AnimateTypes.CHANGECOLOR) {
            currColor = animations.get(j).getColor2();
          }
        }
      }
    }
  }

  /**
   * This method updates all sizes of animations within the timeline and
   * animations Lists. If a change size animation is added, this method will
   * update the animations after the added one to have the correct starting size.
   */
  private void updateBeginAnimaSizes() {
    List<Double> currSize;
    List<Double> nextSize;
    AnimatedShape currShape;
    for (int i = 0; i < shapes.size(); i++) {
      currShape = shapes.get(i);
      currSize = currShape.getInitialSize();
      nextSize = currSize;
      for (int j = 0; j < timeline.size(); j++) {
        for (int k = 0; k < timeline.get(j).size(); k++) {
          if (timeline.get(j).get(k).getChangedShape().equals(currShape)) {
            timeline.get(j).get(k).setSizeParams1(currSize);
            if (timeline.get(j).get(k).getType() == Animations.AnimateTypes.CHANGESIZE) {
              if (timeline.get(j).get(k).getTime2() == j) {
                nextSize = timeline.get(j).get(k).getSizeParams2();
              }
            }
          }
        }
        currSize = nextSize;
      }
      currSize = currShape.getInitialSize();
      for (int j = 0; j < animations.size(); j++) {
        if (animations.get(j).getChangedShape().equals(currShape)) {
          animations.get(j).setSizeParams1(currSize);
          if (animations.get(j).getType() == Animations.AnimateTypes.CHANGESIZE) {
            currSize = animations.get(j).getSizeParams2();
          }
        }
      }
    }
  }

  public static final class Builder implements TweenModelBuilder<SimpleAnimationModel> {
    private SimpleAnimationModel model = new SimpleAnimation();

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addOval(String name, float cx, float cy,
                                                           float xRadius, float yRadius, float red,
                                                           float green, float blue, int startOfLife,
                                                           int endOfLife) {
      List<Double> params = new ArrayList<Double>();
      params.add((double) xRadius);
      params.add((double) yRadius);
      this.model.createShape(name, AnimatedShape.ShapeType.OVAL, new RGB(red, green, blue),
              new Position2D(cx, cy), params, startOfLife, endOfLife);
      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addRectangle(String name, float lx, float ly,
                                                                float width, float height,
                                                                float red, float green, float blue,
                                                                int startOfLife, int endOfLife) {
      List<Double> params = new ArrayList<Double>();
      params.add((double) width);
      params.add((double) height);
      this.model.createShape(name, AnimatedShape.ShapeType.RECTANGLE, new RGB(red, green, blue),
              new Position2D(lx, ly), params, startOfLife, endOfLife);
      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addMove(String name, float moveFromX,
                                                           float moveFromY, float moveToX,
                                                           float moveToY, int startTime,
                                                           int endTime) {
      AnimatedShape shapeToChange;
      if (shapeToChange(name) != null) {
        shapeToChange = shapeToChange(name);
        this.model.moveShape(shapeToChange, new Position2D(moveToX, moveToY), startTime, endTime);
      }

      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addColorChange(String name, float oldR,
                                                                  float oldG, float oldB,
                                                                  float newR, float newG,
                                                                  float newB, int startTime,
                                                                  int endTime) {
      AnimatedShape shapeToChange;
      if (shapeToChange(name) != null) {
        shapeToChange = shapeToChange(name);
        this.model.changeShapeColor(shapeToChange, new RGB(newR, newG, newB), startTime, endTime);
      }

      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addScaleToChange(String name, float fromSx,
                                                                    float fromSy, float toSx,
                                                                    float toSy, int startTime,
                                                                    int endTime) {
      AnimatedShape shapeToChange;
      if (shapeToChange(name) != null) {
        shapeToChange = shapeToChange(name);
        List<Double> newParams = new ArrayList<Double>();
        newParams.add((double) toSx);
        newParams.add((double) toSy);
        this.model.changeShapeSize(shapeToChange, newParams, startTime, endTime);
      }

      return this;
    }

    @Override
    public SimpleAnimationModel build() {
      return this.model;
    }

    private AnimatedShape shapeToChange(String name) {
      AnimatedShape shape;
      for (int i = 0; i < this.model.getShapes().size(); i++) {
        if (this.model.getShapes().get(i).getShapeName().equals(name)) {
          shape = this.model.getShapes().get(i);
          return shape;
        }
      }
      return null;
    }
  }
}
