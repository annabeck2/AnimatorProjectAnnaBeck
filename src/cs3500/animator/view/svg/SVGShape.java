package cs3500.animator.view.svg;

import cs3500.animator.model.shape.IModelShape;
import java.io.IOException;

/**
 * <p>Interface to represent an SVG shape.</p>
 */
public interface SVGShape {

  /**
   * <p>Appends the shape's initial state to the Appendable.</p>
   * @param shapeId the id of the shape
   * @param state the state of the shape
   * @param out where to write the output to
   * @throws IOException if cannot write to appendable
   */
  void appendInitialState(String shapeId, IModelShape state, Appendable out) throws IOException;

  /**
   * <p>Appends the animation of the state to the Appendable.</p>
   * @param start the starting state of the shape
   * @param end the ending state of the shape
   * @param startTime the start time of the motion
   * @param endTime the end time of the motion
   * @param out where to write the output to
   * @throws IOException if cannot write to appendable
   */
  void appendAnimate(IModelShape start, IModelShape end, double startTime, double endTime,
      Appendable out) throws IOException;

  /**
   * <p>Append a set visibility to the Appendable.</p>
   * @param startTime the start time
   * @param visible if visible or not visible
   * @param out where to write the output to
   * @throws IOException if cannot write to appendable
   */
  void appendVisibility(double startTime, boolean visible, Appendable out) throws IOException;

  /**
   * <p>Appends the closing tag to Appendable.</p>
   * @param out where to write the output to
   * @throws IOException if cannot write to appendable
   */
  void appendClosingTag(Appendable out) throws IOException;

}
