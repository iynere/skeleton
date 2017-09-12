package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import dao.TagDao;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final TagDao tags;

    public TagController(TagDao tags) {
        this.tags = tags;
    }

    @GET
    @Path("/tags/{tag}")
    public List<ReceiptResponse> getReceiptsByTag(@PathParam("tag") String tag) {
     List<ReceiptsRecord> receiptsRecords = tags.getAllReceiptsByTag(tag);
        return receiptsRecords.stream().map(ReceiptResponse::new).collect(toList());
    }

    //Moved to the HelloWorldController
//    @GET
//    @Path("/netid")
//    public String getNetID() {
//        return "jba68";
//    }


    @PUT
    @Path("/tags/{tag}")
    public String toggleTag(@PathParam("tag") String tagName, int receiptID) {
       if (tags.tagExists(receiptID, tagName)){
            tags.deleteTag(receiptID, tagName);
       }
       else {
          tags.insert(receiptID, tagName);
       }
        return "";
    }
}
