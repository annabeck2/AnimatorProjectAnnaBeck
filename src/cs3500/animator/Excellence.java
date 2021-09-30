package cs3500.animator;

import cs3500.animator.controller.ControllerFactory;
import cs3500.animator.controller.IAnimatorController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.util.AnimationReader;

import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.ViewFactory;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import javax.swing.JOptionPane;

/**
 * <p>A class containing the main method to run the easy animator program from the command
 * line.</p>
 */
public class Excellence {

  /**
   * The main method from which to invoke the easy animator program from the command line.
   * @param args command line arguments
   * @throws IOException if rendering fails
   */
  public static void main(String[] args) throws IOException {
    BufferedWriter out =
        new BufferedWriter(new OutputStreamWriter(System.out)); // defaults to System.out
    Readable in = null;
    IAnimatorModel<IModelShape> model;
    IAnimatorView view;
    IAnimatorController controller;
    double tempo = 1.0; // defaults to 1.0 tick/sec

    if (args.length % 2 != 0 || args.length > 8) {
      // if uneven number of args or more than 8 args, must be invalid
      displayError("Invalid number of arguments!");
      return;
    }

    String viewType = null;
    boolean inSpecified = false;
    boolean viewSpecified = false;

    for (int i = 0; i < args.length - 1; i += 2) {
      String currCommand = args[i].toLowerCase(); // the current command
      String argToCommand = args[i + 1]; // argument to the current command

      try {
        switch (currCommand) {
          case "-in":
            in = parseInCommand(argToCommand);
            inSpecified = true;
            break;
          case "-out":
            out = parseOutCommand(argToCommand);
            break;
          case "-view":
            viewType = argToCommand.toLowerCase();
            viewSpecified = true;
            break;
          case "-speed":
            tempo = parseSpeedCommand(argToCommand);
            break;
          default:
            displayError("Unknown command type!");
        }
      } catch (IllegalArgumentException e) {
        displayError(String.format("Invalid argument. *%s*", e.getMessage()));
        return;
      }
    }

    if (!inSpecified || !viewSpecified) {
      // if no input source or view type was specified
      displayError("Input file source and view type must be specified!");
      return;
    }

    try {
      model = AnimationReader.parseFile(in, AnimatorModel.getBuilder());
    } catch (Exception e) {
      displayError(String.format("Parsing input file failed. *%s*", e.getMessage()));
      return;
    }

    try {
      view = ViewFactory.create(viewType, model, out, tempo);
      controller = ControllerFactory.create(view, model, out, tempo);
    } catch (IllegalArgumentException e) {
      // if the view type entered is invalid
      displayError(e.getMessage());
      return;
    }

    controller.start();
    out.close();
  }

  /**
   * <p>Display a popup window with the given error message.</p>
   *
   * @param message the error message to display
   */
  private static void displayError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * <p>Return an input source from which to read in instructions for building a model.</p>
   *
   * @return an input source from which to read in instructions for building a model
   * @throws IllegalArgumentException if the argument is invalid
   */
  private static Readable parseInCommand(String arg) throws IllegalArgumentException {
    Readable fileReader;
    try {
      fileReader = new FileReader(arg);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Input file not found!");
    }
    return fileReader;
  }

  /**
   * <p>Return an output source from which to output the model.</p>
   *
   * @return an output source from which to output the model
   * @throws IllegalArgumentException if the argument is invalid
   */
  private static BufferedWriter parseOutCommand(String arg) throws IllegalArgumentException {
    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(arg, true);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to create specified output file!");
    }
    return new BufferedWriter(fileWriter);
  }

  /**
   * <p>Return the tempo of the view.</p>
   *
   * @return the tempo of the view.
   * @throws IllegalArgumentException if the tempo is invalid
   */
  private static double parseSpeedCommand(String arg) throws IllegalArgumentException {
    double tempo;
    try {
      tempo = Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid tempo value!");
    }
    return tempo;
  }

}
