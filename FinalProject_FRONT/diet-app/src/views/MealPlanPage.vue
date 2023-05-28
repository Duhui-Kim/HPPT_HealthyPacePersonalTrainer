<template>
  <div>
    <!-- 헤더와 메인화면(로그인 전후) 들어가야함 -->
    <HeaderNav></HeaderNav>

    <!-- 메인 컨텐츠 들어갈 곳 -->
    <MealPlanList></MealPlanList>

    <!-- 푸터 들어갈 곳 -->
    <TheFooter></TheFooter>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import MealPlanList from "@/components/Mealplan/MealPlanList.vue";
import TheFooter from "@/components/common/TheFooter.vue";

export default {
  name: "MealPlanView",
  computed: {},
  components: {
    HeaderNav,
    TheFooter,
    MealPlanList,
  },
};
</script>

<style></style>
