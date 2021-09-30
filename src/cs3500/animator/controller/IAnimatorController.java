package cs3500.animator.controller;

import java.io.IOException;

/**
 * A controller for an animator program.
 */
public interface IAnimatorController {

  /**
   * Starts the animator program.
   * @throws IOException if rendering fails
   * @throws UnsupportedOperationException if an operation is called on an incompatible view type
   */
  void start() throws IOException, UnsupportedOperationException;

}
