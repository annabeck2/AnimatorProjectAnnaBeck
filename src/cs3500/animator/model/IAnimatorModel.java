package cs3500.animator.model;

import cs3500.animator.model.shape.ShapeType;
import java.awt.Color;

/**
 * <p>The model for an animator program. Maintains the state and enforces the rules
 * of the animation. Mutable: Allows for adding, removing, and manipulating shapes and the
 * transitions they undergo.</p>
 *
 * @param <S> the type of shape used by the model
 */
public interface IAnimatorModel<S> extends IReadOnlyAnimatorModel<S> {

  /**
   * <p>Adds a shape to the model. Shapes have no key frames initially.</p>
   *
   * @param id   unique ID of the shape
   * @param type type of the shape
   */
  void addShape(String id, ShapeType type) throws IllegalArgumentException, NullPointerException;

  /**
   * <p>Adds a keyframe to the model. Note that if an identical keyframe exists for the same shape
   * at the same tick, this does nothing.</p>
   *
   * @param tick   the tick at which the keyframe occurs
   * @param id     ID of the shape that has the given keyframe
   * @param x      x position of the shape in the keyframe
   * @param y      y position of the shape in the keyframe
   * @param width  width of the shape in the keyframe
   * @param height height of the shape in the keyframe
   * @param color  color of the shape in the keyframe
   * @throws IllegalArgumentException <p>if no shape with the given ID exists, if the tick is
   *                                  negative, or if the new keyframe conflicts with an existing
   *                                  one</p>
   * @throws NullPointerException     if any params are {@code null}
   */
  void addKeyFrame(int tick, String id, double x, double y, double width, double height,
      Color color) throws IllegalArgumentException, NullPointerException;

  /**
   * Remove a shape from the cs3500.animator.model. Removes all associated states.
   *
   * @param id the shape to remove
   * @throws IllegalArgumentException if no shape with the given ID exists
   */
  void removeShape(String id) throws IllegalArgumentException;

  /**
   * <p>Remove the given shape's keyframe at the given time.</p>
   *
   * @param tick time of the keyframe to remove
   * @param id   ID of shape
   * @throws IllegalArgumentException <p>if a shape with the given ID does not exist, or if a
   *                                  keyframe does not exist at the specified tick</p>
   */
  void removeKeyFrame(int tick, String id) throws IllegalArgumentException;

  /**
   * <p>Brings the shape with the given ID to the "front" of the canvas (orders it on top of all
   * other shapes).</p>
   *
   * @param id the id of the shape
   * @throws IllegalArgumentException if a shape with the given ID does not exist
   */
  void bringToFront(String id) throws IllegalArgumentException;

  /**
   * <p>Sends the shape with the given ID to the "back" of the canvas (orders it below all other
   * shapes).</p>
   *
   * @param id the id of the shape
   * @throws IllegalArgumentException if a shape with the given ID does not exist
   */
  void sendToBack(String id) throws IllegalArgumentException;

  /**
   * Specify the bounding box to be used for the animation.
   * @param x The leftmost x value
   * @param y The topmost y value
   * @param width The width of the bounding box
   * @param height The height of the bounding box
   */
  void setBounds(int x, int y, int width, int height) throws IllegalArgumentException;

}
