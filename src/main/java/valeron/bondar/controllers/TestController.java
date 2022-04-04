package valeron.bondar.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${test.prop}")
    private String test;

    @GetMapping("/second-service/hello")
    public ResponseEntity test() {
        return ResponseEntity.ok(test);
    }

}
