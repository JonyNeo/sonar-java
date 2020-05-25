package checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.withinPercentage;
import static org.junit.Assert.*;
class AssertionArgumentOrderCheck {
  static final String CONSTANT = "";
  void junit() {
    assertEquals(0, new AssertionArgumentOrderCheck().actual());
    assertEquals(new AssertionArgumentOrderCheck().actual(), 0); // Noncompliant [[sc=62;ec=63;secondary=10]]
    assertEquals("message", new AssertionArgumentOrderCheck().actual(), 0); // Noncompliant
    assertEquals("message", 0, new AssertionArgumentOrderCheck().actual());
    assertEquals("message", "constantString", actualObject());
    assertEquals("message", actualObject(), "constantString"); // Noncompliant
    assertSame(0, new AssertionArgumentOrderCheck().actual());
    assertSame(new AssertionArgumentOrderCheck().actual(), 0); // Noncompliant
    assertNotSame(new AssertionArgumentOrderCheck().actual(), 0); // Noncompliant
    assertNotSame("message", new AssertionArgumentOrderCheck().actual(), 0); // Noncompliant

    //assert equals with double/float and delta
    assertEquals("message", 0d, actualDouble(), 1d);
    assertEquals(0d, actualDouble(), 1d);
    assertEquals(actualDouble(), 0.0d, 1d); // Noncompliant {{Swap these 2 arguments so they are in the correct order: expected value, actual value.}}
    assertEquals("message", actualDouble(), 0.0d, 1d); // Noncompliant
    assertEquals(actualDouble(), 0.0d); // Noncompliant

    assertEquals("message", 0f, actualFloat(), 1f);
    assertEquals(0f, actualFloat(), 1f);
    assertEquals(actualFloat(), 0.0f, 1f); // Noncompliant {{Swap these 2 arguments so they are in the correct order: expected value, actual value.}}
    assertEquals("message", actualFloat(), 0.0f, 1f); // Noncompliant
    assertEquals(actualFloat(), 0.0f); // Noncompliant


    assertEquals("message", actualObject(), CONSTANT); // Noncompliant
    assertEquals("message", actualObject(), AssertionArgumentOrderCheck.CONSTANT); // Noncompliant
    assertEquals("message", AssertionArgumentOrderCheck.CONSTANT, actualObject());
  }

  void assertJ() {
    assertThat(new AssertionArgumentOrderCheck().actual()).isEqualTo(0);
    assertThat(0).isEqualTo(new AssertionArgumentOrderCheck().actual()); // Noncompliant [[sc=16;ec=17]] {{Swap this expected value with the actual value.}}
    assertThat(actualObject()).isEqualTo("constantString");
    assertThat("constantString").isEqualTo(actualObject()); // Noncompliant
    assertThat(0).isLessThanOrEqualTo(actualObject()); // Noncompliant
    assertThat("constantString").as("message").isEqualTo(actualObject()); // Noncompliant
    assertThat(0.1).isCloseTo(actualObject(), withinPercentage(11)); // Noncompliant
    assertThat(actualObject()).isEqualTo(CONSTANT);
    assertThat(CONSTANT).isEqualTo(actualObject()); // Noncompliant
  }

  int actual() {
    return 0;
  }
  double actualDouble() {
    return 0;
  }
  float actualFloat() {
    return 0;
  }
  int actualObject() {
    return 0;
  }

  public void testMethod() throws Exception {
    MyBean bean = new MyBean();

    assertEquals(1, bean.getDouble(), 0);
    assertEquals("no FP here", 1, bean.getDouble(), 0);
  }

  static class MyBean {
    public double getDouble() {
      return 1;
    }
  }
}
