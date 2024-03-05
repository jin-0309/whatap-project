package controller;

import dto.req.ProductRequestDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.ProductService;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    ProductService productService;

    @Inject
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Path("/get/{id}")
    public Response getProduct(@PathParam("id") Long id) {
        return Response.ok(productService.findById(id)).build();
    }

    @GET
    @Path("/get/page")
    public Response getProductByPagination(@QueryParam("page") @DefaultValue("0") int page,
                                           @QueryParam("size") @DefaultValue("10") int size) {
        return Response.ok(productService.findPagination(page, size)).build();
    }

    @POST
    @Path("/add")
    public Response addProduct(ProductRequestDto dto) {
        return Response.ok(productService.addProduct(dto)).build();
    }
}
