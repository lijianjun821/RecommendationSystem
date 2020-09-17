package com.ljj.businessserverback.service;

import com.ljj.businessserverback.model.domain.AverageProducts;
import com.ljj.businessserverback.model.domain.Product;
import com.ljj.businessserverback.model.recom.Recommendation;
import com.ljj.businessserverback.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> getRecommendProducts(List<Recommendation> recommendations){
        List<Integer> ids = new ArrayList<>();
        for (Recommendation rec: recommendations) {
            ids.add(rec.getProductId());
        }
        return getProducts(ids);
    }

    private List<Product> getProducts(List<Integer> productIds){
        Query query = new Query();
        Criteria criteria =new Criteria();
        criteria.and("productId").in(productIds);
        query.addCriteria(criteria);
        List<Product> products = mongoTemplate.find(query, Product.class, Constant.MONGODB_PRODUCT_COLLECTION);
        documentToProduct(products);
        return products;
    }
    private void documentToProduct(List<Product> products){
        for(int i =0 ;i<products.size();i++){
            Query query = new Query();
            Criteria criteria =new Criteria();
            criteria.and("productId").in(products.get(i).getProductId());
            query.addCriteria(criteria);
            List<AverageProducts> averageProducts = mongoTemplate.find(query, AverageProducts.class, Constant.MONGODB_AVERAGE_PRODUCTS_SCORE_COLLECTION);

            if(averageProducts!=null && averageProducts.size()>0){
                products.get(i).setScore(averageProducts.get(0).getAvg());
            }
        }
    }

}
