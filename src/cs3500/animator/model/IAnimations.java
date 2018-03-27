package cs3500.animator.model;

import java.util.List;

/**
 * This is the interface for the Animations class.
 * This holds all the methods that can be used to read and modify
 * characteristics of an animation performed on a shape.
 */
public interface IAnimations {

  Integer getTime1();

  Integer getTime2();

  Animations.AnimateTypes getType();

  AnimatedShape getChangedShape();

  Position2D getPosition1();

  Position2D getPosition2();

  RGB getColor1();

  RGB getColor2();

  List<Double> getSizeParams1();

  List<Double> getSizeParams2();

  void setPosition1(Position2D position1);

  void setColor1(RGB color1);

  void setSizeParams1(List<Double> sizeParams1);
}
