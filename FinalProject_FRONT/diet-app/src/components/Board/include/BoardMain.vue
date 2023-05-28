<template>
  <div class="detail-container-board">
    <div class="detail-container-board-title">
      <div class="detail-container-board-title-title" v-cloak>
        {{ board.boardTitle }}
      </div>
      <!-- 좋아요 버튼 -->
      <div class="detail-container-board-title-like">
        <div
          v-if="board.updateTime !== null"
          class="detail-container-board-title-time"
          v-cloak
        >
          {{ board.updateTime }} (수정됨)
        </div>
        <div v-else class="detail-container-board-title-time" v-cloak>
          {{ board.createTime }}
        </div>
        <i
          v-if="!alreadyLiked"
          class="fa-sharp fa-regular fa-heart heart-icon"
          @click="likeBoard"
        ></i>
        <i
          v-else-if="alreadyLiked"
          class="fa-sharp fa-solid fa-heart heart-icon"
          @click="dislikeBoard"
        ></i>
      </div>
    </div>

    <!-- 게시글 내용 -->
    <div class="detail-container-board-content">
      <div v-html="board.boardContent"></div>
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
      <div class="detail-container-board-count-review" v-cloak>
        댓글 {{ reviewCnt }}개
      </div>
      <div class="detail-container-board-count-like" v-cloak>
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
        <div class="detail-container-board-review-writer" v-cloak>
          {{ review.writer }}
        </div>
        <div class="detail-container-board-review-content" v-cloak>
          {{ review.reviewContent }}
        </div>
        <button
          v-show="review.userId === user.userId"
          class="detail-container-board-review-button"
          @click="deleteReview(review.reviewId)"
        >
          삭제
        </button>
      </div>
    </div>

    <!-- 댓글 등록창 -->
    <div class="detail-container-board-review-input">
      <input
        type="text"
        v-model="reviewContent"
        ref="reviewInput"
        v-on:keyup.enter="registReview"
      />
      <button @click="registReview">등록</button>
    </div>
    <!-- 게시글 조작 버튼 -->
    <div class="detail-container-board-review-buttons">
      <button v-show="board.userId === user.userId">
        <router-link :to="{ name: 'BoardUpdate' }">수정</router-link>
      </button>
      <button
        @click="deleteBoard(board.boardId)"
        v-show="board.userId === user.userId"
      >
        삭제
      </button>
      <button for="link" class="linktolist">
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
  data() {
    return {
      reviewContent: "",
    };
  },
  computed: {
    user() {
      return this.$store.state.hui.user;
    },
    board() {
      return this.$store.state.hui.board;
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
    idols() {
      return this.$store.state.hui.idols;
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
    alreadyLiked() {
      return this.$store.state.hui.checkLiked;
    },
  },

  components: { RouterLink },
  created() {
    const currentUrl = window.location.href;
    const urlParts = currentUrl.split("/");
    const boardId = urlParts[urlParts.length - 1];

    this.$store.dispatch("checkLiked", {
      userId: this.user.userId,
      boardId: boardId,
    });
  },
  methods: {
    likeBoard() {
      const currentUrl = window.location.href;
      const urlParts = currentUrl.split("/");
      const boardId = urlParts[urlParts.length - 1];

      this.$store
        .dispatch("likeBoard", {
          userId: this.user.userId,
          boardId: boardId,
        })
        .then(() => {
          setTimeout(() => {
            this.$store.dispatch("getLikeCnt", boardId);
          }, 300);
        });
    },
    dislikeBoard() {
      const currentUrl = window.location.href;
      const urlParts = currentUrl.split("/");
      const boardId = urlParts[urlParts.length - 1];
      this.$store
        .dispatch("dislikeBoard", {
          userId: this.user.userId,
          boardId: boardId,
        })
        .then(() => {
          setTimeout(() => {
            this.$store.dispatch("getLikeCnt", boardId);
          }, 300);
        });
    },
    deleteBoard(boardId) {
      if (confirm("정말 삭제하시겠습니까?")) {
        this.$store.dispatch("deleteBoard", boardId);
        alert("게시글이 삭제되었습니다.");
        this.$router.push("/board");
      }
    },
    deleteReview(reviewId) {
      if (confirm("정말 삭제하시겠습니까?")) {
        this.$store.dispatch("deleteReview", reviewId);
      }
    },
    follow(friendId) {
      this.$store
        .dispatch("followUser", {
          userId: this.user.userId,
          friendId: friendId,
        })
        .then(() => {
          setTimeout(() => {
            this.$store.dispatch("getIdols", this.user.userId);
          }, 100);
        });
    },
    unfollow(friendId) {
      this.$store
        .dispatch("unfollowUser", {
          userId: this.user.userId,
          friendId: friendId,
        })
        .then(() => {
          setTimeout(() => {
            this.$store.dispatch("getIdols", this.user.userId);
          }, 100);
        });
    },
    registReview() {
      if (this.reviewContent.trim().length === 0) {
        alert("댓글 내용을 입력해주세요");
        this.$refs.reviewInput.focus();
      } else {
        let review = {
          userId: this.user.userId,
          boardId: this.board.boardId,
          writer: this.user.userName,
          reviewContent: this.reviewContent,
        };

        this.$store.dispatch("registReview", review).then(() => {
          setTimeout(() => {
            this.reviewContent = "";
            this.$store.dispatch("getReviews", this.board.boardId);
          }, 100);
        });
      }
    },
  },
};
</script>

<style scoped>
.heart-icon {
  position: relative;
  left: 0rem;
  top: 0.5rem;
  font-size: xx-large;
  color: #fa8585;
}
.linktolist {
  grid-column: 3 / span 1;
}
.follow {
  background-color: #ffffff;
  font-size: 1rem;
  border: 0px;
  width: 4rem;
  height: 2rem;
  color: green;
  border-radius: 5px;
  transition: 0.4s ease;
}

.follow:hover {
  cursor: pointer;
  background-color: green;
  color: #ffffff;
}

.unfollow {
  background-color: #ffffff;
  font-size: 1rem;
  border: 0px;
  width: 5rem;
  height: 2rem;
  color: rgb(240, 121, 121);
  border-radius: 5px;
  transition: 0.4s ease;
}

.unfollow:hover {
  cursor: pointer;
  background-color: rgb(240, 121, 121);
  color: #ffffff;
}

.notUsed {
  background-color: #cde990;
  font-size: 1rem;
  border: 0px;
  width: 4rem;
  height: 2rem;
  border-radius: 5px;
  box-shadow: none;
}

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
  white-space: nowrap;
  overflow-y: hidden;
  overflow-x: scroll;
  z-index: 999;
}

.detail-container-board-title-title::-webkit-scrollbar {
  height: 0.5rem; /* 스크롤 바의 너비 */
}

.detail-container-board-title-title::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.detail-container-board-title-title::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.detail-container-board-title-title::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 썸의 호버 상태 색상 */
}

.detail-container-board-title-time {
  font-size: medium;
  font-weight: bold;
}

.detail-container-board-title-like {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-end;
  margin-top: 0.3rem;
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
  overflow-y: scroll;
  max-height: 15rem;
  padding: 1rem 2rem;
}

.detail-container-board-content::-webkit-scrollbar {
  width: 0.5rem; /* 스크롤 바의 너비 */
}

.detail-container-board-content::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.detail-container-board-content::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.detail-container-board-content::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 썸의 호버 상태 색상 */
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
  overflow-x: scroll;
}

.detail-container-board-review-writer::-webkit-scrollbar {
  height: 0.5rem; /* 스크롤 바의 너비 */
}

.detail-container-board-review-writer::-webkit-scrollbar-track {
  background-color: transparent; /* 스크롤 바 트랙의 배경색 */
}

.detail-container-board-review-writer::-webkit-scrollbar-thumb {
  background-color: #b1afaf; /* 스크롤 바 썸의 색상 */
  border-radius: 5px; /* 스크롤 바 썸의 모서리 반경 */
}

.detail-container-board-review-writer::-webkit-scrollbar-thumb:hover {
  background-color: #a3a3a3; /* 스크롤 바 썸의 호버 상태 색상 */
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

.detail-container-board-review-input > button:hover {
  cursor: pointer;
  border: 2px solid #ff7777;
  background-color: #ff7777;
  color: #ffffff;
}

.detail-container-board-review-buttons {
  grid-row: 13 / span 1;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(3, 3rem);
  justify-content: end;
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

input {
  box-shadow: 1px 1px 3px rgb(201, 200, 200);
}
button {
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}
</style>
