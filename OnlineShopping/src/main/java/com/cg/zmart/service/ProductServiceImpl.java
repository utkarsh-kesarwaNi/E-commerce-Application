package com.cg.zmart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.zmart.entity.Product;
import com.cg.zmart.exception.NotFoundException;
import com.cg.zmart.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository prodRepo;

	@Override
	public Product createOrupdateProduct(Product product) throws NotFoundException {
		Optional<Product> pObj=prodRepo.findById(product.getId());
		if(pObj.isPresent()) {
			Product newProduct=pObj.get();
			newProduct.setImageUrl(product.getImageUrl());
			newProduct.setProductCategory(product.getProductCategory());
			newProduct.setProductDescription(product.getProductDescription());
			newProduct.setProductName(product.getProductName());
			newProduct.setProductQuantity(product.getProductQuantity());
			newProduct.setUnitPrice(product.getUnitPrice());
			
			newProduct=prodRepo.save(product);
			return newProduct;
		}else {
			product=prodRepo.save(product);
			return product;
		}
	}

	// FETCHING ALL PRODUCTS IN THE DB
	@Override
	public List<Product> getAllProducts() {
		return prodRepo.findAll();
	}

	//FETCH BY ID
	@Override
	public Product fetchProductById(Long id) {
		Optional<Product> product=prodRepo.findById(id);
		if(product.isPresent()) {
			return product.get();
		}else {
			throw new NotFoundException("No product exists with the given Id");
		}
	}

	@Override
	public List<Product> destroyProduct(Long id) {
		prodRepo.deleteById(id);
		return prodRepo.findAll();
	}

	@Override
	public List<Product> filterByCategory(String category) {
		return prodRepo.findByProductCategory(category);
	}

}
