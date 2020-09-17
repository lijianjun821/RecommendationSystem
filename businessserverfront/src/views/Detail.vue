<template>
  <div style="background:#f1f1f1;width:70%;margin:0 auto;text-align:center">
    <div style="background:#fff;width:94%;min-height:350px;padding:50px 30px">
      <div style="float:left;background:#fff;width:30%;">
        <div style="border:1px solid #d3d3d3;width: 250px; height: 300px,padding:20px 20px">
          <el-image style="" :src="product.imageUrl" :fit="fit"></el-image>
        </div>
      </div>
      <div style="float:right;width:58%;min-height:300px;border:1px solid #000;text-align:left">
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
    <!-- 标签部分 -->
    <div style="width:100%;min-height:350px;border:1px solid #000;text-align:left">
      <div style="font-size:20px;color:#000"><i class="el-icon-price-tag"></i>大家的标签</div>
      <br />
      <div>
        <el-button style="margin-right:40px;" v-for="item in product.tagSplit" :key="item">{{ item }}</el-button>
      </div>
      <br />
      <div style="font-size:20px;color:#000">相似推荐</div>
      <div style="font-size:15px;color:#000">电商推荐系统猜这些商品和 {{ product.name }} 相似, 推荐依据：基于物品的协同过滤</div>
      <br />
      <div style="font-size:20px;color:#000">相似推荐</div>
      <div style="font-size:15px;color:#000">电商推荐系统猜这些商品和 {{ product.name }} 相似, 推荐依据：基于内容相似度</div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'All',
  data() {
    return {
      fit: 'fill'
    }
  },
  props: {
    product: Object
  },
  methods: {
    //点击评分
    clickRate(item) {
      this.$emit('clickRate', item)
    }
  }
}
</script>

<style lang="scss" scoped></style>
