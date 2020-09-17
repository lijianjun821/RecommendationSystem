<template>
  <el-container>
    <el-header>
      <div style="width:70%;margin:0 auto;">
        <div class="one" style="width:10%"><sapn style="float:left;font-size:20px;margin-top: 15px;cursor:pointer;" @click="toHome()">首页</sapn></div>

        <div class="one" style="width:12%">
          <el-menu mode="horizontal" @select="handleSelect" background-color="#fff" text-color="#000" active-text-color="#000">
            <el-submenu index="1">
              <template slot="title">快速链接</template>
              <el-menu-item index="1-1" @click="toHome()">首页</el-menu-item>
              <el-menu-item index="1-2">实时推荐</el-menu-item>
              <el-menu-item index="1-3">离线推荐</el-menu-item>
              <el-menu-item index="1-4">热门推荐</el-menu-item>
              <el-menu-item index="1-5">评分最多</el-menu-item>
            </el-submenu>
          </el-menu>
        </div>
        <div class="one" style="margin-top: 10px;width:30%">
          <el-input v-model="input" placeholder="Search"></el-input>
        </div>
        <div class="one" style="margin-top: 10px;width:10%"><el-button style="float:left" icon="el-icon-search"></el-button></div>

        <div class="one" style="margin-top: 10px;float:right;width:10%">
          <span style="float:left;margin-top:10px">{{ $store.state.user.username }} </span>
          <el-dropdown trigger="click" style="float:right">
            <span class="el-dropdown-link">
              <el-avatar icon="el-icon-user-solid"></el-avatar>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click="logout()"><sapn @click="logout()">退出</sapn></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </el-header>
    <el-main style="padding-top:0 "
      ><div style="background:#f1f1f1;">
        <br /><br /><br /><br />
        <part
          v-if="page === 'part'"
          :hotProductList="hotProductList"
          :offlineProductList="offlineProductList"
          :rateMoreProductList="rateMoreProductList"
          :streamProductList="streamProductList"
          @clickImage="clickImage"
          @clickRate="clickRate"
          @clickMore="clickMore"
        ></part>
        <all v-if="page === 'all'" :productList="productList" :typeName="typeName" @clickImage="clickImage" @clickRate="clickRate"></all>
        <detail v-if="page === 'detail'" :product="product" @clickRate="clickRate"></detail>
        <br /><br /></div
    ></el-main>
  </el-container>
</template>

<script>
import Part from './Part'
import All from './All'
import Detail from './Detail'
export default {
  name: 'home',
  components: { Part, All, Detail },
  data() {
    return {
      page: 'part',
      showNum: 6,
      typeName: '',
      product: {},
      productList: [],
      hotProductList: [],
      offlineProductList: [],
      rateMoreProductList: [],
      streamProductList: []
    }
  },
  methods: {
    clickImage(item) {
      debugger
      this.page = 'detail'
      this.product = item
      this.product.tagSplit = item.tags.split('|')
    },
    //点击评分
    clickRate(item) {
      console.log('评分')
      console.log(item)
      this.$http.get('/rest/product/userRate', { id: item.productId, score: item.score, username: this.$store.state.user.username }).then(res => {})

      this.$message({
        message: '恭喜您，评分成功！分数为 ' + item.score + ' 颗星',
        type: 'success'
      })
    },
    clickMore(val) {
      this.page = 'all'
      let num = 100
      if (val === 'hot') {
        this.typeName = '热门推荐'
        this.$http.get('/rest/product/hot', { num: num }).then(res => {
          this.productList = []
          this.productList = res.data.products
          this.page = 'all'
        })
      } else if (val === 'rateMore') {
        this.typeName = '评分最多'
        this.$http.get('/rest/product/rate', { num: num }).then(res => {
          this.productList = []
          this.productList = res.data.products
          this.page = 'all'
        })
      } else if (val === 'offline') {
        this.typeName = '离线推荐'
        this.$http.get('/rest/product/offline', { num: num }).then(res => {
          this.productList = []
          this.productList = res.data.products
          this.page = 'all'
        })
      } else if (val === 'stream') {
        this.productList = []
        this.typeName = '实时推荐'
        this.$http.get('/rest/product/stream', { num: num }).then(res => {
          this.productList = res.data.products
          this.page = 'all'
        })
      }
    },
    //获取热门推荐
    getHotProductList(num) {
      this.$http.get('/rest/product/hot', { num: num }).then(res => {
        this.hotProductList = res.data.products
      })
    },
    //获取离线推荐
    getOfflineProductList(num) {
      this.$http.get('/rest/product/offline', { num: num }).then(res => {
        this.offlineProductList = res.data.products
      })
    },
    //获取评分最多推荐
    getRateMoreProductList(num) {
      this.$http.get('/rest/product/rate', { num: num }).then(res => {
        this.rateMoreProductList = res.data.products
      })
    },
    //获取实时推荐
    getStreamProductList(num) {
      this.$http.get('/rest/product/stream', { num: num }).then(res => {
        this.streamProductList = res.data.products
      })
    },
    toHome() {
      this.page = 'part'
      this.getHotProductList(this.showNum)
      this.getOfflineProductList(this.showNum)
      this.getRateMoreProductList(this.showNum)
      this.getStreamProductList(this.showNum)
    },
    logout() {
      this.$router.replace('/')
    }
  },
  mounted() {
    this.toHome()
  }
}
</script>

<style scoped>
.one {
  width: 20%;

  float: left;

  box-sizing: border-box;
}
</style>
