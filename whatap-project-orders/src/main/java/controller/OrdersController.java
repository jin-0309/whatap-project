package controller;

import dto.req.OrdersRequestDto;
import dto.req.OrdersUpdateRequestDto;
import dto.res.OrdersResponseDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import service.OrdersService;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersController {

    OrdersService ordersService;

    @Inject
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @POST
    @Path("")
    @Operation(summary = "주문 추가")
    @APIResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Long.class))})
    public Response orderProduct(OrdersRequestDto dto) {
        return Response.status(Status.CREATED).entity(ordersService.save(dto)).build();
    }

    @GET
    @Path("/{orders_id}")
    @Operation(summary = "주문 조회")
    @APIResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = OrdersResponseDto.class)))
    public Response getOrder(@PathParam("orders_id") Long ordersId) {
        return Response.ok(ordersService.findById(ordersId)).build();
    }

    @GET
    @Path("list")
    @Operation(summary = "주문 목록 조회")
    @APIResponse(responseCode = "200",
            content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = OrdersResponseDto.class)))
    public Response getOrders() {
        return Response.ok(ordersService.findAll()).build();
    }

    @DELETE
    @Path("/{orders_id}")
    @Operation(summary = "주문 삭제")
    @APIResponse(responseCode = "204")
    public Response deleteOrder(@PathParam("orders_id") Long ordersId) {
        ordersService.deleteById(ordersId);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PUT
    @Path("")
    @Operation(summary = "주문 변경")
    @APIResponse(responseCode = "201",
            content = @Content(schema = @Schema(implementation = Long.class)))
    public Response changeOrder(OrdersUpdateRequestDto dto) {
        return Response.status(Status.CREATED).entity(ordersService.update(dto)).build();
    }
}
