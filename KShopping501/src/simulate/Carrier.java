package simulate;

import java.util.Random;

public final class Carrier {
  private Carrier() {}
  private static String[] carriers = { "한신택배", "CJ대안동운", "우체국택배", "FedEx", "DHL" };
  private static Random random = new Random();
  public static String getCarrier() {
    return carriers[random.nextInt(carriers.length)];
  }
}
