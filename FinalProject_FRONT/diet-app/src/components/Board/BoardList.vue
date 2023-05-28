<template>
  <div>
    <!-- 메인 컨텐츠 들어갈 곳 -->
    <main>
      <article>
        <!-- 게시글 들어갈 영역-->
        <div v-if="boardList !== '[]'" class="container">
          <BoardCard
            :board="board"
            v-for="board in boardList"
            :key="board.boardId"
          ></BoardCard>
        </div>
        <div v-else style="text-align: center">
          <h3>게시글이 없습니다</h3>
        </div>
        <!-- 페이지 번호 들어갈 영역 -->
        <div class="page_wrap">
          <div class="page_nation">
            <div
              href="#"
              v-for="page in maxPage"
              :key="page"
              :class="{ active: currentPage === page }"
              @click="setCurrentPage(page)"
            >
              {{ page }}
            </div>
          </div>
        </div>
      </article>
    </main>
  </div>
</template>

<script>
import BoardCard from "@/components/Board/include/BoardCard.vue";

export default {
  name: "BoardList",
  computed: {
    boardList() {
      return this.$store.state.hui.boardList;
    },
    maxPage() {
      return this.$store.state.hui.maxPage;
    },
    currentPage() {
      return this.$store.state.hui.currentPage;
    },
    searchFlag() {
      return this.$store.state.hui.searchFlag;
    },
  },
  props: {
    searchWord: String,
  },

  methods: {
    setCurrentPage(page) {
      if (this.searchFlag) {
        this.$store.dispatch("searchBoard", {
          word: this.searchWord,
          page: page,
        });
      } else {
        this.$store.dispatch("getBoardList", page);
      }
    },
  },
  components: {
    BoardCard,
  },
  created() {
    this.$store.dispatch("getBoardList");
  },
};
</script>

<style scoped>
.search-container {
  display: grid;
  grid-template-columns: repeat(5, 10rem);
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
}

.search-button {
  border-radius: 10px;
  background-color: #ffffff;
}

.search-button:hover {
  background-color: #ffd4d4;
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

.page_nation div {
  display: block;
  margin: 0 3px;
  float: left;
  border: 1px solid #e6e6e6;
  width: 32px;
  height: 32px;
  line-height: 28px;
  text-align: center;
  background-color: #fff;
  font-size: 15px;
  color: #999999;
  text-decoration: none;
  box-shadow: 0.5px 0.5px 2px rgba(189, 185, 185, 0.74);
  border-radius: 3px;
}
.page_nation div:hover {
  cursor: pointer;
}

.page_nation div.active {
  background-color: #cde990;
  color: #fff;
  border: 1px solid #cde990;
}
</style>
