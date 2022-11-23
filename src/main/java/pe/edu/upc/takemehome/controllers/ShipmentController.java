package pe.edu.upc.takemehome.controllers;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.takemehome.entities.Order;
import pe.edu.upc.takemehome.entities.User;
import pe.edu.upc.takemehome.entities.Shipment;
import pe.edu.upc.takemehome.entities.State;
import pe.edu.upc.takemehome.exceptions.ResourceNotFoundException;
import pe.edu.upc.takemehome.exporters.ShipmentsExcel;
import pe.edu.upc.takemehome.repositories.OrderRepository;
import pe.edu.upc.takemehome.repositories.UserRepository;
import pe.edu.upc.takemehome.repositories.ShipmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Data
public class ShipmentController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @GetMapping("/shipments")
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();

        for (Shipment u : shipments) {
            u.setUser(null);
            u.setOrder(null);

        }
        return new ResponseEntity<List<Shipment>>(shipments, HttpStatus.OK);
    }

    @GetMapping("/shipments/users")
    public ResponseEntity<List<Shipment>> getAllShipmentsWithUser() {
        List<Shipment> shipments = shipmentRepository.findAll();

        for (Shipment s : shipments) {
            s.getUser().setShipments(null);
            s.getUser().setOrders(null);
            s.getUser().setCommentsReceived(null);
            s.getUser().setCommentsSend(null);
            s.getUser().setNotifications(null);
            s.getUser().setCommentsSupport(null);
            s.setOrder(null);
        }

        return new ResponseEntity<List<Shipment>>(shipments, HttpStatus.OK);
    }
    @GetMapping("/shipments/users/{id}/noPay")
    public ResponseEntity<List<Shipment>> getAllShipmentsWithIdUserNoPay(@PathVariable("id") Long userid) {
        List<Shipment> shipments = shipmentRepository.findAll();
        List<Shipment> filteredShipment= new ArrayList<Shipment>();
        for (Shipment s : shipments) {
            if(s.getUser().getId()==userid){
                if(s.getState()==State.Reserved) {
                    s.getUser().setShipments(null);
                    s.getUser().setOrders(null);
                    s.getUser().setCommentsReceived(null);
                    s.getUser().setCommentsSend(null);
                    s.getUser().setNotifications(null);
                    s.getUser().setCommentsSupport(null);
                    s.getOrder().setShipment(null);
                    s.getOrder().getUser().setOrders(null);
                    s.getOrder().getUser().setShipments(null);
                    s.getOrder().getUser().setCommentsReceived(null);
                    s.getOrder().getUser().setCommentsSend(null);
                    s.getOrder().getUser().setNotifications(null);
                    s.getOrder().getUser().setCommentsSupport(null);
                    filteredShipment.add(s);

                }
            }
        }

        return new ResponseEntity<List<Shipment>>(filteredShipment, HttpStatus.OK);
    }
    @GetMapping("/shipments/users/{id}/Pay")
    public ResponseEntity<List<Shipment>> getAllShipmentsWithIdUserPay(@PathVariable("id") Long userid) {
        List<Shipment> shipments = shipmentRepository.findAll();
        List<Shipment> filteredShipment= new ArrayList<Shipment>();
        for (Shipment s : shipments) {
            if(s.getUser().getId()==userid ){
                if(s.getState()==State.Shipping || s.getState()==State.Delivered) {
                    s.getUser().setShipments(null);
                    s.getUser().setOrders(null);
                    s.getUser().setCommentsReceived(null);
                    s.getUser().setCommentsSend(null);
                    s.getUser().setNotifications(null);
                    s.getOrder().setShipment(null);
                    s.getUser().setCommentsSupport(null);
                    s.getOrder().getUser().setOrders(null);
                    s.getOrder().getUser().setShipments(null);
                    s.getOrder().getUser().setCommentsReceived(null);
                    s.getOrder().getUser().setCommentsSend(null);
                    s.getOrder().getUser().setNotifications(null);
                    s.getOrder().getUser().setCommentsSupport(null);
                    filteredShipment.add(s);

                }
            }
        }

        return new ResponseEntity<List<Shipment>>(filteredShipment, HttpStatus.OK);
    }
    @GetMapping("/shipments/users/{id}/complete")
    public ResponseEntity<List<Shipment>> getAllShipmentsWithIdUserComplete(@PathVariable("id") Long userid) {
        List<Shipment> shipments = shipmentRepository.findAll();
        List<Shipment> filteredShipment= new ArrayList<Shipment>();
        for (Shipment s : shipments) {
            if(s.getUser().getId()==userid){
                if(s.getState()==State.Delivered) {
                    s.getUser().setShipments(null);
                    s.getUser().setOrders(null);
                    s.getUser().setCommentsReceived(null);
                    s.getUser().setCommentsSend(null);
                    s.getUser().setNotifications(null);
                    s.getOrder().setShipment(null);
                    s.getUser().setCommentsSupport(null);
                    s.getOrder().getUser().setOrders(null);
                    s.getOrder().getUser().setShipments(null);
                    s.getOrder().getUser().setCommentsReceived(null);
                    s.getOrder().getUser().setCommentsSend(null);
                    s.getOrder().getUser().setNotifications(null);
                    s.getOrder().getUser().setCommentsSupport(null);
                    filteredShipment.add(s);

                }
            }
        }

        return new ResponseEntity<List<Shipment>>(filteredShipment, HttpStatus.OK);
    }
    @GetMapping ("/shipments/export/excel/{id}")
    public void exportToExcel(HttpServletResponse response,@PathVariable("id") Long userid) throws IOException{
        response.setContentType("application/octet-stream");
        String headerKey ="Content-Disposition";
        String headerValue = "attachment; filename=shipment_report";
        response.setHeader(headerKey,headerValue);
        List<Shipment> shipments;
        List<Shipment> filteredShipment= new ArrayList<Shipment>();
        shipments = shipmentRepository.findAll();
        for (Shipment s : shipments) {
            if(s.getUser().getId()==userid ){
                if(s.getState()==State.Shipping || s.getState()==State.Delivered) {
                    s.getUser().setShipments(null);
                    s.getUser().setOrders(null);
                    s.getUser().setCommentsReceived(null);
                    s.getUser().setCommentsSend(null);
                    s.getUser().setNotifications(null);
                    s.getOrder().setShipment(null);
                    s.getUser().setCommentsSupport(null);
                    s.getOrder().getUser().setOrders(null);
                    s.getOrder().getUser().setShipments(null);
                    s.getOrder().getUser().setCommentsReceived(null);
                    s.getOrder().getUser().setCommentsSend(null);
                    s.getOrder().getUser().setNotifications(null);
                    s.getOrder().getUser().setCommentsSupport(null);
                    filteredShipment.add(s);
                }
            }
        }
        ShipmentsExcel shipmentsExcel = new ShipmentsExcel(filteredShipment);
        shipmentsExcel.export(response);
    }
    @GetMapping("/shipments/orders")
    public ResponseEntity<List<Shipment>> getAllShipmentsWithOrders() {
        List<Shipment> shipments = shipmentRepository.findAll();

        for (Shipment s : shipments) {
            s.getOrder().setUser(null);
            s.getOrder().setShipment(null);
            s.setUser(null);
        }

        return new ResponseEntity<List<Shipment>>(shipments, HttpStatus.OK);
    }

    @GetMapping("/shipments/orders/user")
    public ResponseEntity<List<Shipment>> getAllShipmentWithOrdersAndUser() {
        List<Shipment> shipments = shipmentRepository.findAll();
        for (Shipment o : shipments) {
            o.getOrder().setUser(null);
            o.getOrder().setShipment(null);
            o.getUser().setOrders(null);
            o.getUser().setShipments(null);
            o.getUser().setCommentsSend(null);
            o.getUser().setCommentsReceived(null);
            o.getUser().setNotifications(null);
            o.getUser().setCommentsSupport(null);
        }
        return new ResponseEntity<List<Shipment>>(shipments, HttpStatus.OK);
    }
    @GetMapping("/shipments/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable("id") Long id) {

        Shipment shipmentFound=shipmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found Shipment with id="+id));

            shipmentFound.getOrder().setUser(null);
            shipmentFound.getOrder().setShipment(null);
            shipmentFound.getUser().setOrders(null);
            shipmentFound.getUser().setShipments(null);
            shipmentFound.getUser().setCommentsReceived(null);
            shipmentFound.getUser().setCommentsSend(null);
            shipmentFound.getUser().setNotifications(null);
            shipmentFound.getUser().setCommentsSupport(null);


        return new ResponseEntity<Shipment>(shipmentFound, HttpStatus.OK);
    }






    @PostMapping("/shipments/{userid}/orders/{oid}")
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment,  @PathVariable("userid") Long userid, @PathVariable("oid") Long orderid){
        User user=userRepository.findById(userid).orElseThrow(()->new ResourceNotFoundException("Not found User with id="+userid));
        Order order= orderRepository.findById(orderid).orElseThrow(()->new ResourceNotFoundException("Not found Order with id="+orderid));
        //Shipment nShip = shipmentRepository.FindByOrder(nShip.getOrder());
        //if (nShip == null) {
        if(order.getShipment() ==null) {

            Shipment newShipment = shipmentRepository.save(new Shipment(shipment.getState(), shipment.getPayment(),
                    shipment.getPaymentDate(),
                    shipment.getArrivalDate(),
                    order,
                    user
            ));

            newShipment.setPaymentDate(null);
            newShipment.setOrder(null);
            newShipment.setUser(null);
            return new ResponseEntity<Shipment>(newShipment, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<Shipment>(order.getShipment(), HttpStatus.OK);
        }

    }

    @DeleteMapping("/shipments/{id}")
    public ResponseEntity<HttpStatus> deleteShipmentById(@PathVariable("id") Long id){
        shipmentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/shipments/{id}")
    public ResponseEntity<Shipment> stateChange(@PathVariable("id")Long id, @RequestBody Shipment shipment){

        Shipment updateShipment = shipmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found User with id="+id));

        updateShipment.setState(shipment.getState());
        updateShipment.setPaymentDate(shipment.getPaymentDate());
        Shipment updatedShipment = shipmentRepository.save(updateShipment);

        updatedShipment.setOrder(null);
        updatedShipment.getUser().setShipments(null);
        updatedShipment.getUser().setNotifications(null);
        updatedShipment.getUser().setCommentsSend(null);
        updatedShipment.getUser().setOrders(null);
        updatedShipment.getUser().setCommentsReceived(null);
        updatedShipment.getUser().setCommentsSupport(null);
        return new ResponseEntity<Shipment>(updatedShipment, HttpStatus.OK);

    }

}
