package valeron.bondar.services;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.Vehicles;
import valeron.bondar.xml.XMLConverter;

import javax.net.ssl.*;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WheelsRangeServiceImpl implements WheelsRangeService{

    @Value("${val.bondar.server.get-vehicles-url}")
    private String getVehiclesUrl;

    private static final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
    private static final SSLContext trustAllSslContext;
    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();

    public static OkHttpClient trustAllSslClient(OkHttpClient client) {
    OkHttpClient.Builder builder = client.newBuilder();
    builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0]);
    builder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    });
    return builder.build();
}

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
