<template>
  <div class="board">
    <img
      v-if="board.boardImg === null"
      src="../../../assets/NoImage.png"
      @click="moveDetail(board.boardId)"
    />
    <img
      v-else
      :src="url"
      :key="board.boardId"
      @click="moveDetail(board.boardId)"
    />
    <div class="board-title">{{ board.boardTitle }}</div>
  </div>
</template>

<script>
export default {
  name: "BoardCard",
  props: {
    board: Object,
  },
  computed: {
    url() {
      return `http://localhost:9999/board/img/${this.board.boardId}`;
    },
  },
  methods: {
    moveDetail(boardId) {
      this.$router.push(`/board/detail/${boardId}`);
    },
  },
};
</script>

<style scoped>
.board {
  /* border: 1px solid gray; */
  border-radius: 5px;
  width: 20rem;
  height: 27rem;
  transition: transform 0.4s ease;
  box-shadow: 0 2px 4px rgba(134, 134, 134, 1);
}

.board:hover {
  transform: scale(1.05);
  cursor: pointer;
}

.board > img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.board-title {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 300px;
  background-color: rgba(0, 0, 0, 0.7);
  color: #fff;
  padding: 10px;
  font-size: x-larger;
  text-align: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.board:hover .board-title {
  opacity: 1;
}
</style>
