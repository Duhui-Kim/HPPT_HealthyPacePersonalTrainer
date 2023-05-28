<template>
  <div>
    <div>
      <div class="detail-container-board-review-writer">
        {{ review.writer }}
      </div>
      <div class="detail-container-board-review-content">
        {{ review.reviewContent }}
      </div>
      <button class="detail-container-board-review-button">삭제</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "ReviewList",
  props: {
    review: Object,
  },
};
</script>

<style>
.detail-container-board-review {
  display: grid;
  grid-template-columns: repeat(14, 2rem);
  gap: 0.5rem;
  margin: 0.5rem 0;
}

.detail-container-board-review-writer {
  grid-column: 1 / span 2;
  font-weight: bold;
}

.detail-container-board-review-content {
  grid-column: 3 / span 11;
}

.detail-container-board-review-button {
  background-color: #ffffff;
  border: 0px;
  width: 3rem;
  height: 1.5rem;
  color: red;
  transition: 0.6s ease;
}

.detail-container-board-review-button:hover {
  cursor: pointer;
  height: 1.5rem;
  background-color: red;
  border-radius: 5px;
  color: #ffffff;
}
</style>
