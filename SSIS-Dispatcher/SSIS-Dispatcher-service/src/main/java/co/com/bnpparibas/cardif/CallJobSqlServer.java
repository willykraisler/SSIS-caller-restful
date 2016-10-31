package co.com.bnpparibas.cardif;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.com.bnpparibas.cardif.inputs.InCallParameters;
import co.com.bnpparibas.cardif.outputs.OutCalledResponse;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("CallJobSqlServer")
public class CallJobSqlServer {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @POST
    @Path("/byParameters")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OutCalledResponse callByParameters(InCallParameters parameters){
    	return new OutCalledResponse();
    }
    
    
    //https://www.ibm.com/developerworks/library/wa-aj-tomcat/
    //http://alvinalexander.com/web/using-curl-scripts-to-test-restful-web-services
    //http://stackoverflow.com/questions/11773846/error-415-unsupported-media-type-post-not-reaching-rest-if-json-but-it-does-if
    //https://github.com/jersey/jersey/tree/master/examples
    //https://github.com/rkazarin/sample-jersey-webapp
    
    
    
    
}
