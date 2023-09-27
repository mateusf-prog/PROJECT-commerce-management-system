package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityMissingDependencyException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.exceptions.product.*;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {

        Product queryProduct = productRepository.findByName(product.getName());

        if (queryProduct != null) {
            throw new EntityAlreadyExistsException("Produto já existe!");
        }

        checkValidations(product);
        productRepository.save(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        } else {
            return product;
        }
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {

        Optional<Product> productQuery = productRepository.findById(product.getId());

        if (productQuery.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado!");
        } else {
            try {
                checkValidations(product);
                productRepository.save(product);
            } catch (Exception e) {
                throw e;
            }
        }

        return product;
    }

    @Override
    @Transactional
    public String deleteProduct(Long id) {

        Optional<Product> productQuery = productRepository.findById(id);

        if (productQuery.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        } else {
            productRepository.deleteById(id);
            return "Produto deletado com sucesso!";
        }
    }

    @Override
    public Product findByName(String name) {

        Product product = productRepository.findByName(name);

        if (product == null) {
            throw new EntityNotFoundException("Produto não encontrado!");
        } else {
            return product;
        }
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
    public String adjustStockQuantity(Long id, int quantity) {

        Optional<Product> productQuery = productRepository.findById(id);

        if (productQuery.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        } else if (quantity < 0) {
            throw new EntityInvalidDataException("Quantidade inválida!");
        } else {
            productQuery.get().setQuantity(quantity);
            updateProduct(productQuery.get());
            return "Quantidade em estoque atualizada!";
        }
    }

    @Override
    public int checkQuantityStockAvailability(String name) {

        Product product = productRepository.findByName(name);

        if (product == null) {
            throw new EntityNotFoundException("Nenhum produto encontrado com o nome especificado!");
        } else {
            return product.getQuantity();
        }
    }

    @Override
    @Transactional
    public String setPrice(Long id, BigDecimal price) {

        Optional<Product> productQuery = productRepository.findById(id);

        if (productQuery.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        } else if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EntityInvalidDataException("Preço inválido!");
        } else {
            productQuery.get().setPrice(price);
            updateProduct(productQuery.get());
            return "Preço atualizado!";
        }
    }

    @Override
    public void checkValidations(Product product) {

        if (product.getName().isBlank() || product.getName().length() < 4) {
            throw new EntityInvalidDataException("Nome inválido!");
        }
        if (product.getCategory() == null) {
            throw new EntityMissingDependencyException("A categoria do produto deve ser válida!");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new EntityInvalidDataException("O preço do produto deve ser válido!");
        }
        if (product.getQuantity() < 0) {
            throw new EntityInvalidDataException("A quantidade em estoque deve ser válida!");
        }
    }
}
