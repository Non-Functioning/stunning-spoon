package cs3500.animator.model;

public class ChangeShapeColor extends Animations {
  /**
   * Constructor for ChangesShapeColor Animation. Takes in parameters that
   * define a changeShapeColor animation and validates the occurring times.
   * @param changedShape  shape to change
   * @param color1        old color
   * @param color2        new color
   * @param time1         beginning time of change
   * @param time2         end time of change
   */
  public ChangeShapeColor(AnimatedShape changedShape, RGB color1, RGB color2, Integer time1,
                          Integer time2) {
    this.type = AnimateTypes.CHANGECOLOR;
    this.changedShape = changedShape;
    this.position1 = changedShape.getInitialPosition();
    this.sizeParams1 = changedShape.getInitialSize();
    this.color1 = color1;
    this.color2 = color2;
    this.time1 = time1;
    this.time2 = time2;
    if (!validateAnimationTimes()) {
      throw new IllegalArgumentException("Invalid times for an animation");
    }
  }
}
