<template>
  <div>
    <div style="background:#f1f1f1;width:70%;margin:0 auto">
      <div style="text-align:left">
        <span style="font-size:30px"> <i class="el-icon-video-play"></i> {{ typeName }}：</span><br /><br /><br />
      </div>
      <div style="min-height:50px;">
        <el-row :gutter="20">
          <el-col :span="4" v-for="item in productList" :key="item.productId"
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
  name: 'All',
  data() {
    return {
      fit: 'fill'
    }
  },
  props: {
    typeName: String,
    productList: Array
  },
  methods: {
    //点击图片
    clickImage(item) {
      this.$emit('clickImage', item)
    },
    //点击评分
    clickRate(item) {
      this.$emit('clickRate', item)
    }
  }
}
</script>

<style lang="scss" scoped></style>
