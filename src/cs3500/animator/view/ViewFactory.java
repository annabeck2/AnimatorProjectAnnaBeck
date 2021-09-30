package cs3500.animator.view;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A factory for creating views for the easy animator.
 */
public class ViewFactory {

  private static final Map<String, Supplier<IAnimatorView>> knownCommands = new HashMap<>();

  /**
   * Create a view for the easy animator.
   * @param type type of view
   * @param model model for the animation
   * @param out where to write the output of the view
   * @param tempo speed of the animation, in ticks per second
   * @return a new view
   * @throws IllegalArgumentException if unknown view type
   */
  public static IAnimatorView create(String type, IAnimatorModel<IModelShape> model, Appendable out,
      double tempo) throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(type);
    knownCommands.put("text", () -> new TextualAnimationView(model, out, tempo));
    knownCommands.put("svg", () -> new SVGAnimationView(model, out, tempo));
    knownCommands.put("visual", () -> new VisualAnimationView(
        model.getX(), model.getY(), model.getWidth(), model.getHeight()));
    knownCommands.put("interactive", () -> new InteractiveAnimationView(
        model.getX(), model.getY(), model.getWidth(), model.getHeight()));

    Supplier<IAnimatorView> result = knownCommands.get(type.toLowerCase(Locale.ROOT));
    if (result == null) {
      throw new IllegalArgumentException("Unknown view type!");
    }
    return result.get();
  }

}
