package app.tracker.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.tracker.dto.PurchaseDTO;
import app.tracker.dto.PurchaseRequest;
import app.tracker.entities.Product;
import app.tracker.entities.Purchase;
import app.tracker.entities.User;
import app.tracker.repositories.RepoProduct;
import app.tracker.repositories.RepoPurchase;
import app.tracker.repositories.RepoUser;
import jakarta.transaction.Transactional;

@Service
public class PurchaseService {
    @Autowired
    private RepoPurchase repoPurchase;

    @Autowired
    private RepoProduct repoProduct;

    @Autowired
    private RepoUser repoUser;

    @Transactional //si algo falla evita guardar datos a medias
    public void storeRecords(List<PurchaseRequest> records, Long userId) {
        List<Purchase> recordToSave = new ArrayList<>();
        User user = repoUser.findUserById(userId);
        for(int i = 0; i < records.size(); i++) {
            //Por cada i creamos una nueva venta 
            Purchase newPurchase = new Purchase();
            String nombre = records.get(i).getNombre();
            int cantidad = records.get(i).getCantidad();
            double precio = records.get(i).getPrecio();

            newPurchase.setFecha(LocalDate.now());
            newPurchase.setCantidad(cantidad);
            newPurchase.setPrecio(precio);

            Product product = repoProduct.findProductByNombre(nombre);
            if(product == null) {
                Product newProduct = new Product();
                newProduct.setNombre(nombre);
                product = repoProduct.save(newProduct);
            }
            
            newPurchase.setUser(user);
            newPurchase.setProduct(product);

            recordToSave.add(newPurchase);
        }
        repoPurchase.saveAll(recordToSave);
    }

    public void deletePurchase(List<Long> purchasesId) {
        repoPurchase.deleteAllById(purchasesId);    
    }

    public List<PurchaseDTO> getRecords(Long userId) {
    return repoPurchase.findPurchaseByUserId(userId).stream()
      .map(purchase -> new PurchaseDTO(
            purchase.getId(),
            purchase.getProduct().getNombre(),
            purchase.getCantidad(),
            purchase.getPrecio(),
            purchase.getFecha()
        ))
        .toList(); 
    }

    public void updatePurchase(Long purchaseId, PurchaseRequest purchaseUpdate) {
        Purchase actualPurchase = repoPurchase.findPurchaseById(purchaseId);

        if(purchaseUpdate.getCantidad() != -1) {
            actualPurchase.setCantidad(purchaseUpdate.getCantidad());
        
        }
        if(purchaseUpdate.getPrecio() != -1) {
            actualPurchase.setPrecio(purchaseUpdate.getPrecio());
        }
        if(purchaseUpdate.getNombre() != null && !purchaseUpdate.getNombre().isBlank()) {
            Product actualProduct = repoProduct.findProductByNombre(purchaseUpdate.getNombre());
            
            if(actualProduct == null) {
                Product newProduct = new Product();
                newProduct.setNombre(purchaseUpdate.getNombre());
                actualProduct = repoProduct.save(newProduct); 
            }   

            actualPurchase.setProduct(actualProduct);
            repoPurchase.save(actualPurchase);
        }
    }

}
