package edu.pucmm.url.Soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class UrlWebService {
    @WebMethod
    public String helloWorld() {
        return "Hello World";
    }
}
