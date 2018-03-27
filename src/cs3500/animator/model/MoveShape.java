package cs3500.animator.model;

public class MoveShape extends Animations {
  /**
   * Constructor for MoveShape Animation. Takes in parameters that
   * define a moveShape animation and validates the occurring times.
   * @param changedShape  shape to change
   * @param position1     old (x, y) position
   * @param position2     new (x, y) position
   * @param time1         beginning time of change
   * @param time2         end time of change
   */
  public MoveShape(AnimatedShape changedShape, Position2D position1, Position2D position2,
                   Integer time1, Integer time2) {
    this.type = AnimateTypes.MOVE;
    this.changedShape = changedShape;
    this.color1 = changedShape.getInitialColor();
    this.sizeParams1 = changedShape.getInitialSize();
    this.position1 = position1;
    this.position2 = position2;
    this.time1 = time1;
    this.time2 = time2;
    if (!validateAnimationTimes()) {
      throw new IllegalArgumentException("Invalid times for an animation");
    }
  }
}
