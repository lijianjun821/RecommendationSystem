package com.ljj.businessserverback.rest;

import com.ljj.businessserverback.http.HttpResult;
import com.ljj.businessserverback.model.domain.User;
import com.ljj.businessserverback.model.recom.Recommendation;
import com.ljj.businessserverback.model.request.*;
import com.ljj.businessserverback.service.ProductService;
import com.ljj.businessserverback.service.RatingService;
import com.ljj.businessserverback.service.RecommenderService;
import com.ljj.businessserverback.service.UserService;
import com.ljj.businessserverback.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

@RequestMapping("/rest/product")
@Controller
public class ProductRestApi {

    private static Logger logger = Logger.getLogger(ProductRestApi.class.getName());

    @Autowired
    private RecommenderService recommenderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    /**
     * 获取热门推荐
     * @return
     */
    @GetMapping(value = "/hot" )
    @ResponseBody
    public HttpResult getHotProducts(@RequestParam("num")int num) {
        List<Recommendation> recommendations = recommenderService.getHotRecommendations(new HotRecommendationRequest(num));
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("products", productService.getRecommendProducts(recommendations));
        return HttpResult.ok(map);
    }
    /**
     * 获取打分最多的商品
     * @return
     */
    @GetMapping(value = "/rate")
    @ResponseBody
    public HttpResult getRateMoreProducts(@RequestParam("num")int num) {
        List<Recommendation> recommendations = recommenderService.getRateMoreRecommendations(new RateMoreRecommendationRequest(num));
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("products", productService.getRecommendProducts(recommendations));
        return HttpResult.ok(map);
    }
    // 离线推荐
    @GetMapping(value = "/offline")
    @ResponseBody
    public HttpResult getOfflineProducts(@RequestParam("username")String username,@RequestParam("num")int num) {
        User user = userService.findByUsername(username);
        List<Recommendation> recommendations = recommenderService.getCollaborativeFilteringRecommendations(new UserRecommendationRequest(user.getUserId(), num));
//        List<Recommendation> recommendations = recommenderService.getCollaborativeFilteringRecommendations(new UserRecommendationRequest(871855, num));
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("products", productService.getRecommendProducts(recommendations));
        return HttpResult.ok(map);
    }

    // 实时推荐
    @GetMapping(value = "/stream")
    @ResponseBody
    public HttpResult getStreamProducts(@RequestParam("username")String username,@RequestParam("num")int num) {
        User user = userService.findByUsername(username);

        List<Recommendation> recommendations = recommenderService.getStreamRecommendations(new UserRecommendationRequest(user.getUserId(), num));
//        List<Recommendation> recommendations = recommenderService.getStreamRecommendations(new UserRecommendationRequest(4867, num));
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("products", productService.getRecommendProducts(recommendations));
        return HttpResult.ok(map);
    }

    @GetMapping(value = "/userRate")
    @ResponseBody
    public HttpResult rateToProduct(@RequestParam("id")int id, @RequestParam("score")Double score, @RequestParam("username")String username) {
        User user = userService.findByUsername(username);
        ProductRatingRequest request = new ProductRatingRequest(user.getUserId(), id, score);
        boolean complete = ratingService.productRating(request);
        //埋点日志
        if(complete) {
            System.out.print("=========埋点=========");
            logger.info(Constant.PRODUCT_RATING_PREFIX + ":" + user.getUserId() +"|"+ id +"|"+ request.getScore() +"|"+ System.currentTimeMillis()/1000);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("message"," 已完成评分！");
        return HttpResult.ok(map);
    }

    // 基于物品的协同过滤
    @GetMapping(value = "/itemcf")
    @ResponseBody
    public HttpResult getItemCFProducts(@RequestParam("id")int id) {
        List<Recommendation> recommendations = recommenderService.getItemCFRecommendations(new ItemCFRecommendationRequest(id));
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("products", productService.getRecommendProducts(recommendations));
        return HttpResult.ok(map);
    }

    // 基于内容的推荐
    @GetMapping(value = "/contentbased")
    @ResponseBody
    public HttpResult getContentBasedProducts(@RequestParam("id")int id) {
        List<Recommendation> recommendations = recommenderService.getContentBasedRecommendations(new ContentBasedRecommendationRequest(id));
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("products", productService.getRecommendProducts(recommendations));
        return HttpResult.ok(map);
    }
}
