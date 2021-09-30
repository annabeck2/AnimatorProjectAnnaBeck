package cs3500.animator.util;

import cs3500.animator.model.IReadOnlyAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ShapeType;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for creating a string representation of the state of an animator model.
 */
public class AnimationStatePrinter {

  /**
   * <p>Append the state of the given model to {@code out}.</p>
   *
   * @param model the model to print
   * @param out   where to print the output to
   * @param tempo speed of the animation, in ticks
   * @throws IllegalArgumentException if tempo is not positive
   * @throws IOException              if failed to write to the output
   */
  public static void print(IReadOnlyAnimatorModel<IModelShape> model, Appendable out, double tempo)
      throws IOException, IllegalArgumentException {
    Objects.requireNonNull(model);
    Objects.requireNonNull(out);
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive!");
    }
    List<String> ids = model.getShapeIds();
    for (String id : ids) {
      appendShape(model, id, model.getShapeType(id), out, tempo);
      out.append("\n");
    }
  }

  /**
   * <p>Append a textual representation of all the keyframes of the shape with the given ID to the
   * output.</p>
   *
   * @param shapeId the id of the given shape
   * @param type    the type of the given shape
   * @throws IllegalArgumentException if tempo is not positive
   * @throws IOException              if failed to write to the output
   */
  private static void appendShape(IReadOnlyAnimatorModel<IModelShape> model, String shapeId,
      ShapeType type, Appendable out, double tempo) throws IOException, IllegalArgumentException {
    Objects.requireNonNull(model);
    Objects.requireNonNull(out);
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive!");
    }
    // append shape's ID and type
    out.append("shape")
        .append(" ")
        .append(shapeId)
        .append(" ")
        .append(type.toString().toLowerCase(Locale.ROOT))
        .append("\n");

    Map<Integer, IModelShape> frames = model.getKeyFrames(shapeId);
    List<Integer> ticks = new ArrayList<>(frames.keySet());
    Collections.sort(ticks);

    // append shape's list of motions
    for (int index = 0; index < ticks.size(); index++) {
      int currTick = ticks.get(index);
      int nextTick = currTick;
      if (index < ticks.size() - 1) {
        // if this is not the last tick, find the next tick as well
        nextTick = ticks.get(index + 1);
      }

      if (index == 0) {
        // for the shape's first tick, always append a single frame motion
        appendMotion(out, shapeId, currTick, currTick,
            frames.get(currTick), frames.get(currTick),
            tempo);
      }

      if (nextTick > currTick) {
        // if this is the NOT last motion
        appendMotion(out, shapeId, currTick, nextTick,
            frames.get(currTick), frames.get(nextTick),
            tempo);
      }
    }
  }

  /**
   * <p>Append a textual representation of a specific motion of a shape.</p>
   *
   * @param out     where to append the output
   * @param shapeId ID of the shape
   * @param tick1   start tick of motion
   * @param tick2   end tick of motion
   * @param state1  state of shape at start of motion
   * @param state2  state of shape at end of motion
   * @param tempo   speed of animation, in ticks per second
   * @throws IllegalArgumentException if tempo is not positive
   * @throws IOException              if appending to out fails
   */
  private static void appendMotion(Appendable out, String shapeId, int tick1, int tick2,
      IModelShape state1, IModelShape state2, double tempo)
      throws IOException, IllegalArgumentException {
    double time1 = ticksToSeconds(tick1, tempo);
    double time2 = ticksToSeconds(tick2, tempo);
    DecimalFormat format = new DecimalFormat("0.#");
    String time1Str = format.format(time1);
    String time2Str = format.format(time2);

    out.append("motion").append(" ")
        .append(shapeId).append(" ")
        .append(time1Str)
        .append(" ")
        .append(state1.printState())
        .append(" ")
        .append(time2Str)
        .append(" ")
        .append(state2.printState())
        .append("\n");
  }

  /**
   * <p>Convert the given tick value to a value in seconds, according to the given tempo.</p>
   *
   * @param tick the tick that we are converting to seconds
   * @return the time during the animation, in seconds
   * @throws IllegalArgumentException if tempo is not positive
   */
  private static double ticksToSeconds(int tick, double tempo) throws IllegalArgumentException {
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive!");
    }
    return (double) tick / tempo;
  }

}
