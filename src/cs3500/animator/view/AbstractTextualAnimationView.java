package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import java.awt.Color;
import java.util.Objects;

/**
 * A text-based view for the easy animator. Encompasses both textual and SVG views.
 */
public abstract class AbstractTextualAnimationView implements IAnimatorView {

  protected final IReadOnlyAnimatorModel<IModelShape> model;
  protected final Appendable out;
  protected final double tempo; // in ticks per second

  /**
   * <p>Constructs a text-based view for the easy animator.</p>
   *
   * @param model the model used in the animation
   * @param out   the output to write to
   * @param tempo the tempo of the animation, in ticks per second
   * @throws IllegalArgumentException if the values for the parameters are invalid
   */
  public AbstractTextualAnimationView(IReadOnlyAnimatorModel<IModelShape> model, Appendable out,
      double tempo) throws IllegalArgumentException {
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive!");
    }
    this.model = Objects.requireNonNull(model);
    this.out = Objects.requireNonNull(out);
    this.tempo = tempo;
  }

  @Override
  public void drawRectangle(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException {
    throw new UnsupportedOperationException("Cannot draw in this view!");
  }

  @Override
  public void drawEllipse(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException {
    throw new UnsupportedOperationException("Cannot draw in this view!");
  }

  @Override
  public void drawPlus(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException {
    throw new UnsupportedOperationException("Cannot draw in this view!");
  }


  @Override
  public void refresh() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Cannot refresh this view!");
  }

  @Override
  public void setTrails(boolean enabled) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Cannot draw in this view!");
  }

  /**
   * <p>Convert the given tick value to a value in seconds, according to this view's specified
   * tempo.</p>
   *
   * @param tick the tick that we are converting to seconds
   * @return the time during the animation, in seconds
   */
  protected double ticksToSeconds(int tick) {
    return (double) tick / this.tempo;
  }

}
