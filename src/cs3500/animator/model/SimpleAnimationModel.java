package cs3500.animator.model;

import java.util.List;

/**
 * The interface for the SimpleAnimation model. Consist of methods that can be used to
 * create an animation. This includes create shapes and defining their characteristics
 * and creating animations performed on shapes and their characteristics.
 */
public interface SimpleAnimationModel {
  /**
   * Creates a shape by specifying the initial name, shape type, color, and size.
   * Also specifies the initial position and at what point in time the shape
   * appears and disappears.
   * @param name    shape name
   * @param type    shape type
   * @param color1  shape color
   * @param initial shape's initial position
   * @param params  shape's size
   * @param time1   time of appearance
   * @param time2   time of disappearance
   */
  void createShape(String name, AnimatedShape.ShapeType type, RGB color1, Position2D initial,
                   List<Double> params, Integer time1, Integer time2);

  /**
   * Copies a given shape and adds in into the model.
   * @param shape   shape to copy
   */
  void copyShape(AnimatedShape shape);

  /**
   * Copies a given animation and adds in into the model.
   * @param animate   animation to copy
   */
  void copyAnimation(Animations animate);

  /**
   * Moves a specified shape to a different position at the specified
   * point in time.
   * @param shape         shape to be moved
   * @param newPosition   shape's new position
   * @param time1         beginning time of move
   * @param time2         end time of move
   */
  void moveShape(AnimatedShape shape, Position2D newPosition, Integer time1, Integer time2);

  /**
   * Changes the color of the specified shape.
   * @param shape     shape to be changed
   * @param newColor  shape's new color
   * @param time1     beginning time of color change
   * @param time2     end time of color change
   */
  void changeShapeColor(AnimatedShape shape, RGB newColor, Integer time1,
                        Integer time2);

  /**
   * Changes the size params of a given shape.
   * @param shape           shape to be changed
   * @param newSizeParams   shape's new size
   * @param time1           beginning time of size change
   * @param time2           end time of size change
   */
  void changeShapeSize(AnimatedShape shape, List<Double> newSizeParams, Integer time1,
                       Integer time2);

  /**
   * Get the entire animation description as a String for all shapes.
   * @return   String representation of animation
   */
  String printAnimation();

  /**
   * Get a single shapes animation description as a String.
   * @param shapeIndex  shape's index
   * @return            String representation of shape
   */
  String getShapeStatus(int shapeIndex);

  /**
   * Gets a specified shape in the animation.
   * @param shapeIndex    shape's index
   * @return              shape
   */
  AnimatedShape getShape(int shapeIndex);

  /**
   * This returns a shape with a name that matches the given name
   * @param name    shape name
   * @return        shape with shape name
   */
  AnimatedShape getShapeByName(String name);

  /**
   * Gets the list of shapes.
   * @return  shape list
   */
  List<AnimatedShape> getShapes();

  /**
   * Gets the list of animations.
   * @return  animation list
   */
  List<Animations> getAnimations();

  /**
   * Gets the timeline list.
   * @return  timeline
   */
  List<List<Animations>> getTimeline();

  /**
   * This method calculates a shape's position at a given time. If a shape is
   * moving at the given time, then this method will return the new position the
   * shape is moving to.
   * @param shape   shape
   * @param time    time of position
   * @return        shape's Position2D at time
   */
  Position2D calcCurrPosition(AnimatedShape shape, int time);

  /**
   * This method calculates a shape's color at a given time. If a shape is
   * changing color at the given time, then this method will return the new
   * color the shape is changing to.
   * @param shape   shape
   * @param time    time of color
   * @return        shape's RGB at time
   */
  RGB calcCurrColor(AnimatedShape shape, int time);

  /**
   * This method calculates a shape's size at a given time. If a shape is
   * changing size at the given time, then this method will return the new
   * size the shape is changing to.
   * @param shape   shape
   * @param time    time of size
   * @return        shape's size at time
   */
  List<Double> calcCurrSize(AnimatedShape shape, int time);

  /**
   * Removes a specified shape and its animations from the animation.
   * @param shapeIndex  shape index to be removed
   */
  void removeShape(int shapeIndex);

  /**
   * Removes a specified shape and its animations from the animation.
   * @param name  shape name to be removed
   */
  void removeShapeByName(String name);

  /**
   * Removes a specified animation from the List of animations.
   * @param shape   shape being animated
   * @param type    animation type
   * @param time    time of animation
   */
  void removeAnimation(AnimatedShape shape, Animations.AnimateTypes type, Integer time);
}
