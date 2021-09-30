package cs3500.animator.view;

/**
 * <p>A listener to receive and respond to events that occur in an interactive animation view.</p>
 */
public interface IViewEventListener {

  /**
   * <p>Handle the event that the animation playback was paused or resumed.</p>
   *
   * @param play whether the animation is set to be playing
   */
  void playbackToggled(boolean play);

  /**
   * <p> Handle the event that the animation playback was restarted.</p>
   */
  void playbackRestarted();

  /**
   * <p> Handle the event that the animation playback speed was increased.</p>
   */
  void speedIncreased();

  /**
   * <p>Handle the event that the animation playback speed was decreased.</p>
   */
  void speedDecreased();

  /**
   * <p>Handle the event that the animation playback looping was enabled or disabled.</p>
   *
   * @param enabled whether the looping is enabled or disabled
   */
  void loopingToggled(boolean enabled);

  /**
   * <p>Handle the event that the discrete playback was enabled or disabled.</p>
   *
   * @param enabled whether discrete playback mode is enabled or disabled
   */
  void discreteToggled(boolean enabled);

  /**
   * <p>Handle the event that the outline mode was enabled or disabled.</p>
   *
   * @param enabled whether outline mode is enabled or disabled
   */
  void outlineToggled(boolean enabled);
}
