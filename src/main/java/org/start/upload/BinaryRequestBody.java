package org.start.upload;

import org.jboss.resteasy.annotations.jaxrs.FormParam;

import java.io.File;

public class BinaryRequestBody {

    @FormParam("file")
    public File file;

    @FormParam("fileName")
    public String fileName;
}
