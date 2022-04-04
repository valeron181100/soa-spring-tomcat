package valeron.bondar.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicles;
import valeron.bondar.services.WheelsRangeService;

@RestController
@RequestMapping(value = "/second-service/api/search/by-number-of-wheels")
@AllArgsConstructor
public class WheelsRangeController {

    private final WheelsRangeService rangeService;

    @GetMapping(value = "/{from}/{to}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Vehicles> getVehiclesByNumberOfWheels(@PathVariable(value = "from") String startRangeNumber,
                                                                @PathVariable(value = "to") String endRangeNumber) throws ResponseException {
        try {
            Integer startRange = Integer.parseInt(startRangeNumber);
            Integer endRange = Integer.parseInt(endRangeNumber);
            Vehicles vehiclesByNumberOfWheels = this.rangeService.getVehiclesByNumberOfWheels(startRange, endRange);
            return new ResponseEntity<>(vehiclesByNumberOfWheels, HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new ResponseException(400, "Invalid parameters they should be numbers");
        }
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity handleException(ResponseException e) {
        return ResponseEntity.status(e.getHttpCode()).body(e.getMessage());
    }
}
