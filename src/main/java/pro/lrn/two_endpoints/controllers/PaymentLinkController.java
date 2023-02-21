package pro.lrn.two_endpoints.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.lrn.two_endpoints.dto.PaymentLinkDTO;
import pro.lrn.two_endpoints.dto.PaymentLinkRequestDTO;
import pro.lrn.two_endpoints.service.InvalidPaymentLinkException;
import pro.lrn.two_endpoints.service.PaymentLinkService;


@RestController
public class PaymentLinkController {

    @Autowired
    private PaymentLinkService paymentLinkService;

    @PostMapping("/parse-link")
    public PaymentLinkDTO parsePaymentLink(@RequestBody PaymentLinkRequestDTO request) {
        return paymentLinkService.parsePaymentLink(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @PostMapping("/create-link")
    public ResponseEntity<String> createPaymentLink(@RequestBody PaymentLinkDTO paymentLinkDTO) throws
            InvalidPaymentLinkException {
        String link = paymentLinkService.createPaymentLink(paymentLinkDTO);
        return ResponseEntity.ok(link);
    }
}


