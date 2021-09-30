package cs3500.animator.view;

/**
 * An interface for an interactive, playable visual view for the animation.
 */
public interface IAnimatorInteractiveView extends IAnimatorView {

  /**
   * Add a subscriber to this view, to receive updates about actions performed.
   * @param listener the subscriber, which handles the events performed
   */
  void addViewEventListener(IViewEventListener listener);

}
