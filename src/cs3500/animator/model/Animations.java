package cs3500.animator.model;

import java.util.List;

/**
 * Class for containing all animation types and their corresponding info.
 */
public class Animations implements IAnimations {
  protected AnimatedShape changedShape;
  public AnimateTypes type;
  protected Integer time1;
  protected Integer time2;
  protected RGB color1;
  protected RGB color2;
  protected List<Double> sizeParams1;
  protected List<Double> sizeParams2;
  protected Position2D position1;
  protected Position2D position2;

  /**
   * Type for possible animations performed on shapes.
   */
  public enum AnimateTypes {
    MOVE, CHANGECOLOR, CHANGESIZE, APPEAR, DISAPPEAR, STILL
  }

  @Override
  public Integer getTime1() {
    return time1;
  }

  @Override
  public Integer getTime2() {
    return time2;
  }

  @Override
  public AnimateTypes getType() {
    return type;
  }

  @Override
  public AnimatedShape getChangedShape() {
    return changedShape;
  }

  @Override
  public Position2D getPosition1() {
    return position1;
  }

  @Override
  public Position2D getPosition2() {
    return position2;
  }

  @Override
  public RGB getColor1() {
    return color1;
  }

  @Override
  public RGB getColor2() {
    return color2;
  }

  @Override
  public List<Double> getSizeParams1() {
    return sizeParams1;
  }

  @Override
  public List<Double> getSizeParams2() {
    return sizeParams2;
  }

  @Override
  public void setPosition1(Position2D position1) {
    this.position1 = position1;
  }

  @Override
  public void setColor1(RGB color1) {
    this.color1 = color1;
  }

  @Override
  public void setSizeParams1(List<Double> sizeParams1) {
    this.sizeParams1 = sizeParams1;
  }

  protected boolean validateAnimationTimes() {
    return ((changedShape.getAppearTime() <= time1) && (changedShape.getDisappearTime() >= time2));
  }

  @Override
  public String toString() {
    StringBuilder newString = new StringBuilder("Shape " + changedShape.getShapeName());
    switch (type) {
      case MOVE:
        newString.append(" moves from ");
        newString.append(position1.toString());
        newString.append(" to ");
        newString.append(position2.toString());
        newString.append(" from t=");
        newString.append(time1);
        newString.append(" to t=");
        newString.append(time2);
        break;
      case CHANGECOLOR:
        newString.append(" changes color from ");
        newString.append(color1.toString());
        newString.append(" to ");
        newString.append(color2.toString());
        newString.append(" from t=");
        newString.append(time1);
        newString.append(" to t=");
        newString.append(time2);
        break;
      case CHANGESIZE:
        newString.append(" scales from ");
        newString.append(changedShape.sizeParamsToString(sizeParams1));
        newString.append(" to ");
        newString.append(changedShape.sizeParamsToString(sizeParams2));
        newString.append(" from t=");
        newString.append(time1);
        newString.append(" to t=");
        newString.append(time2);
        break;
      case APPEAR:
        newString.append(" appears at t=");
        newString.append(time1);
        break;
      case DISAPPEAR:
        newString.append(" disappears at t=");
        newString.append(time1);
        break;
      case STILL:
        newString.append(" is still at position ");
        newString.append(position1.toString());
        break;

      default:
        throw new IllegalArgumentException("Operation type not found.");
    }
    return newString.toString();
  }
}
