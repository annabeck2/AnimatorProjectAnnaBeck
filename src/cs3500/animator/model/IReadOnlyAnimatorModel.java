package cs3500.animator.model;

import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ShapeType;
import java.util.List;
import java.util.Map;

/**
 * <p>The model for an animator program. Maintains the state and enforces the rules
 * of the animation. Immutable: Only allows for observer functionality.</p>
 *
 * @param <S> the type of shape used by the model
 */
public interface IReadOnlyAnimatorModel<S> {

  /**
   * <p>Get a representation of this animation at the given tick. Returns a list of shapes in their
   * correct states for the given tick. The list ordered according to the ordering of the shapes on
   * the canvas: bottommost shape is at index 0, topmost is at the end of the list.</p>
   *
   * @param tick time during animation
   * @return the state of the animation at the specified tick
   */
  List<S> getFrame(int tick) throws IllegalArgumentException;

  /**
   * <p>Get a representation of this animation at the next tick. Returns a list of shapes in their
   * correct states for the given tick. The list ordered according to the ordering of the shapes on
   * the canvas: bottommost shape is at index 0, topmost is at the end of the list.</p>
   *
   * @return the state of the animation at the next tick
   * @throws IllegalStateException if there is no next frame
   */
  List<S> getNextFrame() throws IllegalStateException;

  /**
   * <p>Get a representation of this animation at the next *key* tick. The next key tick is the
   * tick at which the next keyframe occurs for any shape. Returns a list of shapes in their correct
   * states for the key tick. The list ordered according to the ordering of the shapes on the
   * canvas: bottommost shape is at index 0, topmost is at the end of the list.</p>
   *
   * @return the state of the animation at the next key tick
   * @throws IllegalStateException if there is no next key frame (note that the last frame is always
   *                               a key frame)
   */
  List<S> getNextKeyFrame() throws IllegalArgumentException;

  /**
   * Check whether there is another frame to render.
   *
   * @return whether there is another frame to render
   */
  boolean hasNextFrame();

  /**
   * <p>Resets the internal tick counter of the model to 0, effectively rewinding the animation
   * playback to the beginning.</p>
   */
  void reset();

  /**
   * <p>Get the first tick of the animation at which any shape is present, or -1 if there are no
   * keyframes. Note that although an animation is assumed to start at tick 0, it is possible for no
   * shapes to be present until some later tick.</p>
   *
   * @return the first tick of the animation at which any shape is present
   */
  int getStartTick();

  /**
   * <p>Get the last tick of the animation at which any shape is present, or -1 if there are no
   * keyframes.</p>
   *
   * @return the last tick of the animation at which any shape is present
   */
  int getEndTick();

  /**
   * <p>Returns all shapes in the model, ordered by their layering configuration on
   * the canvas. Bottommost shapes are at the beginning of the list, the top shape is the last
   * one.</p>
   *
   * @return a list of all shape IDs in the model
   */
  List<String> getShapeIds();

  /**
   * <p>Return the type of the shape in the model with the given ID.</p>
   *
   * @param shapeId ID of a shape in the model
   * @return the ShapeType of the shape with the given ID
   * @throws IllegalArgumentException if shape with given ID does not exist
   * @throws NullPointerException     if the shapeId is null
   */
  ShapeType getShapeType(String shapeId) throws IllegalArgumentException, NullPointerException;

  /**
   * Return a mapping of all shape IDs in the model to their shape's type.
   *
   * @return the mapping of all shape IDs to their ShapeType
   */
  Map<String, ShapeType> getShapes(); //TODO: not needed?

  /**
   * <p>Return a mapping from ticks to keyframes at that tick for the given shape. If a shape has
   * no keyframes in this animation, returns an empty map.</p>
   *
   * @param shapeId ID of shape in model
   * @return a mapping from ticks to keyframes at that tick for the given shape
   * @throws IllegalArgumentException if shape with given ID does not exist
   * @throws NullPointerException     if the shapeId is null
   */
  Map<Integer, IModelShape> getKeyFrames(String shapeId)
      throws IllegalArgumentException, NullPointerException;

  /**
   * <p>Get the tick of the keyframe that precedes the given tick for the desired shape. Returns
   * -1 if no such keyframe exists.</p>
   *
   * @param tick    the tick we want to find the previous keyframe of
   * @param shapeId the shapeId of the shape we want to find the keyframe of
   * @return the shape at the given tick
   * @throws IllegalArgumentException if the shape id does not exist
   */
  int findPrevKeyFrame(int tick, String shapeId) throws IllegalArgumentException;

  /**
   * <p>Get the tick of the keyframe that succeeds the given tick for the desired shape. Returns
   * -1 if no such keyframe exists.</p>
   *
   * @param tick    the tick we want to find the next keyframe of
   * @param shapeId the shapeId of the shape we want to find the keyframe of
   * @return the shape at the given tick
   * @throws IllegalArgumentException if the shape id does not exist
   */
  int findNextKeyFrame(int tick, String shapeId) throws IllegalArgumentException;

  /**
   * <p>Return the leftmost x value of the bounding box for this animation.</p>
   *
   * @return the leftmost x value of the bounding box for this animation
   */
  int getX();

  /**
   * <p>Return the topmost y value of the bounding box for this animation.</p>
   *
   * @return the topmost y value of the bounding box for this animation
   */
  int getY();

  /**
   * <p>Return the width of the bounding box for this animation.</p>
   *
   * @return the width of the bounding box for this animation
   */
  int getWidth();

  /**
   * <p>Return the height of the bounding box for this animation.</p>
   *
   * @return the height of the bounding box for this animation
   */
  int getHeight();

  /**
   * Get a string representation of all the keyframes in this animation.
   *
   * @return a string representation of the animation
   */
  @Override
  String toString();
}
