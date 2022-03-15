package valeron.bondar.services;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicle;
import valeron.bondar.xml.XMLConverter;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class WheelsIncreaseServiceImpl implements WheelsIncreaseService{

    @Value("${val.bondar.server.get-vehicles-url}")
    private String getVehiclesUrl;

    private static final MediaType XML
            = MediaType.get("application/xml; charset=utf-8");

    @Override
    public void increaseWheels(Integer vehicleId, Integer wheelsNumber) throws ResponseException {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(getVehiclesUrl + "/" + vehicleId)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String vehicleXml = response.body().string().replaceAll("Vehicle", "vehicle");
            Vehicle vehicle = XMLConverter.convertToJava(vehicleXml);
            vehicle.setNumberOfWheels(vehicle.getNumberOfWheels() + wheelsNumber);
            RequestBody body = RequestBody.create(XMLConverter.convert(vehicle), XML);
            Request updateRequest = new Request.Builder()
                    .url(getVehiclesUrl + "/" + vehicleId)
                    .patch(body)
                    .build();
            Response updateResponse = client.newCall(updateRequest).execute();
            updateResponse.close();
        } catch (IOException | JAXBException e) {
            throw new ResponseException(500, "Something went wrong during sending request");
        }
    }
}
