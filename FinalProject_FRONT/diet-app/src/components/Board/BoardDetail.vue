import { RouterView } from 'vue-router';

<template>
  <main>
    <article class="detail">
      <!-- 게시글 상세 영역-->
      <div class="detail-container">
        <!-- 좌측 이미지 자리 -->

        <img
          v-if="board.boardImg === null"
          class="detail-container-img"
          src="../../assets/NoImage.png"
        />
        <img v-else class="detail-container-img" :src="url" />
        <!-- 우측 게시글 자리 -->
        <RouterView></RouterView>
      </div>
    </article>
  </main>
</template>

<script>
export default {
  name: "BoardDetail",

  computed: {
    user() {
      return this.$store.state.hui.user;
    },
    board() {
      return this.$store.state.hui.board;
    },
    url() {
      return `http://localhost:9999/board/img/${this.board.boardId}`;
    },
  },
  methods: {
    findBoardId() {
      const currentUrl = window.location.href;
      const urlParts = currentUrl.split("/");
      return urlParts[urlParts.length - 1];
    },
  },
  created() {
    const boardId = this.findBoardId();
    this.$store.dispatch("getBoard", boardId);
    this.$store.dispatch("getReviews", boardId);
    this.$store.dispatch("getLikeCnt", boardId);
    this.$store.dispatch("getIdols", this.user.userId);
  },
};
</script>

<style scoped>
.detail {
  display: flex;
  justify-content: center;
  margin-bottom: 6rem;
}

.detail-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  /* border: 1px solid black;
   */
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);

  border-radius: 30px;
  width: 80rem;
  height: 45rem;
  overflow: hidden;
}

.detail-container-img {
  width: 100%;
  height: 100%;
  object-fit: fill;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}
</style>
