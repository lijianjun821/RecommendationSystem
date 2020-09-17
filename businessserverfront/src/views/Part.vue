<template>
  <div>
    <!-- 实时推荐模块 -->
    <div style="background:#f1f1f1;width:70%;margin:0 auto">
      <!-- 实时推荐模块文字说明部分 -->
      <div style="text-align:left">
        <span style="font-size:30px"><i class="el-icon-video-play"></i> 实时推荐</span><br />
        <span>电商推荐系统猜你喜欢 <el-button type="text" @click="clickMore('stream')">更多。。。</el-button></span>
      </div>
      <!-- 实时推荐模块图片部分 -->
      <div style="min-height:50px;">
        <el-row :gutter="20">
          <el-col :span="4" v-for="item in streamProductList" :key="item.productId"
            ><div class="grid-content bg-purple" style="cursor:pointer;" @click="clickImage(item)">
              <div style="width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">{{ item.name }}</div>
              <el-image style="width: 150px; height: 180px" :src="item.imageUrl" :fit="fit"></el-image>
            </div>
            <el-rate v-model="item.score" allow-half text-color="#ff9900" @change="clickRate(item)"> </el-rate
          ></el-col>
        </el-row>
      </div>
    </div>
    <br /><br />
    <!-- 离线推荐模块 -->
    <div style="background:#f1f1f1; width:70%;margin:0 auto">
      <!-- 离线推荐模块文字说明部分 -->
      <div style="text-align:left">
        <span style="font-size:30px"><i class="el-icon-video-play"></i>离线推荐</span><br />
        <span>最新添加的商品 <el-button type="text" @click="clickMore('offline')">更多。。。</el-button></span>
      </div>
      <br />
      <!-- 离线推荐模块图片部分 -->
      <div style="min-height:50px;">
        <el-row :gutter="20">
          <el-col :span="4" v-for="item in offlineProductList" :key="item.productId"
            ><div class="grid-content bg-purple" style="cursor:pointer;" @click="clickImage(item)">
              <div style="width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">{{ item.name }}</div>
              <el-image style="width: 150px; height: 180px" :src="item.imageUrl" :fit="fit"></el-image>
            </div>
            <el-rate v-model="item.score" allow-half text-color="#ff9900" @change="clickRate(item)"> </el-rate
          ></el-col>
        </el-row>
      </div>
    </div>
    <br /><br />
    <!-- 热门推荐模块 -->
    <div style="background:#f1f1f1; width:70%;margin:0 auto">
      <!-- 热门推荐模块文字说明部分 -->
      <div style="text-align:left">
        <span style="font-size:30px"><i class="el-icon-video-play"></i>热门推荐</span><br />
        <span>电商推荐系统推荐了这些商品 <el-button type="text" @click="clickMore('hot')">更多。。。</el-button></span>
      </div>
      <!-- 热门推荐模块图片部分 -->
      <div style="min-height:50px;">
        <el-row :gutter="20">
          <el-col :span="4" v-for="item in hotProductList" :key="item.productId"
            ><div class="grid-content bg-purple" style="cursor:pointer;" @click="clickImage(item)">
              <div style="width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">{{ item.name }}</div>
              <el-image style="width: 150px; height: 180px" :src="item.imageUrl" :fit="fit"></el-image>
            </div>
            <el-rate v-model="item.score" allow-half text-color="#ff9900" @change="clickRate(item)"> </el-rate
          ></el-col>
        </el-row>
      </div>
    </div>
    <br /><br />
    <!-- 评分最多推荐模块 -->
    <div style="background:#f1f1f1;width:70%;margin:0 auto">
      <!-- 评分最多推荐模块文字说明部分 -->
      <div style="text-align:left">
        <span style="font-size:30px"><i class="el-icon-video-play"></i>评分最多</span><br />
        <span>为了改进推荐系统的精度，评分越多越好 <el-button type="text" @click="clickMore('rateMore')">更多。。。</el-button></span>
      </div>
      <!-- 评分最多推荐模块图片部分 -->
      <div style="min-height:50px;">
        <el-row :gutter="20">
          <el-col :span="4" v-for="item in rateMoreProductList" :key="item.productId"
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
  name: 'home',
  data() {
    return { fit: 'fill' }
  },
  props: {
    hotProductList: Array,
    offlineProductList: Array,
    rateMoreProductList: Array,
    streamProductList: Array
  },
  methods: {
    //点击图片
    clickImage(item) {
      this.$emit('clickImage', item)
    },
    //点击评分
    clickRate(item) {
      this.$emit('clickRate', item)
    },
    clickMore(val) {
      this.$emit('clickMore', val)
    }
  },
  mounted() {}
}
</script>

<style lang="scss" scoped></style>
