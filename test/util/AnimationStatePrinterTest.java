package util;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.util.AnimationStatePrinter;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests / error checking for {@link AnimationStatePrinter}.
 */
public class AnimationStatePrinterTest {

  @Test(expected = NullPointerException.class)
  public void testNullModel() throws IOException {
    Appendable out = new StringBuilder();
    double tempo = 5.0;
    AnimationStatePrinter.print(null, out, tempo);
  }

  @Test(expected = NullPointerException.class)
  public void testNullAppendable() throws IOException {
    IAnimatorModel<IModelShape> model = new AnimatorModel();
    double tempo = 5.0;
    AnimationStatePrinter.print(model, null, tempo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidZeroTempo() throws IOException {
    IAnimatorModel<IModelShape> model = new AnimatorModel();
    Appendable out = new StringBuilder();
    double tempo = 0;
    AnimationStatePrinter.print(model, out, tempo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNegativeTempo() throws IOException {
    IAnimatorModel<IModelShape> model = new AnimatorModel();
    Appendable out = new StringBuilder();
    double tempo = -3;
    AnimationStatePrinter.print(model, out, tempo);
  }

}