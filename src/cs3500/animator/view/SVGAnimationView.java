package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.view.svg.SVGShape;
import cs3500.animator.view.svg.SVGShapeFactory;
import java.io.IOException;
import java.util.List;

/**
 * <p>An SVG renderer for the easy animator. This view produces a textual output of the animation
 * in SVG format that is supported by most browsers.</p>
 */
public class SVGAnimationView extends AbstractTextualAnimationView {

  /**
   * <p>Constructs an SVG view for the easy animator.</p>
   *
   * @param model the model used in the animation
   * @param out   the output to write to
   * @param tempo the tempo of the animation, in ticks per second
   * @throws IllegalArgumentException if the values for the parameters are invalid
   */
  public SVGAnimationView(IReadOnlyAnimatorModel<IModelShape> model, Appendable out, double tempo)
      throws IllegalArgumentException {
    super(model, out, tempo);
  }

  @Override
  public void render() throws IOException {
    this.out.append("<svg")
        .append(" viewbox=\"")
        .append(String.valueOf(this.model.getX())).append(" ")
        .append(String.valueOf(this.model.getY())).append(" ")
        .append(String.valueOf(this.model.getWidth())).append(" ")
        .append(String.valueOf(this.model.getHeight())).append("\"")
        .append(" xmlns=\"").append("http://www.w3.org/2000/svg").append("\"")
        .append(">").append("\n");
    List<String> ids = this.model.getShapeIds();
    for (String id : ids) {
      this.appendShape(id);
    }
    this.out.append("</svg>");
  }

  /**
   * <p>Outputs an SVG representation of the given shape. Includes opening and closing tags, and
   * {@code <animate>} tags describing all the transformations each attribute of the shape undergoes
   * over the course of the animation.</p>
   *
   * @param shapeId ID of shape to output
   * @throws IOException if output fails
   */
  private void appendShape(String shapeId) throws IOException {
    int firstTick = this.model.findNextKeyFrame(-1, shapeId);
    if (firstTick == -1) {
      // if the shape has no keyframes, don't print anything
      return;
    }
    IModelShape initialState = this.model.getKeyFrames(shapeId).get(firstTick);
    ShapeType shapeType = initialState.getType();
    SVGShape svgShape = SVGShapeFactory.create(shapeType);

    // print the initial state (opening tag) of the shape
    svgShape.appendInitialState(shapeId, initialState, out);

    int currTick = firstTick;
    // make shape visible right before its first animation
    double firstTime = this.ticksToSeconds(firstTick);
    svgShape.appendVisibility(firstTime, true, out);

    // then enumerate all the animations performed on the shape for each keyframe
    while (currTick != -1) {
      int nextTick = this.model.findNextKeyFrame(currTick, shapeId);
      if (nextTick != -1) {
        // if there is a next tick, print shape's animations from this tick to the next
        IModelShape startState = this.model.getKeyFrames(shapeId).get(currTick);
        IModelShape endState = this.model.getKeyFrames(shapeId).get(nextTick);
        double startTime = this.ticksToSeconds(currTick);
        double endTime = this.ticksToSeconds(nextTick);
        svgShape.appendAnimate(startState, endState, startTime, endTime, out);
      } else {
        // make shape invisible right after its last keyframe
        double finalTime = this.ticksToSeconds(currTick);
        svgShape.appendVisibility(finalTime, false, out);
      }
      currTick = nextTick;
    }

    svgShape.appendClosingTag(out);
  }

}
