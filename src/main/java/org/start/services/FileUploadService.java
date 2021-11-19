package org.start.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.start.beans.file.FormData;
import org.start.entity.Document;
import org.start.entity.TestData;
import org.start.upload.*;

@Path("api/v1/files")
@ApplicationScoped
public class FileUploadService {
    private static final Logger LOGGER = Logger.getLogger(FileUploadService.class.getName());



    @ConfigProperty(name = "local-files.location")
    String SERVER_UPLOAD_LOCATION_FOLDER;

    @Path("")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response upload(MultipartFormDataInput input) throws Exception {

        // modify to Dependence Injection
        UploadHelper helper = new UploadHelper();


        String fileName = "";
        String fileNameWithPath = "";
        Map<String, List<InputPart>> formParts = input.getFormDataMap();

        List<InputPart> inPart = formParts.get("file"); // "file" should match the name attribute of your HTML file input
        for (InputPart inputPart : inPart) {
            try {
                // Retrieve headers, read the Content-Disposition header to obtain the original name of the file
                MultivaluedMap<String, String> headers = inputPart.getHeaders();
                fileName = helper.parseFileName(headers);


                // Handle the body of that part with an InputStream
                // Закрывать стрим при ошибке надо бы
                InputStream istream = inputPart.getBody(InputStream.class, null);

                fileNameWithPath = SERVER_UPLOAD_LOCATION_FOLDER + fileName;

                helper.saveFile(istream, fileNameWithPath);
                istream.close();
            } catch (Exception e) {
                e.printStackTrace();
                String msgOutput = "Error while uploading file ";
                return Response.status(500).entity(msgOutput).build();
            }
        }

        String msgOutput = "Successfully uploaded file " + fileNameWithPath;

        //TODO нужно ловить ошибки нормально
        int lastIndexOf = fileName.lastIndexOf(".");

        String fileExtension = fileName.substring(lastIndexOf + 1);


        Document doc = new Document();

        doc.date = ZonedDateTime.now();
        doc.doc_type = fileExtension;
        doc.comment = "";
        doc.original_name = fileName;
        doc.path_to_file = fileNameWithPath;

        doc.persistAndFlush();

        return Response.ok(doc.id).build();
    }


    @Path("upload2")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String echo(@MultipartForm BinaryRequestBody input) throws Exception {


        LOGGER.info(MessageFormat.format("File path: {0}", input.file.getAbsolutePath()));
        File tempFile = new File("qwe.pdf");
        input.file.renameTo(tempFile);

        return MessageFormat.format("File path: {0}", input.file.getAbsolutePath());
    }




    @Path("test")
    @GET
    public TestData testReq() {
        System.out.println("test call");
        return new TestData("123", "qwe");
    }
}

