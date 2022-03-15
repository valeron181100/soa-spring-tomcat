package valeron.bondar.services;

import valeron.bondar.exceptions.ResponseException;

public interface WheelsIncreaseService {
    void increaseWheels(Integer vehicleId, Integer wheelsNumber) throws ResponseException;
}
