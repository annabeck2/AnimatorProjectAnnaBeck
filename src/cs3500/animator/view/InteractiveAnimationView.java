package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * <p> A view for displaying animations graphically that includes playback controls.</p>
 */
public class InteractiveAnimationView extends VisualAnimationView implements
    IAnimatorInteractiveView, ActionListener {

  private final List<IViewEventListener> subscribers; // listeners for events occurring in the view
  protected JPanel buttonPanel;
  private JButton playPauseButton;
  private JButton loopButton;
  private JButton toggleDiscrete;
  private JButton toggleOutline;
  private boolean paused;
  private boolean loopEnabled;
  private boolean discreteEnabled;
  private boolean outlineMode;

  /**
   * <p>Constructs an interactive view for playing back animations.</p>
   *
   * @param x      x-position on screen to place the frame
   * @param y      y-position on screen to place the frame
   * @param width  width of the canvas
   * @param height height of the canvas
   * @throws NullPointerException     if the model is {@code null}.
   * @throws IllegalArgumentException if width or height are <= zero
   */
  public InteractiveAnimationView(int x, int y, int width, int height)
      throws NullPointerException, IllegalArgumentException {
    super(x, y, width, height);
    this.subscribers = new ArrayList<>();
    this.subscribers.add(this.drawingPanel);
    this.paused = true;
    this.loopEnabled = false;
    this.discreteEnabled = false;
    this.outlineMode = false;
    this.buttonPanel = new JPanel();
  }

  @Override
  protected void initGui() {
    playPauseButton = new JButton("Play");
    playPauseButton.setActionCommand("playPause");
    playPauseButton.addActionListener(this);

    JButton restartButton = new JButton("Restart");
    restartButton.setActionCommand("restart");
    restartButton.addActionListener(this);

    loopButton = new JButton("Enable looping");
    loopButton.setActionCommand("loop");
    loopButton.addActionListener(this);

    JButton increaseSpeed = new JButton("Speed++");
    increaseSpeed.setActionCommand("incSpeed");
    increaseSpeed.addActionListener(this);

    JButton decreaseSpeed = new JButton("Speed--");
    decreaseSpeed.setActionCommand("decSpeed");
    decreaseSpeed.addActionListener(this);

    toggleDiscrete = new JButton("Enable discrete mode");
    toggleDiscrete.setActionCommand("mode");
    toggleDiscrete.addActionListener(this);

    toggleOutline = new JButton("Outline mode");
    toggleOutline.setActionCommand("outline");
    toggleOutline.addActionListener(this);

    this.setLayout(new BorderLayout());
    this.buttonPanel = new JPanel();
    this.buttonPanel.setLayout(new FlowLayout());
    this.buttonPanel.setBorder(BorderFactory.createTitledBorder("Playback options"));

    this.buttonPanel.add(playPauseButton);
    this.buttonPanel.add(restartButton);
    this.buttonPanel.add(loopButton);
    this.buttonPanel.add(increaseSpeed);
    this.buttonPanel.add(decreaseSpeed);
    this.buttonPanel.add(toggleDiscrete);
    this.buttonPanel.add(toggleOutline);

    add(new JScrollPane(this.drawingPanel), BorderLayout.CENTER);
    add(this.buttonPanel, BorderLayout.SOUTH);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    switch (actionCommand) {
      case "playPause":
        broadcastPlaybackToggled();
        break;
      case "restart":
        broadcastPlaybackRestarted();
        break;
      case "loop":
        broadcastLoopingToggled();
        break;
      case "incSpeed":
        broadcastSpeedIncreased();
        break;
      case "decSpeed":
        broadcastSpeedDecreased();
        break;
      case "mode":
        broadcastDiscreteToggled();
        break;
      case "outline":
        broadcastOutlineToggled();
        break;
      default:
        throw new IllegalArgumentException("Unknown action command!");
    }

  }

  /**
   * <p>Notify all subscribers that the playback has been toggled.</p>
   */
  private void broadcastPlaybackToggled() {
    this.paused = !this.paused;
    for (IViewEventListener l : this.subscribers) {
      l.playbackToggled(!this.paused);
    }
    // change label on button to match new state
    if (this.paused) {
      this.playPauseButton.setText("Play");
    } else {
      this.playPauseButton.setText("Pause");
    }
  }

  /**
   * <p>Notify all subscribers that the playback has been restarted.</p>
   */
  private void broadcastPlaybackRestarted() {
    this.broadcastPlaybackToggled();
    for (IViewEventListener l : this.subscribers) {
      l.playbackRestarted();
    }
    this.refresh();
    this.broadcastPlaybackToggled();
  }

  /**
   * <p>Notify all subscribers that the playback looping has been toggled.</p>
   */
  private void broadcastLoopingToggled() {
    this.loopEnabled = !this.loopEnabled;
    for (IViewEventListener l : this.subscribers) {
      l.loopingToggled(this.loopEnabled);
    }
    // change label on button to match new state
    if (this.loopEnabled) {
      this.loopButton.setText("Disable looping");
    } else {
      this.loopButton.setText("Enable looping");
    }
  }

  /**
   * <p>Notify all subscribers that the playback speed has been increased.</p>
   */
  private void broadcastSpeedIncreased() {
    for (IViewEventListener l : this.subscribers) {
      l.speedIncreased();
    }
  }

  /**
   * <p>Notify all subscribers that the playback speed has been decreased.</p>
   */
  private void broadcastSpeedDecreased() {
    for (IViewEventListener l : this.subscribers) {
      l.speedDecreased();
    }
  }

  private void broadcastDiscreteToggled() {
    this.discreteEnabled = !this.discreteEnabled;
    for (IViewEventListener l : this.subscribers) {
      l.discreteToggled(this.discreteEnabled);
    }
    // change label on button to match new state
    if (this.discreteEnabled) {
      this.toggleDiscrete.setText("Enable continuous mode");
    } else {
      this.toggleDiscrete.setText("Enable discrete mode");
    }
  }

  private void broadcastOutlineToggled() {
    this.outlineMode = !this.outlineMode;
    for (IViewEventListener l : this.subscribers) {
      l.outlineToggled(this.outlineMode);
    }
    // change label on button to match new state
    if (this.outlineMode) {
      this.toggleOutline.setText("Fill mode");
    } else {
      this.toggleOutline.setText("Outline mode");
    }
  }

  @Override
  public void addViewEventListener(IViewEventListener listener) {
    this.subscribers.add(Objects.requireNonNull(listener));
  }

}
