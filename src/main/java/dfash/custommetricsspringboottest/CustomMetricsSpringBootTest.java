package dfash.custommetricsspringboottest;

import com.fasterxml.jackson.databind.JsonNode;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.atomic.AtomicInteger;



@RestController
public class CustomMetricsSpringBootTest {

  private AtomicInteger loadMetric;
  private final Counter testCounter;

  public CustomMetricsSpringBootTest(MeterRegistry meterRegistry) {
    loadMetric = meterRegistry.gauge("app_load", new AtomicInteger(1));
    testCounter = meterRegistry.counter("custom_counter");
  }

  public static int nthFibonacciTerm(int n) {
    if (n == 1 || n == 0) {
      return n;
    }
    return nthFibonacciTerm(n-1) + nthFibonacciTerm(n-2);
  }

  @GetMapping("/")
  public String index() {
    return "Hello !!!!\n";
  }

  @PostMapping("/fibonacci")
  public String fibonacciRoute(@RequestBody JsonNode payload){
    System.out.println("fibonacci IN: "+payload);
    testCounter.increment();
    return "RESULT: " + nthFibonacciTerm(payload.get("number").asInt());
  }

  @PostMapping("/load")
  public String loadRoute(@RequestBody JsonNode payload) {
    System.out.println("load IN: "+payload);
//    System.out.println(payload.get("value").asInt());
    loadMetric.set(payload.get("value").asInt());
    return "SET";
  }

}
