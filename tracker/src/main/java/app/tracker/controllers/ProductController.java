package app.tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.tracker.service.PurchaseService;
import app.tracker.exception.HttpException;
import app.tracker.helper.AuthUtils;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;

import app.tracker.dto.PurchaseDTO;
import app.tracker.dto.PurchasePayLoad;
import app.tracker.dto.PurchaseRequest;

@RestController
@RequestMapping("/api/records")
public class ProductController {
    
    @Autowired
    private PurchaseService purchaseService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<?> getProducts() {
        try {
            Long userId = AuthUtils.getCurrentUserId();
            
            List<PurchaseDTO> products = purchaseService.getRecords(userId);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.ok(products);
        } 
        catch(HttpException err) {
            return ResponseEntity.status(err.getStatus()).body(err.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> saveProducts(@RequestBody PurchasePayLoad payload) {
        try {
            Long userId = AuthUtils.getCurrentUserId();

            purchaseService.storeRecords(payload.getProductList(), userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ventas guardadas");
        }
        catch(HttpException err) {
            return ResponseEntity.status(err.getStatus()).body(err.getMessage());
        }

    }
    
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestBody List<Long> purchasesId) {
        try {
            purchaseService.deletePurchase(purchasesId);
            return ResponseEntity.status(HttpStatus.OK).body("Ventas eliminadas");
        }
        catch(HttpException err) {
            return ResponseEntity.status(err.getStatus()).body(err.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestParam Long purchaseId, @RequestBody PurchaseRequest purchaseUpdate) {
        try {
            purchaseService.updatePurchase(purchaseId, purchaseUpdate);
            return ResponseEntity.status(HttpStatus.OK).body("Ventas actualizadas");
        } 
        catch(HttpException err) {
            return ResponseEntity.status(err.getStatus()).body(err.getMessage());
        }
    }

}
