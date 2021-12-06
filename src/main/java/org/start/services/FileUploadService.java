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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.start.beans.file.FormData;
import org.start.entity.Correction;
import org.start.entity.Document;
import org.start.entity.TestData;
import org.start.upload.*;
import javax.ws.rs.core.Response.ResponseBuilder;

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

        ZonedDateTime curDateTime = null;

        String fileNameOrigin = "";
        String fileNameGenerated = "";
        String fileNameWithPath = "";
        String fileExtension = "";

        Map<String, List<InputPart>> formParts = input.getFormDataMap();

        List<InputPart> inPart = formParts.get("file"); // "file" should match the name attribute of your HTML file input
        for (InputPart inputPart : inPart) {
            try {
                // Retrieve headers, read the Content-Disposition header to obtain the original name of the file
                MultivaluedMap<String, String> headers = inputPart.getHeaders();
                fileNameOrigin = helper.parseFileName(headers);

                //Extension from ORIGINAL NAME WITHOUT "."
                int lastIndexOf = fileNameOrigin.lastIndexOf(".");
                fileExtension = fileNameOrigin.substring(lastIndexOf + 1);

                // Handle the body of that part with an InputStream
                // Закрывать стрим при ошибке надо бы
                InputStream istream = inputPart.getBody(InputStream.class, null);

                //Use generated fileName
                fileNameGenerated = helper.generateUniqueName();
                //generate date for db
                curDateTime = ZonedDateTime.now();

                fileNameWithPath = SERVER_UPLOAD_LOCATION_FOLDER + fileNameGenerated + "." + fileExtension;

                helper.saveFile(istream, fileNameWithPath);
                istream.close();
            } catch (Exception e) {
                e.printStackTrace();
                String msgOutput = "Error while uploading file ";
                return Response.status(500).entity(msgOutput).build();
            }
        }

        String msgOutput = "Successfully uploaded file " + fileNameWithPath;

        //After successful file write to disc
        //TODO нужно ловить ошибки нормально
        Document doc = new Document();

        doc.date = curDateTime;
        doc.doc_type = fileExtension;
        doc.comment = "";
        doc.original_name = fileNameOrigin;
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



    @GET
    @Path("download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFileWithGet(@PathParam("id") long correction_id) {

        Correction correction = Correction.findById(correction_id);

        //System.out.println("Download file "+file);

        File fileDownload = new File(correction.doc.path_to_file);

        ResponseBuilder response = Response.ok((Object) fileDownload);
        response.header("Content-Disposition", "attachment;filename=" + correction.doc.original_name);

        return response.build();
    }

//    @Path("download/{id}")
//    @GET
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response downloadFileByID(@PathParam("id") long correction_id) throws Exception {
//
//        Correction correction = Correction.findById(correction_id);
//
//        File file = new File(correction.doc.path_to_file);
//
//        //return Response.ok(file).build();
//
//
//        InputStream is = new FileInputStream(correction.doc.path_to_file);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int len;
//        byte[] buffer = new byte[4096];
//        while ((len = is.read(buffer, 0, buffer.length)) != -1) {
//            baos.write(buffer, 0, len);
//        }
//
//        return Response.ok(baos).build();
//    }


    @Path("test")
    @GET
    public TestData testReq() {
        System.out.println("test call");
        return new TestData("123", "qwe");
    }
}

