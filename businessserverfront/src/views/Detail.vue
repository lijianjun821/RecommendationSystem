<template>
  <div style="background:#f1f1f1;width:70%;margin:0 auto;text-align:center">
    <div style="background:#fff;width:94%;min-height:350px;padding:50px 30px">
      <div style="float:left;background:#fff;width:30%;">
        <div style="width: 250px; height: 300px,padding:20px 20px">
          <el-image style="" :src="product.imageUrl" :fit="fit"></el-image>
        </div>
      </div>
      <div style="float:right;width:58%;min-height:300px;text-align:left">
        <div style="font-size:40px;color:#808080">
          {{ product.name }}
        </div>
        <br />
        <el-rate v-model="product.score" allow-half text-color="#ff9900" @change="clickRate(product)"> </el-rate><br />
        <div style="font-size:15px;color:red">平均评分</div>
        <div style="font-size:20px;color:#000">{{ product.score }} 颗星</div>
        <br /><br />
        <div style="font-size:15px;color:red">商品类别</div>
        <div style="font-size:20px;color:#000">{{ product.categories }}</div>
      </div>
    </div>
    <br />
    <!-- 标签部分 -->
    <div style="width:100%;min-height:350px;text-align:left">
      <div style="font-size:20px;color:#000"><i class="el-icon-price-tag"></i>大家的标签</div>
      <br />
      <div>
        <el-button style="margin-right:40px;" v-for="item in product.tagSplit" :key="item">{{ item }}</el-button>
      </div>
      <br />
      <div style="font-size:20px;color:#000">相似推荐</div>
      <div style="font-size:15px;color:#000">电商推荐系统猜这些商品和 {{ product.name }} 相似, 推荐依据：基于物品的协同过滤</div>
      <br />
      <!-- 图片 -->
      <div style="min-height:50px;">
        <el-row :gutter="20">
          <el-col :span="4" v-for="item in itemCFProductList" :key="item.productId"
            ><div class="grid-content bg-purple" style="cursor:pointer;" @click="clickImage(item)">
              <div style="width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">{{ item.name }}</div>
              <el-image style="width: 150px; height: 180px" :src="item.imageUrl" :fit="fit"></el-image>
            </div>
            <el-rate v-model="item.score" allow-half text-color="#ff9900" @change="clickRate(item)"> </el-rate
          ></el-col>
        </el-row>
      </div>
      <br />
      <div style="font-size:20px;color:#000">相似推荐</div>
      <div style="font-size:15px;color:#000">电商推荐系统猜这些商品和 {{ product.name }} 相似, 推荐依据：基于内容相似度</div>
      <br />
      <!-- 图片 -->
      <div style="min-height:50px;">
        <el-row :gutter="20">
          <el-col :span="4" v-for="item in contentBasedProductList" :key="item.productId"
            ><div class="grid-content bg-purple" style="cursor:pointer;" @click="clickImage(item)">
              <div style="width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">{{ item.name }}</div>
              <el-image style="width: 150px; height: 180px" :src="item.imageUrl" :fit="fit"></el-image>
            </div>
            <el-rate v-model="item.score" allow-half text-color="#ff9900" @change="clickRate(item)"> </el-rate
          ></el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Detail',
  data() {
    return {
      fit: 'fill',
      contentBasedProductList: [],
      itemCFProductList: []
    }
  },
  props: {
    product: Object
  },
  methods: {
    //点击图片
    clickImage(item) {
      this.$emit('clickImage', item)
    },
    //点击评分
    clickRate(item) {
      this.$emit('clickRate', item)
    }, //获取实时推荐
    getContentBasedProductList(id) {
      this.$http.get('/rest/product/contentbased', { id: id }).then(res => {
        this.contentBasedProductList = res.data.products
      })
    }, //获取实时推荐
    getItemCFProductList(id) {
      this.$http.get('/rest/product/itemcf', { id: id }).then(res => {
        this.itemCFProductList = res.data.products
      })
    }
  },
  mounted() {
    this.getContentBasedProductList(this.product.productId)
    this.getItemCFProductList(this.product.productId)
  },
  watch: {
    product: {
      handler(newName, oldName) {
        this.getContentBasedProductList(newName.productId)
        this.getItemCFProductList(newName.productId)
      }
    }
  }
}
</script>

<style lang="scss" scoped></style>
