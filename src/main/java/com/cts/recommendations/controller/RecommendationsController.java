package com.cts.recommendations.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.recommendations.entity.ConsumerQuery;
import com.cts.recommendations.entity.Products;
import com.cts.recommendations.model.ConsumerQueryBO;
import com.cts.recommendations.model.ProductsBO;
import com.cts.recommendations.repo.ConsumerQueryRepository;
import com.cts.recommendations.repo.ProductsRepository;

@RequestMapping("/v1")
@RestController
public class RecommendationsController {

	@Autowired
	ConsumerQueryRepository  consumerQueryRepository;
	
	@Autowired
	ProductsRepository productsRepository;
	
	@GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sayHello() {
		return "Hello world!";
	}
	
	@PostMapping(path = "/saveConsumers", consumes = "application/json")
	public String saveConsumers(
			@RequestBody ConsumerQueryBO consumerQueryBO) {
		ConsumerQuery consumerQuery = new ConsumerQuery();
		consumerQuery.setBrands(consumerQueryBO.getBrands());
		consumerQuery.setColors(consumerQueryBO.getColors());
		consumerQuery.setConsumerId(consumerQueryBO.getConsumerId());
		consumerQuery.setSegments(consumerQueryBO.getSegments());
		consumerQuery.setMaxBudget(consumerQueryBO.getMaxBudget());
		consumerQueryRepository.save(consumerQuery);
		return "Consumer Query is stored in Recommendations AI!";
	}
	
	@GetMapping(value = "/retreive", produces = MediaType.APPLICATION_JSON_VALUE)
	public ConsumerQueryBO getConsumerQuery(@RequestParam String consumerId) throws Exception {
		ConsumerQuery consumerQuery = consumerQueryRepository.findByConsumerID(consumerId);
		if(consumerQuery==null)
			throw new Exception("Customer id not found");
		ConsumerQueryBO consumerQueryBO = new ConsumerQueryBO();
		consumerQueryBO.setBrands(consumerQuery.getBrands());
		consumerQueryBO.setColors(consumerQuery.getColors());
		consumerQueryBO.setConsumerId(consumerQuery.getConsumerId());
		consumerQueryBO.setSegments(consumerQuery.getSegments());
		consumerQueryBO.setMaxBudget(consumerQuery.getMaxBudget());
		return consumerQueryBO;
	}
	
	@PostMapping(path = "/saveProducts", consumes = "application/json")
	public String saveProducts(@RequestBody List<ProductsBO> productsBO) {
		List<Products> products = productsBO.stream().map(productBo -> {
			Products product = new Products();
			product.setBrand(productBo.getBrand());
			product.setColor(productBo.getColor());
			product.setPrice(productBo.getPrice());
			product.setProductCharacteristics(productBo.getProductCharacteristics());
			product.setProductid(productBo.getProductid());
			product.setProductName(productBo.getProductName());
			product.setSegment(productBo.getSegment());
			return product;
		}).collect(Collectors.toList());
		productsRepository.saveAll(products);
		return "Products stored in recommendations ai!";
	}
	
	@GetMapping(value = "/retreiveByBrand", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductsBO> retreiveByBrand(@RequestParam String brand) throws Exception {
		List<Products> products = productsRepository.findBybrand(brand);
		if (products == null)
			throw new Exception("Products not found for brand");
		List<ProductsBO> productsBos = products.stream().map(product -> {
			ProductsBO productBo = new ProductsBO();
			productBo.setBrand(product.getBrand());
			productBo.setColor(product.getColor());
			productBo.setPrice(product.getPrice());
			productBo.setProductCharacteristics(product.getProductCharacteristics());
			productBo.setProductid(product.getProductid());
			productBo.setProductName(product.getProductName());
			productBo.setSegment(product.getSegment());
			return productBo;
		}).collect(Collectors.toList());
		return productsBos;
	}
	
	@GetMapping(value = "/computeByConsumerId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductsBO>> computeByConsumerId(@RequestParam("consumerId") String consumerId) throws Exception {
		List<Products> products = productsRepository.findByUserPreference(consumerId);
		if (products == null)
			throw new Exception("Products not found for brand");
		List<ProductsBO> productsBos = products.stream().map(product -> {
			ProductsBO productBo = new ProductsBO();
			productBo.setBrand(product.getBrand());
			productBo.setColor(product.getColor());
			productBo.setPrice(product.getPrice());
			productBo.setProductCharacteristics(product.getProductCharacteristics());
			productBo.setProductid(product.getProductid());
			productBo.setProductName(product.getProductName());
			productBo.setSegment(product.getSegment());
			return productBo;
		}).collect(Collectors.toList());
		return  new ResponseEntity<List<ProductsBO>>(productsBos, HttpStatus.ACCEPTED);
	}
}
