package valeron.bondar.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.Vehicles;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Component
public class XMLConverter {

    @Autowired
    private static ResourceLoader resourceLoader;

    public static String convert(Vehicle product) {

        StringWriter writer = new StringWriter();

        if (product != null) {

            try {
                JAXBContext context = JAXBContext.newInstance(Vehicle.class);

                Marshaller marshaller = context.createMarshaller();

                marshaller.marshal(product, writer);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }

    public static String convert(Vehicles vehicles) {

        StringWriter writer = new StringWriter();

        if (vehicles != null) {

            try {
                JAXBContext context = JAXBContext.newInstance(Vehicles.class);

                Marshaller marshaller = context.createMarshaller();

                marshaller.marshal(vehicles, writer);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }

    public static Vehicle convertToJava(String xml) throws JAXBException {

        StringWriter writer = new StringWriter();

        Vehicle vehicle = null;

        JAXBContext context = JAXBContext.newInstance(Vehicle.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setAdapter(new NormalizedStringAdapter());
        vehicle = (Vehicle) unmarshaller.unmarshal(new StringReader(xml));

        return vehicle;
    }

    public static Vehicles convertListToJava(String xml) throws JAXBException {

        StringWriter writer = new StringWriter();

        Vehicles vehicles = null;

        JAXBContext context = JAXBContext.newInstance(Vehicles.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setAdapter(new NormalizedStringAdapter());
        vehicles = (Vehicles) unmarshaller.unmarshal(new StringReader(xml));

        return vehicles;
    }

    public static void listf(File directory, List<File> files) {

        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if(fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listf(new File(file.getAbsolutePath()), files);
                }
            }
    }


}

