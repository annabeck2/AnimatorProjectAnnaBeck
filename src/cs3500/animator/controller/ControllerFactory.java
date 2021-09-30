package cs3500.animator.controller;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.InteractiveAnimationView;
import cs3500.animator.view.SVGAnimationView;
import cs3500.animator.view.TextualAnimationView;
import cs3500.animator.view.VisualAnimationView;

/**
 * A factory for creating controllers for the easy animator.
 */
public class ControllerFactory {

  /**
   * <p>Create a controller.</p>
   *
   * @param view  view used
   * @param model model used
   * @param out   where to write output of the view
   * @param tempo speed of the animation, in ticks per second
   * @return a controller for the easy animator
   */
  public static IAnimatorController create(IAnimatorView view, IAnimatorModel<IModelShape> model,
      Appendable out, double tempo) {

    if (view instanceof TextualAnimationView) {
      return new TextualAnimationController((TextualAnimationView) view, out, tempo);
    } else if (view instanceof SVGAnimationView) {
      return new SVGAnimationController((SVGAnimationView) view, out, tempo);
    } else if (view instanceof InteractiveAnimationView) {
      return new InteractiveAnimationController(model, (InteractiveAnimationView) view, tempo);
    } else {
      return new VisualAnimationController(model, (VisualAnimationView) view, tempo);
    }
  }

}
