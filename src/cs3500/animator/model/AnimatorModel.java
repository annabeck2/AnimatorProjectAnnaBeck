package cs3500.animator.model;

import cs3500.animator.model.shape.ModelShape;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationStatePrinter;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import cs3500.animator.model.shape.IModelShape;

/**
 * The model for the easy animator. Uses shapes of type {@link IModelShape}.
 */
public class AnimatorModel implements IAnimatorModel<IModelShape> {

  // mapping from all shape IDs to all of their keyframes for any given tick
  private final Map<String, Map<Integer, IModelShape>> keyframes;
  // enables easy finding of shapes from IDs
  private final Map<String, ShapeType> shapeTypeMap;

  // info about the ordering of shapes on top of each other (index 0 is bottommost shape)
  private final List<String> layers;
  private int currentTick; // for getNextFrame()
  private final int startTick; // tick of first frame in animation (always assumed to start at 0)
  private int endTick; // tick of last frame in animation

  // The following values define the bounding box of the animation
  private int x;
  private int y;
  private int width;
  private int height;

  /**
   * <p>Constructs a default animator model with default values for the fields: The current tick is
   * 0, the end ticks is -1. The (x,y) position is (0,0), the width is 800 and the height is
   * 600.</p>
   */
  public AnimatorModel() {
    this.x = 0;
    this.y = 0;
    this.width = 800;
    this.height = 600;
    this.keyframes = new HashMap<>();
    this.layers = new ArrayList<>();
    this.shapeTypeMap = new HashMap<>();
    this.currentTick = 0;
    this.startTick = 0; // animation always starts at 0
    this.endTick = -1;
  }

  @Override
  public List<IModelShape> getFrame(int tick) throws IllegalArgumentException {
    if (tick < 0 || tick > this.getEndTick()) {
      // if the given tick is past the end of the animation
      throw new IllegalArgumentException("Tick is out of range!");
    }

    List<IModelShape> frame = new ArrayList<>();
    // iterate over this.layers to preserve ordering
    for (String shapeId : this.layers) {
      IModelShape interpolatedFrame = this.interpolateFrame(tick, shapeId);
      if (interpolatedFrame != null) {
        frame.add(interpolatedFrame);
      }
    }
    return frame;
  }

  /**
   * <p>Return the state for the given shape at the given tick during the animation. Interpolates
   * the state of a shape between keyframes, or returns a keyframe if the tick falls on eactly a
   * keyframe.</p>
   *
   * @param tick    tick during the animation
   * @param shapeId ID of desired shape
   * @return the state for the given shape at the given tick during the animation
   * @throws IllegalArgumentException if a shape with the given ID does not exist
   */
  private IModelShape interpolateFrame(int tick, String shapeId) throws IllegalArgumentException {
    Objects.requireNonNull(shapeId);
    if (!this.idExists(shapeId)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }

    // check if there is a keyframe at the desired tick
    IModelShape frame = this.keyframes.get(shapeId).get(tick);
    if (frame != null) {
      // if this shape has a keyframe at this particular tick :)
      return frame;
    }

    // otherwise, must interpolate :(
    int prevTick = this.findPrevKeyFrame(tick, shapeId);
    int nextTick = this.findNextKeyFrame(tick, shapeId);

    if (prevTick != -1 && nextTick != -1) {
      IModelShape prevState = this.keyframes.get(shapeId).get(prevTick);
      IModelShape nextState = this.keyframes.get(shapeId).get(nextTick);

      List<Double> prevAttributes = prevState.getAttributes();
      List<Double> nextAttributes = nextState.getAttributes();
      List<Double> currAttributes = new ArrayList<>();

      for (int i = 0; i < prevAttributes.size(); i++) {
        double current = this.linearInterpolateAttribute(
            prevTick, nextTick, tick, prevAttributes.get(i), nextAttributes.get(i));
        currAttributes.add(current);
      }

      double x = currAttributes.remove(0);
      double y = currAttributes.remove(0);
      double width = currAttributes.remove(0);
      double height = currAttributes.remove(0);
      double red = currAttributes.remove(0);
      double green = currAttributes.remove(0);
      double blue = currAttributes.remove(0);
      Color color = new Color((int) red, (int) green, (int) blue);

      ShapeType type = this.shapeTypeMap.get(shapeId);
      frame = new ModelShape(type, x, y, width, height, color);
    }

    return frame;
  }

  /**
   * <p>Interpolates at a given tick for a single value ("tweening").</p>
   *
   * @param startTick    the start tick of the transition
   * @param endTick      the end tick of the transition
   * @param currTick     the tick where we want the frame at
   * @param initialState initial state of the attribute that is being interpolated
   * @param finalState   final state of the attribute that is being interpolated
   * @return the value of the interpolated attribute
   * @throws IllegalArgumentException if the tick is not in the range
   */
  private double linearInterpolateAttribute(double startTick, double endTick, double currTick,
      double initialState, double finalState) throws IllegalArgumentException {
    if (endTick < startTick) {
      throw new IllegalArgumentException("End tick cannot be before start tick!");
    }
    if (endTick <= currTick || startTick >= currTick) {
      throw new IllegalArgumentException("Cannot interpolate this tick, not in range!");
    }
    return
        initialState * ((endTick - currTick) / (endTick - startTick))
            + finalState * ((currTick - startTick) / (endTick - startTick));
  }

  @Override
  public int findPrevKeyFrame(int tick, String shapeId) throws IllegalArgumentException {
    Objects.requireNonNull(shapeId);
    if (!this.idExists(shapeId)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }

    Map<Integer, IModelShape> frames = this.keyframes.get(shapeId);
    int prevTick = -1;
    boolean hasPrevTick = false;
    for (int i : frames.keySet()) {
      // iterate over all tick values of keyframes
      if (i < tick && i > prevTick) {
        prevTick = i;
        hasPrevTick = true;
      }
    }

    if (!hasPrevTick) {
      return -1;
    }
    return prevTick;
  }

  @Override
  public int findNextKeyFrame(int tick, String shapeId) throws IllegalArgumentException {
    //TODO: skips first key frame
    Objects.requireNonNull(shapeId);
    if (!this.idExists(shapeId)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }

    Map<Integer, IModelShape> frames = this.keyframes.get(shapeId);
    int nextTick = Integer.MAX_VALUE;
    boolean hasNextFrame = false;
    for (int i : frames.keySet()) {
      // iterate over all tick values of keyframes
      if (i > tick && i < nextTick) {
        nextTick = i;
        hasNextFrame = true;
      }
    }

    if (!hasNextFrame) {
      return -1;
    }
    return nextTick;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public List<IModelShape> getNextFrame() throws IllegalStateException {
    if (!hasNextFrame()) {
      throw new IllegalStateException("Animation does not have a next frame!");
    }
    return this.getFrame(this.currentTick++); // get frame, then increment tick
  }

  @Override
  public List<IModelShape> getNextKeyFrame() throws IllegalArgumentException {
    List<IModelShape> result = this.getFrame(this.currentTick);
    int nextKeyTick = this.endTick;

    for (String id : this.keyframes.keySet()) {
      int temp = this.findNextKeyFrame(this.currentTick, id);
      if (temp < nextKeyTick) {
        // next key tick is the next tick that is later than the current tick
        nextKeyTick = temp;
      }
    }

    this.currentTick = nextKeyTick;
    return result;
  }

  @Override
  public boolean hasNextFrame() {
    return this.currentTick <= this.endTick && this.currentTick >= 0;
  }

  @Override
  public void reset() {
    this.currentTick = 0;
  }

  /**
   * <p>Calculate the last tick at which a shape is present in the animation.</p>
   *
   * @return the last tick at which a shape is present in the animation
   */
  private int findEndTick() {
    boolean hasKeyframe = false;
    int endTick = 0;

    for (Map<Integer, IModelShape> frames : this.keyframes.values()) {
      // for each shape
      for (int tick : frames.keySet()) {
        // for each keyframe of the shape
        hasKeyframe = true;
        endTick = Math.max(endTick, tick);
      }
    }
    if (!hasKeyframe) {
      return -1;
    }
    return endTick;
  }

  @Override
  public int getStartTick() {
    return this.startTick;
  }

  @Override
  public int getEndTick() {
    return this.endTick;
  }

  @Override
  public List<String> getShapeIds() {
    return new ArrayList<>(this.layers); // preserves ordering of layers
  }

  @Override
  public ShapeType getShapeType(String shapeId)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(shapeId);
    if (!this.idExists(shapeId)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }
    return this.shapeTypeMap.get(shapeId);
  }

  @Override
  public Map<String, ShapeType> getShapes() {
    return new HashMap<>(this.shapeTypeMap);
  }

  @Override
  public Map<Integer, IModelShape> getKeyFrames(String shapeId)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(shapeId);
    if (!this.idExists(shapeId)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }
    return this.keyframes.get(shapeId); // should never be null
  }

  @Override
  public void bringToFront(String id) throws IllegalArgumentException {
    if (!this.idExists(id)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }
    this.layers.remove(id);
    this.layers.add(id);
  }

  @Override
  public void sendToBack(String id) throws IllegalArgumentException {
    if (!this.idExists(id)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }
    this.layers.remove(id);
    this.layers.add(0, id);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive!");
    }
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("X and Y must be non-negative!");
    }
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Checks whether a shape with the given ID exists in the model.
   *
   * @param id ID of a shape in the model
   * @return whether a shape with the given ID already exists
   */
  private boolean idExists(String id) {
    return this.shapeTypeMap.containsKey(id);
  }

  @Override
  public void removeShape(String id) throws IllegalArgumentException {
    if (!this.idExists(id)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }
    this.keyframes.remove(id);
    this.layers.remove(id);
    this.shapeTypeMap.remove(id);
  }

  @Override
  public void addShape(String id, ShapeType type)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(id);
    Objects.requireNonNull(type);
    if (this.idExists(id)) {
      throw new IllegalArgumentException("Shape with given ID already exists!");
    }
    this.shapeTypeMap.put(id, type);
    this.layers.add(id);
    this.keyframes.put(id, new HashMap<>()); // important to init the hashmap here
  }

  @Override
  public void addKeyFrame(int tick, String shapeId, double x, double y, double width, double height,
      Color color) throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(shapeId);
    if (!this.idExists(shapeId)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("Tick cannot be negative!");
    }

    ShapeType type = this.shapeTypeMap.get(shapeId); // find type of the shape with given ID
    IModelShape keyframe = new ModelShape(type, x, y, width, height, color);
    if (this.isConflict(tick, shapeId, keyframe)) {
      throw new IllegalArgumentException("New keyframe conflicts with existing keyframe!");
    }
    // either add a new keyframe, or leave the remaining (identical) one unchanged
    this.keyframes.get(shapeId).put(tick, keyframe);
    // recalculate the ending tick for the animation
    this.endTick = Math.max(tick, this.endTick);
  }

  /**
   * <p>Ensures that the given keyframe does not conflict with another keyframe for the given
   * shape. Two keyframes conflict if they occur at the same tick AND they operate on the same
   * shape.</p>
   *
   * @param tick     tick at which keyframe occurs
   * @param shapeId  ID of shape in keyframe
   * @param keyframe keyframe to check
   * @return whether the given keyframe conflicts with an existing one for the given shape
   * @throws IllegalArgumentException if the keyframe conflicts with another
   */
  private boolean isConflict(int tick, String shapeId, IModelShape keyframe)
      throws IllegalArgumentException {
    Objects.requireNonNull(shapeId);
    Objects.requireNonNull(keyframe);

    if (!this.idExists(shapeId)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }

    IModelShape conflictingKeyframe = this.keyframes.get(shapeId).get(tick);
    //conflicts if there is already a keyframe at that tick AND it is different than the new one
    return conflictingKeyframe != null && !conflictingKeyframe.equals(keyframe);
  }

  @Override
  public void removeKeyFrame(int tick, String shapeId) throws IllegalArgumentException {
    Objects.requireNonNull(shapeId);
    if (!this.idExists(shapeId)) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }
    Map<Integer, IModelShape> frames = this.keyframes.get(shapeId);
    if (!frames.containsKey(tick)) {
      throw new IllegalArgumentException("No keyframe exists for the specified tick!");
    }
    frames.remove(tick);
    // recalculate the ending tick for the animation
    this.endTick = this.findEndTick();
  }

  @Override
  public String toString() {
    Appendable sb = new StringBuilder();
    try {
      AnimationStatePrinter.print(this, sb, 1);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append toString output!");
    }
    return sb.toString();
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * A builder class for creating an animator model.
   */
  public static final class Builder implements AnimationBuilder<IAnimatorModel<IModelShape>> {

    private final IAnimatorModel<IModelShape> model;

    /**
     * Constructs a {@code Builder}.
     */
    public Builder() {
      this.model = new AnimatorModel();
    }

    @Override
    public IAnimatorModel<IModelShape> build() {
      return this.model;
    }

    @Override
    public AnimationBuilder<IAnimatorModel<IModelShape>> setBounds(int x, int y, int width,
        int height) {
      this.model.setBounds(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel<IModelShape>> declareShape(String name, String type) {
      this.model.addShape(name, ShapeType.valueOf(type.toUpperCase(Locale.ROOT)));
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel<IModelShape>> addMotion(String name, int t1, int x1,
        int y1, int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2,
        int r2, int g2, int b2) {
      this.model.addKeyFrame(t1, name, x1, y1, w1, h1, new Color(r1, g1, b1));
      this.model.addKeyFrame(t2, name, x2, y2, w2, h2, new Color(r2, g2, b2));
      return this;
    }
  }

}
