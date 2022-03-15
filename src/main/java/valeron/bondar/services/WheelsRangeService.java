package valeron.bondar.services;

import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.Vehicles;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface WheelsRangeService {
    Vehicles getVehiclesByNumberOfWheels(Integer startRange, Integer endRange) throws ResponseException;
}
