package cs3500.animator.model;

public class ShapeAppears extends Animations {
  /**
   * Constructor for ShapeAppears Animation. Takes in parameters that
   * define a shapeAppears animation.
   * @param shape   affected shape
   * @param time1   time of appearance
   */
  public ShapeAppears(AnimatedShape shape, Integer time1) {
    this.type = AnimateTypes.APPEAR;
    this.changedShape = shape;
    this.position1 = shape.getInitialPosition();
    this.color1 = shape.getInitialColor();
    this.sizeParams1 = shape.getInitialSize();
    this.time1 = time1;
  }
}
