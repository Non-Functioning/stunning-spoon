package cs3500.animator.model;

/**
 * Represents a shape that does not have any movements at the moment.
 */
public class StillShape extends Animations {
  /**
   * Constructor for a still shape.
   *
   * @param changedShape The shape that is to be presented as a still shape.
   * @param time1        Time for the shape to be still.
   */
  public StillShape(AnimatedShape changedShape, Integer time1) {
    this.type = AnimateTypes.STILL;
    this.changedShape = changedShape;
    this.position1 = changedShape.getInitialPosition();
    this.color1 = changedShape.getInitialColor();
    this.sizeParams1 = changedShape.getInitialSize();
    this.time1 = time1;
  }
}
