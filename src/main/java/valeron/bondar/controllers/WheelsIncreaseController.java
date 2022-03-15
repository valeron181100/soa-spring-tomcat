package valeron.bondar.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Increase;
import valeron.bondar.services.WheelsIncreaseService;

@RestController
@RequestMapping(value = "/api/add-wheels")
@AllArgsConstructor
public class WheelsIncreaseController {

    private final WheelsIncreaseService increaseService;

    @PostMapping(value = "/{vehicle-id}/number-of-wheels", consumes = MediaType.APPLICATION_XML_VALUE)
    public void increaseWheels(@PathVariable(value = "vehicle-id") Integer vehicleId, @RequestBody Increase model) throws ResponseException {
        this.increaseService.increaseWheels(vehicleId, model.getWheels());
        int k = 0;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Increase> test() {
        Increase increase = new Increase();
        increase.setWheels(3);
        return new ResponseEntity<>(increase, HttpStatus.OK);
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<String> handleException(ResponseException e) {
        return ResponseEntity.status(e.getHttpCode()).body(e.getMessage());
    }

}
