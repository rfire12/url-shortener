package edu.pucmm.url.Soap;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class XmlDateFormat extends XmlAdapter<String, Timestamp> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String marshal(Timestamp l) throws Exception {
        return dateFormat.format(l);
    }

    @Override
    public Timestamp unmarshal(String l) throws Exception {
        return new Timestamp(dateFormat.parse(l).getTime());
    }
}
