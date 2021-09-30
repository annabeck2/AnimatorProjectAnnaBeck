package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.util.AnimationStatePrinter;
import java.io.IOException;

/**
 * <p>A textual view for the easy animator. This view produces a textual output of
 * the animation, detailing the state of each shape at each keyframe throughout the animation.</p>
 */
public class TextualAnimationView extends AbstractTextualAnimationView {

  /**
   * <p>Constructs a textual view for the easy animator.</p>
   *
   * @param model the model used in the animation
   * @param out   the output to write to
   * @param tempo the tempo of the animation, in ticks per second
   * @throws IllegalArgumentException if the values for the parameters are invalid
   */
  public TextualAnimationView(IReadOnlyAnimatorModel<IModelShape> model, Appendable out,
      double tempo) throws IllegalArgumentException {
    super(model, out, tempo);
  }

  @Override
  public void render() throws IOException {
    // render the location and size of the canvas
    this.out
        .append(String.format(
            "canvas %d %d %d %d", model.getX(), model.getY(), model.getWidth(), model.getHeight()))
        .append("\n");
    AnimationStatePrinter.print(model, out, tempo);
  }

}
