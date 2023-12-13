package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryServiceImpl categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryServiceImpl categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(Product product) {

        Category category = categoryService.findByName(product.getCategory().getName());
        Optional<Product> queryProduct = productRepository.findByName(product.getName());

        if (queryProduct.isPresent()) {
            throw new EntityAlreadyExistsException("Produto já existe!");
        }

        product.setCategory(category);
        Product productSaved = productRepository.save(product);
        ProductDTO productDTO = convertProductToProductDTO(product);
        productDTO.setId(productSaved.getId());
        return productDTO;
    }

    @Override
    public ProductDTO findById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        }

        ProductDTO productDTO = convertProductToProductDTO(product.get());
        productDTO.setId(product.get().getId());
        return productDTO;
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {

        Optional<Product> productQuery = productRepository.findById(product.getId());

        if (productQuery.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        }

        productRepository.save(product);
        return product;
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        Optional<Product> productQuery = productRepository.findById(id);

        if (productQuery.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        }

        productRepository.deleteById(id);
    }

    @Override
    public Product findByName(String name) {

        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado. Nome: " + name);
        }

        return product.get();
    }

    @Override
    public List<ProductDTO> findAll() {

        List<Product> products = productRepository.findAll();

        if(products.isEmpty()) {
            throw new EntityNotFoundException("Lista vazia!");
        }

        List<ProductDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = convertProductToProductDTO(product);
            productDTO.setId(product.getId());
            listDTO.add(productDTO);
        }
        return listDTO;
    }

    @Override
    @Transactional
    public String adjustStockQuantity(String productName, int newQuantity) {

        Optional<Product> productQuery = productRepository.findByName(productName);

        if (productQuery.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado - " + productName) ;
        }
        if (newQuantity < 0) {
            throw new EntityInvalidDataException("Quantidade inválida!");
        }

        productQuery.get().setQuantity(newQuantity);
        updateProduct(productQuery.get());
        return "Quantidade em estoque atualizada!";
    }
    
    public void returnQuantityInStockAfterCanceledOrder(List<OrderItem> list) {

         for(OrderItem item : list) {
            Optional<Product> product = productRepository.findByName(item.getProductName());
                if (product.isEmpty()) {
                    throw new EntityNotFoundException("Produto não encontrado - " + item.getProductName());
                }
                product.get().setQuantity(product.get().getQuantity() + item.getQuantity());
                productRepository.save(product.get());
        }
    }

    @Override
    public int checkQuantityStockAvailability(String name) {

        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto no estoque com o nome: " + name);
        }

        return product.get().getQuantity();
    }

    @Override
    @Transactional
    public String setPrice(Long id, BigDecimal newPrice) {

        Optional<Product> productQuery = productRepository.findById(id);

        if (productQuery.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        }
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EntityInvalidDataException("Novo preço inválido!");
        }

        productQuery.get().setPrice(newPrice);
        updateProduct(productQuery.get());
        return "Preço atualizado!";
    }

    public ProductDTO convertProductToProductDTO(Product product) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryName(product.getCategory().getName());
        return productDTO;
    }
}
