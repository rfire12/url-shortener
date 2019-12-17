package edu.pucmm.url.Soap;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class XmlSqlDateFormat extends XmlAdapter<String, Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String marshal(Date d) throws Exception {
        return dateFormat.format(d);
    }

    @Override
    public Date unmarshal(String d) throws Exception {
        return new Date(dateFormat.parse(d).getDate());
    }
}
