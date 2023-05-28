<template>
  <div class="detail-container-board">
    <div class="detail-container-board-title">
      <div class="detail-container-board-title-title">
        <input
          class="input-box input-title"
          type="text"
          v-model="board.boardTitle"
        />
      </div>
    </div>

    <!-- 게시글 내용 -->
    <div class="detail-container-board-content">
      <textarea class="input-box" v-model="board.boardContent" />
    </div>

    <!-- 작성자 -->
    <div class="detail-container-board-writer">
      <p v-cloak>{{ board.boardWriter }}</p>
      <button
        class="follow"
        v-if="alreadyFollowed === 2"
        @click="follow(board.userId)"
      >
        팔로우
      </button>
      <button
        class="unfollow"
        v-else-if="alreadyFollowed === 1"
        @click="unfollow(board.userId)"
      >
        언팔로우
      </button>
      <button class="notUsed" v-else-if="alreadyFollowed === 0">&nbsp;</button>
    </div>
    <!-- 댓글 수와 좋아요 수 -->
    <div class="detail-container-board-count">
      <div class="detail-container-board-count-review">
        댓글 {{ reviewCnt }}개
      </div>
      <div class="detail-container-board-count-like">
        좋아요 {{ likeCnt }}개
      </div>
    </div>

    <!-- 댓글 목록 -->
    <div class="detail-container-board-reviews">
      <!-- 댓글 내용 -->
      <div
        class="detail-container-board-review"
        v-for="review in reviewList"
        :key="review.reviewId"
      >
        <div class="detail-container-board-review-writer">
          {{ review.writer }}
        </div>
        <div class="detail-container-board-review-content">
          {{ review.reviewContent }}
        </div>
        <button
          class="detail-container-board-review-button"
          @click="deleteReview(review.reviewId)"
        >
          삭제
        </button>
      </div>
    </div>

    <!-- 댓글 등록창 -->
    <div class="detail-container-board-review-input">
      <input type="text" disabled />
      <button disabled>등록</button>
    </div>
    <!-- 게시글 조작 버튼 -->
    <div class="detail-container-board-review-buttons">
      <button @click="saveBoard">저장</button>
      <button>
        <router-link :to="{ name: 'BoardMain' }">취소</router-link>
      </button>
      <button for="link">
        <router-link
          id="link"
          to="/board"
          style="font-weight: bold; color: black"
          >목록</router-link
        >
      </button>
    </div>
  </div>
</template>

<script>
import { RouterLink } from "vue-router";

export default {
  computed: {
    user() {
      return this.$store.state.hui.user;
    },
    board() {
      let board = this.$store.state.hui.board;

      const replaceStr = board.boardContent.replace(/<br\s*\/?>/gi, "\n");
      board.boardContent = replaceStr;

      return board;
    },
    reviewList() {
      return this.$store.state.hui.reviewList;
    },
    reviewCnt() {
      return this.reviewList.length;
    },
    likeCnt() {
      return this.$store.state.hui.likeCnt;
    },
    alreadyFollowed() {
      // 자기자신이면 0
      if (this.board.userId === this.user.userId) return 0;
      // 이미 친구이면 1
      for (let idol of this.idols) {
        if (idol.userId === this.board.userId) return 1;
      }
      // 둘 다 아니면 2
      return 2;
    },
  },
  components: { RouterLink },

  methods: {
    saveBoard() {
      if (confirm("저장하시겠습니까?")) {
        this.$store.dispatch("updateBoard", this.board);
        this.$router.push({ name: "BoardMain" });
      }
    },
  },
};
</script>

<style scoped>
.detail-container-board {
  display: grid;
  grid-template-rows: repeat(13, 2.5rem);
  gap: 1rem;
}

.detail-container-board > * {
  padding: 2rem;
}

.detail-container-board-title {
  grid-row: 1 / span 1;
  padding: 0.6rem 2rem;
  display: grid;
  grid-template-columns: repeat(3, 11rem);
  grid-gap: 1rem;
}

.detail-container-board-title-title {
  font-size: 2rem;
  font-weight: bold;
  grid-column: 1 / span 2;
}

.detail-container-board-title-title > input {
  font-size: 2rem;
  font-weight: bold;
  grid-column: 1 / span 2;
}

.detail-container-board-title-like {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.detail-container-board-title-like > font-awesome-icon {
  color: red;
  font-size: xx-large;
  transition: 0.4s ease;
}

.detail-container-board-title-like > font-awesome-icon:hover {
  cursor: pointer;
  transform: scale(1.2);
}

.detail-container-board-content {
  grid-row: 2 / span 3;
  width: 35rem;
  overflow-y: scroll;
  max-height: 14rem;
  padding: 1rem 2rem;
  overflow-x: hidden;
}

.detail-container-board-content > textarea {
  width: 34.5rem;
  height: 6rem;
  margin-bottom: 0;
}

.detail-container-board-content > textarea::-webkit-scrollbar {
  width: 0.5rem; /* 스크롤 바의 너비 */
}

.detail-container-board-content > textarea::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.detail-container-board-content > textarea::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.detail-container-board-content > textarea::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 썸의 호버 상태 색상 */
}

.detail-container-board-content::-webkit-scrollbar {
  width: 0rem; /* 스크롤 바의 너비 */
}

.detail-container-board-writer {
  display: flex;
  justify-content: space-between;
  grid-row: 5 / span 1;
  align-items: center;
  font-size: 1.3rem;
  font-weight: 700;
  background-color: #cde990;
  padding: 1.73rem 2.6rem 1.73rem 1.73rem;
}

.detail-container-board-writer > button {
  background-color: #ffffff;
  font-size: 1rem;
  border: 0px;
  width: 4rem;
  height: 2rem;
  color: green;
  border-radius: 5px;
  transition: 0.4s ease;
}

.detail-container-board-writer > button:hover {
  cursor: pointer;
  background-color: green;
  color: #ffffff;
}

.detail-container-board-count {
  display: flex;
  justify-content: space-between;
  grid-row: 6 / span 1;
  padding: 1rem 2.6rem 1rem 2rem;
}

.detail-container-board-count > * {
  font-weight: bold;
}

.detail-container-board-reviews {
  grid-row: 7 / span 4;
  overflow-y: scroll;
  max-height: 15rem;
  padding: 0.5rem 2rem 0.5rem 2rem;
}

.detail-container-board-reviews::-webkit-scrollbar {
  width: 0.5rem; /* 스크롤 바의 너비 */
}

.detail-container-board-reviews::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.detail-container-board-reviews::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.detail-container-board-reviews::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 썸의 호버 상태 색상 */
}

.detail-container-board-review {
  display: grid;
  grid-template-columns: repeat(14, 2rem);
  gap: 0.5rem;
  margin: 0.5rem 0;
}
.detail-container-board-review-writer {
  grid-column: 1 / span 3;
  font-weight: bold;
  white-space: nowrap;
}

.detail-container-board-review-content {
  grid-column: 4 / span 10;
}

.detail-container-board-review-button {
  background-color: #ffffff;
  border: 0px;
  width: 3rem;
  height: 1.5rem;
  color: red;
  transition: 0.6s ease;
  box-shadow: none;
}

.detail-container-board-review-button:hover {
  cursor: pointer;
  height: 1.5rem;
  background-color: red;
  border-radius: 5px;
  color: #ffffff;
}

.detail-container-board-review-input {
  grid-row: 12 / span 1;
  padding: 0rem 2rem 0rem 2rem;
}

.detail-container-board-review-input {
  display: grid;
  grid-template-columns: repeat(13, 2rem);
  grid-template-rows: 0;
  gap: 0.7rem;
}

.detail-container-board-review-input > input {
  grid-column: 1 / span 12;
  height: 1.5rem;
  border-radius: 5px;
  border-color: #ffd4d4;
}

.detail-container-board-review-input > button {
  grid-column: 13 / span 1;
  width: 3rem;
  height: 1.8rem;
  border: 2px solid #ffd4d4;
  border-radius: 5px;
  background-color: #ffd4d4;
  transition: 0.4s ease;
}

.detail-container-board-review-buttons {
  grid-row: 13 / span 1;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(3, 3rem);
  justify-content: flex-end;
  gap: 1.5rem;
  margin-right: 3.5rem;
}

.detail-container-board-review-buttons > button {
  font-size: 1rem;
  border: 0px;
  width: 4rem;
  height: 2rem;
  color: rgb(189, 25, 25);
  border-radius: 5px;
  transition: 0.4s ease;
}
.detail-container-board-review-buttons > button:hover {
  cursor: pointer;
  border: 2px solid #ff7777;
  background-color: #ff7777;
  color: #ffffff;
}

.input-box {
  border: 1px solid white;
  border-radius: 7px;
  height: 1.2rem;
  box-shadow: 1px 1px 3px rgb(201, 200, 200);
  padding: 0.5rem;
  /* width: 5rem; */
}

.input-title {
  padding: 0.9rem 0.7rem;
}

input {
  box-shadow: 1px 1px 3px rgb(201, 200, 200);
}
button {
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}

.notUsed {
  background-color: #cde990 !important;
  font-size: 1rem;
  border: 0px;
  width: 4rem;
  height: 2rem;
  border-radius: 5px;
  box-shadow: none;
}
</style>
