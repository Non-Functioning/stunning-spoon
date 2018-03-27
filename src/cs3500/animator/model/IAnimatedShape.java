package cs3500.animator.model;

import java.util.List;

/**
 * This is the interface for the AnimatedShape class.
 * This holds all the methods that can be used read information
 * about the characters of a shape.
 */
public interface IAnimatedShape {

  Position2D getInitialPosition();

  RGB getInitialColor();

  List<Double> getInitialSize();

  String getShapeName();

  Integer getAppearTime();

  Integer getDisappearTime();

  AnimatedShape.ShapeType getShapeType();

  AnimatedShape.RefPointType getRefPoint();

  /**
   * Converts the given size parameters into a String based on the shape's type.
   * @param size    shape's size parameters
   * @return        string representation of the shape's size
   */
  String sizeParamsToString(List<Double> size);
}
