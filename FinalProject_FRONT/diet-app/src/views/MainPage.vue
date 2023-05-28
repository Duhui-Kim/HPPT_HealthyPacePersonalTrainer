<template>
  <div>
    <!-- 헤더와 메인화면(로그인 전후) 들어가야함 -->
    <HeaderNav></HeaderNav>
    <!-- 
      JWT 유무에 따라 로그인 체크 바뀜 
      -> 로그아웃 시 또는 요청이 deny될 때 JWT null로 바꿔주기
    -->
    <after-login v-if="LoginCheck"></after-login>
    <before-login v-else></before-login>
    <!-- 푸터 들어갈 곳 -->
    <TheFooter></TheFooter>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import TheFooter from "@/components/common/TheFooter.vue";
import AfterLogin from "../components/Main/AfterLogin.vue";
import BeforeLogin from "../components/Main/BeforeLogin.vue";

export default {
  name: "MainPageView",
  computed: {
    LoginCheck() {
      let JWT = this.$store.state.hui.JWT;

      if (JWT == null) {
        return false;
      } else {
        return true;
      }
    },
  },
  components: {
    AfterLogin,
    BeforeLogin,
    HeaderNav,
    TheFooter,
  },
};
</script>

<style></style>
