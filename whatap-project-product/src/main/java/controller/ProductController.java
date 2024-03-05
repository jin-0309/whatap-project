package controller;

import dto.req.ProductRequestDto;
import dto.res.ProductResponseDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
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
    @Operation(summary = "상품 조회")
    @APIResponseSchema(ProductResponseDto.class)
    public Response getProduct(@PathParam("id") Long id) {
        return Response.ok(productService.findById(id)).build();
    }

    @GET
    @Path("/get/page")
    @Operation(summary = "상품 pagination 조회")
    @APIResponse(responseCode = "200",
            content = {@Content(schema = @Schema(type = SchemaType.ARRAY, implementation = ProductResponseDto.class))})
    public Response getProductByPagination(@QueryParam("page") @DefaultValue("0") int page,
                                           @QueryParam("size") @DefaultValue("10") int size) {
        return Response.ok(productService.findPagination(page, size)).build();
    }

    @POST
    @Path("/add")
    @Operation(summary = "상품 추가")
    @APIResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Long.class))})
    public Response addProduct(ProductRequestDto dto) {
        return Response.status(Status.CREATED).entity(productService.save(dto)).build();
    }

    @PUT
    @Path("/update/{id}")
    @Operation(summary = "상품 수정")
    @APIResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Long.class))})
    public Response updateProduct(@PathParam("id") Long id, ProductRequestDto dto) {
        return Response.status(Status.CREATED).entity(productService.update(dto, id)).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Operation(summary = "상품 삭제")
    @APIResponse(responseCode = "204")
    public Response deleteProduct(@PathParam("id") Long id) {
        productService.deleteById(id);
        return Response.noContent().build();
    }
}
