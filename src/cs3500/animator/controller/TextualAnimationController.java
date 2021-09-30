package cs3500.animator.controller;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.view.TextualAnimationView;

/**
 * <p>A textual/text-based controller for the easy animator. Produces a text-based output depending
 * on the specifics defined by the concrete class.</p>
 */
public class TextualAnimationController extends AbstractTextualAnimationController {

  /**
   * <p>Constructs a {@code TextualAnimationController} to use with a pre-existing textual
   * view (and model).</p>
   *
   * @param view  a textual view for the controller to use to render
   * @param out   where to print the output
   * @param tempo speed of the animation, in ticks per second
   * @throws IllegalArgumentException if tempo is not positive
   */
  public TextualAnimationController(TextualAnimationView view, Appendable out, double tempo)
      throws IllegalArgumentException {
    super(view, out, tempo);
  }

  /**
   * <p>Constructs a {@code TextualAnimationController} for use on a model.</p>
   *
   * @param model model for the controller to operate on
   * @param out   where to print the output
   * @param tempo speed of the animation, in ticks per second
   * @throws IllegalArgumentException if tempo is not positive
   */
  public TextualAnimationController(IAnimatorModel<IModelShape> model, Appendable out, double tempo)
      throws IllegalArgumentException {
    this(new TextualAnimationView(model, out, tempo),
        out,
        tempo);
  }

}
