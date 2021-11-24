package org.start.beans.correction;

import org.start.entity.Document;

import java.time.ZonedDateTime;

public class CorrectionData {

    public long id;
    public ZonedDateTime date;
    public String code;
    public String name;
    public boolean type;

    public Document doc;


}
