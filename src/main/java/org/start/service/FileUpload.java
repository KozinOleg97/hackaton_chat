package org.start.service;

import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.start.upload.*;

@Path("file")
public class FileUpload {
    private static final Logger LOGGER = Logger.getLogger(FileUpload.class.getName());

    @ConfigProperty(name = "local-files.location")
    String SERVER_UPLOAD_LOCATION_FOLDER;

    @Path("upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response upload(MultipartFormDataInput input) throws Exception {

        // modify to Dependence Injection
        UploadHelper helper = new UploadHelper();


        String fileName = "";
        Map<String, List<InputPart>> formParts = input.getFormDataMap();

        List<InputPart> inPart = formParts.get("file"); // "file" should match the name attribute of your HTML file input
        for (InputPart inputPart : inPart) {
            try {
                // Retrieve headers, read the Content-Disposition header to obtain the original name of the file
                MultivaluedMap<String, String> headers = inputPart.getHeaders();
                fileName = helper.parseFileName(headers);


                // Handle the body of that part with an InputStream
                InputStream istream = inputPart.getBody(InputStream.class,  null);

                fileName = SERVER_UPLOAD_LOCATION_FOLDER + fileName;

                helper.saveFile(istream, fileName);

            } catch (Exception e) {
                e.printStackTrace();
                String msgOutput = "Error while uploading file ";
                return Response.status(500).entity(msgOutput).build();
            }
        }

        String msgOutput = "Successfully uploaded file " + fileName;
        return Response.status(200).entity(msgOutput).build();
    }


    @Path("test")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String echo(@MultipartForm BinaryRequestBody input) throws Exception {


        LOGGER.info(MessageFormat.format("File path: {0}", input.file.getAbsolutePath()));
        File tempFile = new File("qwe.pdf");
        input.file.renameTo(tempFile);

        return MessageFormat.format("File path: {0}", input.file.getAbsolutePath());
    }

}
