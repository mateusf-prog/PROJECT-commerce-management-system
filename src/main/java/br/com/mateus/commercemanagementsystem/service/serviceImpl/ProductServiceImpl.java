package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.CategoryNotExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Product createProduct(Product product) {

        checkCategoryExists(product.getCategory());
        Optional<Product> queryProduct = productRepository.findByName(product.getName());

        if (queryProduct.isPresent()) {
            throw new EntityAlreadyExistsException("Produto já existe!");
        }

        productRepository.save(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        }
        return product;
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
    public Optional<Product> findByName(String name) {

        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        }
        return product;
    }

    @Override
    public List<Product> findAll() {

        List<Product> products = productRepository.findAll();

        if(products.isEmpty()) {
            throw new EntityNotFoundException("Lista vazia!");
        }
        return products;
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

    private void checkCategoryExists(String category) {

        List<Category> categories = categoryService.findAll();
        boolean categoryExists = false;

        for (Category item : categories) {
            if (item.getName().equals(category)) {
                categoryExists = true;
                break;
            }
        }

        if (!categoryExists) {
            throw new CategoryNotExistsException("Categoria inexistente.");
        }
    }
}
