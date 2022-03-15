package valeron.bondar.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.Vehicles;
import valeron.bondar.xml.XMLConverter;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WheelsRangeServiceImpl implements WheelsRangeService{

    @Value("${val.bondar.server.get-vehicles-url}")
    private String getVehiclesUrl;

    @Override
    public Vehicles getVehiclesByNumberOfWheels(Integer startRange, Integer endRange) throws ResponseException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(this.getVehiclesUrl)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string().replaceFirst("<Vehicles><vehicle>", "<vehicles>");
            String[] parts = body.split("</vehicle>");
            String substringBody = body.substring(0, body.length() - parts[parts.length - 1].length() - 10) + parts[parts.length - 1].replaceAll("Vehicles", "vehicles");
            Vehicles vehicles = XMLConverter.convertListToJava(substringBody);
            List<Vehicle> filtered = vehicles.getVehicle().stream()
                    .filter(vehicle -> vehicle.getNumberOfWheels() >= startRange && vehicle.getNumberOfWheels() < endRange)
                    .collect(Collectors.toList());
            vehicles.setVehicle(filtered);
            return vehicles;
        } catch (IOException | JAXBException e) {
            throw new ResponseException(500, "Something went wrong during sending request");
        }
    }
}
