package com.tsarit.service.internship.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.tsarit.service.internship.model.webinar;
import com.tsarit.service.internship.service.webinarService;

import ch.qos.logback.core.status.Status;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.xhtmlrenderer.pdf.ITextRenderer;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class webinarcontroller {

	@Autowired
	private webinarService webinarService;
	static int saveid;
	static Long amountc;
	static String orderidc;

	@PostMapping("/webinarsave")
	public ResponseEntity<?> save(@RequestBody webinar webinar) {
		try {
			webinarcontroller.saveid = 0;
			webinar w = webinarService.save(webinar);
			saveid = w.getId();
			return ResponseEntity.ok("saved");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.ok("faild");
		}

	}

	@GetMapping("/findByEmail/{email}")
	public ResponseEntity<List<webinar>> getWebinarsByEmail(@PathVariable("email") String email) {
		List<webinar> webinars = webinarService.findemail(email);
		if (webinars.isEmpty()) {
			return ResponseEntity.noContent().build();

		}
		return ResponseEntity.ok(webinars);
	}

	private final String razorpayKeyId = "rzp_live_oRtGw5y3RbD9MH";
	private final String razorpayKeySecret = "fVDvWMy31lr90mO29r9eeg5I";

	@PostMapping("/create-order-webinar")
	public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> request) {
		try {
//        	int internshipId = Integer.parseInt(request.get("internshipId").toString());

			// Initialize Razorpay client
			RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

			double amountInRupees = Double.parseDouble(request.get("amount").toString());

			webinarcontroller.amountc = (long) amountInRupees;
			System.out.println("amountc :" + amountc);
			int amountInPaise = (int) (amountInRupees * 100);

			// Create Razorpay order
			Map<String, Object> orderRequest = new HashMap<>();
			orderRequest.put("amount", amountInPaise); // Ensure amount is in paise
			orderRequest.put("currency", "INR");
			orderRequest.put("payment_capture", 1); // Auto capture

			Order order = razorpayClient.orders.create(new JSONObject(orderRequest));
			String orderId = order.get("id");
			webinarcontroller.orderidc = orderId;
			System.out.println("orderId:" + orderId);

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

	@PostMapping("/verify-payment-webinar")
	public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> request) {
		try {
			// Extract details from request

//        	int orderId = Integer.parseInt(request.get("order_id").toString());
			String orderId = orderidc;
			String paymentId = request.get("payment_id");
			String signature = request.get("signature");

			// Validate payment signature here (using Razorpay SDK or manually)
			if (orderId == null) {
				throw new IllegalArgumentException("oreder is missing or invalid");
			}

			// Assuming payment is valid, update the amount in the database
			Long amount = amountc;
			if (amountc == null) {
				throw new IllegalArgumentException("Amount is missing or invalid");
			}
			LocalDate date=LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = date.format(formatter);

			// Parse amount and handle potential NumberFormatException
			System.out.println("amount :" + amount);
//             webinarService.saveOrderDetails(internshipcontroller.saveid, orderId, amount,paymentId,signature);

			webinarService.updateAmountwebinar(webinarcontroller.saveid, orderId, amount,formattedDate);
			internshipcontroller.amountc = (long) 0;
			internshipcontroller.orderidc = null;
			return ResponseEntity.ok("Payment verified and amount updated successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to verify payment: " + e.getMessage());
		}
	}

	@GetMapping("/api/generate-certificate")
    public ResponseEntity<ByteArrayResource> generateCertificate(@RequestParam String email) {
        try {
            // Find the user by email, expecting a list of webinar objects
            List<webinar> webinars = webinarService.findemail(email);
            
            // Check if the list is empty
            if (webinars == null || webinars.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Retrieve the first webinar object from the list
            webinar userWebinar = webinars.get(0); // Get the first webinar object

            // Dynamic HTML template with user name
            String name = userWebinar.getName(); // Get the user's name from the webinar object
            int id=userWebinar.getId();
            String certificateid="TIT0"+id;
            String date=userWebinar.getDate();
          

            // Get the absolute path for the certificate image
            String certificateImagePath = getClass().getClassLoader().getResource("static/certificertificate.jpg").toExternalForm();
            System.out.println("Image Path: " + certificateImagePath);
            
            // HTML for the certificate with the absolute path for the background image
            String html = "<!DOCTYPE html>" +
            	    "<html>" +
            	    "<head><title>Certificate</title>" +
            	    "<style>" +
            	    "@import url('https://fonts.googleapis.com/css2?family=Satisfy&amp;display=swap');" +
            	    "html, body { " +
            	    "margin: 0; " +
            	    "padding: 0; " +
            	    "width: 100%; " +
            	    "height: 100%; " +
            	    "background: none; " +
            	    "display: flex; " +
            	    "justify-content: center; " +
            	    "align-items: center; " +
            	    "} " +
            	    "@page { size: 525px 450px; margin: 0; } " +
            	    ".certificate-container { " +
            	    "position: relative; " +
            	    "width: 525px; " +
            	    "height: 450px; " +
            	    "margin: 0; " +
            	    "background-image: url('" + certificateImagePath + "');"+ 
            	    "background-size: cover; " +
            	    "background-position: center; " +
            	    "text-align: center; " +
            	    "display: flex; " +               // Added flexbox for centering
            	    "justify-content: center; " +      // Horizontal centering
            	    "align-items: center; " +
            	    "} " +
            	    ".user-name { " +
            	    "position: absolute; " +
            	    "top: 50%; " +
            	    "left: 40%; " +
            	    "transform: translate(-50%, -50%); " +
            	    "font-size: 25px; " +
            	    "padding-top: 10px; " +
            	    "text-align: center; " +
            	    "display: flex; " +            
            	    "justify-content: center; " + 
            	    "text-align: center; " +
            	    "color: #000; " +
            	    "font-family: 'Satisfy',cursive; " +
            	    "white-space: nowrap; "+
            	    "} " +
            	    ".date { " +
            	    "position: absolute; " +
            	    "right: 80px; " +
            	    "bottom: 115px; " +
            	    "font-size: 10px; " +
            	    "} " +
            	    ".number { " +
            	    "position: absolute; " +
            	    "left: 40px; " +
            	    "bottom: 115px; " +
            	    "font-size: 10px; " +
            	    "} " +
            	    "</style>" +
            	    "</head>" +
            	    "<body>" +
            	    "<div class='certificate-container'>" +
            	    "<div class='user-name'>" + name + "</div>" +
            	    "<div class='number'>CERTIFICATE ID :"+certificateid+"</div>" +
            	    "<div class='date'>DATE : "+date+"</div>" +
            	    "</div>" +
            	    "</body>" +
            	    "</html>";


            // Convert HTML to PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            // Set the page size based on the certificate image size, assuming it's 700x600 pixels
            // Convert pixel values to points (1 pixel = 0.75 points)
            renderer.getSharedContext().setBaseURL("file:src/main/resources/static/");
            renderer.setDocumentFromString(html);
            renderer.layout();

            // Set custom PDF page size to match the certificate image size (no margins)
            renderer.getOutputDevice();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();

            // Prepare PDF response
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(outputStream.size())
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
