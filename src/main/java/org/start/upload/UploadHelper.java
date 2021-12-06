package org.start.upload;

import javax.ws.rs.core.MultivaluedMap;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadHelper {


    // Parse Content-Disposition header to get the original file name
    public String parseFileName(MultivaluedMap<String, String> headers) {

        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {

            if ((name.trim().startsWith("filename"))) {

                String[] tmp = name.split("=");

                String fileName = tmp[1].trim().replaceAll("\"", "");

                return fileName;
            }
        }
        return "randomName";
    }

    // save uploaded file to a defined location on the server
    public void saveFile(InputStream uploadedInputStream,
                         String serverLocation) {

        try {
            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public String generateUniqueName() {
        String filename = "";

        int max = 1000;
        int min = 9999;
        String rndStr = String.valueOf(min + (int) (Math.random() * ((max - min) + 1)));


        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss").format(new Date());

        filename = timeStamp + "-rnd-" + rndStr;
        return filename;
    }

}
