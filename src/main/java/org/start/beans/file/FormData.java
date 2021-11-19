package org.start.beans.file;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class FormData {

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String description;

    @RestForm("image")
    public FileUpload file;
}