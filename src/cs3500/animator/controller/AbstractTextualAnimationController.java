package cs3500.animator.controller;

import cs3500.animator.view.AbstractTextualAnimationView;
import java.io.IOException;
import java.util.Objects;

/**
 * A text-based controller for the easy animator. Encompasses both textual and SVG controllers.
 */
public abstract class AbstractTextualAnimationController implements IAnimatorController {

  protected final AbstractTextualAnimationView view;

  /**
   * <p>Constructs an {@code AbstractTextualAnimationController} to use with a pre-existing textual
   * view (and model).</p>
   *
   * @param view  a textual view for the controller to use to render
   * @param out   where to print the output
   * @param tempo speed of the animation, in ticks per second
   * @throws IllegalArgumentException if tempo is not positive
   */
  public AbstractTextualAnimationController(AbstractTextualAnimationView view, Appendable out,
      double tempo) throws IllegalArgumentException {
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive!");
    }
    Objects.requireNonNull(view);
    Objects.requireNonNull(out);
    this.view = view;
  }

  @Override
  public void start() throws IOException, UnsupportedOperationException {
    this.view.render();
  }
}
