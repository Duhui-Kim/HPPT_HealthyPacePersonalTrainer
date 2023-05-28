<template>
  <div>
    <!-- 헤더와 메인화면(로그인 전후) 들어가야함 -->
    <HeaderNav></HeaderNav>

    <!-- 메인 컨텐츠 들어갈 곳 -->
    <main>
      <!-- 검색 버튼 -->
      <section class="search-container">
        <input
          type="text"
          class="search-input"
          ref="searchArea"
          v-model="search"
          v-on:keyup.enter="searchBoard"
        />
        <button class="search-button" @click="searchBoard">검색</button>
        <button class="registBoard" @click="registForm">게시글 등록</button>
      </section>

      <!-- List랑 Detail 나오는 곳 -->
      <RouterView :searchWord="searchWord"></RouterView>
    </main>

    <!-- 푸터 들어갈 곳 -->
    <TheFooter></TheFooter>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import TheFooter from "@/components/common/TheFooter.vue";
import { RouterView } from "vue-router";

export default {
  name: "BoardPageView",
  data() {
    return {
      search: "",
      searchFlag: false,
      searchWord: "",
    };
  },
  components: {
    HeaderNav,
    TheFooter,
    RouterView,
  },
  methods: {
    searchBoard() {
      if (this.search.trim() !== "") {
        this.$store.dispatch("searchBoard", { word: this.search, page: 1 });
        this.searchFlag = true;
        this.searchWord = this.search;
        this.search = "";
        if (this.$route.name !== "BoardList") {
          this.$router.push({ name: "BoardList" });
        }
      } else {
        alert("검색어를 입력해주세요");
        this.$refs.searchArea.focus();
      }
    },
    registForm() {
      this.$router.push({ name: "BoardRegist" });
    },
  },
};
</script>

<style scoped>
.search-container {
  display: grid;
  grid-template-columns: repeat(6, 9rem);
  grid-template-rows: repeat(1, 3rem);
  justify-content: center;
  grid-gap: 1rem;
  margin: 4rem 1rem 3rem 1rem;
}

.search-input {
  grid-column: 1 / span 4;
  border-radius: 10px;
  padding: 1rem;
  font-size: 1rem;
  background-color: #ffffffb9;
  border: #ffffffb9 1px;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}

.search-button {
  border-radius: 10px;
  background-color: #ffffffb9;
  width: 7rem;
  margin-left: 2.5rem;
  border: #ffffffb9 1px;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}

.registBoard {
  border-radius: 10px;
  background-color: #ffffffb9;
  width: 7rem;
  margin-left: 2.5rem;
  border: #ffffffb9 1px;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}

.search-button:hover {
  cursor: pointer;
  background-color: #ffd4d4;
}

.registBoard:hover {
  cursor: pointer;
  background-color: #cde990;
}

.container {
  display: grid;
  grid-template-columns: repeat(3, 20rem);
  grid-gap: 2rem;
  justify-content: center;
}
.page_wrap {
  text-align: center;
  font-size: 0;
  margin: 4rem 1rem 3rem 1rem;
}

.page_nation {
  display: inline-block;
}

.page_nation .none {
  display: none;
}

.page_nation a {
  display: block;
  margin: 0 3px;
  float: left;
  border: 1px solid #e6e6e6;
  width: 28px;
  height: 28px;
  line-height: 28px;
  text-align: center;
  background-color: #fff;
  font-size: 13px;
  color: #999999;
  text-decoration: none;
}

.page_nation a.active {
  background-color: #42454c;
  color: #fff;
  border: 1px solid #42454c;
}
</style>
