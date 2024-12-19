package com.tsarit.service.internship.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.standard.expression.OrExpression;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.tsarit.service.internship.model.internship;
import com.tsarit.service.internship.service.internshipService;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class internshipcontroller {

	@Autowired
	private internshipService internshipService;
	
	@GetMapping(value = {"/", "/{path:[^\\.]*}"})
    public String index() {
        // Forward to index.html
        return "forward:/index.html";
    }
	
	static int saveid;
	static Long amountc;
	static String orderidc;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody internship internship){
		try {
			internshipcontroller.saveid=0;
			internship i=internshipService.save(internship);
			saveid = i.getId(); // Assuming 'getId()' returns the ID of the saved internship
	        return ResponseEntity.ok(saveid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("faild");
		}
		return null;
	}
	
//	@PostMapping("/update-amount")
//	public ResponseEntity<String> updateAmount(@RequestBody internship request) {
//	    try {
//	        internshipService.updateamount(request.getId(), request.getAmount());
//	        return ResponseEntity.ok("Amount updated successfully");
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update amount: " + e.getMessage());
//	    }
//	}
//	
	private final String razorpayKeyId = "rzp_live_oRtGw5y3RbD9MH";
    private final String razorpayKeySecret = "fVDvWMy31lr90mO29r9eeg5I";

    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> request) {
        try {
//        	int internshipId = Integer.parseInt(request.get("internshipId").toString());

        	// Initialize Razorpay client
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            
            double amountInRupees = Double.parseDouble(request.get("amount").toString());
            
            internshipcontroller.amountc=(long) amountInRupees;
            System.out.println("amountc :"+amountc);
            int amountInPaise = (int) (amountInRupees * 100);
            
            // Create Razorpay order
            Map<String, Object> orderRequest = new HashMap<>();
            orderRequest.put("amount", amountInPaise); // Ensure amount is in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("payment_capture", 1); // Auto capture

            Order order = razorpayClient.orders.create(new JSONObject(orderRequest));
            String orderId = order.get("id");
            internshipcontroller.orderidc=orderId;
            System.out.println("orderId:"+orderId);
            
//            internshipService.saveOrderDetails(internshipcontroller.saveid, orderId, amountInRupees);

            Map<String, Object> response = new HashMap<>();
            response.put("order_id", orderId);
//             System.out.println("payment");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create order: " + e.getMessage()));
        }
    }
    
    @PostMapping("/verify-payment")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> request) {
        try {
            // Extract details from request

//        	int orderId = Integer.parseInt(request.get("order_id").toString());
        	String orderId = orderidc;
            String paymentId = request.get("payment_id");
            String signature = request.get("signature");

            // Validate payment signature here (using Razorpay SDK or manually)
            if (orderId == null ) {
                throw new IllegalArgumentException("oreder is missing or invalid");
            }

            // Assuming payment is valid, update the amount in the database
            Long amount = amountc;
            if (amountc == null ) {
                throw new IllegalArgumentException("Amount is missing or invalid");
            }

            // Parse amount and handle potential NumberFormatException
             System.out.println("amount :"+amount);
//            internshipService.saveOrderDetails(internshipcontroller.saveid, orderId, amount,paymentId,signature);

            internshipService.updateAmount(internshipcontroller.saveid,orderId, amount);
            internshipcontroller.amountc=(long) 0;
            internshipcontroller.orderidc=null;
            return ResponseEntity.ok("Payment verified and amount updated successfully.");
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to verify payment: " + e.getMessage());
        }
    }
	

}
