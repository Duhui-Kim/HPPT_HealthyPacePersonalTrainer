<template>
  <div>
    <!-- 검색버튼들 -->
    <section class="search-container">
      <div class="search-container-button btn-1" @click="searchVideos('추천')">
        추천
      </div>
      <div class="search-container-button btn-2" @click="searchVideos('상체')">
        상체
      </div>
      <div class="search-container-button btn-3" @click="searchVideos('하체')">
        하체
      </div>
      <div class="search-container-button btn-4" @click="searchVideos('코어')">
        코어
      </div>
      <div
        class="search-container-button btn-5"
        @click="searchVideos('유산소')"
      >
        유산소
      </div>
    </section>

    <article>
      <!-- 영상 들어갈 영역-->
      <div class="container">
        <div
          v-for="video in videos"
          :key="video.videoId"
          class="video"
          @click="setVideo(video.videoId)"
        >
          <img
            :src="`https://img.youtube.com/vi/${video.videoId}/sddefault.jpg`"
            alt="썸네일"
          />

          <div class="video-title">
            {{ video.title }}
          </div>
        </div>
      </div>

      <!-- 모달 -->

      <div class="modal-container" v-if="viewModal">
        <div class="modal-content">
          <div class="modal-video">
            <iframe
              class="modal-video-detail"
              :src="`https://www.youtube.com/embed/${selectedVideo.videoId}`"
              :key="selectedVideo"
              width="560"
              height="315"
              frameborder="0"
              allowfullscreen
            ></iframe>
          </div>
          <button class="btn i1 close-modal-btn" @click="closeModal">
            닫기
          </button>
        </div>
      </div>
    </article>
  </div>
</template>

<script>
export default {
  data() {
    return {
      viewModal: false,
    };
  },
  computed: {
    videos() {
      return this.$store.state.hwi.videos;
    },
    selectedVideo() {
      return this.$store.state.hwi.video;
    },
  },

  methods: {
    searchVideos(query) {
      if (query === "추천") {
        query = this.$store.state.hui.user.exerciseType;
      }
      this.$store.dispatch("searchVideos", query);
    },
    closeModal() {
      this.viewModal = false;
    },
    setVideo(videoId) {
      this.$store.dispatch("getVideo", videoId).then(() => {
        this.viewModal = true;
      });
    },
  },

  mounted() {
    this.searchVideos("추천");
  },
};
</script>

<style scoped>
body {
  margin: 0;
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.25rem;
  border: 1px solid black;
  background-color: #ffffff;
}

.search-container {
  display: grid;
  grid-template-columns: repeat(5, 7rem);
  grid-template-rows: repeat(1, 3rem);
  justify-content: center;
  grid-gap: 3rem;
  margin: 4rem 1rem 3rem 1rem;
}

.search-container-button {
  border-radius: 40px;
  text-align: center;
  padding: 0.6rem;
  font-size: large;
  font-weight: bold;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
}

.btn-1 {
  background-color: #edadad;
  transition: 0.3s ease;
}
.btn-2 {
  background-color: #f6e4a3;
  transition: 0.3s ease;
}
.btn-3 {
  background-color: #97dbe4;
  transition: 0.3s ease;
}
.btn-4 {
  background-color: #daa8f9;
  transition: 0.3s ease;
}
.btn-5 {
  background-color: #e8c7b0;
  transition: 0.3s ease;
}
.btn-1:hover {
  cursor: pointer;
  background-color: #ee5252;
  color: #ffffff;
}
.btn-2:hover {
  cursor: pointer;
  background-color: #e4b407;
  color: #ffffff;
}
.btn-3:hover {
  cursor: pointer;
  background-color: #13cbe4;
  color: #ffffff;
}
.btn-4:hover {
  cursor: pointer;
  background-color: #9e38dd;
  color: #ffffff;
}
.btn-5:hover {
  cursor: pointer;
  background-color: #e47a2e;
  color: #ffffff;
}

.container {
  display: grid;
  grid-template-columns: repeat(2, 40rem);
  grid-gap: 5rem;
  justify-content: center;
  align-items: center;
  margin-bottom: 5rem;
}

.video {
  /* border: 2px solid rgb(0, 0, 0); */
  border-radius: 10px;
  width: 100%;
  height: 30rem;
  transition: 0.4s ease;
  box-shadow: 2px 1px 4px rgba(134, 134, 134, 1);
  display: grid;
  grid-template-rows: repeat(8, 5.2rem);
  opacity: 0.7;
  background-color: white !important;
}

.video:hover {
  transform: scale(1.05);
  cursor: pointer;
  opacity: 1;
}

.video > img {
  grid-row: 1 / span 5;
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px;
}

.video-title {
  text-align: center;
  padding: 1rem;
  font-size: x-large;
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 모달 */
/* 모달 내용 */
.modal-content {
  background-color: #ffd4d4;
  padding: 2rem;
  border-radius: 5px;
  width: 50rem;
  height: 30rem;

  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.527);

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 9999;
}

.modal-content > * {
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  border: 3px solid rgb(255, 255, 255);
}

.btn {
  width: 6rem;
  height: 2.5rem;
  border-radius: 10px;
  border: 2px;
  padding: 0.4rem 0;
}

.btn:hover {
  cursor: pointer;
}

.close-modal-btn:hover {
  color: #ffffff;
  background-color: #6e6e6e;
}

/* 모달 비디오 */
.modal-video {
  border: 2px solid rgba(134, 134, 134, 1);
  border-radius: 10px;
  width: 45rem;
  height: 30rem;
  transition: 0.4s ease;
  box-shadow: 0 2px 4px rgba(134, 134, 134, 1);
  margin-bottom: 2rem;
}

.modal-video img {
  width: 100%;
  height: auto;
  object-fit: cover;
  border-radius: 10px;
}

.modal-video-detail {
  width: 100%;
  height: 100%;
}
</style>
