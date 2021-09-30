package view;

import static org.junit.Assert.assertTrue;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.SVGAnimationView;
import cs3500.animator.view.TextualAnimationView;
import cs3500.animator.view.ViewFactory;
import cs3500.animator.view.VisualAnimationView;
import org.junit.Test;

/**
 * Tests for {@link ViewFactory}.
 */
public class ViewFactoryTest {

  /**
   * Factory method for creating blank {@link AnimatorModel}s.
   *
   * @return a new {@link AnimatorModel}
   */
  public IAnimatorModel<IModelShape> getModel() {
    return new AnimatorModel();
  }

  @Test(expected = NullPointerException.class)
  public void testNullType() {
    Appendable out = new StringBuilder();
    ViewFactory.create(null, this.getModel(), out, 1.0);
  }

  @Test
  public void testTextual() {
    Appendable out = new StringBuilder();
    IAnimatorView view = ViewFactory.create("text", this.getModel(), out, 1.0);
    assertTrue(view instanceof TextualAnimationView);
  }

  @Test
  public void testVisual() {
    Appendable out = new StringBuilder();
    IAnimatorView view = ViewFactory.create("visual", this.getModel(), out, 1.0);
    assertTrue(view instanceof VisualAnimationView);
  }

  @Test
  public void testSvg() {
    Appendable out = new StringBuilder();
    IAnimatorView view = ViewFactory.create("svg", this.getModel(), out, 1.0);
    assertTrue(view instanceof SVGAnimationView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidType() {
    Appendable out = new StringBuilder();
    IAnimatorView view = ViewFactory.create("party", this.getModel(), out, 1.0);
  }

}
